package com.bsol.iri.fileSharing.models;

/**
 * 
 * @author rupesh
 *
 *         Here data type is taken as Object because sometimes key/value may be
 *         String/integer
 */
public class GroupingDataForChart {

	private Object Key;
	private Object value;

	public Object getKey() {
		return Key;
	}

	public void setKey(Object key) {
		Key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "GroupingDataForChart [Key=" + Key + ", value=" + value + "]";
	}

}
