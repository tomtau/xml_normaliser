package uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd;

import java.io.File;
import java.util.Scanner;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD.DTDType;

/**
 * Unit tests of the basic DTD parser (one more simple DTD)
 * 
 * @author Tomas Tauber
 * 
 */
public class DTDParserTestCompany {

	/* the parsed DTD object */
	private static DTD parsed;

	/* the test DTD */
	private static final File TEST_FILE = new File("tests-resources",
			"company.dtd");

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
		for (String element : new String[] {"company", "department", "dep_name", "constitution", "employee", "name", "position"}) {
			Assert.assertTrue(parsed.getElements().contains(element));
		}
	}

	@Test
	public void testAttributesMatches() {
		Assert.assertEquals("Tests if the attributes match.", 2,
				parsed.getAttributes().size());
		for (String attribute : new String[] {"@eno", "@dno"}) {
			Assert.assertTrue(parsed.getAttributes().contains(attribute));
		}
	}	
	
	@Test
	public void testRootElementMatches() {
		Assert.assertEquals("Tests if the root element is correct.", "company",
				parsed.getRoot());
	}
	
	@Test
	public void testDisjuctiveType() {
		DTD.DTDType expected = DTDType.SIMPLE;
		DTD.DTDType actual = parsed.getType();
		Assert.assertEquals("Tests the DTD type.", expected, actual);
	}	
}
