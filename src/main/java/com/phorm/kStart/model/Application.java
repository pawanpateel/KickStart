package com.phorm.kStart.model;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class Application {
	
	@NotNull(message="Application name can not be null.")
	@NotEmpty(message="Application name can not be empty.")
	@Size(max=30, message="Application name can contain max 30 characters.")
	@Pattern(regexp="^[A-Za-z_]+$", message="Application name can contain only alphabets and underscore.")
	private String appName;
	
	@NotNull(message="User name can not be null.")
	@NotEmpty(message="User name can not be empty.")
	@Size(max=30, message="User name can contain max 30 characters.")
	@Pattern(regexp="^[A-Za-z_]+$", message="User name can contain only alphabets and underscore.")
	private String userName;
	
	@NotNull(message="Place name can not be null.")
	@NotEmpty(message="Place name can not be empty.")
	@Size(max=30, message="Place name can contain max 30 characters.")
	@Pattern(regexp="^[A-Za-z_]+$", message="Place name can contain only alphabets and underscore.")
	private String placeName;
	
	private ArrayList<@Valid Modal> models=new ArrayList<Modal>();
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public ArrayList<@Valid Modal> getModels() {
		return models;
	}
	public void setModels(ArrayList<@Valid Modal> models) {
		this.models = models;
	}

}
