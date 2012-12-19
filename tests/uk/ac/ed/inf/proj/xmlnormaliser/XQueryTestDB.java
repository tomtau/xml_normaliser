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

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;
import uk.ac.ed.inf.proj.xmlnormaliser.validator.TransformAction;
import uk.ac.ed.inf.proj.xmlnormaliser.validator.XNFTransformation;

/**
 * Unit tests of XQuery generation for moving attributes
 * 
 * @author Tomas Tauber
 * 
 */
public class XQueryTestDB {

	/* the parsed objects */
	private static Map<FDPath, FDPath> originalFds;
	private static DTD parsedDTD;

	/* the test files */
	private static final File TEST_FILE_FD = new File("tests-resources",
			"db.txt");
	private static final File TEST_FILE_DTD = new File("tests-resources",
			"db.dtd");
	private static final File TEST_FILE_XQ = new File("tests-resources",
			"db.xq");

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
	public void testPurgeDB() throws Exception {
		StringBuilder result = new StringBuilder();
		Queue<String> attrF = new LinkedList<String>();
		attrF.add("(name($node) != 'inproceedings' or name($att) != 'year')");
		Queue<String> attrA = new LinkedList<String>();
		attrA.add("if (name($node) = 'issue') then attribute {'year'} {$node/inproceedings[position() = 1]/@year}");
		Queue<String> nodeF = new LinkedList<String>();
		String output = XQueryGenerator.purge("local:transform($nf1, $na1, $af1, $aa1, doc(\"test.xml\")/db)/db", 0, result, attrF, attrA, nodeF, "$nai");
		Assert.assertEquals("local:transform($nfi, $nai, $af0, $aa0, local:transform($nf1, $na1, $af1, $aa1, doc(\"test.xml\")/db)/db)", output);
		Assert.assertTrue(attrF.isEmpty());
		Assert.assertTrue(attrA.isEmpty());
		Assert.assertEquals(", $af0 := function($node as element(), $att as attribute()) as attribute()* {\n"
							+ "if ((name($node) != 'inproceedings' or name($att) != 'year')) then attribute {name($att)} {$att}\n"
							+ "else ()}\n"
							+", $aa0 := function($node as element()) as attribute()* {\n"
							+ "if (name($node) = 'issue') then attribute {'year'} {$node/inproceedings[position() = 1]/@year}\n"
							+ "else () }\n"
							, result.toString());
	}
	
	@Test
	public void testXQueryMoveAttribute() throws Exception {
		String expected = Utils.readFile(TEST_FILE_XQ);
		List<TransformAction> actions = new LinkedList<TransformAction>();
		actions.add(XNFTransformation.moveAttribute(new FDPath("db.conf.issue"), "db.conf.issue.inproceedings.@year", originalFds, parsedDTD));
		Assert.assertEquals(expected, XQueryGenerator.applyActions("test.xml", actions, parsedDTD));
		
	}
	
}
