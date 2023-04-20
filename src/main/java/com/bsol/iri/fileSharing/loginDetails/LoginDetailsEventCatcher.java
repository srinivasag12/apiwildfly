package com.bsol.iri.fileSharing.loginDetails;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;


@Component
public class LoginDetailsEventCatcher {

	private static final Logger log = LoggerFactory.getLogger(LoginDetailsEventCatcher.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	private UserLoginDetailsService userLoginDetailsService;

	@EventListener
	public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {
		log.warn("Unsuccessful login attempts , details  - {}", event.toString());
		userLoginDetailsService.loginFailed((String) event.getAuthentication().getPrincipal(), getClientIP());
		
	}

	@EventListener
	public void authenticationSuccess(AuthenticationSuccessEvent event) {
		log.warn("Login Success event , details  - {}", event.toString());
		userLoginDetailsService.loginSucceeded(event.getAuthentication().getName().toString(), getClientIP());

	}

	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}
}
