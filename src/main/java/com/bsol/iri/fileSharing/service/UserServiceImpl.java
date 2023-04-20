package com.bsol.iri.fileSharing.service;

import java.io.IOException;

/**
 * @author rupesh
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bsol.iri.fileSharing.clamAv.dto.FileScanResponseDto;
import com.bsol.iri.fileSharing.clamAv.service.FileScanService;
import com.bsol.iri.fileSharing.emailService.ComposeEmail;
import com.bsol.iri.fileSharing.emailService.EmailProviderConfiguration;
import com.bsol.iri.fileSharing.entity.DownloadLink;
import com.bsol.iri.fileSharing.entity.FileDownloadLink;
import com.bsol.iri.fileSharing.entity.FileDownloadLinkId;
import com.bsol.iri.fileSharing.entity.FolderDetails;
import com.bsol.iri.fileSharing.entity.LinkDetails;
import com.bsol.iri.fileSharing.entity.LoginDetailsHistory;
import com.bsol.iri.fileSharing.entity.MaDescription;
import com.bsol.iri.fileSharing.entity.MaFileLocation;
import com.bsol.iri.fileSharing.entity.MaUser;
import com.bsol.iri.fileSharing.entity.UploadedFiles;
import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.loginDetails.UserLoginDetailsService;
import com.bsol.iri.fileSharing.mappers.DownLinkToLinkDetails;
import com.bsol.iri.fileSharing.mappers.HistoryMapper;
import com.bsol.iri.fileSharing.mappers.LinkDetailsMapper;
import com.bsol.iri.fileSharing.mappers.SharedLinkToDashBoard;
import com.bsol.iri.fileSharing.mappers.UploadedFileModelMapper;
import com.bsol.iri.fileSharing.mappers.UploadedToArchieveFile;
import com.bsol.iri.fileSharing.models.AllLinkDetailsResponse;
import com.bsol.iri.fileSharing.models.ArchiveDetails;
import com.bsol.iri.fileSharing.models.ArchiveFolder;
import com.bsol.iri.fileSharing.models.ArchiveLink;
import com.bsol.iri.fileSharing.models.ArchivedFileDetails;
import com.bsol.iri.fileSharing.models.ArchivedFolderDetails;
import com.bsol.iri.fileSharing.models.ArchivedLinkDetails;
import com.bsol.iri.fileSharing.models.CommunicationDetails;
import com.bsol.iri.fileSharing.models.DashBoardData;
import com.bsol.iri.fileSharing.models.DashBoardLinkDetails;
import com.bsol.iri.fileSharing.models.DashBoardSharedFile;
import com.bsol.iri.fileSharing.models.Description;
import com.bsol.iri.fileSharing.models.Folder;
import com.bsol.iri.fileSharing.models.LinkCommunicationHistory;
import com.bsol.iri.fileSharing.models.LinkDetail;
import com.bsol.iri.fileSharing.models.LinkHistory;
import com.bsol.iri.fileSharing.models.LinkIdArray;
import com.bsol.iri.fileSharing.models.LinkIdWithEmail;
import com.bsol.iri.fileSharing.models.LinkStatusChangeReq;
import com.bsol.iri.fileSharing.models.LinkValidity;
import com.bsol.iri.fileSharing.models.LocationDetails;
import com.bsol.iri.fileSharing.models.PrevouslyUploadedFiles;
import com.bsol.iri.fileSharing.models.RemoveSpecificFile;
import com.bsol.iri.fileSharing.models.ShareFile;
import com.bsol.iri.fileSharing.models.SharedFileResponse;
import com.bsol.iri.fileSharing.models.SharedToThirdPartyFiles;
import com.bsol.iri.fileSharing.models.UnArchiveRequest;
import com.bsol.iri.fileSharing.models.UpdatePwd;
import com.bsol.iri.fileSharing.models.UserModel;
import com.bsol.iri.fileSharing.models.thirdParty.UploadDetails;
import com.bsol.iri.fileSharing.repos.DownloadLinkRepo;
import com.bsol.iri.fileSharing.repos.FileDownloadLinkRepo;
import com.bsol.iri.fileSharing.repos.FolderDetailsRepo;
import com.bsol.iri.fileSharing.repos.LinkDetailsRepo;
import com.bsol.iri.fileSharing.repos.LoginDetailsHistoryRepo;
import com.bsol.iri.fileSharing.repos.MaDescriptionRepo;
import com.bsol.iri.fileSharing.repos.MaFileLocationRepo;
import com.bsol.iri.fileSharing.repos.UploadedFileRepo;
import com.bsol.iri.fileSharing.repos.UserRepo;
import com.bsol.iri.fileSharing.storage.service.UploadDownload;
import com.bsol.iri.fileSharing.util.AppConstant;
import com.bsol.iri.fileSharing.util.ClearTempFolder;
import com.bsol.iri.fileSharing.util.DateTimeUtil;
import com.bsol.iri.fileSharing.util.EncryptionDecrytion;
import com.bsol.iri.fileSharing.util.SqlQueries;

@Service
@SuppressWarnings({ "deprecation", "unchecked" })
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private LinkDetailsRepo linkDetailsRepo;

	@Autowired
	private UploadedFileRepo uploadedFileRepo;

	@Autowired
	private OtpGenerator otpGenerator;

	@Autowired
	private FileScanService fileScanService;

	@Autowired
	private FolderDetailsRepo folderDeatilsRepo;

	@Autowired
	private ComposeEmail composeEmail;

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
	private EmailProviderConfiguration emailProv;

	@Autowired
	private MaDescriptionRepo maDescriptionRepo;

	@Autowired
	private ClearTempFolder clearTempFolder;

	@Autowired
	private LoginDetailsHistoryRepo loginDetailsHistoryRepo;

	@Autowired
	private UserLoginDetailsService userLoginDetailsService;

	@Override
	public Map<String, String> authUser(UserModel user) {
		log.info("Inside authUser with details - {}", user);
		Map<String, String> map = new HashMap<>();
		MaUser usr = userRepo.findByEmail(user.getEmail());
		if (usr != null && new BCryptPasswordEncoder().matches(user.getPwd(), usr.getPwd())) {
			map.put("userId", usr.getUser_id().toString());
			map.put("firstName", usr.getFirstName());
			map.put("lastName", usr.getLastName());
			if (usr.getLastLogIn() == null) {
				usr.setLastLogIn(new Date());
				userRepo.save(usr);
				return map;
			}
			map.put("parentId", EncryptionDecrytion.encryptWithoutSlace(usr.getUser_id().toString()));
			map.put("role", usr.getRole().toString());
			map.put("last_login", usr.getLastLogIn() != null ? usr.getLastLogIn().toString() : null);
			map.put("last_logOut", usr.getLastLogOut() != null ? usr.getLastLogOut().toString() : null);
			map.put("passwordExpiredOn", pwdExpiredOn(user.getEmail()));
			usr.setLastLogIn(new Date());
			userRepo.save(usr);
			log.info("User details founds updating the last login for user {}", user.getEmail());

		} else {
			log.error("No User found with the email {}", user.getEmail());
			map.put("userId", "Invalid Credentials");
		}
		return map;
	}

	@Override
	public ResponseEntity<Map<String, String>> logoutUser(Integer userId, HttpServletRequest request)
			throws DataNotFoundException {
		log.info("Inside logoutUser with userId - {}", userId);
		String authAccess = request.getHeader("authorization");
		String refresh_Token = "";
		Map<String, String> msg = new HashMap<>();
		HttpStatus status = null;
		log.info("checking authorization for user id {}", userId);
		if (authAccess != null) {
			log.info("Getting access token");
			String accesstokn = authAccess.replace("Bearer", "").trim();
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(accesstokn);
			if (accessToken != null) {
				log.info("Trying to revoke the token for the userid {}", userId);
				if (revokeToken(accessToken, tokenStore, refresh_Token)) {
					log.info("Token removed , updating User Details for userid {}", userId);
					Optional<MaUser> usr = userRepo.findById(userId);
					if (!usr.isPresent()) {
						log.error("No user found for with userId {}", userId);
						throw new DataNotFoundException("No User Found");
					}
					MaUser user = usr.get();
					user.setLastLogOut(new Date());
					userRepo.save(user);
					log.info("User Logged out Successfully {}", userId);
					msg.put("msg", "User logged out successfully");
					status = HttpStatus.OK;
				}
			} else {
				log.error("Invalid access token for the user ID {}", userId);
				msg.put("msg", "Invalid access token: " + accesstokn);
				status = HttpStatus.BAD_REQUEST;
			}
		} else {
			log.error("Access Token is empty for the user ID {} can not logout", userId);
			msg.put("msg", "access token is empty");
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<Map<String, String>>(msg, status);

	}

	@Override
	public List<SharedFileResponse> getSharedFiles(Integer userId) throws DataNotFoundException {

		log.info("inside getSharedFiles userId {}", userId);
		Query sharedFileQuery = entityManager.createNativeQuery(SqlQueries.sharedFiles).unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(SharedFileResponse.class));

		sharedFileQuery.setParameter("linkStatus", AppConstant.COMPLETED_LINK);
		sharedFileQuery.setParameter("loggedInstatus", AppConstant.COMPLETED_LOGGED_IN);
		sharedFileQuery.setParameter("userId", userId);

		List<SharedFileResponse> sharedFilesList = (List<SharedFileResponse>) sharedFileQuery.getResultList();
		if (sharedFilesList.isEmpty()) {
			log.info("No files shared for the user {}", userId);
			throw new DataNotFoundException(" No data found");
		}
		return sharedFilesList;
	}

	@Override
	public List<AllLinkDetailsResponse> getAllLinkDetails(Integer userId, int linkId, int page, int pageSize,
			String status) throws DataNotFoundException, CustomMessageException {

		log.info("inside getSharedFiles userId {}", userId);
		log.debug("get all link details with user id {}, linkId {}, page {}, pagesize {}, status {}", userId, linkId,
				page, pageSize, status);
		List<String> strList = Arrays.asList(status.split(","));
		ArrayList<Integer> statusList = new ArrayList<>();

		statusList.addAll(strList.stream().map(Integer::valueOf).collect(Collectors.toList()));

		List<LinkDetails> lisnkDetailsList = linkDetailsRepo.findByUserIdAndLinkTypeAndLinkStatusIn(userId, linkId,
				statusList, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")));

		if (lisnkDetailsList.isEmpty()) {
			log.error("no link details found with user id {}, linkId {}, page {}, pagesize {}, status {}", userId,
					linkId, page, pageSize, status);
			throw new DataNotFoundException("No Details Found");
		}
		return new LinkDetailsMapper(getDiscriptionMap()).getAllLinkResponseFromLinkDetails(lisnkDetailsList);
	}

	@Override
	public Boolean updateLinkStatus(LinkStatusChangeReq status) {
		log.debug("Inside updateLinkStatus with status {} ", status);
		log.info("changing the status of the link {}", status);
		LinkDetails ld = linkDetailsRepo.findByLink(status.getUrn());
		ld.setLinkStatus(status.getStatus());
		if (linkDetailsRepo.save(ld).getLinkStatus() > 0)
			return true;
		else
			return false;
	}

	@Override
	public Integer regenOTP(String urn) throws DataNotFoundException, CustomMessageException {
		log.debug("Inside regenOTP with URN {} ", urn);
		log.info(" Re - Generating OTP for the Link {}", urn);
		return otpGenerator.generateOTP(urn);
	}

	@Override
	public Boolean extendLinkValidity(LinkValidity linkValidity) {
		log.debug("Inside extendLinkValidity with linkValidity {} ", linkValidity);

		LinkDetails ld = linkDetailsRepo.findByLink(linkValidity.getUrn());
		ld.setExtended_days((ld.getExtended_days() + linkValidity.getDays()));
		Date curExpiryDate = ld.getExpiryDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(curExpiryDate);
		cal.add(Calendar.DATE, linkValidity.getDays());
		ld.setExpiryDate(cal.getTime());
		ld.setIsExtended(AppConstant.TRUE);
		ld.setLinkStatus(AppConstant.EXTENDED_LINK);
		if (linkDetailsRepo.save(ld) != null) {
			log.info("link validity extended with {} days , details are ", linkValidity.getDays(), linkValidity);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Boolean cancelRequest(String urn) {
		log.debug("Inside cancelRequest with urn {} ", urn);
		LinkDetails ld = linkDetailsRepo.findByLink(urn);
		ld.setCancelRequest(AppConstant.ACTIVE);
		if (linkDetailsRepo.save(ld).getCancelRequest() > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<DashBoardData> getRecentThirdPartyUploadedFile(Integer userId, int pageNo, int pageSize)
			throws DataNotFoundException {
		log.debug("Inside getRecentThirdPartyUploadedFile with userId {} pageno {} pageSize {}", userId, pageNo,pageSize);
		Optional<MaUser> usr = userRepo.findById(userId);
		if (!usr.isPresent()) {
			log.debug("no Recent Third Party Uploaded File with userId {} found", userId);
			throw new DataNotFoundException("No User Found");
		}
		Date lastLogout = usr.get().getLastLogOut();
		if (lastLogout == null)
			lastLogout = usr.get().getLastLogIn();

		List<UploadedFiles> files = uploadedFileRepo.findByUserIdAndCreatedOnAfter(userId, lastLogout,
				PageRequest.of(pageNo, pageSize, Sort.by("createdOn")));
		if (files.isEmpty())
			throw new DataNotFoundException("No Data Found");
		return new UploadedFileModelMapper(getThirdPartyOflink(lastLogout)).getDashBoardData(files);

	}

	private Map<Integer, String> getThirdPartyOflink(Date LogoutDate) {
		log.debug("Inside getThirdPartyOflink with Date {} ", LogoutDate.toString());

		Query thirdPartEmailQuery = entityManager.createNativeQuery(SqlQueries.thirdPartyEmailByLinkId)
				.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(LinkIdWithEmail.class));

		thirdPartEmailQuery.setParameter("logoutDate", LogoutDate);
		List<LinkIdWithEmail> linkList = (List<LinkIdWithEmail>) thirdPartEmailQuery.getResultList();

		Map<Integer, String> linkEmailMap = linkList.stream()
				.collect(Collectors.toMap(LinkIdWithEmail::getLINKID, LinkIdWithEmail::getEMAIL));
		return linkEmailMap;

	}

	@Override
	public List<DashBoardSharedFile> getRecentSharedFile(Integer userId, int pageNo, int pageSize)
			throws DataNotFoundException {
		log.debug("Inside getRecentSharedFile with userId {} ", userId);
		log.debug("Inside Recent Shared File with userId {} pageno {} pageSize {}", userId, pageNo,pageSize);
		Query sharedFileQuery = entityManager.createNativeQuery(SqlQueries.FILE_SHARED_TO_LOGGED_IN_USER)
				.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(DashBoardSharedFile.class));

		sharedFileQuery.setParameter("userid", userId);

		List<DashBoardSharedFile> linkList = (List<DashBoardSharedFile>) sharedFileQuery.getResultList();
		if (linkList.isEmpty() || linkList == null) {
			log.warn("no data found for Recent Shared File with userId {} pageno {} pageSize {}", userId, pageNo,pageSize);
			throw new DataNotFoundException("No data Found");
		}else

			return linkList;
	}

	@Override
	public List<DashBoardLinkDetails> getSharedLinkStatus(Integer userId, int pageNo, int pageSize)
			throws DataNotFoundException, CustomMessageException {
		log.debug("Inside getSharedLinkStatus with userId {} ", userId);
		log.warn("Shared Link Status with userId {} pageno {} pageSize {}", userId, pageNo,pageSize);
		List<LinkDetails> ldList = linkDetailsRepo.findByUserId(userId,
				PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending()));

		if (ldList.isEmpty()) {
			log.warn("no data found for Shared Link Status with userId {} pageno {} pageSize {}", userId, pageNo,pageSize);
			throw new DataNotFoundException("No Data Found");
		} else {
			return new SharedLinkToDashBoard(getDiscriptionMap()).getDashBoardLinkDetailsList(ldList);
		}
	}

	@Override
	public Boolean completeDownload(Integer linkId) throws DataNotFoundException {
		log.debug("Inside completeDownload with linkId {} ", linkId);
		Optional<LinkDetails> otpLinkDetails = linkDetailsRepo.findById(linkId);
		if (otpLinkDetails.isPresent()) {
			LinkDetails ld = otpLinkDetails.get();
			ld.setLoggedInStatus(AppConstant.COMPLETED_LOGGED_IN);
			ld.setLinkStatus(AppConstant.COMPLETED_LINK);
			ld.setUpdatedAt(DateTimeUtil.getTodaysDate());
			ld.setIsViewed(AppConstant.FALSE);
			ld.setCompletedOn(DateTimeUtil.getTodaysDate());
			linkDetailsRepo.save(ld);
			log.info("All download completed for the link id {}", linkId);
			return true;
		} else {
			log.info("Some files are still not compete cownloaded for the link {}", linkId);
			throw new DataNotFoundException("No Details Found");
		}
	}

	@Override
	public Integer createLink(LinkDetail folderDetails) throws CustomMessageException {
		log.debug("Inside createLink with folderDetails {} ", folderDetails);
		List<FolderDetails> fdList = folderDeatilsRepo.findByUserIdAndVesselNameAndVesselDescAndImoAndVslOfficialNo(
				folderDetails.getUserId(), folderDetails.getVessel().trim(), folderDetails.getDesc(),
				folderDetails.getImo(), folderDetails.getVslOfficialNo());

		if (!fdList.isEmpty()) {
			log.error("One Folder is Already exist with Given details {}", folderDetails);
			throw new CustomMessageException("One Folder is Already exist with Given details");
		}

		FolderDetails fd = new FolderDetails();
		fd.setUserId(folderDetails.getUserId());
		fd.setVesselDesc(folderDetails.getDesc());
		fd.setVesselName(folderDetails.getVessel().trim());
		fd.setCreatedOn(DateTimeUtil.getTodaysDate());
		fd.setImo(folderDetails.getImo());
		fd.setVslOfficialNo(folderDetails.getVslOfficialNo());
		log.debug("link Created with the folder details {}",folderDetails);
		return folderDeatilsRepo.save(fd).getFolderId();

	}

	@Override
	public ResponseEntity<Map<String, String>> uploadFile(MultipartFile file, Integer fileOwner, Integer folderId)
			throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		log.debug("Inside uploadFile with fileOwner {} folderId {}  ", fileOwner, folderId);
		log.debug("File name - {} file size - {} KB", file.getName(), (file.getSize() / 1024));
		final String exts = "jpeg jpg png pdf doc docx xlsx zip rar 7z gz archive pps  ppt  pptx xlsm  xls txt mp4 mkv flv mov avi wmv 3gp aac aac mp3  m4b m4p ogg oga wma wmv ";
		final String zipExts = "zip rar 7z gz archive";
		String fileName = file.getOriginalFilename();
		String ext = StringUtils.getFilenameExtension(fileName).toLowerCase();
		log.info("Checking file Extention");
		map.put("filename", fileName);
		if (!exts.contains(ext)) {
			log.error("File extention not allowed {}",ext);
			map.put("msg", "File format not supported " + fileName);
			map.put("operation", "failed");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Optional<FolderDetails> optFolderDetails = folderDeatilsRepo.findById(folderId);
		if (!optFolderDetails.isPresent()) {
			map.put("msg", "Invalid Link ");
			map.put("operation", "failed");
			log.error("Invalid link supplied with id {} ", folderId);
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			// file scanning
			if (!zipExts.contains(ext)) {
				log.info("Scanning the uploaded file please wait..");
				List<FileScanResponseDto> scanResult = fileScanService.scanFiles(new MultipartFile[] { file });

				if (scanResult.get(0).getDetected()) {
					map.put("msg", "Virus Detected");
					map.put("operation", "failed");
					log.error("Virus found in uploaded file {}", file.getOriginalFilename());
					return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				if (fileScanService.isFileContainsVirus(file)) {
					map.put("msg", "Virus Detected");
					map.put("operation", "failed");
					log.error("Virus found in uploaded file {}", file.getOriginalFilename());
					return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

			return new ResponseEntity<Map<String, String>>(
					uploadFileToFolder(file, new UploadDetails("NA", fileOwner, folderId), optFolderDetails.get()),
					HttpStatus.OK);
		}
	}

	@Override
	public Set<String> sharedFiles(ShareFile shareFile) throws CustomMessageException {

		log.debug("Inside sharedFiles details {}", shareFile);
		Set<String> alreadyExistedLink = new HashSet<String>();
		for (String email : shareFile.getEmails()) {

			// Fetch previous details if any
			List<Integer> linkStatus = new ArrayList<Integer>();
			linkStatus.add(AppConstant.ACTIVE_LINK);
			linkStatus.add(AppConstant.EXTENDED_LINK);
			DownloadLink link = downloadLinkRepo.findFirstByFolderIdAndEmailAndLinkStatusIn(shareFile.getFolderId(),
					email.toLowerCase(), linkStatus);
			if (link != null) {
				alreadyExistedLink.add(email.toLowerCase());
			} else {

				log.debug("sending email to {} regarding shared files with details " , email, shareFile);
				DownloadLink dlink = new DownloadLink();
				dlink.setCreatedOn(DateTimeUtil.getTodaysDate());
				dlink.setEmail(email.toLowerCase());
				dlink.setExtDays(0);
				dlink.setFolderId(shareFile.getFolderId());
				dlink.setLoggedInStatus(AppConstant.NOT_YET_LOGGED_IN);
				dlink.setLinkStatus(AppConstant.ACTIVE_LINK);
				String URL = otpGenerator.randomAlphaNumeric();
				dlink.setURL(URL);
				dlink.setCreatedBy(shareFile.getUserId());
				dlink.setExpired_on(DateTimeUtil.addDaysToCurrectDate(shareFile.getDays()));
				Integer dlinkId = downloadLinkRepo.save(dlink).getLinkId();
				otpGenerator.generateOnlyOTP(dlinkId);
				log.info("Otp generated for email - {} ", email.toLowerCase());
				for (Integer fileId : shareFile.getFiles()) {
					FileDownloadLink fdl = new FileDownloadLink();
					fdl.setDownloadLinkId(dlinkId);
					fdl.setFileId(fileId);
					fileDownloadLinkRepo.save(fdl);
				}

				composeEmail(shareFile, URL, email.toLowerCase());
			}
		}
		return alreadyExistedLink;
	}

	private Boolean composeEmail(ShareFile shareFile, String url, String email) throws CustomMessageException {
		log.debug("Inside composeEmail details {}, URL {} email {}", shareFile, url, email);
		Optional<FolderDetails> optionalFd = folderDeatilsRepo.findById(shareFile.getFolderId());
		if (!optionalFd.isPresent()) {
			log.error("No details found with details {} url {} and email {}", shareFile, url, email);
			throw new CustomMessageException("No Details Found");
		}
		FolderDetails fd = optionalFd.get();
		return composeEmail.composeDownloadLink(email, AppConstant.DOWNLOAD_URL,
				String.format(AppConstant.DOWNLOAD_URL_BODY, emailProv.getUiHost() + url, fd.getVesselName(),
						otpGenerator.getDescription(fd.getVesselDesc()), fd.getVesselName(),
						DateTimeUtil.addDaysToCurrectDate(shareFile.getDays())));
	}

	@Override
	public List<Folder> getSharedLink(Integer userId, int page, int pageSize) throws CustomMessageException {
		log.debug("Inside getSharedLink userId {}, page {} pageSize {}", userId, page, pageSize);
		List<FolderDetails> folderDetailsList = folderDeatilsRepo.findByUserId(userId,
				PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdOn")));
		Map<Integer, String> descMap = getDiscriptionMap();
		log.info("Getting folder details by user name");
		if (folderDetailsList.isEmpty()) {
			log.error("No details found for userId {}, page {} pageSize {}", userId, page, pageSize);
			throw new CustomMessageException("No Details Found");
		} else {
			List<Folder> folderList = new ArrayList<Folder>();
			log.info("Iterating through folder to fetch specify details");
			for (FolderDetails folderDetails : folderDetailsList) {
				if (folderDetails.getIsArchived() == null || folderDetails.getIsArchived() == AppConstant.FALSE) {
					Folder f = new Folder();
					f.setDesc(descMap.get(folderDetails.getVesselDesc()));
					f.setVesselName(folderDetails.getVesselName());
					f.setFolderId(folderDetails.getFolderId());
					f.setImo(folderDetails.getImo());
					f.setVslOfficialNo(folderDetails.getVslOfficialNo());
					folderList.add(f);
				}
			}
			return folderList;
		}

	}

	public Map<String, String> uploadFileToFolder(MultipartFile file, UploadDetails uploadDetails,
			FolderDetails folderDetails) {
		log.debug("Inside uploadFileToFolder uploadDetails {}, folderDetails {} file name  {} fileSize {} bytes",
				uploadDetails, folderDetails, file.getOriginalFilename(), file.getSize());

		Map<String, String> map = new HashMap<String, String>();
		Integer fileId = 0;
		try {
			String fileName = file.getOriginalFilename();
			String ext = StringUtils.getFilenameExtension(fileName);

			log.info("saving the file details");
			UploadedFiles upFile = new UploadedFiles();
			upFile.setUserId(uploadDetails.getFileOwner());
			upFile.setFileName(fileName);
			upFile.setIsArchive(AppConstant.FALSE);
			upFile.setIsDeleted(AppConstant.FALSE);
			upFile.setIsFolder(AppConstant.FALSE);
			upFile.setIsShared(AppConstant.FALSE);
			upFile.setFileSize(BigInteger.valueOf(file.getSize()));
			upFile.setFileType(ext);
			upFile.setCreatedBy(uploadDetails.getEmail());
			upFile.setCreatedOn(new Date());
			upFile.setFolderId(folderDetails.getFolderId());

			fileId = uploadedFileRepo.save(upFile).getFileId();
			log.info("getting the file location for the file {}", file.getOriginalFilename());
			String destination = locationDetails.getSharedDirectory() + fileId + "." + ext;

			log.info("Uploadeing file {} to the storage", file.getOriginalFilename());
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
				log.error("File {} Uploaded succesfully", file.getOriginalFilename());

			} else {
				map.put("msg", "Unable to save " + fileName);
				map.put("operation", "failed");
				uploadedFileRepo.deleteById(fileId);
				log.error("File {} not Uploaded, reverting the Transaction details", file.getOriginalFilename());
			}
		} catch (Exception e) {
			log.error("Some Exception Occured while uploading file {} , error is {} ",file.getOriginalFilename(), e.getMessage());
			if (fileId != 0)
				uploadedFileRepo.deleteById(fileId);
			map.put("msg", "Something Unexpected Occured");
			map.put("operation", "failed");
		}

		clearTempFolder.clearAllFileOlderThan24Hr();
		return map;
	}

	@Override
	public PrevouslyUploadedFiles getUploadedFiles(Integer folderId) throws DataNotFoundException {
		log.debug("inside getUploadedFiles folderId {}", folderId);
		log.debug("Uploaded Files in folderId {}", folderId);
		List<DashBoardSharedFile> sharedFileDetails = new ArrayList<DashBoardSharedFile>();
		List<String> archieveFiles = new ArrayList<String>();
		PrevouslyUploadedFiles preUploadedFiles = new PrevouslyUploadedFiles();
		List<UploadedFiles> uploadedFiles = uploadedFileRepo.findByFolderId(folderId);
		if (uploadedFiles.isEmpty()) {
			log.error("No file uploaded by the user in the link with folderid {}", folderId);
			throw new DataNotFoundException("No File uploaded yet");
		}

		for (UploadedFiles uploadedFile : uploadedFiles) {
			if (uploadedFile.getIsDeleted() == AppConstant.FALSE) {

				if (uploadedFile.getIsArchive() == AppConstant.FALSE) {
					DashBoardSharedFile dsFile = new DashBoardSharedFile();
					dsFile.setId(BigInteger.valueOf(uploadedFile.getFileId()));
					dsFile.setFileSize(uploadedFile.getFileSize());
					dsFile.setName(uploadedFile.getFileName());
					dsFile.setType(uploadedFile.getFileType());
					dsFile.setSharedDate(uploadedFile.getCreatedOn());
					sharedFileDetails.add(dsFile);
				} else {
					archieveFiles.add(uploadedFile.getFileName());
				}
			}

		}
		preUploadedFiles.setArchieveFiles(archieveFiles);
		preUploadedFiles.setUploadedFiles(sharedFileDetails);
		return preUploadedFiles;
	}

	@Override
	public List<AllLinkDetailsResponse> getAllDownLink(Integer userId, Integer pageNo, Integer pageSize, String status)
			throws CustomMessageException {
		log.debug("inside getAllDownLink userId {} status {} page no () pagesize {}", userId, status, pageNo, pageSize);
		List<String> strList = Arrays.asList(status.split(","));
		ArrayList<Integer> statusList = new ArrayList<>();

		statusList.addAll(strList.stream().map(Integer::valueOf).collect(Collectors.toList()));

		List<FolderDetails> fDlist = folderDeatilsRepo.findByUserId(userId);
		List<Integer> folderIdList = new ArrayList<Integer>();
		for (FolderDetails fDetails : fDlist) {
			folderIdList.add(fDetails.getFolderId());
		}

		List<DownloadLink> DownLinkList = downloadLinkRepo.findByFolderIdInAndLinkStatusIn(folderIdList, statusList,
				PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "createdOn")));

//		List<DownloadLink> DownLinkList = downloadLinkRepo.findByFolderIdIn(folderIdList,
//				PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "createdOn")));

		return new DownLinkToLinkDetails(fDlist, getDiscriptionMap()).getLinkDetailsResponseList(DownLinkList);
	}

	@Override
	public boolean extendDownlink(LinkValidity linkval) {
		log.debug("Inside extendDownlink linkval {}", linkval);
		DownloadLink downloadLink = downloadLinkRepo.findFirstByURL(linkval.getUrn());
		downloadLink.setExtDays(downloadLink.getExtDays() + linkval.getDays());
		downloadLink.setLinkStatus(AppConstant.EXTENDED_LINK);
		downloadLink.setExpired_on(DateTimeUtil.addDays(downloadLink.getExpired_on(), linkval.getDays()));
		if (downloadLinkRepo.save(downloadLink).getLinkId() > 0)
			return true;
		else
			return false;

	}

	@Override
	public Boolean updateDownLinkStatus(LinkStatusChangeReq status) throws CustomMessageException {
		log.debug("Inside updateDownLinkStatus status {}", status);
		DownloadLink ld = downloadLinkRepo.findFirstByURL(status.getUrn());
		if (ld != null) {
			ld.setLinkStatus(status.getStatus());
			if (downloadLinkRepo.save(ld).getLinkId() > 0)
				return true;
			else
				return false;
		} else
			throw new CustomMessageException("Invalid URL");
	}

	@Override
	public ArchiveDetails getArchiveFile(Integer userId) throws CustomMessageException {
		log.debug("Inside getArchiveFile userId {}", userId);
		List<ArchivedLinkDetails> archiveLinkList = new ArrayList<ArchivedLinkDetails>();
		List<ArchivedFolderDetails> archiveFolderList = new ArrayList<ArchivedFolderDetails>();
		List<ArchivedFileDetails> arfile = new ArrayList<ArchivedFileDetails>();
		Map<Integer, String> vesselNameMap = new HashMap<Integer, String>();
		Map<Integer, String> vesselDescMap = new HashMap<Integer, String>();
		Map<Integer, Integer> vesselImoMap = new HashMap<Integer, Integer>();
		Map<Integer, String> vesselOfficialNoMap = new HashMap<Integer, String>();
		Set<Integer> excludedLink = new HashSet<>();
		Set<Integer> excludedFolder = new HashSet<>();

		Set<Integer> includedLink = new HashSet<>();
		Set<Integer> includedFolder = new HashSet<>();

		List<LinkDetails> archiveLink = linkDetailsRepo.findByUserIdAndIsArchiveAndArchivedOnBetween(userId,
				AppConstant.TRUE, DateTimeUtil.subDaysToCurrectDate(AppConstant.DELETE_ARCHIVE_AFTER_DAYS),
				DateTimeUtil.getTodaysDate());
		Map<Integer, String> descriptionMap = getDiscriptionMap();
		for (LinkDetails linkDetails : archiveLink) {
			ArchivedLinkDetails archLink = new ArchivedLinkDetails();
			excludedLink.add(linkDetails.getLinkId());
			archLink.setLinkId(linkDetails.getLinkId());
			archLink.setArchieveDate(linkDetails.getArchivedOn());
			archLink.setDesc(descriptionMap.get(linkDetails.getLinkDesc()));
			archLink.setVesselName(linkDetails.getVesselName());
			archLink.setExpiredOn(
					DateTimeUtil.addDays(linkDetails.getArchivedOn(), AppConstant.DELETE_ARCHIVE_AFTER_DAYS));
			archLink.setImo(linkDetails.getImo());
			archLink.setVslOfficialNo(linkDetails.getOfficialNo());
			archiveLinkList.add(archLink);
		}

		List<FolderDetails> archiveFolder = folderDeatilsRepo.findByUserIdAndIsArchivedAndArchivedOnBetween(userId,
				AppConstant.TRUE, DateTimeUtil.subDaysToCurrectDate(AppConstant.DELETE_ARCHIVE_AFTER_DAYS),
				DateTimeUtil.getTodaysDate());

		for (FolderDetails folderDetail : archiveFolder) {
			ArchivedFolderDetails fd = new ArchivedFolderDetails();
			excludedFolder.add(folderDetail.getFolderId());

			fd.setArchieveDate(folderDetail.getArchivedOn());
			fd.setDesc(descriptionMap.get(folderDetail.getVesselDesc()));
			fd.setExpiredOn(DateTimeUtil.addDays(folderDetail.getArchivedOn(), AppConstant.DELETE_ARCHIVE_AFTER_DAYS));
			fd.setFolderId(folderDetail.getFolderId());
			fd.setVesselName(folderDetail.getVesselName());
			fd.setVslOfficialNo(folderDetail.getVslOfficialNo());
			fd.setImo(folderDetail.getImo());

			archiveFolderList.add(fd);
		}

		List<UploadedFiles> upFileList = uploadedFileRepo.findByUserIdAndIsArchiveAndIsDeletedAndArchivedDateBetween(
				userId, AppConstant.TRUE, AppConstant.FALSE,
				DateTimeUtil.subDaysToCurrectDate(AppConstant.DELETE_ARCHIVE_AFTER_DAYS), DateTimeUtil.getTodaysDate());

		if (!upFileList.isEmpty()) {
			for (UploadedFiles uploadedFiles : upFileList) {
				if (uploadedFiles.getLinkId() != null) {
					includedLink.add(uploadedFiles.getLinkId());
				} else {
					includedFolder.add(uploadedFiles.getFolderId());
				}
			}

			if (!includedLink.isEmpty()) {
				List<LinkDetails> ldDetails = linkDetailsRepo.findByLinkIdIn(includedLink);
				for (LinkDetails linkDetails : ldDetails) {
					vesselNameMap.put(linkDetails.getLinkId(), linkDetails.getVesselName());
					vesselDescMap.put(linkDetails.getLinkId(), descriptionMap.get(linkDetails.getLinkDesc()));
					vesselImoMap.put(linkDetails.getLinkId(), linkDetails.getImo());
					vesselOfficialNoMap.put(linkDetails.getLinkId(), linkDetails.getOfficialNo());
				}
			}

			if (!includedFolder.isEmpty()) {
				List<FolderDetails> fdList = folderDeatilsRepo.findByFolderIdIn(includedFolder);
				for (FolderDetails folderDetails : fdList) {
					vesselNameMap.put(folderDetails.getFolderId(), folderDetails.getVesselName());
					vesselDescMap.put(folderDetails.getFolderId(), descriptionMap.get(folderDetails.getVesselDesc()));
					vesselImoMap.put(folderDetails.getFolderId(), folderDetails.getImo());
					vesselOfficialNoMap.put(folderDetails.getFolderId(), folderDetails.getVslOfficialNo());
				}
			}

			arfile = new UploadedToArchieveFile(vesselNameMap, vesselDescMap, vesselOfficialNoMap, vesselImoMap,
					excludedFolder, excludedLink).getArchivedFiles(upFileList);

		}
		return new ArchiveDetails(archiveLinkList, archiveFolderList, arfile);

	}

	@Override
	public Integer unArchiveFile(UnArchiveRequest unArchive) throws CustomMessageException {
		log.debug("Inside unArchiveFile unArchive {}", unArchive);
		UploadedFiles uf = uploadedFileRepo.findByUserIdAndFileId(unArchive.getUserId(), unArchive.getFileId());
		if (uf == null) {
			log.error("No archieve file found for details {}",unArchive);
			throw new CustomMessageException("File not found");}
		else {
			uf.setIsArchive(AppConstant.FALSE);
			uf.setUpdatedOn(null);
			uf.setUpdatedBy(unArchive.getUserId() + "");
			return uploadedFileRepo.save(uf).getFileId();
		}
	}

	@Override
	public Boolean archieveFile(Integer fileId) throws DataNotFoundException {
		log.debug("Inside archieveFile fileId {}", fileId);
		Optional<UploadedFiles> uploadedFile = uploadedFileRepo.findById(fileId);
		if (uploadedFile.isPresent()) {
			UploadedFiles uf = uploadedFile.get();
			uf.setIsArchive(AppConstant.TRUE);
			uf.setArchivedDate(DateTimeUtil.getTodaysDate());
			uploadedFileRepo.save(uf);
			return true;
		} else {
			log.error("No archieve File  {} found", fileId);
			throw new DataNotFoundException("No Data Found");
		}
	}

	@Override
	public Integer archiveFolder(ArchiveFolder archiveFolder) throws CustomMessageException {
		log.debug("Inside archiveFolder archiveFolder {}", archiveFolder);
		ArrayList<Integer> statusList = new ArrayList<>();
		statusList.add(AppConstant.ACTIVE);
		statusList.add(AppConstant.EXTENDED_LINK);
		if (downloadLinkRepo.findFirstByFolderIdAndLinkStatusIn(archiveFolder.getFolderId(), statusList) != null) {
			log.error("Can not delete the files as some links are active {}", archiveFolder);
			throw new CustomMessageException("Can not delete, Some links are active");
		}

		FolderDetails folderDetails = folderDeatilsRepo.findByUserIdAndFolderId(archiveFolder.getUserId(),
				archiveFolder.getFolderId());

		if (folderDetails == null) {
			log.error("No folder Link found for the userID  {}", archiveFolder.getUserId());
			throw new CustomMessageException("No details Found");
		}
		folderDetails.setIsArchived(AppConstant.TRUE);
		folderDetails.setArchivedOn(DateTimeUtil.getTodaysDate());
		folderDeatilsRepo.save(folderDetails);

		// cancel all the link associated with this folder
		List<DownloadLink> dLinkLst = downloadLinkRepo.findByFolderId(folderDetails.getFolderId());
		for (DownloadLink downloadLink : dLinkLst) {
			downloadLink.setLinkStatus(AppConstant.CANCELED_LINK);
			downloadLink.setUpdatedOn(DateTimeUtil.getTodaysDate());
		}
		downloadLinkRepo.saveAll(dLinkLst);
		// make all files archive
		List<UploadedFiles> uploadedFileList = uploadedFileRepo.findByFolderId(folderDetails.getFolderId());

		for (UploadedFiles uploadedFiles : uploadedFileList) {
			uploadedFiles.setIsArchive(AppConstant.TRUE);
			uploadedFiles.setArchivedDate(DateTimeUtil.getTodaysDate());
		}

		uploadedFileRepo.saveAll(uploadedFileList);

		return folderDetails.getFolderId();
	}

	@Override
	public Integer unArchiveFolder(ArchiveFolder archiveFolder) throws CustomMessageException {
		log.debug("Inside unArchiveFolder archiveFolder {}", archiveFolder);
		FolderDetails folderDetails = folderDeatilsRepo.findByUserIdAndFolderId(archiveFolder.getUserId(),
				archiveFolder.getFolderId());

		if (folderDetails == null) {
			log.error("No folder Link found for the userID  {}", archiveFolder.getUserId());
			throw new CustomMessageException("No details Found");
		}
		folderDetails.setIsArchived(AppConstant.FALSE);
		folderDetails.setArchivedOn(null);
		folderDeatilsRepo.save(folderDetails);
		// make all files archive

		List<UploadedFiles> uploadedFileList = uploadedFileRepo.findByFolderId(folderDetails.getFolderId());

		for (UploadedFiles uploadedFiles : uploadedFileList) {
			uploadedFiles.setIsArchive(AppConstant.FALSE);
			uploadedFiles.setArchivedDate(null);
		}

		uploadedFileRepo.saveAll(uploadedFileList);

		return folderDetails.getFolderId();
	}

	@Override
	public List<Integer> archiveLink(ArchiveLink archieveLink) throws CustomMessageException {
		log.debug("Inside archiveLink archieveLink {}", archieveLink);
		List<Integer> successFullyArchieveId = new ArrayList<>();
		for (Integer linkid : archieveLink.getLinkId()) {

			LinkDetails linkDetails = linkDetailsRepo.findByLinkIdAndUserId(linkid, archieveLink.getUserId());
			if (linkDetails == null) {
				log.error("No folder details found for  userID  {} and link ID ", archieveLink.getUserId(), linkid);
				throw new CustomMessageException("Folder Details Not found");
			} else {
				linkDetails.setArchivedOn(DateTimeUtil.getTodaysDate());
				linkDetails.setIsArchive(AppConstant.TRUE);
			}

			linkDetailsRepo.save(linkDetails);
			List<UploadedFiles> uploadedFileList = uploadedFileRepo.findByLinkId(linkDetails.getLinkId());
			for (UploadedFiles uploadedFiles : uploadedFileList) {
				uploadedFiles.setArchivedDate(DateTimeUtil.getTodaysDate());
				uploadedFiles.setIsArchive(AppConstant.TRUE);
			}
			uploadedFileRepo.saveAll(uploadedFileList);
			successFullyArchieveId.add(linkDetails.getLinkId());
		}
		return successFullyArchieveId;
	}

	@Override
	public List<Integer> unArchiveLink(ArchiveLink archieveLink) throws CustomMessageException {
		log.debug("Inside unArchiveLink archieveLink {}", archieveLink);
		List<Integer> successFullyUnArchieveId = new ArrayList<>();
		for (Integer linkid : archieveLink.getLinkId()) {
			LinkDetails linkDetails = linkDetailsRepo.findByLinkIdAndUserId(linkid, archieveLink.getUserId());
			if (linkDetails == null) {
				log.error("No folder details found for  userID  {} and link ID ", archieveLink.getUserId(), linkid);
				throw new CustomMessageException("Folder Details Not found");
			} else {
				linkDetails.setArchivedOn(null);
				linkDetails.setIsArchive(AppConstant.FALSE);
			}
			linkDetailsRepo.save(linkDetails);
			List<UploadedFiles> uploadedFileList = uploadedFileRepo.findByLinkId(linkDetails.getLinkId());
			for (UploadedFiles uploadedFiles : uploadedFileList) {
				uploadedFiles.setArchivedDate(null);
				uploadedFiles.setIsArchive(AppConstant.FALSE);
			}
			uploadedFileRepo.saveAll(uploadedFileList);
			successFullyUnArchieveId.add(linkDetails.getLinkId());
		}
		return successFullyUnArchieveId;
	}

	@Override
	public List<Description> getDescription() throws CustomMessageException {
		log.debug("Inside getDescription ");
		List<Description> descriptionList = new ArrayList<Description>();
		List<MaDescription> descs = maDescriptionRepo.findByIsActive(AppConstant.TRUE);
		if (descs.isEmpty()) {
			log.error("No Active description found");
			throw new CustomMessageException("No Description Found");}

		for (MaDescription maDescription : descs) {
			Description description = new Description();
			description.setId(maDescription.getId());
			description.setDesc(maDescription.getDesc());
			descriptionList.add(description);
		}
		return descriptionList;

	}

	@Override
	public Map<Integer, String> getDiscriptionMap() throws CustomMessageException {
		log.debug("Inside getDiscriptionMap ");
		List<MaDescription> descs = maDescriptionRepo.findAll();
		if (descs.isEmpty())
			throw new CustomMessageException("No Description Found");

		return descs.stream().collect(Collectors.toMap(MaDescription::getId, MaDescription::getDesc));

	}

	@Override
	public List<SharedToThirdPartyFiles> sharedToThirdParty(Integer userId) throws CustomMessageException {
		log.debug("Inside sharedToThirdParty userId {}", userId);
		List<Integer> linkIdList = new ArrayList<Integer>();
		List<DownloadLink> downloadLinks = downloadLinkRepo.findByCreatedBy(userId);
		for (DownloadLink downloadLink : downloadLinks) {
			linkIdList.add(downloadLink.getLinkId());
		}

		if (downloadLinks.isEmpty()) {
			log.error("No file shared to  userID  {} ", userId);
			throw new CustomMessageException("No Files Shared yet");}

		Query sharedTpThirdParty = entityManager.createNativeQuery(SqlQueries.SHARED_THIRD_PARTY_FOLDERS)
				.unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(SharedToThirdPartyFiles.class));

		sharedTpThirdParty.setParameter("linkId", linkIdList);
		List<SharedToThirdPartyFiles> linkList = (List<SharedToThirdPartyFiles>) sharedTpThirdParty.getResultList();

		return linkList;
	}

	@Override
	public Boolean removeSpecificFileFromSharedDownloadLink(RemoveSpecificFile fileDetails) {
		log.debug("Inside removeSpecificFileFromSharedDownloadLink fileDetails {}", fileDetails);
		fileDownloadLinkRepo
				.deleteById(new FileDownloadLinkId(fileDetails.getFileId(), fileDetails.getDownloadLinkId()));
		return true;

	}

	@Override
	public Boolean removeSharedFileFromAllUser(Integer fileId) {
		log.debug("Inside removeSharedFileFromAllUser fileId {}", fileId);
		List<FileDownloadLink> fdlList = fileDownloadLinkRepo.findByFileId(fileId);
		fileDownloadLinkRepo.deleteInBatch(fdlList);

		UploadedFiles uf = uploadedFileRepo.findById(fileId).get();

		uf.setIsArchive(AppConstant.ACTIVE);
		uf.setArchivedDate(DateTimeUtil.getTodaysDate());

		if (uploadedFileRepo.save(uf).getFileId() > 0)
			return true;
		else
			return false;
	}

	@Override
	public String getDesctiptionById(Integer descId) throws CustomMessageException {
		return getDiscriptionMap().get(descId);
	}

	/**
	 * History
	 * 
	 * @throws CustomMessageException
	 */

	@Override
	public List<CommunicationDetails> getLinkComunication(Integer userId) throws CustomMessageException {

		Query linkDetailsComunication = entityManager.createNativeQuery(SqlQueries.LINK_DETAILS_HISORY_BY_USER)
				.unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(LinkCommunicationHistory.class));

		linkDetailsComunication.setParameter("linkstatus", AppConstant.COMPLETED_LINK);
		linkDetailsComunication.setParameter("userId", userId);
		List<LinkCommunicationHistory> LinkCommunicationHistorylist = (List<LinkCommunicationHistory>) linkDetailsComunication
				.getResultList();

		Query folderComunication = entityManager.createNativeQuery(SqlQueries.FOLDER_DETAILS_HISORY_BY_USER)
				.unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(LinkCommunicationHistory.class));

		folderComunication.setParameter("userId", userId);
