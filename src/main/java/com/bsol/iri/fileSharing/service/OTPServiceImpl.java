package com.bsol.iri.fileSharing.service;

/**
 * @author rupesh
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.models.EmailVerificationDTO;
import com.bsol.iri.fileSharing.models.GenerateEmail;

@Service
public class OTPServiceImpl implements OTPService {
	private final Logger log = LoggerFactory.getLogger(OTPServiceImpl.class);

	@Autowired
	OtpGenerator otpGenerator;

	@Override
	public boolean generateOtp(GenerateEmail mailDetails) throws DataNotFoundException, CustomMessageException {
		if (mailDetails.getEmail().isEmpty()) {
			log.error("No receiptiant found");
			throw new DataNotFoundException("No RECEIPTIANT found");
		}
		for (String email : mailDetails.getEmail()) {
			log.debug("Sendong details to " + email);
			otpGenerator.generateUrl(email.toLowerCase(), mailDetails);
		}
		
		log.info("OTP generated email Sent with details {}",mailDetails);
		return true;
	}

	@Override
	public boolean validateEmailOTP(EmailVerificationDTO emailVerificationDto) {
		// TODO Auto-generated method stub
		return false;
	}

}
