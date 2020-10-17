package com.yea.loaninquiry.tool;

import java.nio.charset.StandardCharsets;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class NVISoapClient {
	
	private final CloseableHttpClient httpclient = HttpClients.createDefault();

	private static final int TIMEOUT_MILLIS = 1000;
	
	public NVISoapResponse callSOAPServer(final String url, final String actionUri, final String body) {
		return callSOAPServer(url, actionUri, body, null);
	}
	
	public NVISoapResponse callSOAPServer(final String url, final String actionUri, final String body, final String authorization) {

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT_MILLIS).setConnectTimeout(TIMEOUT_MILLIS).build();

		HttpPost httppost = new HttpPost(url);
		httppost.setConfig(requestConfig);
		httppost.setHeader("soapaction", actionUri);
		httppost.setHeader("Content-Type", "text/xml; charset=utf-8");
		if(authorization != null && !authorization.isEmpty()) {
			httppost.setHeader("Authorization",  authorization.startsWith("Basic ") ? authorization : "Basic "+authorization);	
		}

		try {
			HttpEntity entity = new StringEntity(body, StandardCharsets.UTF_8);
			httppost.setEntity(entity);

			HttpResponse response = httpclient.execute(httppost);// calling server
			HttpEntity r_entity = response.getEntity(); // get response
			Header[] headers = response.getAllHeaders();
			
			if (r_entity != null) {
				String result = EntityUtils.toString(r_entity);
				int statusCode = response.getStatusLine().getStatusCode();
				return new NVISoapResponse(statusCode,result);
			}
			
		} catch (Exception e) {
			return new NVISoapResponse(-1, e.getMessage());
		}
		
		return new NVISoapResponse(-1,null);
	}
	
//	public void test1() {
//	// SOAP request(xml) read-in
//	 File req_xml = new File("test/xml/request.xml");
//
//	 // SOAP request send
//	 HttpPost post = new HttpPost("http://localhost:8080/test/api/");
//	 post.setEntity(new InputStreamEntity(new FileInputStream(req_xml), req_xml.length()));
//	 post.setHeader("Content-type", "text/xml; charset=UTF-8");
//	 post.setHeader("SOAPAction", "");
//	 HttpClient client = new DefaultHttpClient();
//	 HttpResponse response = client.execute(post);
//
//	 // SOAP response(xml) get
//	 String res_xml = EntityUtils.toString(response.getEntity());
//}

}
