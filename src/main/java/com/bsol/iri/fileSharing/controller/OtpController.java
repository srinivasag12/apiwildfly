package com.bsol.iri.fileSharing.controller;

/**
 * 
 * @author rupesh
 *
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.models.GenerateEmail;
import com.bsol.iri.fileSharing.service.OTPService;

@RestController
@RequestMapping("api/link")
public class OtpController {

	private static final Logger log = LoggerFactory.getLogger(OtpController.class);

	@Autowired
	private OTPService otpService;

	/**
	 * This method is used to generate the Otp
	 * 
	 * @return
	 * @throws DataNotFoundException
	 * @throws CustomMessageException
	 */
	@PostMapping(value = "generate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> generateOTP(@RequestBody GenerateEmail mailDetails)
			throws DataNotFoundException, CustomMessageException {

		log.info("Inside api/link/generate");
		log.debug("Params are {}", mailDetails);
		return new ResponseEntity<Boolean>(otpService.generateOtp(mailDetails), HttpStatus.OK);
	}

	/**
	 * This method is used to verify the otp
	 * 
	 * @param otp
	 * @return
	 */
//	@PostMapping(value = "validate", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Boolean> validateEmailOTP(@RequestBody EmailVerificationDTO emailVerificationDto) {
//		return new ResponseEntity<Boolean>(otpService.validateEmailOTP(emailVerificationDto), HttpStatus.OK);
//	}
}
