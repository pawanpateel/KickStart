package com.phorm.kStart.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class Feelds {
	
	@NotNull(message="Field name can not be null.")
	@NotEmpty(message="Field name can not be empty.")
	@Size(max=30, message="Field name can contain max 30 characters.")
	@Pattern(regexp="^[A-Za-z0-9_]+$", message="Field name can contain only alphabets, numbers and underscore.")
	private String fieldName;
	
	@NotNull(message="Data type can not be null.")
	@NotEmpty(message="Data type can not be empty.")
	@Size(max=30, message="Data type can contain max 30 characters.")
	@Pattern(regexp="^[A-Za-z_]+$", message="Data type can contain only alphabets and underscore.")
	private String dataType;
	
	//@Pattern(regexp="^[true|false]$", message="ID can contain only boolean.")
	private boolean isPrimary=false;
	
	//@Pattern(regexp="^[true|false]$", message="Nullable can contain only boolean.")
	private boolean isNullable=false;
	
	//@Pattern(regexp="^[true|false]$", message="CanBeEmpty can contain only boolean.")
	private boolean canBeEmpty=false;
	
	//@NotNull(message="Minimum value can not be null.")
	//@Size(min=0)
	//@NotEmpty(message="Minimum value can not be empty.")
	@Pattern(regexp="^[0-9.]*$", message="Minimum value can contain only numbers and decimal point.")
	private String minValue="";
	
	//@NotNull(message="Maximum value can not be null.")
	//@Size(min=0)
	//@NotEmpty(message="Maximum value can not be empty.")
	@Pattern(regexp="^[0-9.]*$", message="Maximum value can contain only numbers and decimal point.")
	private String maxValue="";	
	private String upperName;
	
	//@Size(min=0)
	@Pattern(regexp="^onetoone$|^onetomany$|^manytoone$|^manytomany$", message="Relation type can contain only OnetoOne, OnetoMany, ManytoOne and ManytoMany.")
	private String relationType;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
		setUpperName(firstCharUpper(fieldName));
	}
	public String getUpperName() {
		return upperName;
	}
	public void setUpperName(String upperName) {
		this.upperName = upperName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public boolean getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	public boolean getIsNullable() {
		return isNullable;
	}
	public void setIsNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}
	public boolean getCanBeEmpty() {
		return canBeEmpty;
	}
	public void setCanBeEmpty(boolean canBeEmpty) {
		this.canBeEmpty = canBeEmpty;
	}
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	private String firstCharUpper(String inString) {
		if(inString.isEmpty()) {
			return "";
		}else {
		char[] nameArray=inString.toCharArray();
		nameArray[0]=Character.toUpperCase(nameArray[0]);
		return new String(nameArray);
		}
	}

}
