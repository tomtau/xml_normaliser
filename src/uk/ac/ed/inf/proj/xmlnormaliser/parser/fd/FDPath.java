package uk.ac.ed.inf.proj.xmlnormaliser.parser.fd;

import java.util.HashSet;

/**
 * A class that represents a set of paths
 * @author Tomas Tauber
 *
 */
public class FDPath {
	
	/* a set of all paths represented as strings */
	private HashSet<String> paths;
	
	/**
	 * A default constructor
	 */
	public FDPath() {
		paths = new HashSet<String>();
	}
	
	/**
	 * A constructor with a variable number of paths
	 * @param arrayOfPaths
	 */
	public FDPath(String... arrayOfPaths) {
		paths = new HashSet<String>();
		for (String path : arrayOfPaths) {
			paths.add(path);
		}
	}
	
	/**
	 * Adds a new path
	 * @param path String representation of a path
	 */
	public void addPath(String path) {
		paths.add(path);
	}

	@Override
	public boolean equals(Object obj) {
		FDPath other = (FDPath) obj;
		if (paths.size() == other.paths.size()) {
			for (String path : paths) {
				if (!paths.contains(path)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (paths.isEmpty()) {
			return -1;
		} else {
			int result = 0;
			for (String path : paths) {
				result += path.hashCode();
			}
			return result;
		}
	}
	
	
}
