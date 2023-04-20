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
@Table(name = "ma_role")
public class MaRoles {

	@Id
	@TableGenerator(name = "idGenerator", table = "file_upload_seq_store", pkColumnName = "id_Seq", pkColumnValue = "pk_key_id", valueColumnName = "Seq_Value", initialValue = 999, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGenerator")
	@Column(name = "ROLE_ID")
	private Integer roleId;

	@Column(name = "ROLE_DESC")
	private String roleDesc;

	@Column(name = "CREATED_ON")
	private Date created_on;

	@Column(name = "CREATED_BY")
	private Date created_by;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public Date getCreated_by() {
		return created_by;
	}

	public void setCreated_by(Date created_by) {
		this.created_by = created_by;
	}

	@Override
	public String toString() {
		return "MaRoles [roleId=" + roleId + ", roleDesc=" + roleDesc + ", created_on=" + created_on + ", created_by="
				+ created_by + "]";
	}

}
