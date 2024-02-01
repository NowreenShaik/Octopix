package com.octopix.testcases;

import java.util.List;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.octopix.utility.ExcelGetSetData;

public class MqttSubscriber {

	public static void main(String[] args) {

		String broker = "tcp://localhost:1883";
		String clientId = "Xinthe_parking";
		String topic = "parking";

		try {
			@SuppressWarnings("resource")
			MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
			System.out.println("connecting to broker :" + broker);
			client.connect();
			System.out.println("Connected");

			client.setCallback(new MqttCallback() {
				GetterData gettingData = new GetterData();
				List<FourWheeler> excelData = gettingData.fourWheelerExcelData();

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					
					String filePath = "C:\\Users\\Xinthe\\eclipse-workspace\\Octopix\\src\\main\\java\\com\\octopix\\testdata\\Gamdias201223loop.xlsx";

					ExcelGetSetData getSet = new ExcelGetSetData(filePath);
					int rowCount = getSet.getRowCount("ALPR");
					System.out.println(rowCount);

					System.out.println("Excel Data Size is:" + excelData.size());

					getSet.removeColumn("ALPR", 4);
					getSet.removeColumn("ALPR", 5);
					getSet.removeColumn("ALPR", 6);

					getSet.addColumn("ALPR", "MQTT_OCR");
					getSet.addColumn("ALPR", "MQTT_Color");
					getSet.addColumn("ALPR", "MQTT_Inspection");

					String messagePayload = new String(message.getPayload());
					System.out.println(messagePayload);

					ConvertJsonToFourWheeler converter = new ConvertJsonToFourWheeler();
					List<FourWheeler> fourWheelerList = converter.getMyClassList(messagePayload);

					System.out.println(fourWheelerList);
					System.out.println();

//					for (int i = 0; i < excelData.size(); i++) {

					for (int i = 0, rowNum = 2; i < fourWheelerList.size() && rowNum <= rowCount; i++, rowNum++) {

						FourWheeler excelFourWheeler = excelData.get(i + 1);
						FourWheeler fourWheeler = fourWheelerList.get(i);

						if (excelFourWheeler.getOCR().equals(fourWheeler.getOCR())) {
//		                	System.out.println("MQTT OCR data and Excel OCR data are matched");
							System.out.println("Excel OCR:" + excelFourWheeler.getOCR() + " = " + "MQTT OCR:"
									+ fourWheeler.getOCR());
							getSet.setCellData("ALPR", "MQTT_OCR", rowNum, fourWheeler.getOCR());
						} else {
//		                	System.out.println("MQTT OCR data and Excel OCR data not matched");
							System.out.println("Excel OCR:" + excelFourWheeler.getOCR() + " != " + "MQTT OCR:"
									+ fourWheeler.getOCR());
							getSet.setCellData("ALPR", "MQTT_OCR", fourWheeler.getOCR(), rowNum);
						}

						if (excelFourWheeler.getColor().equals(fourWheeler.getColor())) {
//		                	System.out.println("MQTT Color data and Excel Color data are matched");
							System.out.println("Excel Color:" + excelFourWheeler.getColor() + " = " + "MQTT Color:"
									+ fourWheeler.getColor());
							getSet.setCellData("ALPR", "MQTT_Color", rowNum, fourWheeler.getColor());
						} else {
//		                	System.out.println("MQTT Color data and Excel Color data not matched");
							System.out.println("Excel Color:" + excelFourWheeler.getColor() + " != " + "MQTT Color:"
									+ fourWheeler.getColor());
							getSet.setCellData("ALPR", "MQTT_Color", fourWheeler.getColor(), rowNum);
						}

						if (excelFourWheeler.getInspection().equals(fourWheeler.getInspection())) {
//		                	System.out.println("MQTT Inspection data and Excel Inspection data are matched");
							System.out.println("Excel Inspection:" + excelFourWheeler.getInspection() + " = "
									+ "MQTT Inspection:" + fourWheeler.getInspection());
							getSet.setCellData("ALPR", "MQTT_Inspection", rowNum, fourWheeler.getInspection());
						} else {
//		                	System.out.println("MQTT Inspection data and Excel Inspection data not matched");
							System.out.println("Excel Inspection:" + excelFourWheeler.getInspection() + " != "
									+ "MQTT Inspection:" + fourWheeler.getInspection());
							getSet.setCellData("ALPR", "MQTT_Inspection", fourWheeler.getInspection(), rowNum);
						}

						System.out.println();
					}

				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
				}

				@Override
				public void connectionLost(Throwable cause) {
					System.out.println("Connection lost:" + cause.getMessage());
					cause.printStackTrace();
//					System.exit(0);

				}
			});

			client.subscribe(topic);
			System.out.println("Subscribed to topic: " + topic);

			while (true) {
				Thread.sleep(1000);
			}
		} catch (MqttException | InterruptedException e) {
			e.printStackTrace();
		}

	}
}
