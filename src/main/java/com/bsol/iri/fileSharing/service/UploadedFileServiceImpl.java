package com.bsol.iri.fileSharing.service;

/**
 * @author rupesh
 */

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsol.iri.fileSharing.entity.UploadedFiles;
import com.bsol.iri.fileSharing.util.AppConstant;
import com.bsol.iri.fileSharing.util.EncryptionDecrytion;

public class UploadedFileServiceImpl implements UploadedFileService {

	private static final Logger log = LoggerFactory.getLogger(UploadedFileServiceImpl.class);

	@Override
	public boolean addInitialFolder(Integer userId, Integer fileId) {
		log.info("creating the required initial folders for the user ID ", userId);
		UploadedFiles files = new UploadedFiles();
		files.setFileName(EncryptionDecrytion.encrypt(userId.toString()));
		files.setIsFolder(AppConstant.TRUE);
		files.setCreatedOn(new Date());
		log.info("Initial folder created for the user ID {} with the folder name {}", userId,
				EncryptionDecrytion.encrypt(userId.toString()));
		return false;
	}

}
