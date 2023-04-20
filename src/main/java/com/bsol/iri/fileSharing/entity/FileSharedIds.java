package com.bsol.iri.fileSharing.entity;

/**
 * 
 * @author rupesh
 *
 */
import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class FileSharedIds implements Serializable {

	 
	private static final long serialVersionUID = 7851063512625997575L;
	
	private Integer fileId;
	private Integer sharedTo;
	private Integer sharedBy;

	public FileSharedIds() {
		// TODO Auto-generated constructor stub
	}

	public FileSharedIds(Integer fileId, Integer sharedTo, Integer sharedBy) {
		this.fileId = fileId;
		this.sharedTo = sharedTo;
		this.sharedBy = sharedBy;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getSharedTo() {
		return sharedTo;
	}

	public void setSharedTo(Integer sharedTo) {
		this.sharedTo = sharedTo;
	}

	public Integer getSharedBy() {
		return sharedBy;
	}

	public void setSharedBy(Integer sharedBy) {
		this.sharedBy = sharedBy;
	}

	@Override
	public int hashCode() {
		final int prime = 71;
		int result = 1;
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		result = prime * result + ((sharedTo == null) ? 0 : sharedTo.hashCode());
		result = prime * result + ((sharedBy == null) ? 0 : sharedBy.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		FileSharedIds other = (FileSharedIds) obj;

		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;

		if (sharedTo == null) {
			if (other.sharedTo != null)
				return false;
		} else if (!sharedTo.equals(sharedTo))
			return false;

		if (sharedBy == null) {
			if (other.sharedBy != null)
				return false;
		} else if (!sharedBy.equals(sharedBy))
			return false;
		
		return true;
	}

}
