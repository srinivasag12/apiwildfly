package com.bsol.iri.fileSharing.service;

/**
 * @author rupesh
 */

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bsol.iri.fileSharing.entity.LinkDetails;
import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.models.DashBoardSharedFile;
import com.bsol.iri.fileSharing.models.DownloadDetails;
import com.bsol.iri.fileSharing.models.ForgetPassword;
import com.bsol.iri.fileSharing.models.LinkDetailsResponseModel;
import com.bsol.iri.fileSharing.models.ResetPassword;
import com.bsol.iri.fileSharing.models.ThirdPartyUpload;
import com.bsol.iri.fileSharing.models.UploadedFiles;
import com.bsol.iri.fileSharing.models.thirdParty.Login;
import com.bsol.iri.fileSharing.models.thirdParty.UploadDetails;

public interface ThirdPartyService {

	public Map<String, String> saveSharedDocument(MultipartFile file, UploadDetails uploadDetails,
			LinkDetails linkDetails);

	LinkDetailsResponseModel getLinkDetails(String link) throws DataNotFoundException, CustomMessageException;

	Integer authorizeUser(Login login) throws DataNotFoundException;

	Boolean deleteFile(Integer fileId) throws DataNotFoundException;

	ThirdPartyUpload getUncompleteUrlUploadsFiles(Integer linkId) throws DataNotFoundException, CustomMessageException;

	DownloadDetails getUploadedFiles(Integer linkId) throws CustomMessageException;

	Integer forgetPwd(String email) throws CustomMessageException;

	Boolean verifyOTP(ForgetPassword forgetPwd);

	Boolean resetPwd(ResetPassword resetPwd) throws CustomMessageException;
}
