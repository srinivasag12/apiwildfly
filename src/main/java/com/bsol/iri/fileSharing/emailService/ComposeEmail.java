package com.bsol.iri.fileSharing.emailService;

/**
 * 
 * @author rupesh
 *
 */

import com.bsol.iri.fileSharing.entity.FolderDetails;
import com.bsol.iri.fileSharing.entity.LinkDetails;
import com.bsol.iri.fileSharing.models.GenerateEmail;

public interface ComposeEmail {

	Boolean composeUrl(String emailId, String url, GenerateEmail mailDetails, String expiryDate, String desc);
	Boolean composeDownloadLink(String recep, String sub, String body);
	Boolean composeOTP(LinkDetails ld, Integer otp, String desc);
	Boolean comporeOTP(String email, FolderDetails fd, Integer otp,String description);
}
