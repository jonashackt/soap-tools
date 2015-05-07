package de.codecentric.soaptools;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import de.codecentric.soaptools.exception.SoapToolsException;

public class FileUtils {

	private static final String XML_FILES_FOLDER = "requests/";
	
	public static String readFileInClasspath2String(String fileName) throws SoapToolsException {
		String file;
		try {
			Path filepath = Paths.get(buildFileUri(fileName));
			file = Files.lines(filepath).collect(Collectors.joining());
		} catch (Exception exception) {
			throw new SoapToolsException("Could not read File: " + exception.getMessage());
		}
		return file;
	}

	public static <T> T readSoapMessageFromFileAndUnmarshallBody2Object(String fileName, Class<T> jaxbClass) throws SoapToolsException {
		return XmlUtils.readSoapMessageFromStreamAndUnmarshallBody2Object(readFileInClasspath2InputStream(fileName), jaxbClass);
	}
	
	private static FileInputStream readFileInClasspath2InputStream(String fileName) throws SoapToolsException {
		FileInputStream fileInputStream = null;
		try {
			File file = new File(buildFileUri(fileName));
			fileInputStream = new FileInputStream(file);
		} catch (Exception exception) {
			throw new SoapToolsException("Could not read File: " + exception.getMessage());
		}
		return fileInputStream;		
	}		
	
	private static URI buildFileUri(String fileName) throws URISyntaxException {
		return FileUtils.class.getClassLoader().getResource(XML_FILES_FOLDER + fileName).toURI();
	}
	
}