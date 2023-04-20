package com.bsol.iri.fileSharing.config;



import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bsol.iri.fileSharing.emailService.EmailProviderConfiguration;


@Component
public class CORSFilter implements Filter {
	
	
	private static final Logger log = LoggerFactory.getLogger(CORSFilter.class);


@Autowired
EmailProviderConfiguration conf;


@Override
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    log.info(" Adding Headers to the Response");
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    response.setHeader("Access-Control-Allow-Origin", conf.getUiHost().split("/full/login/")[0]);
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
    response.setHeader("Access-Control-Max-Age", "31536000");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

    
    if(!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
        chain.doFilter(req, res);
    }
}

  @Override
  public void init(FilterConfig filterConfig) {
	  log.info("Filter Initialize");
  }

  @Override
  public void destroy() {
	  log.info("Filter Destroyed");
  }

}