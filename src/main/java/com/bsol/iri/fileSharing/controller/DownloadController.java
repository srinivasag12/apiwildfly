package com.bsol.iri.fileSharing.controller;

/**
 * 
 * @author rupesh
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsol.iri.fileSharing.entity.DownloadLink;
import com.bsol.iri.fileSharing.entity.FileDownloadLink;
import com.bsol.iri.fileSharing.entity.FileDownloadLinkId;
import com.bsol.iri.fileSharing.entity.MaFileLocation;
import com.bsol.iri.fileSharing.entity.UploadedFiles;
import com.bsol.iri.fileSharing.exception.DataNotFoundException;
import com.bsol.iri.fileSharing.models.FilePreview;
import com.bsol.iri.fileSharing.repos.DownloadLinkRepo;
import com.bsol.iri.fileSharing.repos.FileDownloadLinkRepo;
import com.bsol.iri.fileSharing.repos.MaFileLocationRepo;
import com.bsol.iri.fileSharing.repos.UploadedFileRepo;
import com.bsol.iri.fileSharing.storage.service.UploadDownload;
import com.bsol.iri.fileSharing.util.AppConstant;
import com.bsol.iri.fileSharing.util.DateTimeUtil;
import com.bsol.iri.fileSharing.util.DeleteDirectories;

@RestController
@RequestMapping("api/download")
public class DownloadController {

	@Autowired
	MaFileLocationRepo locationRepo;

	@Autowired
	UploadedFileRepo fileDetailsRepo;

	@Autowired
	private UploadDownload updown;

	@Autowired
	private FileDownloadLinkRepo fileDownloadRepo;

	@Autowired
	private DownloadLinkRepo downloadLinkRepo;

	@Autowired
	ServletContext servletContext;

	private String localDir = "src/main/resources/tmp/";

	private static Logger log = LoggerFactory.getLogger(DownloadController.class);

	@GetMapping("thirdparty/shared/file/{fileId}")
	public ResponseEntity<Object> getthirdPartyFile(@PathVariable("fileId") Integer fileId, HttpServletRequest request,
			HttpServletResponse response) throws IOException, DataNotFoundException {
		log.info("inside api/download/thirdparty/shared/file/fileId with data file id {}",fileId);
		log.debug("fileId {}", fileId);
		return getFile(fileId, request, response, false);
	}

	@GetMapping("user/shared/file/{fileId}")
	public ResponseEntity<Object> getUserFile(@PathVariable("fileId") Integer fileId, HttpServletRequest request,
			HttpServletResponse response) throws IOException, DataNotFoundException {
		log.info("inside api/download/user/shared/file/fileId with data file id {}",fileId);
		log.debug("fileId {}", fileId);
		return getFile(fileId, request, response, true);
	}

	@GetMapping("thirdparty/shared/file/preview/{fileId}")
	public ResponseEntity<FilePreview> getThirdpartyFilePreview(@PathVariable("fileId") Integer fileId,
			HttpServletRequest request, HttpServletResponse response) throws IOException, DataNotFoundException {
		log.info("inside api/download/thirdparty/shared/file/preview/fileId with data file id {}",fileId);
		log.debug("fileId {}", fileId);
		return getFilePreview(fileId, request, response, false);
	}

	@GetMapping("user/shared/file/preview/{fileId}")
	public ResponseEntity<FilePreview> getUserFilePreview(@PathVariable("fileId") Integer fileId,
			HttpServletRequest request, HttpServletResponse response) throws IOException, DataNotFoundException {
		log.info("inside api/download/user/shared/file/preview/fileIdwith data file id {}",fileId);
		log.debug("fileId {}", fileId);
		return getFilePreview(fileId, request, response, true);
	}

	private ResponseEntity<Object> getFile(Integer fileId, HttpServletRequest request, HttpServletResponse response,
			boolean isViewUpdate) throws IOException, DataNotFoundException {

		log.info(" Request to download the file with the fileId {}",fileId);
		File newDirectoryNameLocation = new File(localDir);
		new DeleteDirectories().recursiveDelete(newDirectoryNameLocation);
		Optional<MaFileLocation> ld = locationRepo.findById(fileId);
		if (ld.isPresent()) {
			log.info("file found with fileId {}, checking directory",fileId);
			MaFileLocation loc = ld.get();
			if (!newDirectoryNameLocation.exists()) {
				log.info("Directory not found , creating directory");
				newDirectoryNameLocation.mkdirs();
			}

			String filename = fileViewed(fileId, isViewUpdate);
			log.info("Downloading the file with fileId {}", fileId);
			if (updown.moveFile(loc.getBucketName(), localDir + filename)) {
				String contentType = "application/octet-stream";
				Path filePath = Paths.get(localDir + filename).toAbsolutePath().normalize();
				log.info("Fetching the file");
				try {
					Resource resource = new UrlResource(filePath.toUri());
					if (resource.exists()) {
						return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
								.header(HttpHeaders.CONTENT_DISPOSITION,
										"attachment; filename=\"" + resource.getFilename() + "\"")
								.body(resource);
					} else {
						log.error("File not found with id {}, while sending response", fileId);
						throw new FileNotFoundException(" File Not Found");
					}
				} catch (IOException e) {
					log.error("Error Happend {} " + e.getMessage());
					e.printStackTrace();
				}
			}

		} else {
			log.error("File not found on Source location with ID {}", fileId);
			throw new DataNotFoundException("File not Found");
		}
		return null;
	}

	private ResponseEntity<FilePreview> getFilePreview(Integer fileId, HttpServletRequest request,
			HttpServletResponse response, boolean isViewUpdate) throws IOException, DataNotFoundException {

		log.info("Inside getFilePreview for fileId {}", fileId);
		FilePreview preview = new FilePreview();
		File newDirectoryNameLocation = new File(localDir);
		new DeleteDirectories().recursiveDelete(newDirectoryNameLocation);
		Optional<MaFileLocation> ld = locationRepo.findById(fileId);
		log.debug("finding the File details with id {}", fileId);
		if (ld.isPresent()) {
			MaFileLocation loc = ld.get();
			if (!newDirectoryNameLocation.exists()) {
				log.info("Directory not found creating Directory");
				newDirectoryNameLocation.mkdirs();
			}

			String filename = fileViewed(fileId, isViewUpdate);
			log.info("Downloading the file with file Id {}", fileId);
			if (updown.moveFile(loc.getBucketName(), localDir + filename)) {
				Path path = Paths.get(localDir + filename);
				byte[] fileByte = null;
				String encodedString = null;
				if (path != null) {
					try {
						log.info("Reading file bytes");
						fileByte = Files.readAllBytes(path);
						log.info("Encoding the String");
						encodedString = Base64.getMimeEncoder().encodeToString(fileByte);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				preview.setFileData(encodedString);

				return new ResponseEntity<FilePreview>(preview, HttpStatus.OK);
			}

		} else {
			throw new DataNotFoundException("File not Found");
		}
		return null;
	}

	private String fileViewed(Integer fileId, boolean isViewApplicable) {
		log.info("finding file with id {}", fileId);
		log.debug("isViewApplicable {}", isViewApplicable);
		UploadedFiles uploadedFile = fileDetailsRepo.findById(fileId).get();
		String filename = uploadedFile.getFileName();
		log.debug("file name  {}", filename);
		log.debug("Object details {}", uploadedFile);
		if (isViewApplicable == true && (uploadedFile.getIsViewed() == null || uploadedFile.getIsViewed() == 0)) {
			uploadedFile.setIsViewed(AppConstant.TRUE);
			uploadedFile.setViewdAt(DateTimeUtil.getTodaysDate());
			fileDetailsRepo.save(uploadedFile);
		}

		return filename;
	}

	@GetMapping("view/shared/file/{linkId}/{fileId}")
	public ResponseEntity<Object> downloadThirdpartyFile(@PathVariable("linkId") Integer linkId,
			@PathVariable("fileId") Integer fileId, HttpServletRequest request, HttpServletResponse response)
			throws IOException, DataNotFoundException {
		log.info("Inside api/download/view/shared/file/{linkId}/{fileId}  fileId {}   linkId {}", fileId, linkId);
		setIsViewOnFileDownload(linkId, fileId);
		return getFile(fileId, request, response, false);
	}

	@GetMapping("view/shared/file/preview/{linkId}/{fileId}")
	public ResponseEntity<FilePreview> downloadThirdpartyFilePreview(@PathVariable("linkId") Integer linkId,
			@PathVariable("fileId") Integer fileId, HttpServletRequest request, HttpServletResponse response)
			throws IOException, DataNotFoundException {
		log.info("Inside api/download/view/shared/file/preview/{linkId}/{fileId}  fileId {}   linkId {}", fileId,
				linkId);
		setIsViewOnFileDownload(linkId, fileId);
		return getFilePreview(fileId, request, response, true);
	}

	private boolean setIsViewOnFileDownload(Integer linkId, Integer fileId) {
		Optional<FileDownloadLink> fdlOptional = fileDownloadRepo.findById(new FileDownloadLinkId(fileId, linkId));

		log.info("getting file download link with link id {} and fileId {}", linkId, fileId);
		if (fdlOptional.isPresent()) {
			log.info("Set isview flag TRUE to fileDownloadLink object");
			FileDownloadLink fdl = fdlOptional.get();
			fdl.setIsViewed(AppConstant.TRUE);
			fdl.setViewedOn(DateTimeUtil.getTodaysDate());
			log.info("Object to save with value {} ", fdl);
			fileDownloadRepo.save(fdl);

			// Check for rest Unviewd fle
			if (fileDownloadRepo.findDiffrent(linkId).intValue() == 0) {
				// update the link status
				
				log.info("All files of this link are viewed , changing link_status to complete");
				DownloadLink dl = downloadLinkRepo.findById(linkId).get();
				dl.setLinkStatus(AppConstant.COMPLETED_LINK);
				dl.setUpdatedOn(DateTimeUtil.getTodaysDate());
				downloadLinkRepo.save(dl);
			}

			return true;
		} else
			return false;

	}
}
