package uk.ac.ed.inf.proj.xmlnormaliser.parser.fd;

import java.util.HashMap;

/**
 * A class with a static method to parse a text with XML functional dependencies expressed as:
 * path1; path2 -> path3
 * path4 -> path5
 * (...)
 * @author Tomas Tauber
 *
 */
public class FDParser {
	
	private FDParser() {
	}
	
	/**
	 * Parses the FD string and returns a mapping from a set of paths to a set of paths
	 * @param document string to parse
	 * @return the mapping of FDs (from a set of paths to a set of paths)
	 */
	public static HashMap<FDPath, FDPath> parse(String document) {
		HashMap<FDPath, FDPath> result = new HashMap<FDPath, FDPath>();
		return result;
	}
	
}
