package uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd;

import java.io.File;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ed.inf.proj.xmlnormaliser.Utils;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD.DTDType;

/**
 * Unit tests of the basic DTD parser
 * 
 * @author Tomas Tauber
 * 
 */
public class DTDParserTestCourses {

	/* the parsed DTD object */
	private static DTD parsed;

	/* the test DTD */
	private static final File TEST_FILE = new File("tests-resources",
			"courses.dtd");

	/**
	 * Reads the file and loads it into the parser
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		parsed = DTDParser.parse(Utils.readFile(TEST_FILE));
	}

	@Test
	public void testElementsMatch() {
		Assert.assertEquals("Tests if the elements match.", 7,
				parsed.getElements().size());
		for (String element : new String[] {"courses", "course", "title", "student", "name", "grade", "taken_by"}) {
			Assert.assertTrue(parsed.getElements().contains(element));
		}
	}

	@Test
	public void testAttributesMatches() {
		Assert.assertEquals("Tests if the attributes match.", 2,
				parsed.getAttributes().size());
		for (String attribute : new String[] {"@cno", "@sno"}) {
			Assert.assertTrue(parsed.getAttributes().contains(attribute));
		}
	}	
	
	@Test
	public void testRootElementMatches() {
		Assert.assertEquals("Tests if the root element is correct.", "courses",
				parsed.getRoot());
	}

	@Test
	public void testCoursesElement() {
		String expected = "(course*)";
		String actual = parsed.getElementTypeDefinition("courses");
		Assert.assertEquals("Tests the courses element.", expected, actual);
	}

	@Test
	public void testCourseElement() {
		String expected = "(title,taken_by)";
		String actual = parsed.getElementTypeDefinition("course");
		Assert.assertEquals("Tests the course element.", expected, actual);
		HashSet<String> attributes = parsed.getElementAttributes("course");
		Assert.assertTrue(attributes.contains("@cno"));
		Assert.assertEquals(attributes.size(), 1);
	}

	@Test
	public void testTitleElement() {
		String expected = "(#PCDATA)";
		String actual = parsed.getElementTypeDefinition("title");
		Assert.assertEquals("Tests the title element.", expected, actual);
	}
	
	@Test
	public void testStudentElement() {
		String expected = "(name,grade)";
		String actual = parsed.getElementTypeDefinition("student");
		Assert.assertEquals("Tests the student element.", expected, actual);
		HashSet<String> attributes = parsed.getElementAttributes("student");
		Assert.assertTrue(attributes.contains("@sno"));
		Assert.assertEquals(attributes.size(), 1);		
	}

	@Test
	public void testNameElement() {
		String expected = "(#PCDATA)";
		String actual = parsed.getElementTypeDefinition("name");
		Assert.assertEquals("Tests the name element.", expected, actual);
	}

	@Test
	public void testGradeElement() {
		String expected = "(#PCDATA)";
		String actual = parsed.getElementTypeDefinition("grade");
		Assert.assertEquals("Tests the grade element.", expected, actual);
	}	
	
	@Test
	public void testTakenByElement() {
		String expected = "(student*)";
		String actual = parsed.getElementTypeDefinition("taken_by");
		Assert.assertEquals("Tests the taken_by element.", expected, actual);
	}
	
	@Test
	public void testSimpleType() {
		DTD.DTDType expected = DTDType.SIMPLE;
		DTD.DTDType actual = parsed.getType();
		Assert.assertEquals("Tests the DTD type.", expected, actual);
	}

	@Test
	public void testGetTokensTakenBy() {
		String expected = "student*";
		String actual = DTDParser.getTokens("(student*)")[0];
		Assert.assertEquals("Tests the taken_by get token.", expected, actual);
	}
	
	@Test
	public void testGetTokensPCData() {
		String expected = "#PCDATA";
		String actual = DTDParser.getTokens("(#PCDATA)")[0];
		Assert.assertEquals("Tests the PCDATA get token.", expected, actual);
	}
	
	@Test
	public void testGetTokensCourse() {
		String[] expected = {"title", "taken_by"};
		String[] actual = DTDParser.getTokens("(title, taken_by)");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertEquals("Tests the course get token.", expected[i], actual[i]);
		}
	}	
}
