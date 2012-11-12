package uk.ac.ed.inf.proj.xmlnormaliser;

import java.io.File;
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
 * 
 * @author Tomas Tauber
 * 
 */
public class Main {
	
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	
	private Main(){
	}

	private static void process(String inputDTDPath, String inputXFDPath) {
		try {
			String originalDoc = Utils.readFile(new File(inputDTDPath));
			DTD originalDTD = DTDParser.parse(originalDoc);
			Map<FDPath, FDPath> xfds = FDParser.parse(Utils.readFile(new File(inputXFDPath)));
			List<TransformAction> actions = new ArrayList<TransformAction>();
			boolean invalid = true;
			int newETCount = 0;
			while (invalid) {
				invalid = false;
				for (Entry<FDPath, FDPath> xfd : xfds.entrySet()) {
					for (String rhs : xfd.getValue()) {
						if (!XNFValidator.isTrivial(xfd.getKey(), rhs)) {
							LOGGER.info("Non-trivial XFD: " + xfd.getKey() + " -> " + rhs);
							if (!XNFValidator.isXNF(xfd.getKey(), rhs, xfds, originalDTD)) {
								LOGGER.info("XFD does not satisfy XNF");
								invalid = true;
								boolean createNewET = false;
								for (String lhs : xfd.getKey()) {
									createNewET = createNewET || (lhs.indexOf('@') != -1);
								}
								if (createNewET) {
									actions.addAll(XNFTransformation.createNewET(newETCount, "newET", xfd.getKey(), rhs, xfds, originalDTD));
									newETCount++;
								} else {
									actions.addAll(XNFTransformation.moveAttribute(xfd.getKey(), rhs, xfds, originalDTD));
								}
								break;
							} else {
								LOGGER.info("XFD satisfies XNF");
							}
						} else {
							LOGGER.info("Trivial XFD: " + xfd.getKey() + " -> " + rhs);
						}
					}
					
				}
			}
			System.out.println(TransformAction.applyActions(originalDoc, actions, originalDTD));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.exit(1);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		if (args.length > 1) {
			process(args[0], args[1]);
		} else {
			LOGGER.error("Not enough arguments, please specify <input DTD file> <input XFD file>");
			System.exit(1);
		}

	}

}
