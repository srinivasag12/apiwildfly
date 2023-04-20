package com.bsol.iri.fileSharing.exception;


/**
 * @author rupesh
 * 	 This is the custom class to customize the Error response.
 * 
 */
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {

		@SuppressWarnings("deprecation")
		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

		Object timestamp = errorAttributes.get("timestamp");
		if (timestamp == null) {
			errorAttributes.put("timestamp", dateFormat.format(new Date()));
		} else {
			errorAttributes.put("timestamp", dateFormat.format((Date) timestamp));
		}

		/**
		 * Remove this line if trace is required, when we give report Error option to
		 * user.
		 */
		errorAttributes.remove("trace");

		return errorAttributes;

	}

}
