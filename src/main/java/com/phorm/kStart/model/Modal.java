package com.phorm.kStart.model;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class Modal {
	
	@NotNull(message="Model name can not be null.")
	@NotEmpty(message="Model name can not be empty.")
	@Size(max=30, message="Model name can contain max 30 characters.")
	@Pattern(regexp="^[A-Za-z_]+$", message="Model name can contain only alphabets and underscore.")
	private String modelName;
	
	private String lowerName;
	
	private ArrayList<@Valid Feelds> fields=new ArrayList<Feelds>();
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
		setLowerName(firstCharLower(modelName));
	}
	public ArrayList<Feelds> getFields() {
		return fields;
	}
	public void setFields(ArrayList<Feelds> fields) {
		this.fields = fields;
	}
	public String getLowerName() {
		return lowerName;
	}
	public void setLowerName(String lowerName) {
		this.lowerName = lowerName;
	}
	private String firstCharLower(String inString) {
		if(inString.isEmpty()) {
			return "";
		}else {
		char[] nameArray=inString.toCharArray();
		nameArray[0]=Character.toLowerCase(nameArray[0]);
		return new String(nameArray);
		}
	}
}
