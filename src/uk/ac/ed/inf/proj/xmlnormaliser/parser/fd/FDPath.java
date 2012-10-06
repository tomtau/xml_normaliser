package uk.ac.ed.inf.proj.xmlnormaliser.parser.fd;

import java.util.HashSet;

/**
 * A class that represents a set of paths
 * @author Tomas Tauber
 *
 */
public class FDPath extends HashSet<String> {

	private static final long serialVersionUID = -269171907491472277L;

	/**
	 * A default constructor
	 */
	public FDPath() {
		super();
	}
	
	/**
	 * A constructor with a variable number of paths
	 * @param arrayOfPaths
	 */
	public FDPath(String... arrayOfPaths) {
		super();
		for (String path : arrayOfPaths) {
			this.add(path);
		}
	}

	@Override
	public boolean equals(Object obj) {
		FDPath other = (FDPath) obj;
		if (this.size() == other.size()) {
			for (String path : this) {
				if (!other.contains(path)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (this.isEmpty()) {
			return -1;
		} else {
			int result = 0;
			for (String path : this) {
				result += path.hashCode();
			}
			return result;
		}
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		for (String path : this) {
			output.append(path).append("; ");
		}
		return output.toString();
	}
	
}
