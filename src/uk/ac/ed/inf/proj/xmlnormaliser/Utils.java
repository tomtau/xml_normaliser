package uk.ac.ed.inf.proj.xmlnormaliser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
	 * Routine for writing out a text file
	 * @param outputPath
	 * @param toWrite
	 * @throws FileNotFoundException
	 */
	public static void writeFile(String outputPath, String toWrite) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(outputPath);
		out.println(toWrite);
		out.close();
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
				fileBuffer.append(scanner.nextLine()).append('\n');
			}
		} finally {
			scanner.close();
		}
		return fileBuffer.toString();
	}
}
