package com.yea.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.yea.loaninquiry.controller.model.CustomerRequest;
import com.yea.loaninquiry.tool.NVISoapClient;
import com.yea.loaninquiry.tool.NVISoapResponse;

@Component
public class TcknVerificationService {
	
	private static final Logger logger = LogManager.getLogger(TcknVerificationService.class);
	
	private final static String SERVER_URL = "https://tckimlik.nvi.gov.tr/Service/KPSPublic.asmx";
	private final static String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://tckimlik.nvi.gov.tr/WS\">	<soapenv:Header/>	<soapenv:Body>		<ws:TCKimlikNoDogrula>			<ws:TCKimlikNo>{tckn}</ws:TCKimlikNo>			<ws:Ad>{ad}</ws:Ad>			<ws:Soyad>{soyad}</ws:Soyad>			<ws:DogumYili>{yil}</ws:DogumYili>		</ws:TCKimlikNoDogrula>	</soapenv:Body></soapenv:Envelope>";
	private final static String SOAP_ACTION = "http://tckimlik.nvi.gov.tr/WS/TCKimlikNoDogrula";
	
	@Autowired
	private NVISoapClient client;
	
	public boolean verify(CustomerRequest request) throws SAXException, IOException, ParserConfigurationException {
		String name = parseName(request.getName());
		String surname = parseSurname(request.getName());
		String originBody = body.replace("{tckn}", request.getTckn()).replace("{ad}", name).replace("{soyad}",surname).replace("{yil}", String.valueOf(request.getBirthYear()));
		NVISoapResponse nviServerResponse = client.callSOAPServer(SERVER_URL,SOAP_ACTION,originBody); 
		boolean result = parseResponseFromNvi(nviServerResponse.getResult());
		logger.info("TCKN verification with :" + request.getTckn() + "," + request.getName() + "," + request.getBirthYear() + " is : " + result);
		
		return result;
	}

	private String parseName(String name) {
		String[] names = name.split(" ");
		StringBuilder nameBuilder = new StringBuilder();
		for (int i = 0; i < names.length-1 ; i++) {
			nameBuilder.append(names[i] + " ");
		}
		
		return nameBuilder.toString().trim();
	}
	
	private String parseSurname(String name) {
		if(name.contains(" ")) {
		String[] names = name.split(" ");
		int count = names.length;
		String surname = "";
		surname = names[count-1];
		return surname;
		}
		else return name;		
	}
	
	private boolean parseResponseFromNvi(String response) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
		
		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append(response);
		ByteArrayInputStream input = new ByteArrayInputStream(
		   xmlStringBuilder.toString().getBytes("UTF-8"));
		Document doc = builder.parse(input);
		
		NodeList fList = doc.getElementsByTagName("TCKimlikNoDogrulaResult");		
		Node nNode = fList.item(0);
		
		NodeList sList = nNode.getChildNodes();
		Node total = sList.item(0);
		
		if(total.getTextContent().equalsIgnoreCase("true")) return true;
		else return false;
	}
	

}
