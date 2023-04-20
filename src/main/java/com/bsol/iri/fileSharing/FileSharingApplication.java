package com.bsol.iri.fileSharing;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author rupesh This is the main method class, This class is the starting
 *         point to start the application. This class also contain the root path
 *         message. It also create the temp directory
 */

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
public class FileSharingApplication extends SpringBootServletInitializer {
	
	
	private static final Logger log = LoggerFactory.getLogger(FileSharingApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(FileSharingApplication.class, args);
		log.info("Application Started successfully");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FileSharingApplication.class);
	}

	@RestController
	class HomeController {
		@GetMapping("/")
		public String showHome() {
			return "Welcome To IRI File Sharing application. Application is Up and Running.";
		}
	}

	static {
		log.info("checking the temp directory");
		File tempDirectory = new File("/temp/");
		if (!tempDirectory.exists()) {
			log.info("Directory not available creating temp directory");
			tempDirectory.mkdirs();
		}
	}

}
