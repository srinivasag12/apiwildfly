package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


public class Description {

	private Integer id;
	private String desc;

	public Integer getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Desription [id=" + id + ", desc=" + desc + "]";
	}

}
