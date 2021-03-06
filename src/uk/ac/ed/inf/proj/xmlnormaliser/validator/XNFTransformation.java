package uk.ac.ed.inf.proj.xmlnormaliser.validator;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;


/**
 * Moving attributes and creating new element types
 * 
 * 
 * @author Tomas Tauber
 * 
 */
public class XNFTransformation {

	private XNFTransformation() {
	}
	
	/**
	 * Returns elements of the longest path in the set
	 * @param path set of paths
	 * @return elements of the longest path
	 */
	private static String[] getLongestLastElements(FDPath path) {
		String[] last = {};
		int max = 0;
		for (String p : path) {
			String[] elements = p.split("\\.");
			int lastE = elements.length;
			if (elements[elements.length-1].startsWith("@")) {
				lastE -= 1;
			}
			if (lastE > max) {
				last = elements;
				max = lastE;
			}
		}
		return last;
	}
	
	/**
	 * Returns a relative path from one absolute tree path to another
	 * @param source
	 * @param target
	 * @return
	 */
	static String getRelativePath(String[] source, String[] target) {
		int commonIndex = 0;
		while (commonIndex < source.length && commonIndex < target.length
				&& source[commonIndex].equalsIgnoreCase(target[commonIndex])) {
			commonIndex++;
		}
		StringBuilder relative = new StringBuilder();
		if (source.length != commonIndex) {
			int dirsUp = source.length - commonIndex;
			for (int i = 0; i < dirsUp; i++) {
				relative.append("../");
			}
		}
		for (int i = commonIndex; i < target.length; i++) {
			if (target[i].charAt(0) != '@' && target[i].charAt(0) != '#') {
				relative.append(target[i]).append("/");
			}
		}
		relative.deleteCharAt(relative.length() - 1);
		return relative.toString();
	}
	
	/**
	 * Given an anomalous XFD of the form q -> p.@l, an action to transform DTD and XFDs is returned  
	 * @param leftHandSide
	 * @param rightHandSide
	 * @param originalXfds
	 * @param doc
	 * @return
	 */
	public static TransformAction moveAttribute(FDPath leftHandSide, String rightHandSide, Map<FDPath, FDPath> originalXfds, DTD doc) {
		String[] q = getLongestLastElements(leftHandSide);
		int qIndex = q.length - 1;
		if (q[qIndex].startsWith("@")) {
			qIndex -= 1;
		}
		String lastQ = q[qIndex];
		StringBuilder qPath = new StringBuilder();
		for (int i = 0; i <= qIndex; i++) {
			qPath.append(q[i]).append(".");
		}
		String[] p = rightHandSide.split("\\.");
		String attr = p[p.length - 1];
		qPath.append(attr);
		String lastP = p[p.length - 2];
		doc.moveAttribute(attr, lastP, lastQ);
		
		for (Entry<FDPath, FDPath> xfd : originalXfds.entrySet()) {
			FDPath lhs = xfd.getKey();
			FDPath rhs = xfd.getValue();
			boolean action = false;
			if (lhs.contains(rightHandSide)) {
				lhs = new FDPath();
				lhs.addAll(xfd.getKey());
				lhs.remove(rightHandSide);
				lhs.add(qPath.toString());
				action = true;
			}
			if (rhs.contains(rightHandSide)) {
				rhs = new FDPath();
				rhs.addAll(xfd.getValue());
				rhs.remove(rightHandSide);
				rhs.add(qPath.toString());
				action = true;
			}
			if (action) {
				originalXfds.remove(xfd.getKey());
				originalXfds.put(lhs, rhs);
			}
		}
		
		return new TransformAction(TransformAction.ActionType.MOVE_ATTRIBUTE, new String[] {lastP, lastQ, attr.substring(1), 
				getRelativePath(q, p)});
	}

	/**
	 * Return the last element of the non-attribute path
	 * @param path set of paths
	 * @return last element of the non-attribute path (or null if no such path found)
	 */
	private static String[] getQElements(FDPath path) {
		for (String p : path) {
			if (!p.contains("@")) {
				return p.split("\\.");
			}
		}
		return null;
	}	

