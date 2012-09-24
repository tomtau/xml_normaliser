package uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd;

import java.io.File;
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
	public void testNumberOfElementsMatches() {
		Assert.assertEquals("Tests if the number of elements matches.", 7,
				parsed.getElements().size());
	}

	@Test
	public void testRootElementMatches() {
		Assert.assertEquals("Tests if the root element is correct.", "courses",
				parsed.getRoot());
	}

	@Test
	public void testCoursesElement() {
		DTDElement expected = new DTDElement("courses");
		expected.addChild("course*");
		DTDElement actual = parsed.getElements().get("courses");
		Assert.assertEquals("Tests the courses element.", expected, actual);
	}

	@Test
	public void testCourseElement() {
		DTDElement expected = new DTDElement("course");
		expected.addChild("title");
		expected.addChild("taken_by");
		expected.addAttribute("@cno");
		DTDElement actual = parsed.getElements().get("course");
		Assert.assertEquals("Tests the course element.", expected, actual);
	}

	@Test
	public void testTitleElement() {
		DTDElement expected = new DTDElement("title");
		expected.addChild("#PCDATA");
		DTDElement actual = parsed.getElements().get("title");
		Assert.assertEquals("Tests the title element.", expected, actual);
	}
	
	@Test
	public void testStudentElement() {
		DTDElement expected = new DTDElement("student");
		expected.addChild("name");
		expected.addChild("grade");
		expected.addAttribute("@sno");
		DTDElement actual = parsed.getElements().get("student");
		Assert.assertEquals("Tests the student element.", expected, actual);
	}

	@Test
	public void testNameElement() {
		DTDElement expected = new DTDElement("name");
		expected.addChild("#PCDATA");
		DTDElement actual = parsed.getElements().get("name");
		Assert.assertEquals("Tests the name element.", expected, actual);
	}

	@Test
	public void testGradeElement() {
		DTDElement expected = new DTDElement("grade");
		expected.addChild("#PCDATA");
		DTDElement actual = parsed.getElements().get("grade");
		Assert.assertEquals("Tests the grade element.", expected, actual);
	}	
	
	@Test
	public void testTakenByElement() {
		DTDElement expected = new DTDElement("taken_by");
		expected.addChild("student*");
		DTDElement actual = parsed.getElements().get("taken_by");
		Assert.assertEquals("Tests the taken_by element.", expected, actual);
	}

}
