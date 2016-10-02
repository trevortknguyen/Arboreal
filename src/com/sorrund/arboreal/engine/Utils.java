package com.sorrund.arboreal.engine;

import java.io.*;
import java.util.Scanner;

public class Utils {
	@SuppressWarnings("resource")
	public static String loadResource(String fileName) throws FileNotFoundException, IOException {
		String result = "";
		InputStream in = null;
		Scanner scanner = null;
		try {
			in = Utils.class.getClass().getResourceAsStream(fileName);
			scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
			result = scanner.next();
		} finally {
			if (scanner != null) {
				scanner.close();
			} else if (in != null) {
				in.close();
			}
		}
		return result;
	}
}
