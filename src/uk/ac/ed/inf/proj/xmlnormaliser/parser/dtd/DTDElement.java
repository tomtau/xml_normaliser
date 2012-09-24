package uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd;

import java.util.HashSet;

/**
 * A POJO to hold information about DTD elements
 * @author Tomas Tauber
 *
 */
public class DTDElement {
	/* the element identifier */
	private String id;
	/* the element's children nodes' ids */
	private HashSet<String> children;
	/* the element's attribute ids */
	private HashSet<String> attributes;
	
	/**
	 * A common constructor
	 * @param id the element's identifier
	 */
	public DTDElement(String id) {
		super();
		this.id = id;
		this.children = new HashSet<String>();
		this.attributes = new HashSet<String>();
	}

	/**
	 * Adds a new child
	 * @param child DTD element's id
	 */
	public void addChild(String child) {
		this.children.add(child);
	}
	
	/**
	 * Adds a new attribute
	 * @param attributeId attribute identifier
	 */
	public void addAttribute(String attributeId) {
		this.attributes.add(attributeId);
	}
	
	/**
	 * To compare if two DTD elements are the same.
	 */
	@Override
	public boolean equals(Object obj) {
		DTDElement other = (DTDElement) obj;
		boolean result = other.id == this.id;
		for (String child : this.children) {
			result = result && other.children.contains(child);
		}
		result = result && this.children.size() == other.children.size();
		for (String attributeId : this.attributes) {
			result = result && other.attributes.contains(attributeId);
		}
		result = result && this.attributes.size() == other.attributes.size();
				
		return result;
	}
	
}
