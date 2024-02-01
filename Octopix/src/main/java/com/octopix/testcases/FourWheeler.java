package com.octopix.testcases;

public class FourWheeler {
	
	private String OCR;
	private String color;
	private String inspection;
	private String counting;
	
	public void setOCR(String licensePlate) {
		this.OCR =licensePlate;
	}
	
	public void setColor(String col) {
		this.color = col;
	}
	
	public void setInspection(String isInspected) {
		this.inspection = isInspected;
	}
	
	public void setCounting(String count) {
		this.counting = count;
	}
	
	public String getOCR() {
		return OCR;
	}
	
	public String getColor() {
		return color;
	}
	
	public String getInspection() {
		return inspection;
	}
	
	public String getCounting() {
		return counting;
	}
	
	@Override
    public String toString() {
        return "FourWheeler{" +
                "counting='" + counting + '\'' +
                ", OCR='" + OCR + '\'' +
                ", color='" + color + '\'' +
                ", inspection='" + inspection + '\'' +
                '}';
    }

}
