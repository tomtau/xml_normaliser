package uk.ac.ed.inf.proj.xmlnormaliser.gui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.oy.shared.lm.ant.TaskOptions;
import com.oy.shared.lm.in.DTDtoGRAPH;
import com.oy.shared.lm.out.GRAPHtoDOTtoGIF;

/**
 * For visualising DTDs using Dot and Linguine Maps
 * @author Tomas Tauber
 *
 */
public class GraphDrawing {

	static final File TEMP_FOLDER = new File("_temp");
	static final String SEPARATOR = System.getProperties().getProperty("file.separator");
	
	/**
	 * Generates an image in the temp folder with the graph representing the input DTD
	 * @param inputDTD - text
	 * @param filename - the target output pattern
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static void generateImage(String inputDTD, String filename) throws IOException, SAXException, ParserConfigurationException {
		if (!TEMP_FOLDER.isDirectory()) {
			TEMP_FOLDER.mkdirs();
		}
		
		TaskOptions options = new TaskOptions();
		options.inFile = TEMP_FOLDER + SEPARATOR + filename + ".dtd";
		PrintWriter out = new PrintWriter(options.inFile);
		out.println(inputDTD.replaceAll("<!DOCTYPE\\s+\\w+\\s*\\[", "").replaceAll("]>",""));
		out.close();
		options.dotFile = TEMP_FOLDER + SEPARATOR + filename + ".dot";
		options.outFile = TEMP_FOLDER + SEPARATOR + filename + ".gif";
		if (System.getProperties().getProperty("os.name").toLowerCase().contains("win")) {
			options.exeFile = "libs\\graphviz-2.4\\dot.exe";
		} else {
			options.exeFile = "dot";
		}
		GRAPHtoDOTtoGIF.transform(DTDtoGRAPH.load(options), options.dotFile, options.outFile, options.exeFile);
	}
}
