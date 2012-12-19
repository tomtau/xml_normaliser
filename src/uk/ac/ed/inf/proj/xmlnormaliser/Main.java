package uk.ac.ed.inf.proj.xmlnormaliser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTD;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.dtd.DTDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDParser;
import uk.ac.ed.inf.proj.xmlnormaliser.parser.fd.FDPath;
import uk.ac.ed.inf.proj.xmlnormaliser.validator.TransformAction;
import uk.ac.ed.inf.proj.xmlnormaliser.validator.XNFTransformation;
import uk.ac.ed.inf.proj.xmlnormaliser.validator.XNFValidator;

/**
 * The main method that reads from the command line
 * and the main processing loop of the normalisation algorithm
 * 
 * @author Tomas Tauber
 * 
 */
public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	private Main() {
	}

	/**
	 * Checks if the parsed DTD is in XNF - if not, generates actions to transform it to XNF
	 * and finally outputs the new DTD and a set of XFDs that satisfy XNF.
	 * @param originalDoc - the original DTD String
	 * @param originalDTD - the starting DTD object
	 * @param xfds - a set of XFDs
	 * @param newDTDPath - output of the new DTD
	 * @param newXFDPath - output of the new XFD set
	 * @throws FileNotFoundException
	 */
	static void process(String originalDoc, DTD originalDTD,
			Map<FDPath, FDPath> xfds, String newDTDPath, String newXFDPath)
			throws FileNotFoundException {
		List<TransformAction> actions = new ArrayList<TransformAction>();
		boolean invalid = true;
		int newETCount = 0;
		while (invalid) {
			invalid = false;
			for (Entry<FDPath, FDPath> xfd : xfds.entrySet()) {
				for (String rhs : xfd.getValue()) {
					if (!XNFValidator.isTrivial(xfd.getKey(), rhs)) {
						LOGGER.info("Non-trivial XFD: " + xfd.getKey() + " -> "
								+ rhs);
						if (!XNFValidator.isXNF(xfd.getKey(), rhs, xfds,
								originalDTD)) {
							LOGGER.info("XFD does not satisfy XNF");
							invalid = true;
							boolean moveAttribute = true;
							for (String lhs : xfd.getKey()) {
								moveAttribute = moveAttribute
										&& (lhs.indexOf('@') == -1);
							}
							if (moveAttribute) {
								actions.add(XNFTransformation.moveAttribute(
										xfd.getKey(), rhs, xfds, originalDTD));
							} else {
								actions.add(XNFTransformation.createNewET(
										newETCount, "newET", xfd.getKey(), rhs,
										xfds, originalDTD));
								newETCount++;								
							}
							break;
						} else {
							LOGGER.info("XFD satisfies XNF");
						}
					} else {
						LOGGER.info("Trivial XFD: " + xfd.getKey() + " -> "
								+ rhs);
					}
				}

			}
		}
		PrintWriter out = new PrintWriter(newDTDPath);
		out.println(TransformAction.applyActions(originalDoc, actions,
				originalDTD));
		out.close();
		out = new PrintWriter(newXFDPath);
		for (Entry<FDPath, FDPath> xfd : xfds.entrySet()) {
			out.println(xfd.getKey() + " -> " + xfd.getValue());
		}
		out.close();
	}

	/**
	 * Main - parses the arguments, parses the provided files and passes everything to the processing method.
	 * @param args
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		boolean error = false;
		if (args.length > 1) {
			byte offset = 0;
			File dtdFile = new File(args[0]);

			File xfdFile = new File(args[1]);
			if (!xfdFile.exists()) {
				if (args.length > 2) {
					offset = 1;
					xfdFile = new File(args[2]);
				} else {
					error = true;
				}
			}
			if (!error) {
				try {
					String originalDoc = Utils.readFile(dtdFile);
					DTD originalDTD;
					if (offset == 0) {
						originalDTD = DTDParser.parse(originalDoc);
					} else {
						originalDTD = DTDParser.parse(originalDoc, args[1]);
					}
					Map<FDPath, FDPath> xfds = FDParser.parse(Utils
							.readFile(xfdFile));
					String outputDTDPath;
					String outputXFDPath;
					if (args.length - offset < 3) {
						outputDTDPath = args[0] + ".new";
					} else {
						outputDTDPath = args[2 + offset];
					}
					if (args.length - offset < 4) {
						outputXFDPath = args[1 + offset] + ".new";
					} else {
						outputXFDPath = args[3 + offset];
					}
					process(originalDoc, originalDTD, xfds, outputDTDPath,
							outputXFDPath);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					System.exit(1);
				}
			}
		} else {
			error = true;
		}

		if (error) {
			LOGGER.error("Not enough arguments, please specify <input DTD file> [root node] <input XFD file> [output DTD file] [output XFD file]");
			System.exit(1);
		}
	}

}
