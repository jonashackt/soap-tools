package de.codecentric.soaptools.rawclient;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.codecentric.soaptools.XmlUtils;
import de.codecentric.soaptools.exception.SoapToolsException;

@Component
public class HttpRawClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpRawClient.class);
	
	private String soapServiceUrl;
	
	public void setUrl(String soapServiceUrl) {
		this.soapServiceUrl = soapServiceUrl;
	}
	
	public HttpRawClientResponse callSoapService(String xmlFile) throws SoapToolsException {
		HttpRawClientResponse easyRawSoapResponse = null;
		
		try {
			easyRawSoapResponse = new HttpRawClientResponse();
			
			LOGGER.debug("Calling SoapService with POST on Apache HTTP-Client and configured URL: {}", soapServiceUrl);
			
			String soapAction = extractFirstElementNameInBody(xmlFile);
			
			Response httpResponseContainer = Request
					.Post(soapServiceUrl)
					.bodyString(xmlFile, contentTypeTextXmlUtf8())
					.addHeader("SOAPAction", "\"urn:getWeather\"")
					.execute();
			
			//TODO Extract SOAPAction from XML
			
			HttpResponse httpResponse = httpResponseContainer.returnResponse();			
			easyRawSoapResponse.setHttpStatusCode(httpResponse.getStatusLine().getStatusCode());
			easyRawSoapResponse.setHttpResponseBody(XmlUtils.parseFileStream2Document(httpResponse.getEntity().getContent()));
			
		} catch (Exception exception) {
			throw new SoapToolsException("Some Error accured while trying to Call SoapService for test: " + exception.getMessage());
		}		
		return easyRawSoapResponse;
	}
	
	private String extractFirstElementNameInBody(String xmlFile) {
		// TODO Auto-generated method stub
		return null;
	}

	private ContentType contentTypeTextXmlUtf8() {
		return ContentType.create(ContentType.TEXT_XML.getMimeType(), Consts.UTF_8);
	}
	
	
}
