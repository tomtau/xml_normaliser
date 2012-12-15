package uk.ac.ed.inf.proj.xmlnormaliser.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;


/**
 * A description of an action
 * 
 * @author Tomas Tauber
 * 
 */
public class TransformAction {

	public enum ActionType {
		MOVE_ATTRIBUTE,
		COPY_ATTRIBUTE,
		ADD_NODE,
		MOVE_NODE
	}
	
	private ActionType type;
	private String[] parameters;
	
	/**
	 * @param type
	 * @param node
	 */
	public TransformAction(ActionType type, String[] parameters) {
		super();
		this.type = type;
		this.parameters = parameters;
	}

	/**
	 * @return the type
	 */
	public ActionType getType() {
		return type;
	}

	/**
	 * @return the parameters
	 */
	public String[] getParameters() {
		return parameters;
	}
	
	/**
	 * Returns a new DTD string
	 * @param inputDTD - starting DTD String
	 * @param actions - actions to be applied
	 * @param transformedDTD - the new DTD object
	 * @return new DTD String
	 */
	public static String applyActions(String inputDTD, List<TransformAction> actions, DTD transformedDTD) {
		inputDTD = inputDTD.replace("]>", "");
		for (TransformAction action : actions) {
			switch (action.type) {
			case MOVE_ATTRIBUTE:
				inputDTD = inputDTD.replaceFirst("<!ATTLIST[\\s]+" + action.parameters[0] + "[\\s]+" + action.parameters[2], 
						"<!ATTLIST " + action.parameters[1] + " " + action.parameters[2]);
				break;
			case COPY_ATTRIBUTE:
				inputDTD += "<!ATTLIST " +  action.parameters[1] + " " + action.parameters[2] + " CDATA #REQUIRED>\n";
				break;
			case ADD_NODE:
				Matcher parent = Pattern.compile("<!ELEMENT\\s+" + action.parameters[0] + "\\s+[^>]+").matcher(inputDTD);
				if (parent.find()) {
					inputDTD = parent.replaceFirst("<!ELEMENT " + action.parameters[0] + " " + transformedDTD.getElementTypeDefinition(action.parameters[0]));
				} else {
					inputDTD += "<!ELEMENT " + action.parameters[0] + " " + transformedDTD.getElementTypeDefinition(action.parameters[0]) + ">\n";
					inputDTD += "<!ELEMENT " + action.parameters[1] + " " + transformedDTD.getElementTypeDefinition(action.parameters[1]) + ">\n";
				}
				parent = Pattern.compile("<!ELEMENT\\s+" + action.parameters[1] + "\\s+[^>]+").matcher(inputDTD);
				if (!parent.find()) {
					inputDTD += "<!ELEMENT " + action.parameters[1] + " " + transformedDTD.getElementTypeDefinition(action.parameters[1]) + ">\n";
				}
				break;
			case MOVE_NODE:
				parent = Pattern.compile("<!ELEMENT\\s+" + action.parameters[0] + "\\s+[^>]+").matcher(inputDTD);
				if (parent.find()) {
					inputDTD =parent.replaceFirst("<!ELEMENT " + action.parameters[0] + " " + transformedDTD.getElementTypeDefinition(action.parameters[0]));
				} else {
					inputDTD += "<!ELEMENT " + action.parameters[0] + " " + transformedDTD.getElementTypeDefinition(action.parameters[0]) + ">\n";
					inputDTD += "<!ELEMENT " + action.parameters[1] + " " + transformedDTD.getElementTypeDefinition(action.parameters[1]) + ">\n";
				}
				break;				
			default:
				break;
			}
		}
		if (inputDTD.contains("<!DOCTYPE")) {
			inputDTD += "]>";
		}
		return inputDTD;
	}
}
