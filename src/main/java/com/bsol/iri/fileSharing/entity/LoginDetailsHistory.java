package com.bsol.iri.fileSharing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "LOGIN_DETAILS_HISTORY")
public class LoginDetailsHistory {

	 @Id
	 @GeneratedValue(generator = "sequence-generator")
	    @GenericGenerator(
	      name = "sequence-generator",
	      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	      parameters = {
	        @Parameter(name = "sequence_name", value = "login_sequence"),
	        @Parameter(name = "initial_value", value = "1"),
	        @Parameter(name = "increment_size", value = "1")
	        }
	    )
	@Column(name = "ID")
	private Integer id;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD_HINT")
	private String pwdHint;

	@Column(name = "OLD_PASSWORD")
	private String oldPassword;

	@Column(name = "CREATED_AT")
	private Date createAt;

	@Column(name = "EXPIRED_ON")
	private Date expiredOn;

	@Column(name = "UNSUCCESS_LOGIN_ATTEMPTS")
	private int loginAttempt;

	@Column(name = "LAST_IP_USED")
	private String lastLoginIp;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "LAST_IP_UPDATED_AT")
	private Date lastIpUpdated;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwdHint() {
		return pwdHint;
	}

	public void setPwdHint(String pwdHint) {
		this.pwdHint = pwdHint;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getExpiredOn() {
		return expiredOn;
	}

	public void setExpiredOn(Date expiredOn) {
		this.expiredOn = expiredOn;
	}

	public int getLoginAttempt() {
		return loginAttempt;
	}

	public void setLoginAttempt(int loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastIpUpdated() {
		return lastIpUpdated;
	}

	public void setLastIpUpdated(Date lastIpUpdated) {
		this.lastIpUpdated = lastIpUpdated;
	}

	@Override
	public String toString() {
		return "LoginDetailsHistory [id=" + id + ", email=" + email + ", pwdHint=" + pwdHint + ", oldPassword="
				+ oldPassword + ", createAt=" + createAt + ", expiredOn=" + expiredOn + ", loginAttempt=" + loginAttempt
				+ ", lastLoginIp=" + lastLoginIp + ", status=" + status + ", lastIpUpdated=" + lastIpUpdated + "]";
	}

}
