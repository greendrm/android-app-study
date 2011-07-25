package com.greendrm.ProductAndroid;

public class Product {
	Integer no;
	String name;
	String dimension;
	Integer price;
	
	Product() {
	}
	Product (Integer no, String name, String dimension,
			Integer price) {
		this.no = no;
		this.name = name;
		this.dimension = dimension;
		this.price = price;
	}
	
	void setName(String name) {
		this.name = name;
	}
	void setDimension(String name) {
		this.dimension = name;
	}
	void setPrice(Integer price) {
		this.price = price;
	}
}
