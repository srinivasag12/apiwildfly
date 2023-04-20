package com.bsol.iri.fileSharing.clamAv.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.bsol.iri.fileSharing.clamAv.dto.FileScanResponseDto;
import com.bsol.iri.fileSharing.clamAv.exception.ClamAVSizeLimitException;
import com.bsol.iri.fileSharing.clamAv.impl.ClamAV;
import com.bsol.iri.fileSharing.util.AppConstant;

/**
 * 
 * @author rupesh This is a service class to scan passed file.
 */
@Service
public class FileScanServiceImpl implements FileScanService {

	private static Logger log = LoggerFactory.getLogger(FileScanServiceImpl.class);

	@Autowired
	private ClamAV clamAV;

	public List<FileScanResponseDto> scanFiles(MultipartFile[] files) {
		log.info("File received = {} ", (files != null ? files.length : null));
		return Arrays.stream(files).map(multipartFile -> {
			FileScanResponseDto fileScanResponseDto = new FileScanResponseDto();
			try {
				byte[] response = clamAV.scan(multipartFile.getInputStream(), fileScanResponseDto);
				Boolean status = ClamAV.isCleanReply(response);
				fileScanResponseDto.setDetected(status != null ? !status : status);
				log.info("File Scanned = {} Clam AV Response = {} ", multipartFile.getOriginalFilename(),
						(status != null ? status : null));
			} catch (ClamAVSizeLimitException exception) {
				log.error(" ClamAVSizeLimitException Exception occurred while scanning using clam av = {} {}",multipartFile.getOriginalFilename(),
						exception.getMessage());
				fileScanResponseDto.setErrorMessage(exception.getMessage());
			} catch (Exception e) {
				log.error("Exception occurred while scanning using clam av = {} - {}",multipartFile.getOriginalFilename(), e.getMessage());
				fileScanResponseDto.setErrorMessage(e.getMessage());
			}
			fileScanResponseDto.setFileName(multipartFile.getOriginalFilename());
			fileScanResponseDto.setFileType(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
			return fileScanResponseDto;
		}).collect(Collectors.toList());
	}

	@Override
	public boolean isFileContainsVirus(MultipartFile file) throws IOException {
		log.info("checking the file for viruses {}", file.getOriginalFilename());
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		log.info("fos closed");
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
		bodyMap.add("file", new FileSystemResource(convFile));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Boolean> response = restTemplate.exchange(AppConstant.HELPER_APP_URL, HttpMethod.POST,
				requestEntity, Boolean.class);
		convFile.delete();
		return response.getBody();

	}
}
