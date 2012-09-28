package uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd;

import java.util.HashMap;
import java.util.HashSet;

/**
 * A POJO to hold information about the DTD structure according to the 'A Normal Form for XML Documents'
 * @author Tomas Tauber
 *
 */
public class DTD {
	
	public static enum DTDType {SIMPLE, DISJUNCTIVE};
	
	/* the root element's id */
	private String root;
	/* a set of element ids */
	private final HashSet<String> elements;
	/* a set of attribute labels */
	private final HashSet<String> attributes;
	/* a mapping from elements to element type definitions */
	private final HashMap<String,String> P_mapping;
	/* a mapping from elements to a superset of attributes */
	private final HashMap<String,HashSet<String>> R_mapping;
	/* a type of the DTD */
	private DTDType type;

	public DTD() {
		root = "";
		elements = new HashSet<String>();
		attributes = new HashSet<String>();
		P_mapping = new HashMap<String,String>();
		R_mapping = new HashMap<String,HashSet<String>>();
		setType(DTDType.SIMPLE);
	}
	
	/**
	 * @return the root element ID
	 */
	public String getRoot() {
		return root;
	}
	/**
	 * @param root the root element id
	 */
	public void setRoot(String root) {
		this.root = root;
	}
	
	/**
	 * @return the DTD elements
	 */
	public HashSet<String> getElements() {
		return elements;
	}
	
	/**
	 * adds a new element
	 * @param element
	 */
	public void addElement(String element) {
		elements.add(element);
	}
	
	/**
	 * @return the DTD attributes
	 */
	public HashSet<String> getAttributes() {
		return attributes;
	}
	
	/**
	 * adds a new attribute
	 * @param attribute
	 */
	public void addAtribute(String attribute) {
		attributes.add(attribute);
	}
	
	/**
	 * Returns an element type definition
	 * @param element
	 * @return element type definition
	 */
	public String getElementTypeDefinition(String element) {
		return P_mapping.get(element);
	}
	
	/**
	 * Adds a new element->element type definition mapping
	 * @param element
	 * @param typeDefinition
	 */
	public void addElementTypeDefinition(String element, String typeDefinition) {
		P_mapping.put(element, typeDefinition);
	}
	
	/**
	 * Returns a superset of attributes for a given element
	 * @param element
	 * @return
	 */
	public HashSet<String> getElementAttributes(String element) {
		return R_mapping.get(element);
	}
	
	/**
	 * Adds an attribute to an element
	 * @param element
	 * @param attribute
	 */
	public void addElementAttribute(String element, String attribute) {
		HashSet<String> element_attributes = getElementAttributes(element);
		if (element_attributes == null) {
			element_attributes = new HashSet<String>();
		}
		element_attributes.add(attribute);
		R_mapping.put(element, element_attributes);
	}

	/**
	 * @return the DTD type
	 */
	public DTDType getType() {
		return type;
	}

	/**
	 * @param type the DTD type to set
	 */
	public void setType(DTDType type) {
		this.type = type;
	}
	
}
