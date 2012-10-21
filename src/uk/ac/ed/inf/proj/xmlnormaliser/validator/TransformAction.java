package uk.ac.ed.inf.proj.xmlnormaliser.validator;


/**
 * A description of an action
 * 
 * @author Tomas Tauber
 * 
 */
public class TransformAction {

	public enum ActionType {
		MOVE_ATTRIBUTE,
		ADD_ATTRIBUTE,
		ADD_NODE,
		DELETE_NODE,
		CHANGE_XFD,
		DELETE_XFD,
		ADD_XFD
	}
	
	private ActionType type;
	private Object[] parameters;
	
	/**
	 * @param type
	 * @param node
	 */
	public TransformAction(ActionType type, Object[] parameters) {
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
	public Object[] getParameters() {
		return parameters;
	}
}
