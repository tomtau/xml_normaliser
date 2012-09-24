package uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd;

import java.util.HashMap;

/**
 * A POJO to hold information about the DTD structure
 * @author Tomas Tauber
 *
 */
public class DTD {
	/* the root element's id */
	private String root;
	/* a mapping from element ids to the objects with all information */
	private HashMap<String,DTDElement> elements;
	
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
	public HashMap<String, DTDElement> getElements() {
		return elements;
	}
	/**
	 * @param elements the DTD elements
	 */
	public void setElements(HashMap<String, DTDElement> elements) {
		this.elements = elements;
	}
	
	
}
