package com.bsol.iri.fileSharing.exception;

/**
 * 
 * @author rupesh
 *This class will tranform the Exception to http response.
 */
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<Object> dataNotFoundException(Exception ex, WebRequest request) throws IOException {
		log.error("Composing Error Message for DataNotFoundException - " + ex.getMessage());
		return builtResponse(ex, request);
	}

	@ExceptionHandler(CustomMessageException.class)
	public ResponseEntity<Object> customMsgException(Exception ex, WebRequest request) throws IOException {
		log.error("Composing Error Message for CustomMessageException - " + ex.getMessage());
		return builtResponse(ex, request);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		log.error("Composing Error Message for Exception - " + ex.getMessage());
		return builtResponse(ex, request);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> IntegrityExceptions(Exception ex, WebRequest request) {
		log.error("Composing Error Message for DataIntegrityViolationException - " + ex.getMessage());
		return builtResponse(ex, request);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> ConstraintExceptions(Exception ex, WebRequest request) {
		log.error("Composing Error Message for ConstraintViolationException - " + ex.getMessage());
		return builtResponse(ex, request);
	}

	private ResponseEntity<Object> builtResponse(Exception ex, WebRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("message", ex.getMessage().trim());
		map.put("timestamp", dateFormat.format(new Date()));
		map.put("status", 500);
		map.put("error", "Internal Server Error");
		map.put("path", ((ServletWebRequest) request).getRequest().getRequestURI().toString());
		return new ResponseEntity<Object>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
