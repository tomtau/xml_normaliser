package uk.ac.ed.inf.proj.xmlnormaliser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Various utility methods
 * 
 * @author Tomas Tauber
 * 
 */
public class Utils {

	private Utils() {
	}

	/**
	 * Reads a file and outputs it as String
	 * @param input text file
	 * @return String document
	 * @throws FileNotFoundException
	 */
	public static String readFile(File input) throws FileNotFoundException {
		StringBuilder fileBuffer = new StringBuilder((int) input.length());
		Scanner scanner = new Scanner(input);
		try {
			while (scanner.hasNextLine()) {
				fileBuffer.append(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
		return fileBuffer.toString();
	}
}
