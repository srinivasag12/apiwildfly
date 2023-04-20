package com.bsol.iri.fileSharing.controller;

import java.util.List;


/**
 * 
 * @author rupesh
 *
 */

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bsol.iri.fileSharing.clamAv.dto.FileScanResponseDto;
import com.bsol.iri.fileSharing.clamAv.service.FileScanService;
import com.bsol.iri.fileSharing.entity.LinkDetails;
import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.fileCompression.FileCompressService;
import com.bsol.iri.fileSharing.models.DownloadDetails;
import com.bsol.iri.fileSharing.models.ForgetPassword;
import com.bsol.iri.fileSharing.models.LinkDetailsResponseModel;
import com.bsol.iri.fileSharing.models.LinkId;
import com.bsol.iri.fileSharing.models.ResetPassword;
import com.bsol.iri.fileSharing.models.ThirdPartyUpload;
import com.bsol.iri.fileSharing.models.UrnDetails;
import com.bsol.iri.fileSharing.models.thirdParty.Login;
import com.bsol.iri.fileSharing.models.thirdParty.UploadDetails;
import com.bsol.iri.fileSharing.repos.LinkDetailsRepo;
import com.bsol.iri.fileSharing.service.ThirdPartyService;
import com.bsol.iri.fileSharing.service.UserService;
import com.bsol.iri.fileSharing.util.AppConstant;
import com.bsol.iri.fileSharing.util.ClearTempFolder;

import io.github.techgnious.exception.VideoException;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("api/public")
public class ThirdPartyController {

	@Autowired
	ThirdPartyService thirdPartyService;

	@Autowired
	LinkDetailsRepo linkDetailsRepo;

	@Autowired
	private FileScanService fileScanService;

	@Autowired
	private UserService userService;

	@Autowired
	FileCompressService fileCompressService;

	@Autowired
	private ClearTempFolder clearTempFolder;

	Map<Integer, Integer> linkOTP;
	Map<Integer, Long> linkExpiry;
	Map<String, Long> passwordResetMap;

	private static Logger log = LoggerFactory.getLogger(ThirdPartyController.class);

	public ThirdPartyController() {
		log.info("local Map is initializing");
		linkOTP = new HashMap<Integer, Integer>();
		linkExpiry = new HashMap<Integer, Long>();
		passwordResetMap = new HashMap<String, Long>();
	}

