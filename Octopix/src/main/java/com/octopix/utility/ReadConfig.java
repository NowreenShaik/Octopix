package com.octopix.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {

	Properties property;

	public ReadConfig() {

		File src = new File("./Configuration/Config.properties");

		try {
			FileInputStream fis = new FileInputStream(src);
			property = new Properties();
			property.load(fis);
		} catch (Exception e) {
			System.out.println("Exception is:" + e.getMessage());
		}

	}
	
	public String getUrl() {
		String url = property.getProperty("apiUrl");
		return url;
	}
	
	public String getExcelPath() {
		String excel = property.getProperty("gamdias201223LoopExcelPath");
		return excel;
	}

}
