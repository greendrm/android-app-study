package com.greendrm.ProductAndroid;

public class ProductAndroid extends Product {
	String model;
	String type;
	
	ProductAndroid() {
		super();
	}
	ProductAndroid(Integer no, String name, String dim,
			Integer price, String model, String type) {
		super(no, name, dim, price);
		this.model = model;
		this.type = type;
	}
}
