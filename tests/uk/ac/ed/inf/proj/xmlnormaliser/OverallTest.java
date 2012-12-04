package uk.ac.ed.inf.proj.xmlnormaliser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTDParser.DTDParserException;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;

/**
 * Unit tests of overall testing of the execution
 * 
 * @author Tomas Tauber
 * 
 */
public class OverallTest {

	/* the test DTD */
	private static final String TEST_ROOT = "tests-resources";
	private static final String[] TESTS = {"company", "courses"};
	
	private static final File OUTPUT_DTD = new File("temp.dtd");
	private static final File OUTPUT_XFD = new File("temp.txt");


	private static void deleteTemps() {
		if (OUTPUT_DTD.exists()) {
			OUTPUT_DTD.delete();
		}
		if (OUTPUT_XFD.exists()) {
			OUTPUT_XFD.delete();
		}
	}
	
	@Before
	public void setUp() throws Exception {
		deleteTemps();
	}

	@After
	public void tearDown() throws Exception {
		deleteTemps();
	}

	private void runTest(int index) throws FileNotFoundException, DTDParserException {
		String testDTD = TESTS[index] + ".dtd";
		String testXFD = TESTS[index] + ".txt";
		String expectedDTD = Utils.readFile(new File(TEST_ROOT, testDTD + ".new"));
		String expectedXFD = Utils.readFile(new File(TEST_ROOT, testXFD + ".new"));
		
		String originalDoc = Utils.readFile(new File(TEST_ROOT, testDTD));
		DTD originalDTD = DTDParser.parse(originalDoc);
		Map<FDPath, FDPath> xfds = FDParser.parse(Utils
				.readFile((new File(TEST_ROOT, testXFD))));
		
		Main.process(originalDoc, originalDTD, xfds, "temp.dtd", "temp.txt");
		
		String actualDTD = Utils.readFile(OUTPUT_DTD);
		String actualXFD = Utils.readFile(OUTPUT_XFD);
		
		Assert.assertEquals("Tests DTD: " + TESTS[index], expectedDTD,
				actualDTD);
		Assert.assertEquals("Tests XFDs: " + TESTS[index], expectedXFD,
				actualXFD);
	}

	@Test
	public void testCompany() throws FileNotFoundException, DTDParserException {
		runTest(0);
	}


}
