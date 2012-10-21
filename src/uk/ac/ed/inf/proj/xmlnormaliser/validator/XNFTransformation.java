package uk.ac.ed.inf.proj.xmlnormaliser.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	 * 
	 * @param leftHandSide
	 * @param rightHandSide
	 * @param originalXfds
	 * @param doc
	 * @return
	 */
	public static List<TransformAction> moveAttribute(FDPath leftHandSide, String rightHandSide, HashMap<FDPath, FDPath> originalXfds, DTD doc) {
		return new ArrayList<TransformAction>();
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
