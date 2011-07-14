package com.greendrm;

import java.io.*;

public class HelloFileReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileReader reader = null;
		try {
			reader = new FileReader("poem.txt");
			while(true) {
				int data = reader.read();
				if (data == -1)
					break;
				char ch = (char) data;
				System.out.print(ch);
			}
			reader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found\n");
		}
		catch (IOException e) {
			System.out.println("IO error\n");
		}
	}
}
