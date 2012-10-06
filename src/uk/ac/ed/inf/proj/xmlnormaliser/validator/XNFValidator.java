package uk.ac.ed.inf.proj.xmlnormaliser.validator;

import java.util.HashMap;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;

/**
 * Various static methods for validation
 * 
 * @author Tomas Tauber
 * 
 */
public class XNFValidator {

	private XNFValidator() {
	}

	/**
	 * Returns whether a FD is trivial
	 * @param leftHandSide
	 * @param rightHandSide
	 * @return true if trivial
	 */
	public static boolean isTrivial(FDPath leftHandSide, String rightHandSide) {
		return false;
	}

	/**
	 * Returns a set of XFDs that is combination of implicit DTDs and original FDs without trivial ones
	 * @param documentStructure
	 * @param originalFunctionalDependencies
	 * @return a new set of XFDs
	 */
	public static HashMap<FDPath, FDPath> getSigma(DTD documentStructure, HashMap<FDPath, FDPath> originalFunctionalDependencies) {
		HashMap<FDPath, FDPath> result = new HashMap<FDPath, FDPath>();
		return result;
	}
	
	/**
	 * Computes a closure of a given xfd
	 * @param leftHandSide
	 * @param rightHandSide
	 * @param xfds
	 * @return a closure (set of paths)
	 */
	public static FDPath getClosure(FDPath leftHandSide, String rightHandSide, HashMap<FDPath, FDPath> xfds) {
		FDPath result = new FDPath();
		return result;
	}
}
