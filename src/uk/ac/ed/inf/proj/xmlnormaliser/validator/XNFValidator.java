package uk.ac.ed.inf.proj.xmlnormaliser.validator;

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

}
