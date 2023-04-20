package com.bsol.iri.fileSharing.storage.service;
/**
 * 
 * @author rupesh
 *	
 */
import org.springframework.web.multipart.MultipartFile;

public interface UploadDownload {

	boolean moveFile(MultipartFile source, String destination);
	
	boolean moveFile(String source, String destination);
	
	public boolean removeFile(String source) ;
}
