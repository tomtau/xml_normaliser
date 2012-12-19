package uk.ac.ed.inf.proj.xmlnormaliser;

import java.io.File;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;
import uk.ac.ed.inf.proj.xmlnormaliser.validator.TransformAction;

/**
 * Unit tests of XQuery generation of more complex create new element types transformation
 * 
 * @author Tomas Tauber
 * 
 */
public class XQueryTestCompany2 {

	/* the parsed objects */
	private static Map<FDPath, FDPath> originalFds;
	private static DTD parsedDTD;

	/* the test files */
	private static final File TEST_FILE_FD = new File("tests-resources",
			"company2.txt");
	private static final File TEST_FILE_DTD = new File("tests-resources",
			"company2.dtd");
	private static final File TEST_FILE_XQ = new File("tests-resources",
			"company2.xq");

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
	public void testXQueryCreateNewET() throws Exception {
		String expected = Utils.readFile(TEST_FILE_XQ);
		List<TransformAction> actions = Main.checkAndGenerateActions(parsedDTD, originalFds);
		Assert.assertEquals(expected, XQueryGenerator.applyActions("test.xml", actions, parsedDTD));
		
	}
	
}
