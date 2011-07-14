package com.greendrm;

import java.io.File;
import java.io.IOException;

public class HelloThread2 {
    public static void main(String args[]) {
        CalcThread thread1 = new CalcThread();
        PrintThread thread2 = new PrintThread();
        SharedArea obj = new SharedArea();
        
        try {
			File tmpFile = File.createTempFile("mytemp", ".tmp", new File("C:\\temp"));
			obj.file = tmpFile;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
        thread1.sharedArea = obj;
        thread2.sharedArea = obj;
        thread1.start();
        thread2.start();
    }
}
