//package com.bsol.iri.fileSharing.emailService;
/**
 * 
 * @author rupesh
 *
 */
//import java.util.Properties;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//
///**
// * 
// * @author rupesh
// *
// */
//@Configuration
//public class EmailConfiguration {
//
//	@Autowired
//	private EmailProviderConfiguration providerConfiguration;
//
//	/**
//	 * Basic config setup for Email service
//	 * 
//	 * @return
//	 */
//	
//	private static Logger log = LoggerFactory.getLogger(EmailConfiguration.class);
//	
//	
//	@Bean
//	public JavaMailSender mailSender() {
//		
//		log.info(" Setting Email Configuration");
//		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//		javaMailSender.setHost(providerConfiguration.getHost());
//		javaMailSender.setPort(providerConfiguration.getPort());
//
//		javaMailSender.setUsername(providerConfiguration.getUsername());
//		javaMailSender.setPassword(providerConfiguration.getPassword());
//
//		Properties properties = javaMailSender.getJavaMailProperties();
////		properties.put("mail.transport.protocol", "smtp");
//		 properties.put("mail.smtp.host", providerConfiguration.getHost());
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.starttls.enable", "true");
//		properties.put("mail.smtp.ssl.trust", providerConfiguration.getHost());
//		properties.put("mail.debug", providerConfiguration.getDebug().toString());
//		log.info("Email Configuration completed");
//		
//		return javaMailSender;
//	}
//
//}
