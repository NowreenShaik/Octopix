package com.octopix.testcases;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReadFile {

	public static void main(String[] args) {

		try {
			String jsonContent = readFileAsString("C:\\Users\\Xinthe\\eclipse-workspace\\Octopix\\Reports\\JSONOutput .txt");
			JSONArray vehiclesArray = new JSONArray(jsonContent);
			
			System.out.println(vehiclesArray);
			
			List<FourWheeler> vehicles = getVehiclesFromJsonArray(vehiclesArray);
			for (FourWheeler fWheeler : vehicles) {
				System.out.println("vehicleTrack: " + fWheeler.getCounting());
				System.out.println("vehicleType: " + fWheeler.getColor());
				System.out.println("isInspected: " + fWheeler.getInspection());
				System.out.println("licensePlateText: " + fWheeler.getOCR());
				System.out.println();				
			}			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
	
	private static List<FourWheeler> getVehiclesFromJsonArray(JSONArray vehiclesArray) {
        List<FourWheeler> vehicles = new ArrayList<>();

        for (int i = 0; i < vehiclesArray.length(); i++) {
            JSONObject vehicleJson = vehiclesArray.getJSONObject(i);
                      
            FourWheeler fWheeler = new FourWheeler();
            fWheeler.setCounting(vehicleJson.getString("vehicleID"));
            fWheeler.setInspection(vehicleJson.getString("isInspected"));
            fWheeler.setColor(vehicleJson.getString("vehicleColor"));
            fWheeler.setOCR(vehicleJson.getString("licensePlateText"));
            
            vehicles.add(fWheeler);
        }

        return vehicles;
    }

}

