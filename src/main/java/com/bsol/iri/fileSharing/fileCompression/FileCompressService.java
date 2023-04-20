package com.bsol.iri.fileSharing.fileCompression;

/**
 * 
 * @author rupesh
 *	This  class will used to compress a Video file to the specified properties
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.IVAudioAttributes;
import io.github.techgnious.dto.IVSize;
import io.github.techgnious.dto.IVVideoAttributes;
import io.github.techgnious.dto.VideoFormats;
import io.github.techgnious.exception.VideoException;

@Service
public class FileCompressService {

	private final Logger log = LoggerFactory.getLogger(FileCompressService.class);

	public MultipartFile getCompressFile(MultipartFile file) throws IllegalStateException, IOException, VideoException {

		log.debug("Compressing the file {} with size {} KB", file.getName(), (file.getSize() / 1024));
		IVCompressor compressor = new IVCompressor();
		String filePath = "src/main/resources/static/";
		String compressedPath = "src/main/resources/static/Compressed/";
		String fileName = file.getOriginalFilename();
		String ext = StringUtils.getFilenameExtension(fileName).toLowerCase();
		String contentType = file.getContentType();
		File filePathDirectory = new File(filePath);
		File compressedPathDirectory = new File(compressedPath);
		log.info("Checking the folders availability");
		if (!filePathDirectory.exists()) {
			log.info("Folder not found ... createing the folder");
			filePathDirectory.mkdirs();
		}
		if (!compressedPathDirectory.exists()) {
			log.info("Folder not found ... createing the folder");
			compressedPathDirectory.mkdirs();
		}

		byte[] VideoOutput = null;
		// to Store the file in static folder with original name
		Path filepath = Paths.get(filePath, file.getOriginalFilename());
		file.transferTo(filepath);

		// setting attribute to convert the file
		File localfile = new File(filePath + fileName);
		IVSize customRes = new IVSize();
		log.info("Setting the height 360 and width 480 of the video");
		customRes.setWidth(480);
		customRes.setHeight(360);
		IVAudioAttributes audioAttribute = new IVAudioAttributes();
		log.info("Setting the bitrate for audio 128kbps dual channel");
		audioAttribute.setBitRate(128000); // 128kbps
		log.info("Setting Audio channel");
		audioAttribute.setChannels(2);
		audioAttribute.setSamplingRate(44100); // 44hz
		IVVideoAttributes videoAttribute = new IVVideoAttributes();
		log.info("Setting bitrate for video 160 kbps");
		videoAttribute.setBitRate(160000); // Here 160 kbps video is 160000
		log.info("Setting Frame per second for Video to 30");
		videoAttribute.setFrameRate(30); // FPS More the frames more quality and size
		videoAttribute.setSize(customRes);

		// Compressing the video with there original name
		if (ext.equalsIgnoreCase("mp4"))
			VideoOutput = compressor.encodeVideoWithAttributes(Files.readAllBytes(localfile.toPath()), VideoFormats.MP4,
					audioAttribute, videoAttribute);
		else if (ext.equalsIgnoreCase("flv"))
			VideoOutput = compressor.encodeVideoWithAttributes(Files.readAllBytes(localfile.toPath()), VideoFormats.FLV,
					audioAttribute, videoAttribute);
		else if (ext.equalsIgnoreCase("mkv")) {
			try {
				byte[] conv = compressor.convertVideoFormat(Files.readAllBytes(localfile.toPath()), VideoFormats.MKV,
						VideoFormats.MP4);
				VideoOutput = compressor.encodeVideoWithAttributes(conv, VideoFormats.MP4, audioAttribute,
						videoAttribute);
			} catch (Exception e) {
				VideoOutput = Files.readAllBytes(localfile.toPath());
			}
		} else if (ext.equalsIgnoreCase("mov"))
			VideoOutput = compressor.encodeVideoWithAttributes(Files.readAllBytes(localfile.toPath()), VideoFormats.MOV,
					audioAttribute, videoAttribute);
		else if (ext.equalsIgnoreCase("avi"))
			VideoOutput = compressor.encodeVideoWithAttributes(Files.readAllBytes(localfile.toPath()), VideoFormats.AVI,
					audioAttribute, videoAttribute);
		else if (ext.equalsIgnoreCase("wmv")) {
			try {
				byte[] conv = compressor.convertVideoFormat(Files.readAllBytes(localfile.toPath()), VideoFormats.WMV,
						VideoFormats.MP4);
				VideoOutput = compressor.encodeVideoWithAttributes(conv, VideoFormats.MP4, audioAttribute,
						videoAttribute);
			} catch (Exception e) {
				VideoOutput = Files.readAllBytes(localfile.toPath());
			}
		}
		// Saving the file to the Compressed folder with original name
		FileUtils.writeByteArrayToFile(new File("src/main/resources/static/Compressed/" + fileName), VideoOutput);
		// deleting the Original file
		localfile.delete();

		File compressedFile = new File(compressedPath + fileName);

		FileInputStream input = new FileInputStream(compressedFile);
		MultipartFile multipartFile = new MockMultipartFile("file", compressedFile.getName(), contentType,
				IOUtils.toByteArray(input));

		// Delete the Compressed File
		input.close();
		log.info("Deleting the compressed file");
		compressedFile.delete();
		return multipartFile;
	}
}
