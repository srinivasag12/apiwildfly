package com.bsol.iri.fileSharing.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author rupesh
 *	Host and port configuration of clamAv
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bsol.iri.fileSharing.clamAv.impl.ClamAV;

@Configuration
public class ClamAvConfig {
	
	private static final Logger log = LoggerFactory.getLogger(ClamAvConfig.class);

	@Value("${clamAv.host}")
	private String host;

	@Value("${clamAv.port}")
	private int port;

	@Bean
	public ClamAV clamAV() {
		log.info("configuring clam on host {} and port {} ",host, port);
		return new ClamAV(host, port);
	}
}
