package uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd;

/**
 * A class with a static method to parse a DTD into a POJO
 * @author Tomas Tauber
 *
 */
public class DTDParser {

	private DTDParser() {
	}
	
	/**
	 * Parses the DTD string and returns an object
	 * @param document string to parse
	 * @return the DTD object containing all information
	 */
	public static DTD parse(String document) {
		DTD result = new DTD();
		return result;
	}
	
}
