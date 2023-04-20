package com.bsol.iri.fileSharing.controller;

/**
 * 
 * @author rupesh
 *
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bsol.iri.fileSharing.exception.CustomMessageException;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.fileCompression.FileCompressService;
import com.bsol.iri.fileSharing.models.AllLinkDetailsResponse;
import com.bsol.iri.fileSharing.models.ArchiveDetails;
import com.bsol.iri.fileSharing.models.ArchiveFolder;
import com.bsol.iri.fileSharing.models.ArchiveLink;
import com.bsol.iri.fileSharing.models.CommunicationDetails;
import com.bsol.iri.fileSharing.models.DashBoardData;
import com.bsol.iri.fileSharing.models.DashBoardLinkDetails;
import com.bsol.iri.fileSharing.models.DashBoardSharedFile;
import com.bsol.iri.fileSharing.models.Description;
import com.bsol.iri.fileSharing.models.Folder;
import com.bsol.iri.fileSharing.models.LinkDetail;
import com.bsol.iri.fileSharing.models.LinkHistory;
import com.bsol.iri.fileSharing.models.LinkIdArray;
import com.bsol.iri.fileSharing.models.LinkStatusChangeReq;
import com.bsol.iri.fileSharing.models.LinkValidity;
import com.bsol.iri.fileSharing.models.LogoutUserDetails;
import com.bsol.iri.fileSharing.models.PrevouslyUploadedFiles;
import com.bsol.iri.fileSharing.models.RemoveSpecificFile;
import com.bsol.iri.fileSharing.models.ShareFile;
import com.bsol.iri.fileSharing.models.SharedFileResponse;
import com.bsol.iri.fileSharing.models.SharedToThirdPartyFiles;
import com.bsol.iri.fileSharing.models.UnArchiveRequest;
import com.bsol.iri.fileSharing.models.UpdatePwd;
import com.bsol.iri.fileSharing.models.UrnDetails;
import com.bsol.iri.fileSharing.models.UserModel;
import com.bsol.iri.fileSharing.service.ThirdPartyService;
import com.bsol.iri.fileSharing.service.UserService;
import com.bsol.iri.fileSharing.util.AppConstant;

import io.github.techgnious.exception.VideoException;

@RestController
@RequestMapping("api/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;

	@Autowired
	ThirdPartyService thirdPartyService;

	@Autowired
	FileCompressService fileCompressService;

	@PostMapping("login")
	public ResponseEntity<Object> getUser(@RequestBody UserModel user) {
		log.info("inside api/user/login");
		log.debug(" user = {}", user);
		return new ResponseEntity<>(service.authUser(user), HttpStatus.OK);
	}

	@PutMapping("logout")
	public ResponseEntity<Map<String, String>> logOutUser(@RequestBody LogoutUserDetails userInfo,
			HttpServletRequest request) throws DataNotFoundException {
		log.info("inside api/user/logout");
		log.debug("userInfo = {}", userInfo);
		return service.logoutUser(userInfo.getUserId(), request);
	}

	@GetMapping("files/shared/{userId}")
	public ResponseEntity<List<SharedFileResponse>> getSharedFilesDetails(@PathVariable("userId") Integer userId)
			throws DataNotFoundException {
		log.info("inside api/user/files/shared/{userId}");
		log.debug("userId = {}", userId);
		return new ResponseEntity<List<SharedFileResponse>>(service.getSharedFiles(userId), HttpStatus.OK);
	}

	@GetMapping("all/links/{userId}/{page}/{pageSise}/{status}")
	public ResponseEntity<List<AllLinkDetailsResponse>> getAllLinkDetails(@PathVariable("userId") Integer userId,
			@PathVariable("page") Integer page, @PathVariable("pageSise") Integer pageSise,
			@PathVariable("status") String status) throws DataNotFoundException, CustomMessageException {
		log.info("inside api/user/all/links/{userId}/{page}/{pageSise}/{status}");
		log.debug("userId = {} ", userId);
		log.debug("page = {}", page);
		log.debug("pageSise = {}", pageSise);
		log.debug("status = {}", status);
		return new ResponseEntity<List<AllLinkDetailsResponse>>(
				service.getAllLinkDetails(userId, AppConstant.BY_OWNER, page, pageSise, status), HttpStatus.OK);
	}

	@PutMapping("link/status")
	public ResponseEntity<Boolean> updateLinkStatus(@RequestBody LinkStatusChangeReq linkstatus) {
		log.info("inside api/user/link/status");
		log.debug("linkstatus = {}", linkstatus);
		return new ResponseEntity<Boolean>(service.updateLinkStatus(linkstatus), HttpStatus.OK);
	}

	@PutMapping("link/regen")
	public ResponseEntity<Integer> regenerateOTP(@RequestBody UrnDetails urn)
			throws DataNotFoundException, CustomMessageException {
		log.info("inside api/user/link/regen");
		log.debug("urn = {}", urn);
		return new ResponseEntity<Integer>(service.regenOTP(urn.getUrn()), HttpStatus.OK);
	}

	@PutMapping("link/ext")
	public ResponseEntity<Boolean> extendLinkValidity(@RequestBody LinkValidity linkval) throws DataNotFoundException {
		log.info("inside api/user/link/ext");
		log.debug("linkval = {}", linkval);
		return new ResponseEntity<Boolean>(service.extendLinkValidity(linkval), HttpStatus.OK);
	}

	@PutMapping("link/cancel")
	public ResponseEntity<Boolean> cancelLink(@RequestBody UrnDetails urn) {
		log.info("inside api/user/link/cancel");
		log.debug("urn = {}", urn);
		return new ResponseEntity<Boolean>(service.cancelRequest(urn.getUrn()), HttpStatus.OK);
	}

	@GetMapping("dash/recent/files/{userId}/{pageSize}/{pageNo}")
	public List<DashBoardData> getDashBoardData(@PathVariable("userId") Integer userId,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("pageNo") Integer pageNo)
			throws DataNotFoundException {
		log.info("inside api/user/dash/recent/files/{userId}/{pageSize}/{pageNo}");
		log.debug("userId = {} , pageSize = {} pageNo = {}", userId, pageSize, pageNo);
		return service.getRecentThirdPartyUploadedFile(userId, pageNo, pageSize);

	}

	@GetMapping("dash/recent/shared/{userId}/{pageSize}/{pageNo}")
	public List<DashBoardSharedFile> getRecentSharedFile(@PathVariable("userId") Integer userId,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("pageNo") Integer pageNo)
			throws DataNotFoundException {
		log.info("inside api/user/dash/recent/shared/{userId}/{pageSize}/{pageNo}");
		log.debug("userId = {} , pageSize = {} pageNo = {}", userId, pageSize, pageNo);
		return service.getRecentSharedFile(userId, pageNo, pageSize);

	}

	@GetMapping("dash/recent/url/{userId}/{pageSize}/{pageNo}")
	public List<DashBoardLinkDetails> getRecentSharedURL(@PathVariable("userId") Integer userId,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("pageNo") Integer pageNo)
			throws DataNotFoundException, CustomMessageException {
		log.info("inside api/user/dash/recent/url/{userId}/{pageSize}/{pageNo}");
		log.debug("userId = {} , pageSize = {} pageNo = {}", userId, pageSize, pageNo);
		return service.getSharedLinkStatus(userId, pageNo, pageSize);

	}

	// Second Scenario Started - Third party download Only

	@PostMapping("create/folder")
	public Integer createLink(@RequestBody LinkDetail linkDetails) throws CustomMessageException {
		log.info("inside api/user/create/folder");
		log.debug("linkDetails = {}", linkDetails);
		return service.createLink(linkDetails);
	}

	@PostMapping(value = "upload/file")
	public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileOwner") Integer fileOwner, @RequestParam("folderId") Integer folderId)
			throws IOException, VideoException {
		log.info("inside api/user/upload/file");
		log.debug("fileOwner = {}  folderId = {}", fileOwner, folderId);
		log.debug("File name  = {}  size  = {} KB", file.getOriginalFilename(), (file.getSize() / 1024));

		// check for extension
		final String exts = "jpeg jpg png pdf doc docx xlsx zip rar 7z gz archive pps  ppt  pptx xlsm  xls txt mp4 mkv flv mov avi wmv 3gp aac aac mp3  m4b m4p  ogg oga wma wmv ";
		final String videoExt = "mkv flv mov avi wmv mp4";
		Map<String, String> map = new HashMap<String, String>();
		String fileName = file.getOriginalFilename();
		String ext = StringUtils.getFilenameExtension(fileName).toLowerCase();

		map.put("filename", fileName);
		Long fileSize = file.getSize();
		if (fileSize >= 1073741824) {
			log.error("Maximum File Size Exceeded");
			map.put("msg", "Maximum File Size Exceeded");
			map.put("operation", "failed");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!exts.contains(ext)) {
			log.error("File format not supported = {} ", fileName);
			map.put("msg", "File format not supported " + fileName);
			map.put("operation", "failed");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (videoExt.contains(ext)) {
			log.info("Compressing the Video");
			file = fileCompressService.getCompressFile(file);
		}
		return service.uploadFile(file, fileOwner, folderId);
	}

	@PostMapping(value = "share/files")
	public ResponseEntity<Set<String>> uploadFile(@RequestBody ShareFile shareFile) throws CustomMessageException {
		log.info("inside api/user/share/files");
		log.debug("shareFile = {}", shareFile);
		return new ResponseEntity<Set<String>>(service.sharedFiles(shareFile), HttpStatus.OK);
	}

	@GetMapping("shared/folder/{userId}/{pageNo}/{pageSize}")
	public ResponseEntity<List<Folder>> getSharedLink(@PathVariable("userId") Integer userId,
			@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize)
			throws CustomMessageException {
		log.info("inside api/user/shared/folder/{userId}/{pageNo}/{pageSize}");
		log.debug("userId = {} , pageSize = {} pageNo = {}", userId, pageSize, pageNo);
		return new ResponseEntity<List<Folder>>(service.getSharedLink(userId, pageNo, pageSize), HttpStatus.OK);
	}

	@GetMapping("uploaded/files/{folderId}")
	public ResponseEntity<PrevouslyUploadedFiles> getUncompleteURLUploads(@PathVariable("folderId") Integer folderId)
			throws DataNotFoundException {
		log.info("inside api/user/shared/folder/{userId}/{pageNo}/{pageSize}");
		log.debug("folderId = {}", folderId);
		return new ResponseEntity<PrevouslyUploadedFiles>(service.getUploadedFiles(folderId), HttpStatus.OK);
	}

	@DeleteMapping("delete/file/{fileId}")
	public ResponseEntity<Boolean> deleteUploadedFile(@PathVariable("fileId") Integer fileId)
			throws DataNotFoundException {
		log.info("inside api/user/delete/file/{fileId}");
		log.debug("fileId = {}", fileId);
		return new ResponseEntity<Boolean>(service.archieveFile(fileId), HttpStatus.OK);
	}

	@GetMapping("all/downlink/{userId}/{page}/{pageSize}/{status}")
	public ResponseEntity<List<AllLinkDetailsResponse>> getAllDownLoadLinkDetails(
			@PathVariable("userId") Integer userId, @PathVariable("page") Integer page,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("status") String status)
			throws DataNotFoundException, CustomMessageException {
		log.info("inside api/user/all/downlink/{userId}/{page}/{pageSize}/{status}");
		log.debug("userId = {} , pageSize = {} status = {} ", userId, pageSize, status);
		return new ResponseEntity<List<AllLinkDetailsResponse>>(service.getAllDownLink(userId, page, pageSize, status),
				HttpStatus.OK);
	}

	@PutMapping("downlink/ext")
	public ResponseEntity<Boolean> extendDownLinkValidity(@RequestBody LinkValidity linkval)
			throws DataNotFoundException {
		log.info("inside api/user/downlink/ext");
		log.debug("linkval = {}", linkval);
		return new ResponseEntity<Boolean>(service.extendDownlink(linkval), HttpStatus.OK);
	}

	@PutMapping("downlink/status")
	public ResponseEntity<Boolean> updateDownLinkStatus(@RequestBody LinkStatusChangeReq linkstatus)
			throws CustomMessageException {
		log.info("inside api/user/downlink/status");
		log.debug("linkstatus = {}", linkstatus);
		return new ResponseEntity<Boolean>(service.updateDownLinkStatus(linkstatus), HttpStatus.OK);
	}

	/**
	 * Third Scenario - both can upload and download
	 * 
	 * @throws CustomMessageException
	 */

	@GetMapping("all/udlinks/{userId}/{page}/{pageSize}/{status}")
	public ResponseEntity<List<AllLinkDetailsResponse>> getAllLinkDetailsForBothParty(
			@PathVariable("userId") Integer userId, @PathVariable("page") Integer page,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("status") String status)
			throws DataNotFoundException, CustomMessageException {
		log.info("inside api/user/all/udlinks/{userId}/{page}/{pageSize}/{status}");
		log.debug("userId = {} , pageSize = {} status = {} ", userId, pageSize, status);
		return new ResponseEntity<List<AllLinkDetailsResponse>>(
				service.getAllLinkDetails(userId, AppConstant.BOTH_PARTY, page, pageSize, status), HttpStatus.OK);
	}

	/**
	 * Get archived file
	 * 
	 * @throws CustomMessageException
	 */

	@GetMapping("file/archived/{userId}")
	public ResponseEntity<ArchiveDetails> getArchivedFile(@PathVariable("userId") Integer userId)
			throws CustomMessageException {
		log.info("inside api/user/file/archived/{userId}");
		log.debug("userId = {}", userId);
		return new ResponseEntity<ArchiveDetails>(service.getArchiveFile(userId), HttpStatus.OK);
	}

	@PutMapping("file/unarchived")
	public ResponseEntity<Integer> unArchiveFile(@RequestBody UnArchiveRequest unArchive)
			throws CustomMessageException {
		log.info("inside api/user/file/unarchived");
		log.debug("unArchive = {}", unArchive);
		return new ResponseEntity<Integer>(service.unArchiveFile(unArchive), HttpStatus.OK);
	}

	// Archiving folders

	@PutMapping("folder/archive")
	public ResponseEntity<Integer> archieveFolder(@RequestBody ArchiveFolder archiveFolder)
			throws CustomMessageException {
		log.info("inside api/user/folder/archive");
		log.debug("archiveFolder = {}", archiveFolder);
		return new ResponseEntity<Integer>(service.archiveFolder(archiveFolder), HttpStatus.OK);

	}

	@PutMapping("folder/unarchive")
	public ResponseEntity<Integer> unArchieveFolder(@RequestBody ArchiveFolder archiveFolder)
			throws CustomMessageException {
		log.info("inside api/user/folder/unarchive");
		log.debug("archiveFolder = {}", archiveFolder);
		return new ResponseEntity<Integer>(service.unArchiveFolder(archiveFolder), HttpStatus.OK);

	}

	// Archive Uploaded files via upload link
	@PutMapping("link/archive")
	public ResponseEntity<List<Integer>> archieveLink(@RequestBody ArchiveLink archiveLink)
			throws CustomMessageException {
		log.info("inside api/user/link/archive");
		log.debug("archiveLink = {}", archiveLink);
		return new ResponseEntity<List<Integer>>(service.archiveLink(archiveLink), HttpStatus.OK);

	}

	@PutMapping("link/unarchive")
	public ResponseEntity<List<Integer>> unArchieveLink(@RequestBody ArchiveLink archiveLink)
			throws CustomMessageException {
		log.info("inside api/user/link/unarchive");
		log.debug("archiveLink = {}", archiveLink);
		return new ResponseEntity<List<Integer>>(service.unArchiveLink(archiveLink), HttpStatus.OK);

	}

	@GetMapping("description")
	public ResponseEntity<List<Description>> getMasterDescription() throws CustomMessageException {
		log.info("inside api/user/description");
		return new ResponseEntity<List<Description>>(service.getDescription(), HttpStatus.OK);
	}

	@GetMapping("sharedtothirdparty/{userId}")
	public ResponseEntity<List<SharedToThirdPartyFiles>> getFilesSharedToThirdParty(
			@PathVariable("userId") Integer userId) throws CustomMessageException {
		log.info("inside api/user/sharedtothirdparty/{userId}");
		log.debug("userId = {}", userId);
		return new ResponseEntity<List<SharedToThirdPartyFiles>>(service.sharedToThirdParty(userId), HttpStatus.OK);
	}

	@PutMapping("remove/file")
	public ResponseEntity<Boolean> removeSpecificFileFromLink(@RequestBody RemoveSpecificFile files) {
		log.info("inside api/user/remove/file");
		return new ResponseEntity<Boolean>(service.removeSpecificFileFromSharedDownloadLink(files), HttpStatus.OK);
	}

	@DeleteMapping("remove/sharedfile/{fileId}")
	public ResponseEntity<Boolean> removeFileFromAllUser(@PathVariable("fileId") Integer fileId) {
		log.info("inside api/user/remove/sharedfile/{fileId}");
		log.debug("fileId = {}", fileId);
		return new ResponseEntity<Boolean>(service.removeSharedFileFromAllUser(fileId), HttpStatus.OK);
	}

	/**
	 * History table
	 * 
	 * @throws CustomMessageException
	 */

	@GetMapping("history/{userId}")
	public ResponseEntity<List<CommunicationDetails>> getHistory(@PathVariable("userId") Integer userId)
			throws CustomMessageException {
		log.info("inside api/user/history/{userId}");
		log.debug("userId = {}", userId);
		return new ResponseEntity<List<CommunicationDetails>>(service.getLinkComunication(userId), HttpStatus.OK);
	}

	@GetMapping("link/history/{type}/{vessel}/{descid}")
	public ResponseEntity<List<LinkHistory>> getLinkHistory(@PathVariable("type") char type,
			@PathVariable("vessel") String vessel, @PathVariable("descid") Integer descid)
			throws CustomMessageException {
		log.info("inside api/user/link/history/{type}/{vessel}/{descid}");
		log.debug("type = {}  vessel = {}  descid = {} ", type, vessel, descid);
		return new ResponseEntity<List<LinkHistory>>(service.getLinkHistory(type, vessel, descid), HttpStatus.OK);
	}

	@PutMapping("link/view")
	public ResponseEntity<Boolean> updateClickStatusForLink(@RequestBody LinkIdArray linkIds)
			throws CustomMessageException {
		log.info("inside api/user/link/view");
		log.debug("linkIds = {}", linkIds);
		return new ResponseEntity<Boolean>(service.linkViewed(linkIds), HttpStatus.OK);
	}

	@PutMapping(value = "merge/files")
	public ResponseEntity<Boolean> mergeUploadFile(@RequestBody ShareFile shareFile) throws CustomMessageException {
		log.info("inside api/user/merge/files");
		log.debug("shareFile = {}", shareFile);
		return new ResponseEntity<Boolean>(service.mergeFiles(shareFile), HttpStatus.OK);
	}

	@PutMapping("change/password")
	public ResponseEntity<Boolean> updatePassword(@RequestBody UpdatePwd updatePwd) throws CustomMessageException {
		log.info("inside api/user/change/password");
		log.debug("updatePwd = {}", updatePwd);
		return new ResponseEntity<Boolean>(service.updatePwd(updatePwd), HttpStatus.OK);
	}

	@GetMapping("check/file/{fileiId}")
	public ResponseEntity<Boolean> getFileShared(@PathVariable("fileiId") Integer fileiId) {
		log.info("inside api/user/check/file/{fileiId}");
		log.debug("fileiId = {}", fileiId);
		return new ResponseEntity<Boolean>(service.checkFile(fileiId), HttpStatus.OK);
	}

	
}
