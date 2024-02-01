package com.octopix.testcases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.octopix.utility.ReadConfig;


public class JsonReader {
	
	public static void main(String[] args) {
		
		ReadConfig config = new ReadConfig();
		String apiUrl = config.getUrl();

		try {

			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");

			InputStream inputStream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			
			String repName = "JSONOutput " + ".txt";			
			String filePath = System.getProperty("user.dir") + File.separator +"/Reports/" + repName;			
			FileWriter fileWriter = new FileWriter(filePath);
	        
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				
				fileWriter.write(line+"\n");
				fileWriter.flush();					
			}			
			
			fileWriter.close();

			reader.close();
			inputStream.close();
			connection.disconnect();

		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}

