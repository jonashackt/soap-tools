package de.codecentric.soapi.rawclient;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.codecentric.soapi.rawclient.exception.SoApiRawClientException;
import de.codecentric.soapi.rawclient.util.XmlUtils;




public class SoApiRawClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(SoApiRawClient.class);
	
	private String soapServiceUrl;
	
	public void setUrl(String soapServiceUrl) {
		this.soapServiceUrl = soapServiceUrl;
	}
	
	public SoApiRawClientResponse callSoapService(String xmlFile) throws SoApiRawClientException {
		SoApiRawClientResponse easyRawSoapResponse = null;
		
		try {
			easyRawSoapResponse = new SoApiRawClientResponse();
			
			LOGGER.debug("Calling SoapService with POST on Apache HTTP-Client and configured URL: {}", soapServiceUrl);
			
			Response httpResponseContainer = Request
					.Post(soapServiceUrl)
					.bodyString(xmlFile, contentTypeTextXmlUtf8())
					.addHeader("SOAPAction", "\"urn:getWeather\"")
					.execute();
			
			//TODO Extract SOAPAction from XML
			
			HttpResponse httpResponse = httpResponseContainer.returnResponse();			
			easyRawSoapResponse.setHttpStatusCode(httpResponse.getStatusLine().getStatusCode());
			easyRawSoapResponse.setHttpResponseBody(XmlUtils.parseContent2Document(httpResponse.getEntity().getContent()));
			
		} catch (Exception exception) {
			throw new SoApiRawClientException("Some Error accured while trying to Call SoapService for test: " + exception.getMessage());
		}		
		return easyRawSoapResponse;
	}
	
	private ContentType contentTypeTextXmlUtf8() {
		return ContentType.create(ContentType.TEXT_XML.getMimeType(), Consts.UTF_8);
	}
	
	
}
