package uk.ac.ed.inf.proj.xmlnormaliser.validator;

import java.io.File;
import java.util.Map;

import junit.framework.Assert;

import org.apache.log4j.BasicConfigurator;
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
public class XNFValidatorTestCourses {

	/* the parsed objects */
	private static Map<FDPath, FDPath> originalFds;
	private static DTD parsedDTD;

	/* the test files */
	private static final File TEST_FILE_FD = new File("tests-resources",
			"courses.txt");
	private static final File TEST_FILE_DTD = new File("tests-resources",
			"courses.dtd");

	/**
	 * Reads the file and loads it into the parser
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
		originalFds = FDParser.parse(Utils.readFile(TEST_FILE_FD));
		parsedDTD = DTDParser.parse(Utils.readFile(TEST_FILE_DTD));
	}	
	
	@Test
	public void testIsNotXNF() { 
		Assert.assertFalse(XNFValidator.isXNF(new FDPath("courses", "courses.course.taken_by.student.@sno"), "courses.course.taken_by.student.name.#PCDATA", originalFds, parsedDTD));
	}
}
