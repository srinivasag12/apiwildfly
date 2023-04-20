package com.bsol.iri.fileSharing.clamAv.exception;

public class ClamAVSizeLimitException extends  RuntimeException{

	/**
	 * 
	 * @author rupesh
	 * This is a exception class , called when file Size is Exceeded
	 */
	
	private static final long serialVersionUID = -6342281164492350777L;

	public ClamAVSizeLimitException(String msg) {
        super(msg);
    }
}
