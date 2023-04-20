package com.bsol.iri.fileSharing.storage.service;

/**
 * 
 * @author rupesh
 *	This is a service to send , fetch and remove data from the S3 bucket using SFTP protocol
 */
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bsol.iri.fileSharing.models.SftpPropDetails;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Service
public class UploadDownloadImpl implements UploadDownload {

	@Autowired
	private SftpPropDetails propDetails;

	private static final Logger log = LoggerFactory.getLogger(UploadDownloadImpl.class);

	// Remote and local is file path with file name

	@Override
	public boolean moveFile(MultipartFile source, String destination) {
		log.info("inside MoveFile method _ sending data to AWS  source {} destination {}", source, destination);
		log.debug("file details are name {} size {} Bytes and destination {} ", source.getName(), source.getSize(),
				destination);
		try {
			InputStream inputStream = new ByteArrayInputStream(source.getBytes());
			ChannelSftp channelSftp = setupJsch();
			channelSftp.connect();
			log.info("Connection established");
			log.info("Sending data...");
			channelSftp.put(inputStream, destination);
			log.info("Data send successfully, terminating the connection");
			channelSftp.exit();
			return true;
		} catch (Exception e) {
			log.error("Some error Occured {}", e.getLocalizedMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean moveFile(String source, String destination) {
		log.info("inside MoveFile method _ fetching data to AWS ");
		log.debug("Fetch details are source - {} destination - {} ", source, destination);
		try {
			ChannelSftp channelSftp = setupJsch();
			channelSftp.connect();
			log.info("Connection established");
			log.info("Fetching  data...");
			channelSftp.get(source, destination);
			log.info("Data fetched successfully, terminating the connection");
			channelSftp.exit();
			return true;
		} catch (Exception e) {
			log.error("Some error Occured {}", e.getLocalizedMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeFile(String source) {
		log.info("inside removeFile method");
		log.debug("Fetch details are source - {} ", source);
		try {
			ChannelSftp channelSftp = setupJsch();
			channelSftp.connect();
			log.info("Connection established");
			log.info("Deleting  data...");
			channelSftp.rm(source);
			log.info("Data deleted successfully, terminating the connection");
			channelSftp.exit();
			return true;
		} catch (Exception e) {
			log.error("Some error Occured {}", e.getLocalizedMessage());
			e.printStackTrace();
			return false;
		}
	}

	private ChannelSftp setupJsch() throws JSchException {
		log.info("Establishing connection with the Storage Service S3");
		JSch jsch = new JSch();
		log.info("Settig details");
		jsch.setKnownHosts(propDetails.getKnownHostsFileLoc());
		jsch.addIdentity(propDetails.getPrivateKey());
		Session jschSession = jsch.getSession(propDetails.getUsername(), propDetails.getRemoteHost());
		jschSession.setPassword(propDetails.getPassword());
		jschSession.setHost(propDetails.getRemoteHost());
		jschSession.setPort(22);
		jschSession.setConfig("StrictHostKeyChecking", "no");
		log.info("Establishing connection");
		jschSession.connect();

		return (ChannelSftp) jschSession.openChannel("sftp");

	}

}
