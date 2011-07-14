package com.greendrm.reader;

import java.io.*;

public class HelloReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		InputStream is = System.in;
		Reader reader = new InputStreamReader(is);

		System.out.println("Quit to input 'q'");
		while(true) {
			int i;
			i = reader.read();
			if (i == 'q')
				break;
			
			System.out.print((char)i);
		}
	}
}
