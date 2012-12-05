package uk.ac.ed.inf.proj.xmlnormaliser.parser.fd;

import java.io.File;
import java.util.Map;

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
public class FDParserTestDB {

	/* the parsed FDs */
	private static Map<FDPath, FDPath> parsed;

	/* the test FD */
	private static final File TEST_FILE = new File("tests-resources",
			"db.txt");

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
		Assert.assertEquals("Tests if the number of FDs matches.", 4,
				parsed.keySet().size());
	}
	
	@Test
	public void testFD1Match() {
		FDPath key = new FDPath("db.conf.title.#PCDATA");
		FDPath expected = new FDPath("db.conf");
		FDPath actual = parsed.get(key);
		Assert.assertEquals("Tests the first FD.", expected, actual);		
	}

	@Test
	public void testFD2Match() {
		FDPath key = new FDPath("db.conf.issue");
		FDPath expected = new FDPath("db.conf.issue.inproceedings.@year");
		FDPath actual = parsed.get(key);
		Assert.assertEquals("Tests the second FD.", expected, actual);		
	}

	@Test
	public void testFD3Match() {
		FDPath key = new FDPath("db.conf.issue", "db.conf.issue.inproceedings.title.#PCDATA");
		FDPath expected = new FDPath("db.conf.issue.inproceedings");
		FDPath actual = parsed.get(key);
		Assert.assertEquals("Tests the third FD.", expected, actual);		
	}

	@Test
	public void testFD4Match() {
		FDPath key = new FDPath("db.conf.issue.inproceedings.@key");
		FDPath expected = new FDPath("db.conf.issue.inproceedings");
		FDPath actual = parsed.get(key);
		Assert.assertEquals("Tests the fourth FD.", expected, actual);		
	}	
	
}
