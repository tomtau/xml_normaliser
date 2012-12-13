package uk.ac.ed.inf.proj.xmlnormaliser;

import java.util.List;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.validator.TransformAction;


/**
 * Generation of XQuery transformations
 * 
 * @author Tomas Tauber
 * 
 */
public class XQueryGenerator {

	private XQueryGenerator() {
	}

	/**
	 * Returns a XQuery string to transform the original data to the new DTD
	 * @param actions - actions to be applied
	 * @param transformedDTD - the new DTD object
	 * @return XQuery String to transform the original data
	 */
	public static String applyActions(List<TransformAction> actions, DTD transformedDTD) {
		return "";
	}
}
