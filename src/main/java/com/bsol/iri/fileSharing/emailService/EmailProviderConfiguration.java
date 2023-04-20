package com.bsol.iri.fileSharing.emailService;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * 
 * @author rupesh
 *
 */

@Description("This class will used to provide basic configuration for email service , anf these properties are comming form Properties files")
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailProviderConfiguration {

	private String uiHost;

	public String getUiHost() {
		return uiHost;
	}

	public void setUiHost(String uiHost) {
		this.uiHost = uiHost;
	}

}
