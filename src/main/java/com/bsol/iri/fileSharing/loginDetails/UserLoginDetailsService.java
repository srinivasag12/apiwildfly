package com.bsol.iri.fileSharing.loginDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsol.iri.fileSharing.entity.LoginDetailsHistory;
import com.bsol.iri.fileSharing.entity.MaUser;
import com.bsol.iri.fileSharing.repos.EmailRepo;
import com.bsol.iri.fileSharing.repos.LoginDetailsHistoryRepo;
import com.bsol.iri.fileSharing.repos.UserRepo;
import com.bsol.iri.fileSharing.util.AppConstant;
import com.bsol.iri.fileSharing.util.DateTimeUtil;
import com.bsol.iri.fileSharing.util.EncryptionDecrytion;

@Service
public class UserLoginDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserLoginDetailsService.class);

	@Autowired
	private LoginDetailsHistoryRepo loginDetailsHostory;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailRepo emailRepo;

	public void loginSucceeded(String email, String ip) {
		MaUser user = userRepo.findByEmail(email);
		if (user != null) {
			log.debug("Login Success with email {} and ip {}", email, ip);
			LoginDetailsHistory ldHistory = loginDetailsHostory.findByEmailAndStatus(email, AppConstant.ACTIVE);
			if (ldHistory != null) {
				ldHistory.setLastLoginIp(ip);
				ldHistory.setLoginAttempt(0);
				ldHistory.setLastIpUpdated(DateTimeUtil.getTodaysDate());
				log.debug("Updating login details  {}", ldHistory);
				loginDetailsHostory.save(ldHistory);
			}
		}
	}

	public void loginFailed(String email, String ip) {
		log.info("Login failed with email {}", email);
		MaUser user = userRepo.findByEmail(email);
		if (user != null) {
			if (user.getStatus() == AppConstant.INACTIVE) {
				log.error("Inactive User having email {} tried to login with IP {}", email, ip);
			} else {
				LoginDetailsHistory ldHistory = loginDetailsHostory.findByEmailAndStatus(email, AppConstant.ACTIVE);
				if (ldHistory == null) {
					ldHistory = new LoginDetailsHistory();
					ldHistory.setLoginAttempt(0);
					ldHistory.setStatus(1);
					ldHistory.setId(1);
					ldHistory.setEmail(email);
					ldHistory.setId(null);
				}
				if (ldHistory.getLoginAttempt() == AppConstant.MAX_LOGIN_ATTEMPT) {
					log.debug("Locking the user with id {}", user.getUser_id());
					if (user != null) {
						user.setStatus(AppConstant.USER_LOCKED);
						userRepo.save(user);
					}
					emailRepo.SendEmail(email, AppConstant.LOCKED_ACC_SUB, AppConstant.LOCKED_ACC_BODY);
				}
				ldHistory.setLastLoginIp(ip);
				ldHistory.setLoginAttempt(ldHistory.getLoginAttempt() + 1);
				ldHistory.setLastIpUpdated(DateTimeUtil.getTodaysDate());
				log.debug("Setting Unsuccessful attempt to {} for the email {}", ldHistory.getLoginAttempt() + 1, email);
				loginDetailsHostory.save(ldHistory).getLoginAttempt();

				// Send Email
				if (ldHistory.getLoginAttempt() > 1)
					emailRepo.SendEmail(email, AppConstant.UNSUCCESS_LOGIN_MSG, String.format(
							AppConstant.UNSUCCESS_LOGIN_BODY, email, ip, DateTimeUtil.getTodaysDate().toString()));
			}

		} else {
			log.debug("Trying to loggin With unknown user email {}", email);
		}
	}

	public void createNewUserPassword(String email, String pwd, String hint) {
		log.info("New password update for th email {}.", email);
		LoginDetailsHistory newPassword = new LoginDetailsHistory();
		LoginDetailsHistory ldHistory = loginDetailsHostory.findByEmailAndStatus(email, AppConstant.ACTIVE);
		if (ldHistory != null) {
			ldHistory.setStatus(0);
			loginDetailsHostory.save(ldHistory);
		}
		newPassword.setCreateAt(DateTimeUtil.getTodaysDate());
		newPassword.setEmail(email);
		newPassword.setExpiredOn(DateTimeUtil.addDaysToCurrectDate(45));
		newPassword.setLastIpUpdated(DateTimeUtil.getTodaysDate());
		// newPassword.setLastLoginIp(getClientIP());
		newPassword.setLoginAttempt(0);
		newPassword.setOldPassword(EncryptionDecrytion.encrypt(pwd));
		newPassword.setPwdHint(EncryptionDecrytion.encrypt(hint));
		newPassword.setStatus(1);
		loginDetailsHostory.save(newPassword);
		log.info("Password update succesfully for the email {}", email);

	}

}
