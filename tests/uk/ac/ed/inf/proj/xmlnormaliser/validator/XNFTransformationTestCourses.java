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
 * Unit tests of creating new element types
 * 
 * @author Tomas Tauber
 * 
 */
public class XNFTransformationTestCourses {

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
	public void testGetRelativePath() {
		String[] source = new String[] {"courses"};
		String[] target = "courses.course.taken_by.student.name".split("\\.");
		String expected = "course/taken_by/student/name";
		String actual = XNFTransformation.getRelativePath(source, target);
		Assert.assertEquals(expected, actual);
	}	
	
	@Test
	public void testCreateNewET() {
		List<TransformAction> actions = XNFTransformation.createNewET(0, "newET", new FDPath("courses", "courses.course.taken_by.student.@sno"), "courses.course.taken_by.student.name.#PCDATA", originalFds, parsedDTD);
		Assert.assertEquals(4, actions.size());
		Assert.assertEquals(TransformAction.ActionType.ADD_NODE, actions.get(0).getType());
		Assert.assertEquals("courses", actions.get(0).getParameters()[0]);
		Assert.assertEquals("newET0", actions.get(0).getParameters()[1]);

		Assert.assertEquals(TransformAction.ActionType.ADD_NODE, actions.get(1).getType());
		Assert.assertEquals("newET0", actions.get(1).getParameters()[0]);
		Assert.assertEquals("newET00", actions.get(1).getParameters()[1]);		

		Assert.assertEquals(TransformAction.ActionType.MOVE_NODE, actions.get(2).getType());
		Assert.assertEquals("student", actions.get(2).getParameters()[0]);
		Assert.assertEquals("newET0", actions.get(2).getParameters()[1]);
		Assert.assertEquals("name", actions.get(2).getParameters()[2]);				
		
		Assert.assertEquals(TransformAction.ActionType.COPY_ATTRIBUTE, actions.get(3).getType());
		Assert.assertEquals("student", actions.get(3).getParameters()[0]);
		Assert.assertEquals("newET00", actions.get(3).getParameters()[1]);
		Assert.assertEquals("sno", actions.get(3).getParameters()[2]);
		
	}
	
}