	/**
	 * Return the last element of the attribute paths
	 * @param path set of paths
	 * @return last element of the non-attribute paths (or null if no such path found)
	 */
	private static String[][] getPElements(FDPath path) {
		String[][] result = new String[path.size() - 1][];
		int index = 0;
		for (String p : path) {
			if (p.contains("@")) {
				result[index] = p.split("\\.");
				index++;
			}
		}
		return result;
	}	
	
	
	/**
	 * Given an anomalous XFD of the form q, p1.@l1, p1.@l2, p1.@l3..., pn.@ln -> p.@l, an action to transform DTD and XFDs is returned
	 * @param leftHandSide
	 * @param rightHandSide
	 * @param originalXfds
	 * @param doc
	 * @return
	 */
	public static TransformAction createNewET(int exCount, String namePrefix, FDPath leftHandSide, String rightHandSide, Map<FDPath, FDPath> originalXfds, DTD doc) {
		String[] q = getQElements(leftHandSide);
		String lastQ = q[q.length - 1];
		
		StringBuilder qPath = new StringBuilder(q[0]);
		for (int i = 1; i < q.length; i++) {
			qPath.append(".").append(q[i]);
		}
		ArrayList<String> keyNode = new ArrayList<String>();
		keyNode.add(lastQ);
		keyNode.add(namePrefix + exCount);
		/* add the main node */
		doc.addElement(namePrefix + exCount);
		doc.addElementTypeDefinition(lastQ, "(" + doc.getElementTypeDefinition(lastQ) + ", " + namePrefix + exCount + "*)");
		String[][] keys = getPElements(leftHandSide);
		StringBuilder docTypeDef = new StringBuilder("(");
		for (int innerCount = 0; innerCount < keys.length; innerCount++) {
			/* add its sub nodes */
			doc.addElement((namePrefix + exCount) + innerCount);
			docTypeDef.append(namePrefix).append(exCount).append(innerCount).append("*,");
		}
		docTypeDef.deleteCharAt(docTypeDef.length()-1).append(")");
		doc.addElementTypeDefinition(namePrefix + exCount, docTypeDef.toString());
		String[] p = rightHandSide.split("\\.");
		
		/* move attribute or node */
		if (p[p.length - 1].charAt(0) != '@') {
			keyNode.add(p[p.length - 2]);
			doc.addElementTypeDefinition(p[p.length - 3], doc.getElementTypeDefinition(p[p.length - 3]).replaceAll(p[p.length - 2], "").replaceAll("[(][\\s]*[,|\\|]", "(").replaceAll("[,|\\|][\\s]*[)]", ")"));
			doc.addElementTypeDefinition(namePrefix + exCount, "(" + docTypeDef.toString() + "," + p[p.length - 2] + ")");
		} else {
			keyNode.add(p[p.length - 1]);
			doc.moveAttribute(p[p.length - 1], p[p.length - 2], namePrefix + exCount);
		}
		keyNode.add(getRelativePath(q, p));
		int innerCount = 0;
		for (String[] pn : keys) {
			/* copy attributes to subnodes */
			keyNode.add((namePrefix + exCount) + innerCount);
			keyNode.add(pn[pn.length - 1]);
			keyNode.add(getRelativePath(q, pn));
			doc.addElementAttribute((namePrefix + exCount) + innerCount, pn[pn.length - 1]);
			innerCount++;
		}

		/* delete anomalous xfd */
		originalXfds.get(leftHandSide).remove(rightHandSide);
		if (originalXfds.get(leftHandSide).isEmpty()) {
			originalXfds.remove(leftHandSide);
		}

		/* conversion of original XFDs - implemented, not properly tested */
		String qp = qPath.toString();
		String qpN = qp + "." + namePrefix + exCount;		
		
		for (Entry<FDPath, FDPath> xfd : originalXfds.entrySet()) {
			FDPath lhs = (FDPath) xfd.getKey().clone();
			FDPath rhs = (FDPath) xfd.getValue().clone();
			boolean action = false;
			if (lhs.contains(rightHandSide)) {
				lhs.remove(rightHandSide);
				lhs.add(qpN + "." + p[p.length - 1]);
				action = true;
			}
			if (rhs.contains(rightHandSide)) {
				rhs.remove(rightHandSide);
				rhs.add(qpN + "." + p[p.length - 1]);
				action = true;
			}
			for (String pi : leftHandSide) {
				if (!pi.equalsIgnoreCase(qp)) {
					String attr = pi.substring(pi.lastIndexOf('.'));
					if (lhs.contains(pi)) {
						for (innerCount = 0; innerCount < keys.length; innerCount++) {
							String current = qpN + "." + (namePrefix + exCount) + innerCount;
							if (doc.getElementAttributes((namePrefix + exCount) + innerCount).contains(attr)) {
								lhs.remove(pi);
								lhs.add(current + "." + attr);
								action = true;
							}
						}
					}
					if (rhs.contains(pi)) {
						for (innerCount = 0; innerCount < keys.length; innerCount++) {
							String current = qpN + "." + (namePrefix + exCount) + innerCount;
							if (doc.getElementAttributes((namePrefix + exCount) + innerCount).contains(attr)) {
								rhs.remove(pi);
								rhs.add(current + "." + attr);
								action = true;
							}
						}
					}					
				}
			}
			if (action) {
				originalXfds.remove(xfd.getKey());
				originalXfds.put(lhs, rhs);
			}
		}
		
		/* adding new dependencies */
		for (innerCount = 0; innerCount < keys.length; innerCount++) {
			FDPath lhs1 = new FDPath(qpN);
			FDPath lhs2 = new FDPath(qp);
			String current = qpN + "." + (namePrefix + exCount) + innerCount;
			for (String[] pn : keys) {
				lhs1.add(current + "." + pn[pn.length - 1]);
				lhs2.add(current + "." +pn[pn.length - 1]);
			}
			originalXfds.put(lhs1, new FDPath(current));
			originalXfds.put(lhs2, new FDPath(qpN));
			
		}
		
		return new TransformAction(TransformAction.ActionType.CREATE_KEY_NODE, keyNode.toArray(new String[keyNode.size()]));
	}	
}
