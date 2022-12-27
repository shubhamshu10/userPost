package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    long fieldValue;
    String value;
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s :%s",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	public ResourceNotFoundException(String resourceName, String fieldName, String value) {
		super(String.format("%s not found with %s :%s",resourceName,fieldName,value));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.value = value;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public long getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(long fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
