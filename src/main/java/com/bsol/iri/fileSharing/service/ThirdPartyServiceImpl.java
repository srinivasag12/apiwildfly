package com.bsol.iri.fileSharing.service;

/**
 * @author rupesh
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bsol.iri.fileSharing.emailService.ComposeEmail;
import com.bsol.iri.fileSharing.entity.DownloadLink;
import com.bsol.iri.fileSharing.entity.FileDownloadLink;
import com.bsol.iri.fileSharing.entity.LinkDetails;
import com.bsol.iri.fileSharing.entity.LinkOTP;
import com.bsol.iri.fileSharing.entity.MaFileLocation;
import com.bsol.iri.fileSharing.entity.MaUser;
import com.bsol.iri.fileSharing.entity.UploadedFiles;
import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.loginDetails.UserLoginDetailsService;
import com.bsol.iri.fileSharing.mappers.LinkDetailsMapper;
import com.bsol.iri.fileSharing.models.DashBoardSharedFile;
import com.bsol.iri.fileSharing.models.DownloadDetails;
import com.bsol.iri.fileSharing.models.ForgetPassword;
import com.bsol.iri.fileSharing.models.LinkDetailsModel;
import com.bsol.iri.fileSharing.models.LinkDetailsResponseModel;
import com.bsol.iri.fileSharing.models.LocationDetails;
import com.bsol.iri.fileSharing.models.ResetPassword;
import com.bsol.iri.fileSharing.models.ThirdPartyUpload;
import com.bsol.iri.fileSharing.models.VesselDetails;
import com.bsol.iri.fileSharing.models.thirdParty.Login;
import com.bsol.iri.fileSharing.models.thirdParty.UploadDetails;
import com.bsol.iri.fileSharing.repos.DownloadLinkRepo;
import com.bsol.iri.fileSharing.repos.FileDownloadLinkRepo;
import com.bsol.iri.fileSharing.repos.LinkDetailsRepo;
import com.bsol.iri.fileSharing.repos.LinkOtpRepo;
import com.bsol.iri.fileSharing.repos.MaFileLocationRepo;
import com.bsol.iri.fileSharing.repos.UploadedFileRepo;
import com.bsol.iri.fileSharing.repos.UserRepo;
import com.bsol.iri.fileSharing.storage.service.UploadDownload;
import com.bsol.iri.fileSharing.util.AppConstant;
import com.bsol.iri.fileSharing.util.DateTimeUtil;
import com.bsol.iri.fileSharing.util.EncryptionDecrytion;
import com.bsol.iri.fileSharing.util.SqlQueries;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {
	private final Logger log = LoggerFactory.getLogger(ThirdPartyServiceImpl.class);

	@Autowired
	private LinkDetailsRepo linkDetailsRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private LinkOtpRepo linkOtpRepo;

	@Autowired
	private UploadedFileRepo uploadedFileRepo;

	@Autowired
	private LocationDetails locationDetails;

	@Autowired
	private UploadDownload uploadDownload;

	@Autowired
	private MaFileLocationRepo fileLocationRepo;

	@Autowired
	private DownloadLinkRepo downloadLinkRepo;

	@Autowired
	private FileDownloadLinkRepo fileDownloadLinkRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private OtpGenerator otpGenerator;

	@Autowired
	private ComposeEmail composeEmail;

	@Autowired
	private UserLoginDetailsService userLoginDetailsService;

	@Override
	public Map<String, String> saveSharedDocument(MultipartFile file, UploadDetails uploadDetails,
			LinkDetails linkDetails) {
		Map<String, String> map = new HashMap<String, String>();
		Integer fileId = 0;
		try {
			log.debug("Inside saveSharedDocument with details file UploadDetails - {} linkDetails - {}", uploadDetails,
					linkDetails);

			log.debug("file name - {} file Size - {} KB", file.getName(), (file.getSize() / 1024));
			log.info("Saving details to database");
			String fileName = file.getOriginalFilename();
			String ext = StringUtils.getFilenameExtension(fileName).toLowerCase();
			UploadedFiles upFile = new UploadedFiles();
			upFile.setUserId(linkDetails.getUserId());
			upFile.setFileName(fileName);
			upFile.setIsArchive(AppConstant.FALSE);
			upFile.setIsDeleted(AppConstant.FALSE);
			upFile.setIsFolder(AppConstant.FALSE);
			upFile.setIsShared(AppConstant.FALSE);
			upFile.setFileSize(BigInteger.valueOf(file.getSize()));
			upFile.setFileType(ext);
			upFile.setCreatedBy(uploadDetails.getEmail());
			upFile.setCreatedOn(new Date());
			upFile.setLinkId(linkDetails.getLinkId());

			fileId = uploadedFileRepo.save(upFile).getFileId();
			String destination = locationDetails.getSharedDirectory() + fileId + "." + ext;

			if (uploadDownload.moveFile(file, destination)) {
				map.put("msg", fileName + "  Uploaded Successfully");
				map.put("operation", "Success");
				map.put("fileId", fileId.toString());

				MaFileLocation loc = new MaFileLocation();
				loc.setFileId(fileId);
				loc.setBucketName(destination);
				loc.setCreatedBy(uploadDetails.getEmail());
				loc.setCreatedOn(new Date());
				fileLocationRepo.save(loc);
				log.info("File {} Uploaded ", file.getOriginalFilename());
			} else {
				map.put("msg", "Unable to save " + fileName);
				map.put("operation", "failed");
				uploadedFileRepo.deleteById(fileId);
				log.error("File- {} not Uploaded, reverting the Transaction details", file.getOriginalFilename());
			}
		} catch (Exception e) {
			log.error("Some Exception Occured " + e.getMessage());
			if (fileId != 0)
				uploadedFileRepo.deleteById(fileId);
			map.put("msg", "Something Unexpected Occured");
			map.put("operation", "failed");
		}

		return map;
	}

	@Override
	public LinkDetailsResponseModel getLinkDetails(String link) throws DataNotFoundException, CustomMessageException {

		log.debug("Inside getLinkDetails with details - {} ", link);
		LinkDetails ld = linkDetailsRepo.findByLink(link);
		log.info("Ckecking Link Status");
		if (ld != null) {
			LinkDetailsModel model = new LinkDetailsMapper(userService.getDiscriptionMap())
					.linkDetailsToLinkDetailsModel(ld);
			if (model.getExpiryDate().compareTo(new Date()) < 0) {
				log.error("This link has been expired -  {}", link);
				throw new CustomMessageException("This link is Expired");
			} else if (model.getLinkStatus() == AppConstant.COMPLETED_LINK
					|| model.getLoggedInStatus() == AppConstant.COMPLETED_LOGGED_IN) {
				log.error("This link Activity is completed and Link is closed -  {}", link);
				throw new CustomMessageException("This link Activity is completed and Link is closed");
			} else if (model.getCancelRequest() == AppConstant.TRUE || model.getLinkStatus() == AppConstant.CANCELED_LINK) {
				log.error("This link has been canceled -  {}", link);
				throw new CustomMessageException("This link canceled");
			} else {

				log.info("link {}  details checked and returning the response", link);
				LinkDetailsResponseModel model2 = new LinkDetailsResponseModel();
				model2.setEmail(model.getEmail());
				model2.setLinkDesc(model.getLinkDesc());
				model2.setLinkType(model.getLinkType());
				model2.setVesselName(model.getVesselName());
				model2.setUsername(
						EncryptionDecrytion.decrypt((userRepo.findById(model.getUserId()).get().getEmail())));
				model2.setLinkId(model.getLinkId());
				return model2;

			} // else
		} else {

				log.info("checking {} in downlod link", link);
			DownloadLink dlinkList = downloadLinkRepo.findFirstByURL(link);

			if (dlinkList == null) {
				log.error("this {} is invalid link", link);
				throw new CustomMessageException("Invalid Link");
			}

			if (dlinkList.getLinkStatus() == AppConstant.ACTIVE
					|| dlinkList.getLinkStatus() == AppConstant.EXTENDED_LINK) {
				LinkDetailsResponseModel model = new LinkDetailsResponseModel();
				model.setEmail(dlinkList.getEmail());
				model.setLinkType(AppConstant.THIRD_PARTY_DOWNLOAD_ONLY);
				model.setLinkId(dlinkList.getLinkId());
				log.info("Response is setted and returning");
				log.info("Link {} found in download links returning details", link);
				return model;
			} else {
				throw new CustomMessageException("Link is not active, may be Canceled , Expired or Completed");
			}
		}

	}

	@Override
	public Integer authorizeUser(Login login) throws DataNotFoundException {
		log.trace("inside authorizeUser with details - {} ", login);
		LinkOTP otp = new LinkOTP();
		if (login.getLinkType() == AppConstant.BY_OWNER) {
			LinkDetails ld = linkDetailsRepo.findByEmailAndLinkAndLinkType(login.getUsername(), login.getUrl(),
					login.getLinkType());
			if (ld == null) {
				log.warn("No details found for the login {}", login);
				throw new DataNotFoundException("No data found with given Details");
			}
			otp = linkOtpRepo.findById(ld.getLinkId()).get();
			long seconds = TimeUnit.MILLISECONDS.toSeconds((new Date().getTime() - otp.getUpdatedOn().getTime()));
			if ((int) seconds > AppConstant.OTP_VALIDITY) {
				log.error("OTP Expired for login {} ", login);
				throw new DataNotFoundException("OTP Expired, Please Regenerate OTP");
			} else if (otp.getOtp().equals(Integer.valueOf(login.getPassword()))) {
				log.info("Data found for login details {}", login);
				ld.setLoggedInStatus(AppConstant.LOGGED_IN);
				ld.setUpdatedAt(DateTimeUtil.getTodaysDate());
				ld.setUpdatedBy(login.getUsername());
				linkDetailsRepo.save(ld);
				otp.setOtp(new Random().nextInt(100000));
				return linkOtpRepo.save(otp).getLinkId();

			}
		} else if (login.getLinkType() == AppConstant.THIRD_PARTY_DOWNLOAD_ONLY) {
			DownloadLink dlink = downloadLinkRepo.findFirstByURL(login.getUrl());
			if (dlink == null) {
				log.warn("No details found for the login {}", login);
				throw new DataNotFoundException("No data found with given Details");
			}
			Optional<LinkOTP> linkOptional = linkOtpRepo.findById(dlink.getLinkId());
			if (!linkOptional.isPresent()) {
				throw new DataNotFoundException("Please try login again");
			}

			otp = linkOptional.get();
			long seconds = TimeUnit.MILLISECONDS.toSeconds((new Date().getTime() - otp.getUpdatedOn().getTime()));
			if ((int) seconds > AppConstant.OTP_VALIDITY) {
				log.error("OTP Expired for login {}", login);
				throw new DataNotFoundException("OTP Expired, Please Regenerate OTP");
			} else if (otp.getOtp().equals(Integer.valueOf(login.getPassword()))) {
				log.info("Data found for login details {}", login);
				dlink.setLoggedInStatus(AppConstant.LOGGED_IN);
				downloadLinkRepo.save(dlink);
				otp.setOtp(new Random().nextInt(100000));
				return linkOtpRepo.save(otp).getLinkId();
			}
		}

		return 0;
	}

	@Override
	public Boolean deleteFile(Integer fileId) throws DataNotFoundException {

		log.trace("inside deleteFile with fileId - {}", fileId);
		Optional<UploadedFiles> uploadedFile = uploadedFileRepo.findById(fileId);
		if (uploadedFile.isPresent()) {
			log.info("Deleting the file wit id {}", fileId);
			MaFileLocation fileLocation = fileLocationRepo.findById(fileId).get();
			if (uploadDownload.removeFile(fileLocation.getBucketName())) {
				UploadedFiles uf = uploadedFile.get();
				uf.setIsDeleted(AppConstant.TRUE);
				uf.setUpdatedOn(DateTimeUtil.getTodaysDate());
				uploadedFileRepo.save(uf);
				return true;
			} else
				return false;
		} else {
			log.error("No file found with the file ID {}", fileId);
			throw new DataNotFoundException("No Data Found");
		}
	}

	@Override
	public ThirdPartyUpload getUncompleteUrlUploadsFiles(Integer linkId)
			throws DataNotFoundException, CustomMessageException {

		log.info("Inside getUncompleteUrlUploadsFiles with linkId - {}", linkId);
		ThirdPartyUpload uploadedData = new ThirdPartyUpload();
		Optional<LinkDetails> linkDetailsOptiobal = linkDetailsRepo.findById(linkId);
		if (!linkDetailsOptiobal.isPresent()) {
			throw new DataNotFoundException("Invalid link");
		}
		log.trace("Setting link detail data for link {}", linkId);
		LinkDetails linkDetails = linkDetailsOptiobal.get();
		Map<Integer, String> descMap = userService.getDiscriptionMap();
		uploadedData.setVesselName(linkDetails.getVesselName());
		uploadedData.setDesc(descMap.get(linkDetails.getLinkDesc()));
		uploadedData.setExpirtyDate(linkDetailsOptiobal.get().getExpiryDate());
		uploadedData.setVslOfficialNo(linkDetails.getOfficialNo());
		uploadedData.setImo(linkDetails.getImo());
		log.trace("getUncompleteUrlUploadsFiles for the link {}", linkId);
		List<UploadedFiles> uploadedFiles = uploadedFileRepo.findByLinkId(linkId);
		if (uploadedFiles.isEmpty()) {
			log.error("No file upaded by the user for the linkID {}", linkId);
			return uploadedData;
		}
		List<DashBoardSharedFile> sharedFileDetails = new ArrayList<DashBoardSharedFile>();
		log.info("Uploaded files found in the link id {}", linkId);
		for (UploadedFiles uploadedFile : uploadedFiles) {
			if (uploadedFile.getIsDeleted() == AppConstant.FALSE && uploadedFile.getIsArchive() == AppConstant.FALSE) {
				DashBoardSharedFile dsFile = new DashBoardSharedFile();
				dsFile.setId(BigInteger.valueOf(uploadedFile.getFileId()));
				dsFile.setFileSize(uploadedFile.getFileSize());
				dsFile.setName(uploadedFile.getFileName());
				dsFile.setType(uploadedFile.getFileType());
				dsFile.setSharedDate(uploadedFile.getCreatedOn());
				sharedFileDetails.add(dsFile);

			}

		}
		uploadedData.setUploadedFile(sharedFileDetails);
		return uploadedData;
	}

	@SuppressWarnings("deprecation")
	@Override
	public DownloadDetails getUploadedFiles(Integer linkIdl) throws CustomMessageException {
		log.info("Inside getUploadedFiles with linkId - {}", linkIdl);
		log.trace("inside getUploaded files");
		DownloadDetails dls = new DownloadDetails();
		Query vslDetails = entityManager.createNativeQuery(SqlQueries.VSL_DETAILS_BY_LINKID).unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(VesselDetails.class));

		log.info("Setting parameters to the query to get the upladed filesfor the link {}", linkIdl);
		vslDetails.setParameter("linkId", linkIdl);
		VesselDetails vslDetail = (VesselDetails) vslDetails.getSingleResult();
		log.info("Executing query to get the uploaded files for link id {}", linkIdl);
		dls.setImo(vslDetail.getVSL_IMO_NO());
		dls.setVesselName(vslDetail.getVESSEL_NAME());
		dls.setVesselOfficialNo(vslDetail.getVSL_OFFICIAL_NO());
		dls.setDESCRIPTION(vslDetail.getDESCRIPTION());
		dls.setExpDate(vslDetail.getEXPIRED_ON());
		log.trace("inside third party service impl getUploadedFiles() for the linkId {}", linkIdl);
		List<FileDownloadLink> fdList = fileDownloadLinkRepo.findByDownloadLinkId(linkIdl);
		List<Integer> files = new ArrayList<Integer>();
		for (FileDownloadLink fdl : fdList) {
			files.add(fdl.getFileId());
		}
		log.info("fetching the file related to linkId {}", linkIdl);
		List<UploadedFiles> uFileList = uploadedFileRepo.findByFileIdIn(files);
		List<com.bsol.iri.fileSharing.models.UploadedFiles> fileDetail = new ArrayList<com.bsol.iri.fileSharing.models.UploadedFiles>();

		for (UploadedFiles file : uFileList) {
			com.bsol.iri.fileSharing.models.UploadedFiles f = new com.bsol.iri.fileSharing.models.UploadedFiles();
			f.setFileId(file.getFileId());
			f.setFileSize(file.getFileSize());
			f.setName(file.getFileName());
			f.setType(file.getFileType());
			fileDetail.add(f);
		}
		dls.setFileDetails(fileDetail);
		return dls;
	}

	@Override
	public Integer forgetPwd(String email) throws CustomMessageException {
		log.info("fetching user details having email - {}", email);
		MaUser user = userRepo.findByEmail(email);
		if (user == null) {
			log.error("No email {} found to reset the password", email);
			throw new CustomMessageException("Invalid Email");
		}
		if (user.getStatus() != 1) {
			log.error("{} User is inactive, Please contact admin", email);
			throw new CustomMessageException("This User is inactive, Please contact admin");
		}
		log.info("Generating otp");
		Integer otp = otpGenerator.generateOTP(user.getUser_id());
		String body = String.format(AppConstant.RESET_PASSWORD_MESSAGE, otp);
		log.info("prepare to send email to {} for resting the password", email);
		if (composeEmail.composeDownloadLink(email, AppConstant.RESET_PASSWORD_SUBJECT, body)) {
			log.info("Email sent");
			return user.getUser_id();
		} else {
			throw new CustomMessageException("Unable to send email");
		}
	}

	@Override
	public Boolean verifyOTP(ForgetPassword forgetPwd) {
		log.debug("Inside verifyOTP forgetPwd details - {}", forgetPwd);
		if (linkOtpRepo.existsByLinkIdAndOtp(forgetPwd.getUserId(), forgetPwd.getOtp()))
			return true;
		else
			return false;
	}

	@Override
	public Boolean resetPwd(ResetPassword resetPwd) throws CustomMessageException {
		log.debug("Inside resetPwd resetPwd details - {}", resetPwd);

		log.info("Getting user details");
		Optional<MaUser> userOptional = userRepo.findById(resetPwd.getUserID());
		if (!userOptional.isPresent()) {
			log.error("No user found with the user Id ", resetPwd.getUserID());
			throw new CustomMessageException("No user Found");
		}

		MaUser user = userOptional.get();
		if (user.getStatus() == AppConstant.INACTIVE) {
			throw new CustomMessageException(AppConstant.INACTIVE_USER);
		}
		user.setPwd(new BCryptPasswordEncoder().encode(resetPwd.getPwd()));
		user.setStatus(AppConstant.ACTIVE);
		log.info("Update the password for user {}", user.getEmail());
		userRepo.save(user).getUser_id();
		log.info("Deactivating the OTP for update password for user {}", user.getEmail());
		linkOtpRepo.deleteById(user.getUser_id());

		userLoginDetailsService.createNewUserPassword(user.getEmail(), resetPwd.getPwd(), resetPwd.getHint());

		return composeEmail.composeDownloadLink(user.getEmail(), AppConstant.PASSWORD_CHANGED,
				AppConstant.PASSWORD_CHANGED_BODY);
	}
}