	@RequestMapping(value = "/{url}", method = RequestMethod.GET)
	public ResponseEntity<LinkDetailsResponseModel> showLoginPage(@PathVariable("url") String url)
			throws DataNotFoundException, CustomMessageException {
		log.info("Inside api/public/url");
		log.debug("url = {}", url);
		return new ResponseEntity<LinkDetailsResponseModel>(thirdPartyService.getLinkDetails(url), HttpStatus.OK);
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<Integer> loginProcess(@RequestBody Login login) throws DataNotFoundException {
		log.info("Inside api/public/auth");
		log.debug("login = {}", login);
		Integer randumNumber = 0;
		Integer linkId = thirdPartyService.authorizeUser(login);
		if (linkId.intValue() > 0) {
			log.info("Setting value to Local storage");
			randumNumber = new Random().nextInt(1000000);
			linkOTP.put(linkId, randumNumber);
			linkExpiry.put(linkId, new Date().getTime());
			log.info("Value setted otp {} and expiry {}", linkOTP.get(linkId), linkExpiry.get(linkId));
		}
		log.debug("returned values = {}", randumNumber);
		return new ResponseEntity<Integer>(randumNumber, HttpStatus.OK);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("email") String email, @RequestParam("fileOwner") Integer fileOwner,
			@RequestParam("link") Integer link, @RequestParam("IdCode") Integer IdCode)
			throws VideoException, IOException {

		log.info("Inside api/public/upload");
		log.debug("email = {} fileOwner = {}  link = {} IdCode ={} ", email, fileOwner, link, IdCode);
		log.debug("file name = {}  fileSize = {} KB", file.getOriginalFilename(), (file.getSize() / 1024));

		Map<String, String> map = new HashMap<String, String>();
		log.info("Checking the link Authenticity");

		if (linkOTP.get(link).equals(IdCode)) {
			Long createDate = linkExpiry.get(link);
			long seconds = TimeUnit.MILLISECONDS.toSeconds((new Date().getTime() - createDate));
			if ((int) seconds > AppConstant.USER_SEEESION_LOGGED_OUT_IN_SEC) {
				log.error("Link is ideal for more than 15 minutes");
				return uploadFailedMasg(link, "Timed out, as no operation performed in last few minutes");
			}
		} else {
			log.error("Incorect details are supplied");
			return uploadFailedMasg(link, "Invalid details Supplied , please login Again");
		}

		// compressing the file
		Long fileSize = file.getSize();
		if (fileSize >= 1073741824) {
			log.error("Maximum file size exceeded");
			map.put("msg", "Maximum File Size Exceeded");
			map.put("operation", "failed");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// check for extension
		final String exts = "jpeg jpg png pdf doc docx xlsx zip rar 7z gz archive pps  ppt  pptx xlsm  xls txt mp4 mkv flv mov avi wmv 3gp aac aac mp3  m4b m4p  ogg oga wma wmv ";
		final String videoExt = "mkv flv mov avi wmv mp4";
		final String zipExt = "zip rar 7z gz archive";

		String fileName = file.getOriginalFilename();
		String ext = StringUtils.getFilenameExtension(fileName).toLowerCase();
		if (!exts.contains(ext)) {
			log.error("File format not supported  = {} ", fileName);
			map.put("msg", "File format not supported " + fileName);
			map.put("operation", "failed");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("refreshing values");
		linkExpiry.put(link, new Date().getTime());

		Optional<LinkDetails> optLinkDetails = linkDetailsRepo.findById(link);
		if (!optLinkDetails.isPresent()) {
			map.put("msg", "Invalid Link ");
			map.put("operation", "failed");
			log.error("Invalid link supplied");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {

			// compressing video file
			if (videoExt.contains(ext)) {
				log.info("Compressing the video file");
				file = fileCompressService.getCompressFile(file);
			}

			// file scanning
			if (!zipExt.contains(ext)) {
				log.info("Scanning the file for virus");
				List<FileScanResponseDto> scanResult = fileScanService.scanFiles(new MultipartFile[] { file });

				if (scanResult.get(0).getDetected()) {
					map.put("msg", "Virus Detected");
					map.put("operation", "failed");
					log.error("Virus found in uploaded file");
					linkExpiry.put(link, new Date().getTime());
					return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				if (fileScanService.isFileContainsVirus(file)) {
					map.put("msg", "Virus Detected");
					map.put("operation", "failed");
					log.error("Virus found in uploaded file");
					linkExpiry.put(link, new Date().getTime());
					return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
				}

			}
			map = thirdPartyService.saveSharedDocument(file, new UploadDetails(email.toLowerCase(), fileOwner, link),
					optLinkDetails.get());
			linkExpiry.put(link, new Date().getTime());
			clearTempFolder.clearAllFileOlderThan24Hr();
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		}

	}

	@DeleteMapping("file/{idCode}/{linkId}/{fileId}")
	public ResponseEntity<Boolean> deleteFile(@PathVariable("idCode") Integer idCode,
			@PathVariable("linkId") Integer linkId, @PathVariable("fileId") Integer fileId)
			throws DataNotFoundException, CustomMessageException {

		log.info("Inside api/public/file/{idCode}/{linkId}/{fileId}");
		log.debug("idCode = {} linkId = {}  fileId = {} ", idCode, linkId, fileId);

		if (linkOTP.get(linkId).equals(idCode)) {
			Long createDate = linkExpiry.get(linkId);
			long seconds = TimeUnit.MILLISECONDS.toSeconds((new Date().getTime() - createDate));
			if ((int) seconds > AppConstant.USER_SEEESION_LOGGED_OUT_IN_SEC) {
				linkOTP.remove(linkId);
				linkExpiry.remove(linkId);
				log.error("ideal for more than 15 minuted");
				throw new CustomMessageException("Timed out, as no operation performed in last few minutes");
			}
		} else {
			linkOTP.remove(linkId);
			linkExpiry.remove(linkId);
			log.error("Invalid Details Supplied, please Loggin again");
			throw new CustomMessageException("Invalid Details Supplied, please Loggin again");
		}
		linkExpiry.put(fileId, new Date().getTime());
		return new ResponseEntity<Boolean>(thirdPartyService.deleteFile(fileId), HttpStatus.OK);
	}

	@GetMapping("prevUpload/{linkId}")
	public ResponseEntity<ThirdPartyUpload> getUncompleteURLUploads(@PathVariable("linkId") Integer linkId)
			throws DataNotFoundException, CustomMessageException {
		log.info("Inside api/public/prevUpload/{linkId}");
		log.debug("linkId = {}  ", linkId);
		return new ResponseEntity<ThirdPartyUpload>(thirdPartyService.getUncompleteUrlUploadsFiles(linkId),
				HttpStatus.OK);
	}

	@PutMapping("link/generate/otp")
	public ResponseEntity<Boolean> generateOTP(@RequestBody UrnDetails urn)
			throws DataNotFoundException, CustomMessageException {
		log.info("Inside api/public/link/generate/otp");
		boolean result = false;
		Integer otp = userService.regenOTP(urn.getUrn());
		if (otp > 0)
			result = true;
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

	@PutMapping("upload/complete")
	public ResponseEntity<Boolean> setUploadComplete(@RequestBody LinkId linkdetails)
			throws DataNotFoundException, CustomMessageException {
		log.info("Inside api/public/upload/complete");
		log.debug("L inkdetails details  = {} ", linkdetails);
		if (linkOTP.get(linkdetails.getLink()).equals(linkdetails.getIdCode())) {
			Long createDate = linkExpiry.get(linkdetails.getLink());
			long seconds = TimeUnit.MILLISECONDS.toSeconds((new Date().getTime() - createDate));
			if ((int) seconds > AppConstant.USER_SEEESION_LOGGED_OUT_IN_SEC) {
				linkOTP.remove(linkdetails.getLink());
				linkExpiry.remove(linkdetails.getLink());
				log.error("Timed out, as no operation performed in last few minutes");
				throw new CustomMessageException("Timed out, as no operation performed in last few minutes");
			}
		} else {
			linkOTP.remove(linkdetails.getLink());
			linkExpiry.remove(linkdetails.getLink());
			log.error("Invalid Details Supplied, please Loggin again");
			throw new CustomMessageException("Invalid Details Supplied, please Loggin again");
		}
		linkExpiry.put(linkdetails.getLink(), new Date().getTime());

		return new ResponseEntity<>(userService.completeDownload(linkdetails.getLink()), HttpStatus.OK);
	}

	private ResponseEntity<Map<String, String>> uploadFailedMasg(Integer link, String msg) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "msg");
		map.put("operation", "failed");
		log.error("Upload failed msg ={}", msg);
		linkOTP.remove(link);
		linkExpiry.remove(link);
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	/**
	 * Second Scenario code start from here
	 * 
	 * @throws CustomMessageException
	 */

	@GetMapping("link/details/{linkid}/{id}")
	public ResponseEntity<DownloadDetails> getuploadedFilesOnLink(@PathVariable("linkid") Integer linkid,
			@PathVariable("id") Integer id) throws CustomMessageException {
		log.info("Inside api/public/link/details/{linkid}/{id}");
		log.debug("linkid = {}  id = {}", linkid, id);
		if (linkOTP.get(linkid).equals(id)) {
			Long createDate = linkExpiry.get(linkid);
			long seconds = TimeUnit.MILLISECONDS.toSeconds((new Date().getTime() - createDate));
			if ((int) seconds > AppConstant.USER_SEEESION_LOGGED_OUT_IN_SEC) {
				linkOTP.remove(linkid);
				linkExpiry.remove(linkid);
				log.error("Timed out, as no operation performed in last few minutes");
				throw new CustomMessageException("Timed out, as no operation performed in last few minutes");
			}
		} else {
			linkOTP.remove(linkid);
			linkExpiry.remove(linkid);
			log.error("Invalid Details Supplied, please Loggin again");
			throw new CustomMessageException("Invalid Details Supplied, please Loggin again");
		}
		log.info("Refress the link timeout");
		linkExpiry.put(linkid, new Date().getTime());
		return new ResponseEntity<DownloadDetails>(thirdPartyService.getUploadedFiles(linkid), HttpStatus.OK);
	}

	@PutMapping("forget/password")
	public ResponseEntity<Integer> forgetPassword(@RequestBody ForgetPassword forgetPwd) throws CustomMessageException {
		log.info("api/public/forget/password");
		log.debug("forgetPwd ={}", forgetPwd);
		passwordResetMap.put(forgetPwd.getEmail(), new Date().getTime());
		return new ResponseEntity<Integer>(thirdPartyService.forgetPwd(forgetPwd.getEmail()), HttpStatus.OK);
	}

	@PutMapping("verify/otp")
	public ResponseEntity<Integer> verifyOTP(@RequestBody ForgetPassword forgetPwd) throws CustomMessageException {
		log.info("api/public/verify/otp");
		log.debug("forgetPwd ={}", forgetPwd);
		Integer randumNumber = 0;

		long seconds = TimeUnit.MILLISECONDS
				.toSeconds((new Date().getTime() - passwordResetMap.get(forgetPwd.getEmail())));
		if (seconds > AppConstant.OTP_VALIDITY) {
			return new ResponseEntity<Integer>(randumNumber, HttpStatus.OK);
		}
		if (thirdPartyService.verifyOTP(forgetPwd)) {
			log.info("Setting value to Local storage");
			randumNumber = new Random().nextInt(1000000);
			linkOTP.put(forgetPwd.getUserId(), randumNumber);
			linkExpiry.put(forgetPwd.getUserId(), new Date().getTime());
		}
		return new ResponseEntity<Integer>(randumNumber, HttpStatus.OK);
	}

	@PutMapping("reset/password")
	public ResponseEntity<Boolean> resetPassword(@RequestBody ResetPassword resetPwd) throws CustomMessageException {
		log.info("api/public/reset/password");
		log.debug("resetPwd ={}", resetPwd);
		if (linkOTP.get(resetPwd.getUserID()).equals(resetPwd.getUniqueId())) {
			Long createDate = linkExpiry.get(resetPwd.getUserID());
			long seconds = TimeUnit.MILLISECONDS.toSeconds((new Date().getTime() - createDate));
			if ((int) seconds > AppConstant.USER_SEEESION_LOGGED_OUT_IN_SEC) {
				linkOTP.remove(resetPwd.getUserID());
				linkExpiry.remove(resetPwd.getUserID());
				log.error("Timed out, as no operation performed in last few minutes");
				throw new CustomMessageException("Timed out, as no operation performed in last few minutes");
			}
		} else {
			linkOTP.remove(resetPwd.getUserID());
			linkExpiry.remove(resetPwd.getUserID());
			log.error("Invalid Details Supplied, please Loggin again");
			throw new CustomMessageException("Invalid Details Supplied, please Loggin again");
		}
		linkOTP.remove(resetPwd.getUserID());
		linkExpiry.remove(resetPwd.getUserID());
		return new ResponseEntity<Boolean>(thirdPartyService.resetPwd(resetPwd), HttpStatus.OK);
	}

	@GetMapping("account/status/{email}")
	public ResponseEntity<Map> getFileShared(@PathVariable("email") String email) {
		Map<String, String> map = new HashMap<>();
		log.info("inside api/public/account/status/{email}");
		log.debug("fileiId = {}", email);
		map.put("status", userService.checkAccountStatus(email));
		return new ResponseEntity<Map>(map, HttpStatus.OK);
	}

	@GetMapping("account/hint/{email}")
	public ResponseEntity<Map> getpwdHint(@PathVariable("email") String email) {
		Map<String, String> map = new HashMap<>();
		log.info("inside api/public/account/hint/{email}");
		log.debug("fileiId = {}", email);
		map.put("hint", userService.getPWDHint(email));
		return new ResponseEntity<Map>(map, HttpStatus.OK);
	}
}
