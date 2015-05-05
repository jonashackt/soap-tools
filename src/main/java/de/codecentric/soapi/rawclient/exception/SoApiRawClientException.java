package de.codecentric.soapi.rawclient.exception;

public class SoApiRawClientException extends Exception {

    private static final long serialVersionUID = 1L;

    public SoApiRawClientException(Throwable cause) {
        super(cause);
    }

    public SoApiRawClientException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SoApiRawClientException(String message) {
        super(message);
    }
}
