package com.bsol.iri.fileSharing.service;

/**
 * @author rupesh
 */

import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.models.EmailVerificationDTO;
import com.bsol.iri.fileSharing.models.GenerateEmail;

public interface OTPService {

	boolean generateOtp(GenerateEmail mailDetails) throws DataNotFoundException, CustomMessageException;

	boolean validateEmailOTP(EmailVerificationDTO emailVerificationDto);
}
