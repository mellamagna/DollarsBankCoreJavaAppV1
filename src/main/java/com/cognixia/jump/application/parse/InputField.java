package com.cognixia.jump.application.parse;

public class InputField {

	String fieldName;
	long lowerBound;
	long upperBound;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public long getLowerBound() {
		return lowerBound;
	}
	public void setLowerBound(long lowerBound) {
		this.lowerBound = lowerBound;
	}
	public long getUpperBound() {
		return upperBound;
	}
	public void setUpperBound(long upperBound) {
		this.upperBound = upperBound;
	}
	public InputField(String fieldName, long lowerBound, long upperBound) {
		super();
		this.fieldName = fieldName;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	public InputField(String fieldName) {
		super();
		this.fieldName = fieldName;
		this.lowerBound = 0;
		this.upperBound = Long.MAX_VALUE;
	}

}
