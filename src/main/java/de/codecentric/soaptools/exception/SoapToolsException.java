package de.codecentric.soaptools.exception;

public class SoapToolsException extends Exception {

    private static final long serialVersionUID = 1L;

    public SoapToolsException(Throwable cause) {
        super(cause);
    }

    public SoapToolsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SoapToolsException(String message) {
        super(message);
    }
}
