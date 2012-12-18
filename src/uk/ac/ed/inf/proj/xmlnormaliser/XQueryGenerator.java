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
			+ "$nfi := function($node as element()) as xs:boolean {xs:boolean('true')},\n"
			+ "$nai := function($node as element()) as item()* {()}";

	private static final String AA_FUN_ID = "$aa";
	private static final String AF_FUN_ID = "$af";
	private static final String NA_FUN_ID = "$na";
	private static final String NF_FUN_ID = "$nf";

	private XQueryGenerator() {
	}

	/**
	 * Purges the current transformations into function definitions and outputs
	 * transformation application
	 * 
	 * @param source
	 *            - document or concatenations
	 * @param counter
	 *            - current "purge level"
	 * @param output
	 *            - query StringBuilder
	 * @param attrF
	 *            - attribute filters
	 * @param attrA
	 *            - attribute additions
	 * @param nodeF
	 *            - node filters
	 * @param nodeAFunId
	 *            - node addition function ID
	 * @return String transformation application with the input setting
	 */
	static String purge(String source, int counter, StringBuilder output,
			Queue<String> attrF, Queue<String> attrA, Queue<String> nodeF,
			String nodeAFunId) {
		String nodeFFunId, attrAFunId, attrFFunId;

		if (attrF.isEmpty()) {
			attrFFunId = AF_FUN_ID + "i";
		} else {
			attrFFunId = AF_FUN_ID + counter;
			output.append(", ")
					.append(attrFFunId)
					.append(" := function($node as element(), $att as attribute()) as attribute()* {\nif (")
					.append(attrF.poll());
			while (!attrF.isEmpty()) {
				output.append(" or ").append(attrF.poll());
			}
			output.append(") then attribute {name($att)} {$att}\n"
					+ "else ()}\n");
		}

		if (attrA.isEmpty()) {
			attrAFunId = AA_FUN_ID + "i";
		} else {
			attrAFunId = AA_FUN_ID + counter;
			output.append(", ")
					.append(attrAFunId)
					.append(" := function($node as element()) as attribute()* {\n")
					.append(attrA.poll()).append("\nelse ");
			while (!attrA.isEmpty()) {
				output.append(attrA.poll()).append("\nelse ");
			}
			output.append("() }\n");
		}

		if (nodeF.isEmpty()) {
			nodeFFunId = NF_FUN_ID + "i";
		} else {
			nodeFFunId = NF_FUN_ID + counter;
			output.append(", ").append(nodeFFunId)
					.append(" := function($node as element()) as xs:boolean {")
					.append(nodeF.poll());
			while (!nodeF.isEmpty()) {
				output.append(" or ").append(nodeF.poll());
			}
			output.append("}\n");
		}
		return String.format("local:transform(%s, %s, %s, %s, %s)", nodeFFunId,
				nodeAFunId, attrFFunId, attrAFunId, source);
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
		int count = 0;
		String source = "doc(\"" + filename + "\")/" + transformedDTD.getRoot();
		StringBuilder result = new StringBuilder(TRANSFORMATION_FUNCTION);
		Queue<String> attrF = new LinkedList<String>();
		Queue<String> attrA = new LinkedList<String>();
		Queue<String> nodeF = new LinkedList<String>();

		for (TransformAction action : actions) {
			switch (action.getType()) {
			case MOVE_ATTRIBUTE:
				attrF.add("(name($node) != '" + action.getParameters()[0]
						+ "' or name($att) != '" + action.getParameters()[2]
						+ "')");
				attrA.add("if (name($node) = '" + action.getParameters()[1]
						+ "') then attribute {'" + action.getParameters()[2]
						+ "'} {$node/" + action.getParameters()[3]
						+ "[position() = 1]/@" + action.getParameters()[2]
						+ "}\n");
/*					break;
			case ADD_NODE:
				String attrAFunId = AA_FUN_ID + count;
				result.append(", ")
						.append(attrAFunId)
						.append("function($node as element()) as item()* {\n"
								+ "if (name($node) = '")
						.append(action.getParameters()[0])
						.append("') then\n"
								+ "for $na in distinct-values($node/")
						.append(action.getParameters()[0]).append("/*")
						.append("/text()) return element {'")
						.append(action.getParameters()[1])
						.append("'} {}}\nelse ()}");
				source = purge(source, count, result, attrF, attrA, nodeF,
						attrAFunId);
				count++;
				break;
			case MOVE_NODE:
				nodeF.add("name($node) != '" + action.getParameters()[2] + "'");
				attrAFunId = AA_FUN_ID + count;
				result.append(", ")
						.append(attrAFunId)
						.append("function($node as element()) as item()* {\n"
								+ "if (name($node) = '")
						.append(action.getParameters()[1])
						.append("') then\n"
								+ "for $na in distinct-values($node/")
						.append(action.getParameters()[0]).append("/")
						.append(action.getParameters()[2])
						.append("/text()) return element {'")
						.append(action.getParameters()[1]).append("'} {")
						.append("element {'").append(action.getParameters()[2])
						.append("'} {$na}}").append("\nelse ()}");
				source = purge(source, count, result, attrF, attrA, nodeF,
						attrAFunId);
				count++;

				break;*/
			default:
				break;
			}
		}
		if (!(nodeF.isEmpty() && attrA.isEmpty() && attrF.isEmpty())) {
			source = purge(source, count, result, attrF, attrA, nodeF,
					NA_FUN_ID + "i");
		}
		result.append("return\n").append(source).append("\n");
		return result.toString();
	}
}
