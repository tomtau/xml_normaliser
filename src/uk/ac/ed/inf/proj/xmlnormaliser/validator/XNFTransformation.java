package uk.ac.ed.inf.proj.xmlnormaliser.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;


/**
 * Moving attributes and creating new element types
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
	private static String[] getLastElements(FDPath path) {
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
	 * Given an anomalous XFD of the form q -> p.@l, the set of actions to transform DTD and XFDs is returned  
	 * @param leftHandSide
	 * @param rightHandSide
	 * @param originalXfds
	 * @param doc
	 * @return
	 */
	public static List<TransformAction> moveAttribute(FDPath leftHandSide, String rightHandSide, HashMap<FDPath, FDPath> originalXfds, DTD doc) {
		List<TransformAction> actions = new ArrayList<TransformAction>();
		String[] q = getLastElements(leftHandSide);
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
		actions.add(new TransformAction(TransformAction.ActionType.MOVE_ATTRIBUTE, new Object[] {lastP, lastQ, attr}));
		
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
				lhs = new FDPath();
				lhs.addAll(xfd.getKey());
				lhs.remove(rightHandSide);
				lhs.add(qPath.toString());
				action = true;
			}
			if (action) {
				actions.add(new TransformAction(TransformAction.ActionType.CHANGE_XFD, new Object[] {xfd.getKey(), lhs, rhs}));
			}
		}
		
		return actions;
	}
	
	/**
	 * 
	 * @param leftHandSide
	 * @param rightHandSide
	 * @param originalXfds
	 * @param doc
	 * @return
	 */
	public static List<TransformAction> createNewET(int etCount, FDPath leftHandSide, String rightHandSide, HashMap<FDPath, FDPath> originalXfds, DTD doc) {
		return new ArrayList<TransformAction>();
	}	
}
