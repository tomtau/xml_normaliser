package uk.ac.ed.inf.proj.xmlnormaliser.validator;

import java.io.File;
import java.util.List;
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
 * Unit tests of the moving attributes
 * 
 * @author Tomas Tauber
 * 
 */
public class XNFTransformationTestCompany {

	/* the parsed objects */
	private static Map<FDPath, FDPath> originalFds;
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
		BasicConfigurator.configure();
		originalFds = FDParser.parse(Utils.readFile(TEST_FILE_FD));
		parsedDTD = DTDParser.parse(Utils.readFile(TEST_FILE_DTD));
	}	
	
	@Test
	public void testGetRelativePath() {
		String[] source = "company.department.dep_name".split("\\.");
		String[] target = "company.department.constitution.employee.@eno".split("\\.");
		String expected = "../constitution/employee/@eno";
		String actual = XNFTransformation.getRelativePath(source, target);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testMoveAttribute() {
		List<TransformAction> actions = XNFTransformation.moveAttribute(new FDPath("company.department.dep_name"), "company.department.constitution.employee.@eno", originalFds, parsedDTD);
		Assert.assertEquals(1, actions.size());
		Assert.assertEquals(TransformAction.ActionType.MOVE_ATTRIBUTE, actions.get(0).getType());
		Assert.assertEquals("employee", actions.get(0).getParameters()[0]);
		Assert.assertEquals("dep_name", actions.get(0).getParameters()[1]);
		Assert.assertEquals("eno", actions.get(0).getParameters()[2]);	
		
	}
	
}
