package uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD.DTDType;

/**
 * A class with a static method to parse a DTD into a POJO
 * TODO: multiple attributes
 * TODO: handle entities
 * TODO: parse/represent element type definition better + detect the DTD type 
 * @author Tomas Tauber
 *
 */
public class DTDParser {

	public class DTDParserException extends Exception {
		private static final long serialVersionUID = 2667105558275959268L;
		public DTDParserException(String message) {
			super(message);
		}
	}
	
	private static final Pattern ELEMENT_REGEX = Pattern.compile("<!ELEMENT\\s+\\w+\\s+[^>]+");
	private static final Pattern ATTLIST_REGEX = Pattern.compile("<!ATTLIST\\s+\\w+\\s+\\w+");
	private static final Pattern DOCTYPE_REGEX = Pattern.compile("<!DOCTYPE\\s+\\w+");
	private static final Pattern TOKEN_REGEX = Pattern.compile("(#)?(\\w|_)+[\\*]?");
	
	private DTDParser() {
	}
	
	/**
	 * Gets all tokens from a given children string
	 * @param children
	 * @return array of tokens
	 */
	public static String[] getTokens(String children) {
		if (children.equals("EMPTY")) {
			return new String[] {};
		} else {
			Matcher tokens = TOKEN_REGEX.matcher(children);
			ArrayList<String> result = new ArrayList<String>();
			while (tokens.find()) {
				result.add(tokens.group());
			}
			String[] resultarray = new String[result.size()];
			return result.toArray(resultarray);
		}
	}

	/**
	 * Extracts the main information from DTD string and returns an object
	 * @param document string to parse
	 * @return the DTD object containing all information
	 * @throws DTDParserException 
	 */	
	private static DTD extractInformation(String document) {
		DTD result = new DTD();
		Matcher elements = ELEMENT_REGEX.matcher(document);
		// find element definitions
		while (elements.find()) {
			String element = elements.group().replace("<!ELEMENT", "").trim().replaceAll("\\s+", " ");
			String elementId = element.substring(0,element.indexOf(' '));
			String elementDefinition = element.substring(element.indexOf(' '),element.length()).replaceAll("\\s+", "");
			if (elementDefinition.indexOf('|') != -1) {
				result.setType(DTDType.DISJUNCTIVE);
			}
			result.addElement(elementId);
			result.addElementTypeDefinition(elementId, elementDefinition);
		}
		Matcher attributes = ATTLIST_REGEX.matcher(document);
		// find attribute definitions
		while (attributes.find()) {
			String[] attlist = attributes.group().replace("<!ATTLIST", "").trim().split("\\s+");
			String elementId = attlist[0];
			String attributeLabel = "@" + attlist[1];
			result.addAtribute(attributeLabel);
			result.addElementAttribute(elementId, attributeLabel);
		}
		return result;
	}
	
	/**
	 * Parses an inner DTD string (with a doctype) and returns an object
	 * @param document string to parse
	 * @return the DTD object containing all information
	 * @throws DTDParserException 
	 */
	public static DTD parse(String document) throws DTDParserException {
		DTD result = extractInformation(document);
		Matcher doctype = DOCTYPE_REGEX.matcher(document);
		// find the root element
		if (!doctype.find()) {
			throw new DTDParser().new DTDParserException("Root node not found.");
		} else {
			result.setRoot(doctype.group().replace("<!DOCTYPE", "").trim());
		}
		return result;
	}
	
	/**
	 * Parses an external DTD string (with a provided root node) and returns an object
	 * @param document string to parse
	 * @return the DTD object containing all information
	 * @throws DTDParserException 
	 */
	public static DTD parse(String document, String root) throws DTDParserException {
		DTD result = extractInformation(document);
		result.setRoot(root);
		return result;
	}

}
