package uk.ac.ed.inf.proj.xmlnormaliser.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;

/**
 * Various static methods for validation
 * 
 * @author Tomas Tauber
 * 
 */
public class XNFValidator {

	private static final Logger LOGGER = Logger.getLogger(XNFValidator.class.getName());

	private XNFValidator() {
	}

	/**
	 * Returns whether a FD is trivial
	 * @param leftHandSide
	 * @param rightHandSide
	 * @return true if trivial
	 */
	public static boolean isTrivial(FDPath leftHandSide, String rightHandSide) {
		if (rightHandSide.indexOf('.') == -1) { // root - case 1
			return true;
		}
		boolean hasAttribute = rightHandSide.contains(".@");
		String prefix = null;
		if (hasAttribute) {
			prefix = rightHandSide.split(".@")[0];
		}
		for (String path : leftHandSide) {
			if (path.equalsIgnoreCase(rightHandSide)) { // equal - case 2
				return true;
			}
			if (hasAttribute) {
				if (prefix.equalsIgnoreCase(path)) { // prefix - case 4
					return true;
				}
			} else { // prefix - case 3
				if (path.contains(".@")) {
					if (path.split(".@")[0].equalsIgnoreCase(rightHandSide)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Returns a textual representation of a tree path of an element 
	 * @param pathSoFar - accumulator of string path
	 * @param currentElement - the current element that is looked at 
	 * @param seekedElement - the element whose path has to be returned
	 * @param document - DTD
	 * @return tree path
	 */
	public static String getPath(String pathSoFar, String currentElement, String seekedElement, DTD document) {
		if (currentElement.equalsIgnoreCase(seekedElement)) { //base case if we have it
			if (pathSoFar.length() > 0) {
				return pathSoFar + "." + seekedElement;
			} else {
				return seekedElement;
			}
		} else {
			String[] children = DTDParser.getTokens(document.getElementTypeDefinition(currentElement));
			for (String child : children) {
				if (!child.equals("#PCDATA")) { // recurse only for nodes
					String path;
					if (pathSoFar.length() > 0) {
						path = getPath(pathSoFar + "." + currentElement, child.replace("*", ""), seekedElement, document);
					} else {
						path = getPath(currentElement, child.replace("*", ""), seekedElement, document);
					}
					if (path.endsWith(seekedElement)) { // found it
						return path;
					}
				}
			}
		}
		return ""; // failed
	}

	/**
	 * Returns a set of XFDs that is combination of implicit DTDs and original FDs without trivial ones
	 * @param documentStructure
	 * @param originalFD
	 * @return a new set of XFDs
	 */
	public static HashMap<FDPath, FDPath> getSigma(DTD documentStructure, HashMap<FDPath, FDPath> originalFD) {
		HashMap<FDPath, FDPath> sigma = new HashMap<FDPath, FDPath>();
		for (FDPath key : originalFD.keySet()) {
			for (String rightHandSide : originalFD.get(key)) {
				if (!isTrivial(key, rightHandSide)) {
					if (sigma.containsKey(key)) {
						sigma.get(key).add(rightHandSide);
					} else {
						sigma.put(key, new FDPath(rightHandSide));
					}
				}
			}
		}

		HashSet<String> elements = documentStructure.getElements();
		for (String element : elements) {
			String[] children = DTDParser.getTokens(documentStructure.getElementTypeDefinition(element));
			for (String child : children) {
				if (child.charAt(child.length() - 1) != '*') {
					if (!child.equalsIgnoreCase("#PCDATA")) {
						String path = getPath("", documentStructure.getRoot(), child, documentStructure);
						FDPath prefix = new FDPath(path.substring(0, path.lastIndexOf('.')));
						if (sigma.containsKey(prefix)) {
							sigma.get(prefix).add(path);
						} else {
							sigma.put(prefix, new FDPath(path));
						}
					}
				}
			}
		}

		return sigma;
	}

	/**
	 * Returns the greatest common prefix of 2 paths
	 * @param pathA
	 * @param pathB
	 * @return the greatest common prefix 
	 */
	private static String greatestCommonPrefixPath(String pathA, String pathB) {
		int minLen = Math.min(pathA.length(), pathB.length());
		int lastDot = 0;
		for (int i = 0; i < minLen; i++) {
			if (pathA.charAt(i) == '.') {
				lastDot = i;
			}
			if (pathA.charAt(i) != pathB.charAt(i)) {
				return pathA.substring(0, lastDot);
			}
		}
		return pathA.substring(0, minLen);
	}	

	/**
	 * Computes a closure of a given xfd
	 * @param leftHandSide
	 * @param rightHandSide
	 * @param xfds
	 * @return a closure (set of paths)
	 */
	public static FDPath getClosure(FDPath leftHandSide, String rightHandSide, HashMap<FDPath, FDPath> xfds, DTD documentStructure) {
		FDPath result = new FDPath();
		ArrayList<Object[]> sigma = new ArrayList<Object[]>();
		for (FDPath key : xfds.keySet()) {
			for (String rhs : xfds.get(key)) {
				sigma.add(new Object[] {key, rhs});
			}
		}
		LOGGER.debug("Adding paths from LHS");
		for (String path : leftHandSide) {
			result.add(path); //add all paths of input xfd
		}
		LOGGER.debug(result);
		LOGGER.debug("Adding root");
		result.add(rightHandSide.substring(0, rightHandSide.indexOf('.'))); //root
		LOGGER.debug(result);
		FDPath toAdd = new FDPath();
		LOGGER.debug("Adding (element) prefixes");
		for (String path : result) {
			if (!path.contains("@")) {
				if (path.lastIndexOf('.') != -1) {
					toAdd.add(path.substring(0, path.lastIndexOf('.')));
				}
			}
		}
		result.addAll(toAdd);
		LOGGER.debug(result);
		toAdd.clear();
		LOGGER.debug("Adding (attribute) prefixes");
		for (String path : result) {
			HashSet<String> attributes = documentStructure.getElementAttributes(path.substring(path.lastIndexOf('.')+1));
			if (attributes != null) {
				for (String attribute : attributes) {
					toAdd.add(path + "." + attribute);
				}
			}
		}
		result.addAll(toAdd);
		LOGGER.debug(result);
		int newLength = result.size();
		int oldLength;
		do {
			oldLength = newLength;
			for (int i = 0; i < sigma.size(); i++) {
				FDPath lhs = (FDPath) sigma.get(i)[0];
				String rhs = (String) sigma.get(i)[1];
				LOGGER.debug(lhs + " -> " + rhs);
				String[][] prefixes = new String[lhs.size()][2];
				int p_index = 0;
				for (String path : lhs) {
					prefixes[p_index][0] = path;
					prefixes[p_index][1] = greatestCommonPrefixPath(path, rhs);
					LOGGER.debug(path + " greatest common: " + prefixes[p_index][1] );
					p_index++;
				}
				toAdd.clear();
				for (String qpath : result) {
					boolean prefix_condition = true;
					for (int p = 0; p < p_index; p++) {
						prefix_condition = prefix_condition && qpath.startsWith(prefixes[p][1]) && (prefixes[p][0].startsWith(qpath) || rhs.startsWith(qpath));
					}
					if (prefix_condition) {
						toAdd.add(rhs);
						if (!rhs.contains("@") && rhs.lastIndexOf('.') != -1) {
							String qprime = rhs.substring(0, rhs.lastIndexOf('.'));
							toAdd.add(qprime);
							HashSet<String> attributes = documentStructure.getElementAttributes(qprime.substring(qprime.lastIndexOf('.') + 1));
							if (attributes != null) {
								for (String attribute : attributes) {
									toAdd.add(qprime + "." + attribute);
								}
							}
						}
						sigma.remove(i);
						break;
					}
				}
				LOGGER.debug("Adding common prefixes");
				result.addAll(toAdd);
				LOGGER.debug(result);

			}

			newLength = result.size();
		} while (oldLength != newLength);
		return result;
	}
	

	/**
	 * Return whether a given XFD satisfies XNF
	 * @param leftHandSide
	 * @param rightHandSide
	 * @param originalXfds
	 * @param doc
	 * @return true if in xnf
	 */
	public static boolean isXNF(FDPath leftHandSide, String rightHandSide, HashMap<FDPath, FDPath> originalXfds, DTD doc) {
		String implied = rightHandSide.substring(0, rightHandSide.lastIndexOf('.'));
		FDPath closure =  getClosure(leftHandSide, rightHandSide, getSigma(doc, originalXfds), doc);
		return closure.contains(implied);
	}
}
