package com.greendrm;

import java.io.*;

public class CalcThread extends Thread {
    SharedArea sharedArea;
    FileWriter writer = null;
    public void run() {
        double total = 0.0;
        for (int cnt = 1; cnt < 1000000000; cnt += 2)
            if (cnt / 2 % 2 == 0)
                total += 1.0 / cnt;
            else
                total -= 1.0 / cnt;
        try {
			writer = new FileWriter(sharedArea.file);
			Double result = new Double(total * 4);
			System.out.println("DEBUG : " + result.toString());
			writer.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        //sharedArea.result = total * 4;
        sharedArea.isReady = true;

        synchronized(sharedArea) {
        	sharedArea.notify();
        }
    }
}
