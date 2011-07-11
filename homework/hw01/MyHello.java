package com.myandroid;

import java.io.*;

class MyHello {
	public static void main(String args[]) throws IOException {
		//following codes cannot display Hangul.
		//int i;
		//while( (i = System.in.read()) != '\n') {
		//	System.out.println((char)i);
		//}
		InputStream is = System.in;
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String str = br.readLine();
		System.out.println(str + " Android");
	}
}
