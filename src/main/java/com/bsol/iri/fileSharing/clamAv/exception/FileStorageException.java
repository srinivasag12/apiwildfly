package com.bsol.iri.fileSharing.clamAv.exception;

public class FileStorageException extends RuntimeException {
   
	/**
	 * 
	 * @author rupesh
	 *	This is a exception class, called when any exception is rised on file storing.
	 */
	
	private static final long serialVersionUID = -1529077237022454933L;

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
