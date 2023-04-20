package com.bsol.iri.fileSharing.clamAv.service;

import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * 
 * @author rupesh
 *	
 */
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bsol.iri.fileSharing.clamAv.dto.FileScanResponseDto;

public interface FileScanService {

	List<FileScanResponseDto> scanFiles(MultipartFile[] files);

	boolean isFileContainsVirus(MultipartFile file) throws IOException;

}
