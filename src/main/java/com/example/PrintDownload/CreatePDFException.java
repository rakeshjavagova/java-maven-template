package com.example.PrintDownload;

public class CreatePDFException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreatePDFException() {
        super();
    }

    public CreatePDFException(String message) {
        super(message);
    }

    public CreatePDFException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreatePDFException(Throwable cause) {
        super(cause);
    }
}