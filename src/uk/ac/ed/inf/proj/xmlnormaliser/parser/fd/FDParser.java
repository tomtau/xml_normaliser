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
	
	private static String XFD_SEPERATOR = "\n";
	private static String PATH_SEPERATOR = ";";
	private static String DEPENDENCY_ARROW = "->";
	
	private FDParser() {
	}
	
	/**
	 * Parses the FD string and returns a mapping from a set of paths to a set of paths
	 * @param document string to parse
	 * @return the mapping of FDs (from a set of paths to a set of paths)
	 */
	public static HashMap<FDPath, FDPath> parse(String document) {
		HashMap<FDPath, FDPath> result = new HashMap<FDPath, FDPath>();
		String[] lines = document.replace(" ", "").split(XFD_SEPERATOR);
		for (String line : lines) {
			String[] xfd = line.split(DEPENDENCY_ARROW);
			if (xfd.length == 2) {
				FDPath lhs = new FDPath(xfd[0].split(PATH_SEPERATOR));
				FDPath rhs = new FDPath(xfd[1].split(PATH_SEPERATOR));
				if (result.containsKey(lhs)) {
					result.get(lhs).addAll(rhs);
				} else {
					result.put(lhs, rhs);
				}
			}
		}
		return result;
	}
	
}
