package uk.ac.ed.inf.proj.xmlnormaliser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import junit.framework.Assert;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ed.inf.proj.xmlnormaliser.Utils;
import uk.ac.ed.inf.proj.xmlnormaliser.XQueryGenerator;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;
import uk.ac.ed.inf.proj.xmlnormaliser.validator.TransformAction;
import uk.ac.ed.inf.proj.xmlnormaliser.validator.XNFTransformation;

/**
 * Unit tests of XQuery generation for creating new element types
 * 
 * @author Tomas Tauber
 * 
 */
public class XQueryTestCourses {

	/* the parsed objects */
	private static Map<FDPath, FDPath> originalFds;
	private static DTD parsedDTD;

	/* the test files */
	private static final File TEST_FILE_FD = new File("tests-resources",
			"courses.txt");
	private static final File TEST_FILE_DTD = new File("tests-resources",
			"courses.dtd");
	private static final File TEST_FILE_XQ = new File("tests-resources",
			"courses.xq");

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
	public void testPurgeCourses() throws Exception {
		StringBuilder result = new StringBuilder();
		Queue<String> attrF = new LinkedList<String>();
		Queue<String> attrA = new LinkedList<String>();
		Queue<String> nodeF = new LinkedList<String>();
		nodeF.add("name($node) != 'name'");
		String output = XQueryGenerator.purge("doc(\"test.xml\")/courses", 0, result, attrF, attrA, nodeF, "na0");
		Assert.assertEquals("local:transform($nf0, $na0, $afi, $aai, doc(\"test.xml\")/courses)", output);
		Assert.assertTrue(nodeF.isEmpty());
		Assert.assertEquals(",$nf1 := function($node as element()) as xs:boolean {name($node) != 'name'}\n", result.toString());
	}
	
	@Test
	public void testXQueryCreateNewET() throws Exception {
		String expected = Utils.readFile(TEST_FILE_XQ);
		List<TransformAction> actions = XNFTransformation.createNewET(0, "newET", new FDPath("courses", "courses.course.taken_by.student.@sno"), "courses.course.taken_by.student.name.#PCDATA", originalFds, parsedDTD);
		Assert.assertEquals(expected, XQueryGenerator.applyActions("test.xml", actions, parsedDTD));
		
	}
	
}
