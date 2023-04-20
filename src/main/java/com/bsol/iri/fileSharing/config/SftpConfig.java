package com.bsol.iri.fileSharing.config;

/**
 * 
 * @author rupesh
 *	Basic configuration OF SFTP
 */


import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

import com.bsol.iri.fileSharing.models.LocationDetails;
import com.bsol.iri.fileSharing.models.SftpPropDetails;
import com.jcraft.jsch.ChannelSftp.LsEntry;

@Configuration
public class SftpConfig {

	@Value("${sftp.host}")
	private String sftpHost;

	@Value("${sftp.port:22}")
	private int sftpPort;

	@Value("${sftp.user}")
	private String sftpUser;

	@Value("${sftp.privateKey:#{null}}")
	private Resource sftpPrivateKey;

	@Value("${sftp.privateKeyPassphrase:}")
	private String sftpPrivateKeyPassphrase;

	@Value("${sftp.password:#{null}}")
	private String sftpPasword;

	@Value("${sftp.remote.shared}")
	private String sftpRemoteShared;

	@Value("${sftp.remote.personal}")
	private String sftpRemotePersonal;

	@Value("${sftp.remote.directory}")
	private String sftpRemoteDirectory;

	@Value("${sftp.knownHostsFileLoc}")
	private String knownHostsFileLoc;

	@Value("${sftp.privateKey}")
	private String privateKey;

	@Bean
	public SessionFactory<LsEntry> sftpSessionFactory() {

		DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
		factory.setHost(sftpHost);
		factory.setPort(sftpPort);
		factory.setUser(sftpUser);
//		if (sftpPrivateKey != null) {
//			factory.setPrivateKey(sftpPrivateKey);
//			factory.setPrivateKeyPassphrase(sftpPrivateKeyPassphrase);
//		} else {
//			factory.setPassword(sftpPasword);
//		}
		factory.setPrivateKeyPassphrase(sftpPrivateKeyPassphrase);
		factory.setAllowUnknownKeys(true);
		return new CachingSessionFactory<LsEntry>(factory);
	}

	@Bean
	@ServiceActivator(inputChannel = "toSftpChannel")
	public MessageHandler handler() {
		SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
		handler.setRemoteDirectoryExpression(new LiteralExpression(sftpRemoteDirectory + "iri-file-upload"));
		handler.setFileNameGenerator(new FileNameGenerator() {

			@Override
			public String generateFileName(Message<?> message) {
				if (message.getPayload() instanceof File) {
					return ((File) message.getPayload()).getName();
				} else {
					throw new IllegalArgumentException("File expected as payload.");
				}
			}

		});
		return handler;
	}

	@MessagingGateway
	public interface UploadGateway {

		@Gateway(requestChannel = "toSftpChannel")
		void upload(File file);

	}

//	@Bean
//	SftpPropDetails setSftpDetails() {
//		return new SftpPropDetails(sftpHost, sftpUser, sftpPasword, knownHostsFileLoc, privateKey);
//	}
	@Bean
	SftpPropDetails setSftpDetails() {
		return new SftpPropDetails(sftpHost, sftpUser, sftpPrivateKeyPassphrase, knownHostsFileLoc, privateKey);
	}

	@Bean
	LocationDetails getLocation() {
		return new LocationDetails(sftpRemoteDirectory, sftpRemotePersonal, sftpRemoteShared);
	}

}
