package com.bsol.iri.fileSharing.emailService;

/**
 * 
 * @author rupesh
 * In This class we are composing the Emails.  If you uncomments the EmailService statements then it will start using the email Services which are mentioned on peoperties files
 *
 */

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsol.iri.fileSharing.entity.FolderDetails;
import com.bsol.iri.fileSharing.entity.LinkDetails;
import com.bsol.iri.fileSharing.models.GenerateEmail;
import com.bsol.iri.fileSharing.repos.EmailRepo;
import com.bsol.iri.fileSharing.util.AppConstant;

@Service
public class ComposeEmailImpl implements ComposeEmail {

	@Autowired
	EmailProviderConfiguration email;

	@Autowired
	private EmailRepo emailRepo;

	private static Logger log = LoggerFactory.getLogger(ComposeEmailImpl.class);

	@Override
	public Boolean composeUrl(String emailId, String url, GenerateEmail mailDetails, String expiryDate, String desc) {
		log.info("inside composeURL to generate UPLOAD link");
		log.debug("compose URL details  {} {} {} {}  {} {} {}", emailId, url, mailDetails.getImoNo(),
				mailDetails.getVesselName(), mailDetails.getVesselOfficialNo(), expiryDate, desc);
		List<String> recipients = new ArrayList<>();
		EmailDTO urlEmail = new EmailDTO();
		recipients.add(emailId);

		// Configuring URL
		urlEmail.setRecipients(recipients);

		urlEmail.setSubject(AppConstant.UPLOAD_URL_SUBJECT);
		urlEmail.setBody(String.format(AppConstant.UPLOAD_URL_BODY, email.getUiHost() + url,
				mailDetails.getVesselName(), desc, mailDetails.getVesselName(), expiryDate));
//		return emailService.sendEmail(urlEmail);
		log.info("Sending email with Link to email {}", emailId);
		emailRepo.SendEmail(urlEmail.getRecipients().get(0), urlEmail.getSubject(), urlEmail.getBody());
		return true;
	}

	@Override
	public Boolean composeOTP(LinkDetails ld, Integer otp, String description) {
		log.info("inside composeOTP opt for Link id {}  is ", ld.getLinkId(), otp);
		List<String> recipients = new ArrayList<>();
		EmailDTO otpEmail = new EmailDTO();
		recipients.add(ld.getEmail());

		// Configuring URL
		otpEmail.setRecipients(recipients);
		otpEmail.setSubject(AppConstant.OTP_SUBJECT);
		otpEmail.setBody(String.format(AppConstant.OTP_BODY, otp, ld.getVesselName(), description));
		// return emailService.sendEmail(otpEmail);
		log.info("Sending otp email to email {} with otp {}", ld.getEmail(), otp);
		emailRepo.SendEmail(otpEmail.getRecipients().get(0), otpEmail.getSubject(), otpEmail.getBody());
		return true;
	}

	@Override
	public Boolean composeDownloadLink(String receps, String sub, String body) {

		log.debug("Sending email with details receiptian -  {} sub -  {}  body - {}", receps, sub, body);
		List<String> recipients = new ArrayList<>();
		EmailDTO email = new EmailDTO();
		recipients.add(receps);
		email.setRecipients(recipients);
		email.setSubject(sub);
		email.setBody(body);
		// return emailService.sendEmail(email);

		emailRepo.SendEmail(email.getRecipients().get(0), email.getSubject(), email.getBody());
		log.info("Email sent to email id {}", receps);
		return true;

	}

	@Override
	public Boolean comporeOTP(String emailID, FolderDetails fd, Integer otp, String description) {

		log.info("composing OTP email for {} ", emailID);
		List<String> recipients = new ArrayList<>();
		EmailDTO otpEmail = new EmailDTO();
		recipients.add(emailID);

		// Configuring URL
		otpEmail.setRecipients(recipients);
		otpEmail.setSubject(AppConstant.OTP_SUBJECT);
		otpEmail.setBody(String.format(AppConstant.OTP_BODY, otp, fd.getVesselName(), description));
		// return emailService.sendEmail(otpEmail);
		emailRepo.SendEmail(otpEmail.getRecipients().get(0), otpEmail.getSubject(), otpEmail.getBody());

		log.debug("otp email sent to {} with following details {} {}", otpEmail.getRecipients().get(0),
				otpEmail.getSubject(), otpEmail.getBody());
		return true;
	}

}
