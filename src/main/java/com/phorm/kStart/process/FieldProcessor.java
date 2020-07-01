package com.phorm.kStart.process;


import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Scope;

import com.phorm.kStart.model.Feelds;
import com.phorm.kStart.model.Modal;


@Scope("prototype")
public class FieldProcessor {

	public ArrayList<Modal> errorProcess(ArrayList<Modal> inputList) {
		ArrayList<Feelds> fldList;
		ArrayList<Feelds> newList;
		for(Modal m:inputList) {
			fldList=m.getFields();
			newList=new ArrayList<Feelds>();
			for(Feelds f:fldList) {
				if(!f.getFieldName().equals("tnkvaiaigkddiahul")) {
					newList.add(f);
				}
			}
			m.setFields(newList);					
		}
		return inputList;
	}

	public ArrayList<Modal> finalProcess(ArrayList<Modal> inputList) {
		ArrayList<Feelds> fldList;
		ArrayList<Feelds> newList;
		String datType="";
		boolean pkFound=false;
		ArrayList<String> listType=new ArrayList<String>(Arrays.asList(
				"int", "long", "float", "double", "boolean", "String", "BigDecimal", "BigInteger", "byte", "short", "char"				
				));
		for(Modal m:inputList) {
			fldList=m.getFields();
			newList=new ArrayList<Feelds>();
			pkFound=false;
			for(Feelds f:fldList) {
				datType=f.getDataType();
				if(f.getIsPrimary()) {
					pkFound=true;
				}
				if(!f.getFieldName().equals("tnkvaiaigkddiahul")) {
					if(listType.contains(datType)) {
						f.setRelationType("");
					}else if(datType.equals("Date")) {
						f.setIsPrimary(false);
						f.setIsNullable(false);
						f.setCanBeEmpty(false);
						f.setMinValue("");
						f.setMaxValue("");
						f.setRelationType("");
					}else {
						f.setIsPrimary(false);
						f.setIsNullable(false);
						f.setCanBeEmpty(false);
						f.setMinValue("");
						f.setMaxValue("");
					}
					newList.add(f);
				}
			}
			if(!pkFound) {
				Feelds pkFeeld=new Feelds();
				pkFeeld.setDataType("long");
				pkFeeld.setFieldName(m.getLowerName()+"Id");
				pkFeeld.setRelationType("");
				pkFeeld.setIsPrimary(true);
				pkFeeld.setIsNullable(false);
				pkFeeld.setCanBeEmpty(false);
				pkFeeld.setMinValue("");
				pkFeeld.setMaxValue("");
				newList.add(pkFeeld);
			}
			m.setFields(newList);					
		}
		return inputList;
	}

}
