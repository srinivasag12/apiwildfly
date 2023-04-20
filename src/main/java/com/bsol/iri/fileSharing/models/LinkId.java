package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class LinkId {
	private Integer link;
	private Integer idCode;

	public Integer getLink() {
		return link;
	}

	public void setLink(Integer link) {
		this.link = link;
	}

	public Integer getIdCode() {
		return idCode;
	}

	public void setIdCode(Integer idCode) {
		this.idCode = idCode;
	}

	@Override
	public String toString() {
		return "LinkId [link=" + link + ", IdCode=" + idCode + "]";
	}

}