//		folderComunication.setParameter("linkstatus", AppConstant.COMPLETED_LINK);
		List<LinkCommunicationHistory> FolderCommunicationHistorylist = (List<LinkCommunicationHistory>) folderComunication
				.getResultList();

		return new HistoryMapper(LinkCommunicationHistorylist, FolderCommunicationHistorylist, getDiscriptionMap())
				.getCompleteHistory();
	}

	@Override
	public List<LinkHistory> getLinkHistory(char type, String vessel, Integer descId) throws CustomMessageException {
		if (type == 'U') {
			List<LinkDetails> linkDetails = linkDetailsRepo.findByVesselNameAndLinkDescAndLinkStatus(vessel, descId,
					AppConstant.COMPLETED_LINK);
			if (linkDetails.isEmpty())
				throw new CustomMessageException("No Details found");
			return uploadLInktoHistory(linkDetails);
		} else if (type == 'D') {
			List<Integer> linkStatus = new ArrayList<>();
			linkStatus.add(AppConstant.ACTIVE_LINK);
			linkStatus.add(AppConstant.COMPLETED_LINK);
			linkStatus.add(AppConstant.EXTENDED_LINK);
			FolderDetails fd = folderDeatilsRepo.findByVesselNameAndVesselDesc(vessel, descId);
			if (fd == null)
				throw new CustomMessageException("No Details found");

			List<DownloadLink> downloadLinks = downloadLinkRepo.findByFolderIdAndLinkStatusIn(fd.getFolderId(),
					linkStatus);
			if (downloadLinks.isEmpty())
				throw new CustomMessageException("No Details found");
			return downloadLInktoHistory(fd, downloadLinks);
		} else {
			throw new CustomMessageException("No Details found");
		}
	}

	private List<LinkHistory> uploadLInktoHistory(List<LinkDetails> linkDetails) {
		List<LinkHistory> historyList = new ArrayList<LinkHistory>();
		for (LinkDetails link : linkDetails) {
			LinkHistory history = new LinkHistory();
			history.setCreatedDate(link.getCreatedAt());
			history.setEmail(link.getEmail());
			history.setExpiryDate(link.getExpiryDate());
			history.setImo(link.getImo());
			history.setLinkId(link.getLinkId());
			history.setVslOfficialNo(link.getOfficialNo());
			history.setSubmittedOn(link.getCompletedOn());
			historyList.add(history);
		}

		return historyList;
	}

	private List<LinkHistory> downloadLInktoHistory(FolderDetails fd, List<DownloadLink> downloadLinks) {

		List<LinkHistory> historyList = new ArrayList<LinkHistory>();
		for (DownloadLink link : downloadLinks) {
			LinkHistory history = new LinkHistory();
			history.setCreatedDate(link.getCreatedOn());
			history.setEmail(link.getEmail());
			history.setExpiryDate(link.getExpired_on());
			history.setImo(fd.getImo());
			history.setLinkId(link.getLinkId());
			history.setVslOfficialNo(fd.getVslOfficialNo());

			historyList.add(history);
		}
		return historyList;
	}

	@Override
	public Boolean linkViewed(LinkIdArray linkIds) throws CustomMessageException {
		for (int i = 0; i < linkIds.getLinkArray().size(); i++) {
			Optional<LinkDetails> linkDetailsOpt = linkDetailsRepo.findById(linkIds.getLinkArray().get(i));
			if (linkDetailsOpt.isPresent()) {
				LinkDetails ld = linkDetailsOpt.get();
				ld.setIsViewed(AppConstant.TRUE);
				ld.setViewdAt(DateTimeUtil.getTodaysDate());
				linkDetailsRepo.save(ld).getLinkId();
			}

			if (linkIds.getLinkArray().size() == (i + 1))
				return true;
		}

		throw new CustomMessageException("No details found");
	}

	@Override
	public Boolean mergeFiles(ShareFile shareFile) {

		List<Integer> linkStatus = new ArrayList<Integer>();
		linkStatus.add(AppConstant.ACTIVE_LINK);
		linkStatus.add(AppConstant.EXTENDED_LINK);

		for (String email : shareFile.getEmails()) {
			DownloadLink dl = downloadLinkRepo.findByFolderIdAndEmailAndLinkStatusInAndCreatedBy(
					shareFile.getFolderId(), email.toLowerCase(), linkStatus, shareFile.getUserId());

			for (Integer fileId : shareFile.getFiles()) {
				FileDownloadLink fdl = new FileDownloadLink();
				fdl.setFileId(fileId);
				fdl.setDownloadLinkId(dl.getLinkId());
				fileDownloadLinkRepo.save(fdl);
			}
		}

		return true;
	}

	@Override
	public Boolean updatePwd(UpdatePwd updatePwd) throws CustomMessageException {

		Optional<MaUser> maUserOpt = userRepo.findById(updatePwd.getUserId());
		if (maUserOpt.isPresent()) {

			MaUser user = maUserOpt.get();
			if (user.getStatus() != 1)
				throw new CustomMessageException("User is not Active");
			if (!new BCryptPasswordEncoder().matches(updatePwd.getOldPwd(), user.getPwd()))
				throw new CustomMessageException("Invalid password");
			user.setPwd(new BCryptPasswordEncoder().encode(updatePwd.getPwd()));
			if (userRepo.save(user).getUser_id() > 0) {
				// Update the password hint one getting from front End
				userLoginDetailsService.createNewUserPassword(user.getEmail(), updatePwd.getPwd(),
						updatePwd.getPwdHint());
				return true;
			} else
				return false;

		} else {
			throw new CustomMessageException("No user found");
		}
	}

	public static boolean revokeToken(OAuth2AccessToken accessToken, TokenStore tokenStore, String refreshToken) {

		if (refreshToken != null && refreshToken != "") {
			tokenStore.removeAccessToken(accessToken);
		} else {
			tokenStore.removeAccessToken(accessToken);
			tokenStore.removeRefreshToken(accessToken.getRefreshToken());
		}
		return true;
	}

	@Override
	public Boolean checkFile(Integer fileId) {
		List<FileDownloadLink> fileDownloadLink = fileDownloadLinkRepo.findByFileId(fileId);
		List<Integer> linkStatusCode = new ArrayList<Integer>();
		linkStatusCode.add(AppConstant.ACTIVE_LINK);
		linkStatusCode.add(AppConstant.EXTENDED_LINK);
		Set<Integer> downloadlinkSet = fileDownloadLink.stream().map(p -> p.getDownloadLinkId())
				.collect(Collectors.toSet());
		List<DownloadLink> isActiveLink = downloadLinkRepo.findByLinkIdInAndLinkStatusIn(downloadlinkSet,
				linkStatusCode);
		if (isActiveLink.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public String checkAccountStatus(String email) {
		log.info("Checking account staus for email {}",email);
		MaUser user = userRepo.findByEmail(email);
		if (user == null) {
			log.error("No user found with email {}",email);
			return AppConstant.NO_USER_FOUND;
		} else if (user.getStatus() == AppConstant.INACTIVE) {
			log.warn("user with id {} is inactive",email);
			return AppConstant.INACTIVE_USER;
		} else {
			LoginDetailsHistory ldHistory = loginDetailsHistoryRepo.findByEmailAndStatus(email, AppConstant.ACTIVE);
			if (ldHistory != null) {
				if (ldHistory.getLoginAttempt() > 2) {
					return AppConstant.USER_ACCOUNT_LOCKED;
				} else {
					return String.format(AppConstant.UNSUCCESSFULL_LOGIN_COUNT, (3 - ldHistory.getLoginAttempt()));
				}
			}
			return null;
		}
	}

	@Override
	public String getPWDHint(String email) {
		log.info("getting password hint for account {}",email);
		LoginDetailsHistory ldHistory = loginDetailsHistoryRepo.findByEmailAndStatus(email, AppConstant.ACTIVE);
		if (ldHistory != null) {
			return EncryptionDecrytion.decrypt(ldHistory.getPwdHint());
		}
		return null;
	}

	private String pwdExpiredOn(String Email) {
		LoginDetailsHistory ldHistory = loginDetailsHistoryRepo.findByEmailAndStatus(Email, AppConstant.ACTIVE);
		if (ldHistory != null && ldHistory.getExpiredOn() != null) {
			Long diff = TimeUnit.DAYS.convert(
					Math.abs(ldHistory.getExpiredOn().getTime() - DateTimeUtil.getTodaysDate().getTime()),
					TimeUnit.MILLISECONDS);

			if (diff.intValue() > 0 && diff.intValue() <= 5)
				return String.valueOf(diff);
		}
		return null;
	}
}
