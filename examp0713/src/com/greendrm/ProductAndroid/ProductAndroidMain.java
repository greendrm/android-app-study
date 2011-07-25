package com.greendrm.ProductAndroid;

public class ProductAndroidMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProductAndroid[] obj = new ProductAndroid[10];

		for (int i = 0; i < obj.length; i++) {
			obj[i] = new ProductAndroid(i, "SmartPhone", "20x40x1", 100000*(i+1),
					"MyDroid", "3G");
			System.out.println(obj[i].no + " " + "모델:" + obj[i].model + ", 가격:" + obj[i].price);
		}
	}
}
