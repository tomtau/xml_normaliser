package uk.ac.ed.inf.proj.xmlnormaliser.validator;

import junit.framework.Assert;

import org.junit.Test;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;

/**
 * Unit tests of the XNF validation methods
 * 
 * @author Tomas Tauber
 * 
 */
public class XNFValidatorTestCompany {
	
	@Test
	public void testIsTrivial() {
		Assert.assertTrue(XNFValidator.isTrivial(new FDPath("company.department"), "company.department.@dno"));
	}
	
	@Test
	public void testIsNotTrivial() {
		Assert.assertFalse(XNFValidator.isTrivial(new FDPath("company.department.@dno", "company.department.constitution.employee"), "company.department.constitution.employee.position"));
	}
}
