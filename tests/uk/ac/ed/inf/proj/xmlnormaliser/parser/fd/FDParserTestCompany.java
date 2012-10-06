package uk.ac.ed.inf.proj.xmlnormaliser.parser.fd;

import java.io.File;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ed.inf.proj.xmlnormaliser.Utils;

/**
 * Unit tests of the parsing FDs
 * 
 * @author Tomas Tauber
 * 
 */
public class FDParserTestCompany {

	/* the parsed FDs */
	private static HashMap<FDPath, FDPath> parsed;

	/* the test FD */
	private static final File TEST_FILE = new File("tests-resources",
			"company.txt");

	/**
	 * Reads the file and loads it into the parser
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		parsed = FDParser.parse(Utils.readFile(TEST_FILE));
	}

	
	@Test
	public void testNumberOfFdsMatch() {
		Assert.assertEquals("Tests if the number of FDs matches.", 3,
				parsed.keySet().size());
	}
	
	@Test
	public void testFD1Match() {
		FDPath key = new FDPath("company.department");
		FDPath expected = new FDPath("company.department.@dno");
		FDPath actual = parsed.get(key);
		Assert.assertEquals("Tests the first FD.", expected, actual);		
	}

	@Test
	public void testFD2Match() {
		FDPath key = new FDPath("company.department.@dno", "company.department.constitution.employee");
		FDPath expected = new FDPath("company.department.constitution.employee.position");
		FDPath actual = parsed.get(key);
		Assert.assertEquals("Tests the second FD.", expected, actual);		
	}
	
	@Test
	public void testFD3Match() {
		FDPath key = new FDPath("company.department.dep_name");
		FDPath expected = new FDPath("company.department.constitution.employee.@eno");
		FDPath actual = parsed.get(key);
		Assert.assertEquals("Tests the third FD.", expected, actual);		
	}	
}
