package com.bsol.iri.fileSharing.util;

/**
 * 
 * @author rupesh
 *	All constant which are used in this application are here
 */
public class AppConstant {

	/**
	 * Status
	 */

	public static final int TRUE = 1;
	public static final int FALSE = 0;

	/**
	 * Active Status
	 */

	public static final int ACTIVE = 1;
	public static final int INACTIVE = 0;

	/**
	 * Link Status
	 */
	public static final int ACTIVE_LINK = 1;
	public static final int CANCELED_LINK = 2;
	public static final int EXTENDED_LINK = 3;
	public static final int COMPLETED_LINK = 4;
	public static final int EXPIRED_LINK = 5;

	/**
	 * Logged in status Status
	 */
	public static final int NOT_YET_LOGGED_IN = 1;
	public static final int LOGGED_IN = 2;
	public static final int COMPLETED_LOGGED_IN = 3;

	/**
	 *  Link Type
	 */

	public static final int BY_OWNER = 1; // upload Link will share to third party user by Owner
	public static final int BY_THIRD_PARTY = 2; // third party user will request owner to uploads a file
	public static final int THIRD_PARTY_DOWNLOAD_ONLY = 3; // Owner will share a Direct download link with third party
															// user
	public static final int BOTH_PARTY = 4; // any one Vessel Master or IRI Inspector can upload or download file

	/**
	 * Time Duration constant
	 */

	public static final int ARCHIVE_DELETED_IN_DAYS = 15;
	public static final int USER_SEEESION_LOGGED_OUT_IN_SEC = 28900;
	public static final int OTP_VALIDITY = 900;
	public static final int DELETE_ARCHIVE_AFTER_DAYS = 15;
	public static final int FIRST_NOTIFICATION = 15;
	public static final int SECOND_NOTIFICATION = 30;
	public static final int THIRD_NOTIFICATION = 45;
	
	/**
	 *  USER Roles
	 */
	public static final int ADMIN_ROLE = 1;
	public static final int INSPECTOR_OR_AUDITOR_ROLE = 2;
	
	
	/**
	 * Email  Messages
	 */

	public static final String RESET_PASSWORD_MESSAGE = "Your One time password (OTP) to Reset Password is %s. This OTP will expired in 15 minutes. Please Enter this OTP to set new password.";
	public static final String RESET_PASSWORD_SUBJECT = "RMI FTS - Password Reset";
	
	public static final String UNSUCCESS_LOGIN_MSG = "RMI FTS - Unsuccessful login attempt";
	public static final String UNSUCCESS_LOGIN_BODY = "Someone tried to login to FTS Application with your email : %s having  IP Address:  %s at %s. If it was you, then ignore this mail or else change your password.";
	
	public static final String LOCKED_ACC_SUB = "RMI FTS - Your Account is Locked";
	public static final String LOCKED_ACC_BODY = "You have reached maximum number of Unsuccessful login attempt, For Security Reason your account is locked. \n Please contact the Admin to reset your password or Click On Forget Password to Set a new Password";
	
	public static final String PASSWORD_RESET_SUB = "RMI FTS - Account Password RESET";
	public static final String PASSWORD_RESET_BODY = "Your FTS account password has been changed to default password, please login with default password and set a new password and set a new password.";
	
	public static final String PASSWORD_CHANGED = "RMI FTS - Password Changed";
	public static final String PASSWORD_CHANGED_BODY = "Your FTS account password has been changed successfully.";
	
	public static final String DOWNLOAD_URL = "RMI Maritime Administrator File Download URL";
	public static final String DOWNLOAD_URL_BODY = "Please click the link %s to Download your files for the vessel %s and completion of the  %s. The above URL for vessel %s is active until %s.";
	
	public static final String OTP_SUBJECT  = "RMI Maritime Administrator File Upload One Time Passcode";
	public static final String OTP_BODY= "Your One Time Passcode (OTP) is %s. This OTP will expire after 15 minutes and is for vessel %s and completion of the %s . Once you have successfully logged into the application with your OTP you will not need it again unless you log out and request another OTP";

	public static final String UPLOAD_URL_SUBJECT ="RMI Maritime Administrator File Upload URL";
	public static final String UPLOAD_URL_BODY = "Please click the link %s to upload your files for the vessel %s and completion of the %s . Please note that once you click the Submit button the link will expire so please wait to click Submit until all requested documents and photos are uploaded. The above URL for vessel %s is active until %s";
	
	public static final String ACCOUNT_UNBLOCK_SUBJECT = "RMI FTS - Account Unblocked";
	public static final String ACCOUNT_UNBLOCK_BODY = "Your FTS account has been Unblocked, please login with your password";
		
	/**
	 * Login details 
	 */
	
	public static final Integer MAX_LOGIN_ATTEMPT = 2;
	public static final Integer USER_LOCKED = 2;
	public static final String UNSUCCESSFULL_LOGIN_COUNT = "You have %s attempt left, after that your account will be locked.";
	public static final String USER_ACCOUNT_LOCKED ="Your account is LOCKED, please contact admin";
	public static final String NO_USER_FOUND= "User not found";
	public static final String INACTIVE_USER= "Your account is inactive, please contact admin";
 	
	/***
	 *  Default password
	 */
	
	public static final String DEFAULT_PASSWORD = "Welcome@123";
	public static final int LOGIN_INITIAL_COUNT = 0;
	
	
	/***
	 * Helper app url
	 */
	
	public static final String HELPER_APP_URL = "http://3.7.127.112:8080/ZipUnzipHelper/upload";
}
