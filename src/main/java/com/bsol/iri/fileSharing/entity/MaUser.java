package com.bsol.iri.fileSharing.entity;

/**
 * 
 * @author rupesh
 *
 */
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "ma_user")
public class MaUser {

	@Id
	@TableGenerator(name = "idGenerator", table = "file_upload_seq_store", pkColumnName = "id_Seq", pkColumnValue = "pk_key_id", valueColumnName = "Seq_Value", initialValue = 999, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGenerator")
	@Column(name = "user_id")
	private Integer user_id;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String pwd;

	@Column(name = "status")
	private int status;

	@Column(name = "LAST_LOGIN")
	private Date lastLogIn;

	@Column(name = "LAST_LOGOUT")
	private Date lastLogOut;

	@Column(name = "ROLE_ID")
	private Integer role;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastLogIn() {
		return lastLogIn;
	}

	public void setLastLogIn(Date lastLogIn) {
		this.lastLogIn = lastLogIn;
	}

	public Date getLastLogOut() {
		return lastLogOut;
	}

	public void setLastLogOut(Date lastLogOut) {
		this.lastLogOut = lastLogOut;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "MaUser [user_id=" + user_id + ", email=" + email + ", pwd=" + pwd + ", status=" + status
				+ ", lastLogIn=" + lastLogIn + ", lastLogOut=" + lastLogOut + ", role=" + role + "]";
	}

}
