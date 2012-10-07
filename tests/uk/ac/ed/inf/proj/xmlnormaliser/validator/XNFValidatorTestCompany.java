package uk.ac.ed.inf.proj.xmlnormaliser.validator;

import java.io.File;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ed.inf.proj.xmlnormaliser.Utils;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;

/**
 * Unit tests of the XNF validation methods
 * 
 * @author Tomas Tauber
 * 
 */
public class XNFValidatorTestCompany {

	/* the parsed objects */
	private static HashMap<FDPath, FDPath> originalFds;
	private static DTD parsedDTD;

	/* the test files */
	private static final File TEST_FILE_FD = new File("tests-resources",
			"company.txt");
	private static final File TEST_FILE_DTD = new File("tests-resources",
			"company.dtd");

	/**
	 * Reads the file and loads it into the parser
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		originalFds = FDParser.parse(Utils.readFile(TEST_FILE_FD));
		parsedDTD = DTDParser.parse(Utils.readFile(TEST_FILE_DTD));
	}	
	
	@Test
	public void testIsTrivial() {
		Assert.assertTrue(XNFValidator.isTrivial(new FDPath("company.department"), "company.department.@dno"));
	}
	
	@Test
	public void testIsNotTrivial() {
		Assert.assertFalse(XNFValidator.isTrivial(new FDPath("company.department.@dno", "company.department.constitution.employee"), "company.department.constitution.employee.position"));
	}
	
	@Test
	public void testPathCompany() {
		String expected = "company.department";
		String actual = XNFValidator.getPath("", parsedDTD.getRoot(), "department", parsedDTD);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testPathEmployee() {
		String expected = "company.department.constitution.employee";
		String actual = XNFValidator.getPath("", parsedDTD.getRoot(), "employee", parsedDTD);
		Assert.assertEquals(expected, actual);
	}	
	
	@Test
	public void testGetSigma() {
		HashMap<FDPath, FDPath> expected = new HashMap<FDPath, FDPath>();
		expected.put(new FDPath("company.department"), new FDPath("company.department.dep_name", "company.department.constitution"));
		expected.put(new FDPath("company.department.constitution.employee"), new FDPath("company.department.constitution.employee.name", "company.department.constitution.employee.position"));
		expected.put(new FDPath("company.department.@dno", "company.department.constitution.employee"), new FDPath("company.department.constitution.employee.position"));
		expected.put(new FDPath("company.department.dep_name"), new FDPath("company.department.constitution.employee.@eno"));
		HashMap<FDPath, FDPath> actual = XNFValidator.getSigma(parsedDTD, originalFds);
		Assert.assertEquals(expected.size(), actual.size());
		for (FDPath key : expected.keySet()) {
			Assert.assertEquals(expected.get(key), actual.get(key));
		}
	}
	
	@Test
	public void testGetClosure() {
		FDPath expected = new FDPath("company.department.dep_name", "company", "company.department", "company.department.@dno", "company.department.constitution.employee.@eno");
		FDPath actual = XNFValidator.getClosure(new FDPath("company.department.dep_name"), "company.department.constitution.employee.@eno", XNFValidator.getSigma(parsedDTD, originalFds));
		Assert.assertEquals(expected, actual);
	}
	
}
