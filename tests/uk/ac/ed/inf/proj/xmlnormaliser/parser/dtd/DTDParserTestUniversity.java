package uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd;

import java.io.File;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.ed.inf.proj.xmlnormaliser.Utils;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD.DTDType;

/**
 * Unit tests of the basic DTD parser (disjuctive DTD)
 * 
 * @author Tomas Tauber
 * 
 */
public class DTDParserTestUniversity {

	/* the parsed DTD object */
	private static DTD parsed;

	/* the test DTD */
	private static final File TEST_FILE = new File("tests-resources",
			"university.dtd");

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
		Assert.assertEquals("Tests if the elements match.", 9,
				parsed.getElements().size());
		for (String element : new String[] {"university", "course", "number", "student", "name", "FLname", "first_name", "last_name", "grade"}) {
			Assert.assertTrue(parsed.getElements().contains(element));
		}
	}

	@Test
	public void testAttributesMatches() {
		Assert.assertEquals("Tests if the attributes match.", 0,
				parsed.getAttributes().size());
	}	
	
	@Test
	public void testRootElementMatches() {
		Assert.assertEquals("Tests if the root element is correct.", "university",
				parsed.getRoot());
	}
	
	@Test
	public void testDisjuctiveType() {
		DTD.DTDType expected = DTDType.DISJUNCTIVE;
		DTD.DTDType actual = parsed.getType();
		Assert.assertEquals("Tests the DTD type.", expected, actual);
	}	
}
