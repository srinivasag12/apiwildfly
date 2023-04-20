package com.bsol.iri.fileSharing.service;

/**
 * @author rupesh
 */
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsol.iri.fileSharing.emailService.ComposeEmail;
import com.bsol.iri.fileSharing.entity.DownloadLink;
import com.bsol.iri.fileSharing.entity.FolderDetails;
import com.bsol.iri.fileSharing.entity.LinkDetails;
import com.bsol.iri.fileSharing.entity.LinkOTP;
import com.bsol.iri.fileSharing.entity.MaDescription;
import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.models.GenerateEmail;
import com.bsol.iri.fileSharing.repos.DownloadLinkRepo;
import com.bsol.iri.fileSharing.repos.FolderDetailsRepo;
import com.bsol.iri.fileSharing.repos.LinkDetailsRepo;
import com.bsol.iri.fileSharing.repos.LinkOtpRepo;
import com.bsol.iri.fileSharing.repos.MaDescriptionRepo;
import com.bsol.iri.fileSharing.util.AppConstant;
import com.bsol.iri.fileSharing.util.DateTimeUtil;

@Service
public class OtpGenerator {

	private final Logger log = LoggerFactory.getLogger(OtpGenerator.class);
	@Autowired
	private LinkDetailsRepo linkDetailsRepo;

	@Autowired
	private LinkOtpRepo linkOtpRepo;

	@Autowired
	private ComposeEmail composeEmail;

	@Autowired
	private DownloadLinkRepo downloadLinkRepo;

	@Autowired
	private FolderDetailsRepo folderDetailsRepo;

	@Autowired
	private MaDescriptionRepo maDescriptionRepo;

	private final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";

	/**
	 * Method for generating OTP and put it in cache.
	 * 
	 * @throws CustomMessageException
	 *
	 */
	public Boolean generateUrl(String email, GenerateEmail mailDetails) throws CustomMessageException {

		log.info("Inside generateUrl");
		// find the link with details

		List<LinkDetails> linkDetailsList = linkDetailsRepo.findByEmailAndVesselNameAndLinkDescAndImoAndOfficialNo(
				email.trim(), mailDetails.getVesselName().trim(), mailDetails.getDesc(), mailDetails.getImoNo(),
				mailDetails.getVesselOfficialNo());
		log.info("Checking Link Status");

		for (LinkDetails linkDetail : linkDetailsList) {
			if (linkDetail.getCancelRequest() == AppConstant.FALSE
					&& (linkDetail.getLinkStatus() != AppConstant.COMPLETED_LINK
							|| linkDetail.getLoggedInStatus() != AppConstant.COMPLETED_LOGGED_IN)) {
				throw new CustomMessageException(
						" An upload URL is active for this Email, vessel and description, that will expired on "
								+ linkDetail.getExpiryDate());
			}
		}

		String description = getDescription(mailDetails.getDesc());

		LinkDetails details = new LinkDetails();
		details.setUserId(mailDetails.getUserId());
		details.setEmail(email.trim());
		details.setVesselName(mailDetails.getVesselName().trim());
		details.setLinkDesc(mailDetails.getDesc());
		details.setLink(randomAlphaNumeric());
		details.setCreatedAt(DateTimeUtil.getTodaysDate());
		details.setImo(mailDetails.getImoNo());
		details.setOfficialNo(mailDetails.getVesselOfficialNo());
		details.setExpiryDate(DateTimeUtil.addDaysToCurrectDate(mailDetails.getDays()));
		details.setIsExtended(AppConstant.FALSE);
		details.setLinkStatus(AppConstant.ACTIVE_LINK);
		details.setCancelRequest(AppConstant.FALSE);
		details.setLoggedInStatus(AppConstant.NOT_YET_LOGGED_IN);
		details.setIsArchive(AppConstant.FALSE);
		details.setIsViewed(AppConstant.FALSE);

		if (mailDetails.getLinkType() != null)
			details.setLinkType(mailDetails.getLinkType()); // We will set this later based on request
		else
			details.setLinkType(AppConstant.BY_OWNER);

		LinkDetails linkDtls = linkDetailsRepo.save(details);
		// Send email
		if (composeEmail.composeUrl(email, linkDtls.getLink(), mailDetails, linkDtls.getExpiryDate().toString(),
				description)) {
			generateOnlyOTP(linkDtls.getLinkId());
			log.info("Email Sent email",email);
			return true;
		} else {
			log.info("Email not Sent, reverting the Transaction");
			linkDetailsRepo.deleteById(linkDtls.getLinkId());
			return false;
		}
	}

