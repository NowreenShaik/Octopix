package com.octopix.testcases;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertJsonToFourWheeler {

//	public FourWheeler getMyClass(String payLoad) throws Exception{
//		ObjectMapper mapper = new ObjectMapper();
//		
//		FourWheeler fourWheeler = new FourWheeler();
//		
//		JsonNode json = mapper.readTree(payLoad);	
//		JsonNode vehicleDetails = json.path("vehicleDetails").get(0);
//					
//		fourWheeler.setOCR(vehicleDetails.path("licensePlateText").asText());
//		fourWheeler.setColor(vehicleDetails.path("vehicleColor").asText());	
//		fourWheeler.setInspection(vehicleDetails.path("isInspected").asText());
//        
//        return fourWheeler;
//	}

	public List<FourWheeler> getMyClassList(String payLoad) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonArray = mapper.readTree(payLoad);

		List<FourWheeler> fourWheelerList = new ArrayList<>();

		for (JsonNode entry : jsonArray) {
			FourWheeler fourWheeler = new FourWheeler();

//			JsonNode vehicleDetails = entry.path("vehicleDetails");
			JsonNode vehicleDetails = entry.path("vehicleDetails").get(0);

			fourWheeler.setOCR(vehicleDetails.path("licensePlateText").asText());
			fourWheeler.setColor(vehicleDetails.path("vehicleColor").asText());
			fourWheeler.setInspection(vehicleDetails.path("isInspected").asText());

			fourWheelerList.add(fourWheeler);

		}

		return fourWheelerList;
	}

}

