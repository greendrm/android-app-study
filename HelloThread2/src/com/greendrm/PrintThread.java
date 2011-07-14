package com.greendrm;

import java.io.*;
import java.nio.CharBuffer;

public class PrintThread extends Thread {
    SharedArea sharedArea;
    FileReader reader = null;
    String result = null;
    public void run() {
        while(sharedArea.isReady != true) {
        	synchronized(sharedArea) {
        		try {
					sharedArea.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
        }

        try {
        	char[] cb = new char[100];
			reader = new FileReader(sharedArea.file);
			try {
				int len = reader.read(cb);
				if (len > 0) {
					result = new String(cb, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(result);
    }
}