	// Generate OTP to Give email

//	public Boolean generateOTP(Integer linkId, String email, String vessel, String desc) {
//		log.info("Generarating Random Number");
//		Random random = new Random();
//		int OTP = 100000 + random.nextInt(900000);
//
//		LinkOTP otp = new LinkOTP();
//		otp.setLinkId(linkId);
//		otp.setOtp(OTP);
//		otp.setCreatedOn(DateTimeUtil.getTodaysDate());
//
//		if (linkOtpRepo.save(otp).getOtp() > 0) {
//			log.info("Sending Email");
//			if (composeEmail.composeOTP(email, OTP, vessel, desc)) {
//				log.info("Email Sent");
//				return true;
//			} else {
//				log.info("Email not Sent, reverting the OTP repo details");
//				linkOtpRepo.deleteById(linkId);
//			}
//		}
//		return false;
//	}

	// Generate OTP with urn this is used for third party login

	public Integer generateOTP(String urn) throws DataNotFoundException, CustomMessageException {
		LinkDetails ld = linkDetailsRepo.findByLink(urn);
		if (ld != null) {
			String description = getDescription(ld.getLinkDesc());
			Optional<LinkOTP> otp = linkOtpRepo.findById(ld.getLinkId());
			if (otp.isPresent()) {
				LinkOTP lOtp = otp.get();
				Random random = new Random();
				int OTP = 100000 + random.nextInt(900000);
				lOtp.setOtp(OTP);
				lOtp.setUpdatedOn(DateTimeUtil.getTodaysDate());
				log.info(" OTP Generated");
				if (composeEmail.composeOTP(ld, OTP, description)) {
					log.info("Email Sent");
					return linkOtpRepo.save(lOtp).getOtp();
				} else {
					log.error("Unable to sent email");
					throw new DataNotFoundException("Otp Generated, Unable to Send email");
				}
			} else {
				log.error("Unable to generate the OTP");
				throw new DataNotFoundException("Can not Generate the OTP");
			}
		} else {
			DownloadLink dlink = downloadLinkRepo.findFirstByURL(urn);
			if (dlink == null) {
				throw new DataNotFoundException("Invalid URL");
			} else {
				Optional<LinkOTP> otp = linkOtpRepo.findById(dlink.getLinkId());
				if (otp.isPresent()) {
					LinkOTP lOtp = otp.get();
					Random random = new Random();
					int OTP = 100000 + random.nextInt(900000);
					lOtp.setOtp(OTP);
					lOtp.setUpdatedOn(DateTimeUtil.getTodaysDate());
					log.info(" OTP Generated");
					FolderDetails fd = folderDetailsRepo.findById(dlink.getFolderId()).get();

					String description = getDescription(fd.getVesselDesc());

					if (composeEmail.comporeOTP(dlink.getEmail(), fd, OTP, description)) {
						log.info(" Email Sent");
						return linkOtpRepo.save(lOtp).getOtp();
					}
				} else {
					log.error("Unable to generate the OTP");
					throw new DataNotFoundException("Can not Generate the OTP");
				}
			}
		}
		return null;
	}

	public String randomAlphaNumeric() {
		int count = randomNumber();
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();

	}

	private int randomNumber() {
		return new Random().nextInt(40 - 25) + 20;

	}

	public Boolean generateOnlyOTP(Integer linkId) {

		Random random = new Random();
		int OTP = 100000 + random.nextInt(900000);

		LinkOTP otp = new LinkOTP();
		otp.setLinkId(linkId);
		otp.setOtp(OTP);
		otp.setCreatedOn(DateTimeUtil.getTodaysDate());
		otp.setUpdatedOn(DateTimeUtil.getTodaysDate());
		log.info("OTP generated");
		if (linkOtpRepo.save(otp).getOtp() > 0) {
			log.info("OTP data Updated");
			return true;
		}
		return false;
	}

	public String getDescription(Integer discriptionId) throws CustomMessageException {
		Optional<MaDescription> desrOptional = maDescriptionRepo.findById(discriptionId);
		if (!desrOptional.isPresent()) {
			throw new CustomMessageException("Invalid Description");
		}
		return desrOptional.get().getDesc();
	}

	public Integer generateOTP(Integer linkId) {

		Random random = new Random();
		int OTP = 100000 + random.nextInt(900000);

		LinkOTP otp = new LinkOTP();
		otp.setLinkId(linkId);
		otp.setOtp(OTP);
		otp.setCreatedOn(DateTimeUtil.getTodaysDate());
		otp.setUpdatedOn(DateTimeUtil.getTodaysDate());
		log.info("OTP generated");
		if (linkOtpRepo.save(otp).getOtp() > 0) {
			log.info("OTP data Updated");
			return OTP;
		}
		return 0;
	}
}
