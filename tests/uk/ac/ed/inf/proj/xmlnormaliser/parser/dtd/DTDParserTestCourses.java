package uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

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
		StringBuilder fileBuffer = new StringBuilder((int) TEST_FILE.length());
		Scanner scanner = new Scanner(TEST_FILE);
		try {
			while (scanner.hasNextLine()) {
				fileBuffer.append(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
		parsed = DTDParser.parse(fileBuffer.toString());
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
		for (String attribute : new String[] {"@cno", "@sid"}) {
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
		Assert.assertTrue(attributes.contains("@sid"));
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

}
