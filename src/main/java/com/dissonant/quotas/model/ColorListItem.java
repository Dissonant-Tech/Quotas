package com.dissonant.quotas.model;

public class ColorListItem {

	private int color;
	private String name;

    public ColorListItem(int color, String name) { 
		this.color = color;
		this.name = name;
    }

	public void setColor(int color) {
    	this.color = color;
	}

	public int getColor() {    
		return color;
	}

	public void setName(String name) {
    	this.name = name;
	}

	public String getName() {    
		return name;
	}

}
