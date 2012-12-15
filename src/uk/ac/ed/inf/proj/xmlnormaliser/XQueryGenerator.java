package uk.ac.ed.inf.proj.xmlnormaliser;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.validator.TransformAction;

/**
 * Generation of XQuery transformations
 * 
 * @author Tomas Tauber
 * 
 */
public class XQueryGenerator {

	private static final String TRANSFORMATION_FUNCTION = "declare function local:transform($nodefilter as function(element()) as xs:boolean,"
			+ "$nodeadd as function(element()) as item()*,"
			+ "$attrfilter as function(element(), attribute()) as attribute()*,"
			+ "$attradd as function(element()) as attribute()*, $input as item()*) as item()* {\n"
			+ "for $node in $input\n"
			+ "	return\n"
			+ "		typeswitch($node)\n"
			+ "			case element()\n"
			+ "				return\n"
			+ "					if ($nodefilter($node)) then\n"
			+ "						element {name($node)} {\n"
			+ "							$nodeadd($node),\n"
			+ "							$attradd($node),\n"
			+ "							for $att in $node/@*\n"
			+ "								return $attrfilter($node, $att)\n"
			+ "							,\n"
			+ "						for $child in $node\n"
			+ "							return local:transform($nodefilter, $nodeadd, $attrfilter, $attradd, $child/node())\n"
			+ "						}\n"
			+ "					else ()\n"
			+ "		default return $node\n"
			+ "};\n\n"
			+ "let $aai := function($node as element()) as attribute()* { () },\n"
			+ "$afi := function($node as element(), $att as attribute()) as attribute()* {attribute {name($att)} {$att}},\n"
			+ "$nai := function($node as element()) as xs:boolean {xs:boolean('true')},\n"
			+ "$nfi := function($node as element()) as item()* {()}";

	private static final String AA_FUN_ID = "$aa";
	private static final String AF_FUN_ID = "$af";
	private static final String NA_FUN_ID = "$na";
	private static final String NF_FUN_ID = "$nf";

	private XQueryGenerator() {
	}

	/**
	 * Purges the current transformations into function definitions and outputs transformation application
	 * @param source - document or concatenations
	 * @param counter - current "purge level"
	 * @param output - query StringBuilder
	 * @param attrF - attribute filters
	 * @param attrA - attribute additions
	 * @param nodeF - node filters
	 * @param nodeAFunId - node addition function ID
	 * @return String transformation application with the input setting
	 */
	static String purge(String source, int counter, StringBuilder output,
			Queue<String> attrF, Queue<String> attrA, Queue<String> nodeF,
			String nodeAFunId) {
		return "";
	}

	/**
	 * Returns a XQuery string to transform the original data to the new DTD
	 * 
	 * @param filename
	 *            - name of the XML file
	 * @param actions
	 *            - actions to be applied
	 * @param transformedDTD
	 *            - the new DTD object
	 * @return XQuery String to transform the original data
	 */
	public static String applyActions(String filename,
			List<TransformAction> actions, DTD transformedDTD) {

		return "";
	}
}
