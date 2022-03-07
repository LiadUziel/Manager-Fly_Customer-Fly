package control;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.soap.Node;
//package com.mkyong.xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.Consts;


public class XML_Control {
	private static XML_Control instance;
	
	public static XML_Control getInstance() {
		if (instance == null)
			instance = new XML_Control();
		return instance;
	}
	
	
    public ArrayList<String> importFlightStatusFromXML(String path) throws ClassNotFoundException, SQLException {
    	ArrayList<String> flightAndStatus = new ArrayList<>();
    	try {
			Document doc = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder().parse(new File(path));
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("FlightStatusXML");

			for (int i = 0; i < nl.getLength(); i++) {
//				System.out.println("check");
				if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) nl.item(i);
					el.getElementsByTagName("flightID").getLength();
					for (int j=0; j<el.getElementsByTagName("flightID").getLength(); j++) {
						// get Details and remove spaces
						String flightID = el.getElementsByTagName("flightID").item(j).getTextContent().replaceAll("\\s+","");
						String status = el.getElementsByTagName("status").item(j).getTextContent().replaceAll("\\s+","");
						if (!ShibuzFlightEmployeesControl.getInstance().getAllFlightIDs().contains(flightID))
							continue;
						if (!status.toLowerCase().equals("delayed") && !status.toLowerCase().equals("ontime") && !status.toLowerCase().equals("cancelled"))
							continue;
//						System.out.println(flightID + ", " + status);
						updateStatusInFlight_InDB(flightID, status);
						flightAndStatus.add("Flight ID: " + flightID + ", Status: " + status);
					}
				}
			}
			
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		return flightAndStatus;
    }
    
    
    public void updateStatusInFlight_InDB(String flightID, String status) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(Consts.SQL_UPDATE_STATUS_IN_FLIGHT)){
			int i = 1;
			stmt.setString(i++, status);
			stmt.setString(i++, flightID);
			
			stmt.executeUpdate();
		}
		return;	
    }



}
