package uk.ac.ed.inf.proj.xmlnormaliser.validator;

import java.util.HashMap;
import java.util.HashSet;

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

	public static String getPath(String pathSoFar, String currentElement, String seekedElement, DTD document) {
		if (currentElement.equalsIgnoreCase(seekedElement)) {
			if (pathSoFar.length() > 0) {
				return pathSoFar + "." + seekedElement;
			} else {
				return seekedElement;
			}
		} else {
			String[] children = DTDParser.getTokens(document.getElementTypeDefinition(currentElement));
			for (String child : children) {
				if (!child.equals("#PCDATA")) {
					String path;
					if (pathSoFar.length() > 0) {
						path = getPath(pathSoFar + "." + currentElement, child.replace("*", ""), seekedElement, document);
					} else {
						path = getPath(currentElement, child.replace("*", ""), seekedElement, document);
					}
					if (path.endsWith(seekedElement)) {
						return path;
					}
				}
			}
		}
		return "";
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
	 * Computes a closure of a given xfd
	 * @param leftHandSide
	 * @param rightHandSide
	 * @param xfds
	 * @return a closure (set of paths)
	 */
	public static FDPath getClosure(FDPath leftHandSide, String rightHandSide, HashMap<FDPath, FDPath> xfds) {
		FDPath result = new FDPath();
		return result;
	}
}
