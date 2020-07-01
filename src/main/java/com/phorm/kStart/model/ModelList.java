package com.phorm.kStart.model;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class ModelList {
	private ArrayList<@Valid Modal> models=new ArrayList<Modal>();

	public ArrayList<@Valid Modal> getModels() {
		return models;
	}

	public void setModels(ArrayList<@Valid Modal> models) {
		this.models = models;
	}

}
