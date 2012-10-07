package uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD.DTDType;

/**
 * A class with a static method to parse a DTD into a POJO
 * TODO: handle entities
 * TODO: parse without a doctype (root element as a parameter)
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
		Matcher tokens = TOKEN_REGEX.matcher(children);
		String[] result = new String[tokens.groupCount()];
		int i = 0;
		while (tokens.find()) {
			result[i] = tokens.group();
			i++;
		}
		return result;
	}
	
	/**
	 * Parses the DTD string and returns an object
	 * @param document string to parse
	 * @return the DTD object containing all information
	 * @throws DTDParserException 
	 */
	public static DTD parse(String document) throws DTDParserException {
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
		Matcher doctype = DOCTYPE_REGEX.matcher(document);
		// find the root element
		if (!doctype.find()) {
			throw new DTDParser().new DTDParserException("Root node not found.");
		} else {
			result.setRoot(doctype.group().replace("<!DOCTYPE", "").trim());
		}
		return result;
	}
	
}
