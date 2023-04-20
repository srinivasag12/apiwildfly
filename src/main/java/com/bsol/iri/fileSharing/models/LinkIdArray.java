package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *	Model class
 */


import java.util.ArrayList;
import java.util.List;

public class LinkIdArray {

	private List<Integer> linkArray = new ArrayList<Integer>();

	public List<Integer> getLinkArray() {
		return linkArray;
	}

	public void setLinkArray(List<Integer> linkArray) {
		this.linkArray = linkArray;
	}

	@Override
	public String toString() {
		return "LinkIdArray [linkArray=" + linkArray + "]";
	}

}
