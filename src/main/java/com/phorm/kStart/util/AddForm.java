package com.phorm.kStart.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.context.annotation.Scope;

import com.phorm.kStart.model.Feelds;
import com.phorm.kStart.model.Modal;

@Scope("prototype")
public class AddForm {

	public void createFile(Modal modelObj, String folderPath, String appName, ArrayList<String>  modelList, Map<String, Modal> mpModal, String modelFolder) {
		PrintWriter outputStream = null;

		ArrayList<Feelds> tmpArr;	
		ArrayList<Feelds> fldArr;
		tmpArr=modelObj.getFields();
		String fldName=null;
		String mdlName=modelObj.getModelName();
		Modal tempModal=null;
		String fldType="";
		String inerFldName="";
		String fldRelType="";
		boolean manyPresent=false;
		String manyPKFld="";
		try {
			outputStream = new PrintWriter(new FileWriter(folderPath+"/"+mdlName+"/addForm.jsp"));
			outputStream.println("<%@ page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\"");
			outputStream.println("\tpageEncoding=\"ISO-8859-1\"%>");
			outputStream.println("<%@ taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>");
			outputStream.println("<%@ taglib prefix=\"spring\" uri=\"http://www.springframework.org/tags\" %>");
			outputStream.println("<html>");
			outputStream.println("\t<head>");
			outputStream.println("\t</head>");
			outputStream.println("\t<body>");
			outputStream.println("\t\t<div>");
			outputStream.println("\t\t\t<form:form action = \"/"+appName+"/"+mdlName+"/submit\" method = \"POST\" modelAttribute=\"atr"+mdlName+"\">");
			outputStream.println("\t\t\t\t<br />");
			for(Feelds f:tmpArr) {
				fldName=f.getFieldName();
				fldType=f.getDataType();
				if(!f.getIsPrimary()) {
					if(modelList.contains(fldType)) {
						tempModal=mpModal.get(fldType);
						fldArr=tempModal.getFields();
						fldRelType=f.getRelationType();
						if(fldRelType!=null && fldRelType!="" && (fldRelType.equals("onetomany") || fldRelType.equals("manytomany"))) {
							manyPresent=true;
						}
						outputStream.println("\t\t\t\t--Start "+fldType+" object--<br/>");
						for(Feelds fld:fldArr) {
							inerFldName=fld.getFieldName();
							if(!(fld.getRelationType().equals("manytoone") || fld.getRelationType().equals("manytomany"))) {
								if(!fld.getIsPrimary()) {								
									if(manyPresent) {
										outputStream.println("\t\t\t\t<form:errors path=\""+fldName+"[0]."+inerFldName+"\"/><br/>");
										outputStream.println("\t\t\t\t"+inerFldName+": <form:input path= \""+fldName+"[0]."+inerFldName+"\"/>");
										outputStream.println("\t\t\t\t<br/>");
									}else {
										outputStream.println("\t\t\t\t<form:errors path=\""+fldName+"."+inerFldName+"\"/><br/>");
										outputStream.println("\t\t\t\t"+inerFldName+": <form:input path= \""+fldName+"."+inerFldName+"\"/>");
										outputStream.println("\t\t\t\t<br/>");
									}
								}else {
									File flModalFile= new File(modelFolder+"/"+fldType+"PK.java");
									if(flModalFile.exists()) {			
										if(manyPresent) {
											outputStream.println("\t\t\t\t<form:errors path=\""+fldName+"[0]."+inerFldName+"\"/><br/>");
											outputStream.println("\t\t\t\t"+inerFldName+": <form:input path= \""+fldName+"[0]."+inerFldName+"\"/>");
											outputStream.println("\t\t\t\t<br/>");
										}else {
											outputStream.println("\t\t\t\t<form:errors path=\""+fldName+"."+inerFldName+"\"/><br/>");
											outputStream.println("\t\t\t\t"+inerFldName+": <form:input path= \""+fldName+"."+inerFldName+"\"/>");
											outputStream.println("\t\t\t\t<br/>");
										}
									}else if(fldRelType.equals("manytomany") || fldRelType.equals("onetomany")) {
										outputStream.println("\t\t\t\t<form:errors path=\""+fldName+"[0]."+inerFldName+"\"/><br/>");
										outputStream.println("\t\t\t\t"+inerFldName+": <form:input path= \""+fldName+"[0]."+inerFldName+"\"/>");
										outputStream.println("\t\t\t\t<br/>");
									}
								}
							}
						}
						outputStream.println("\t\t\t\t--End "+fldType+" object--<br/>");
					}else {
						outputStream.println("\t\t\t\t<form:errors path=\""+fldName+"\"/><br/>");
						outputStream.println("\t\t\t\t"+fldName+": <form:input path= \""+fldName+"\"/>");
						outputStream.println("\t\t\t\t<br/>");
					}
				}else {
					manyPKFld=manyPKFld+"\t\t\t\t<form:errors path=\""+fldName+"\"/><br/>\n";
					manyPKFld=manyPKFld+"\t\t\t\t"+fldName+": <form:input path= \""+fldName+"\"/>\n";
					manyPKFld=manyPKFld+"\t\t\t\t<br/>\n";					
				}
			}
			File modalFile= new File(modelFolder+"/"+mdlName+"PK.java");
			if(modalFile.exists() || fldRelType.equals("manytomany") || fldRelType.equals("onetomany")) {						
				outputStream.println(manyPKFld);				
			}
			outputStream.println("\t\t\t\t<input type = \"submit\" value = \"Submit\" />");
			outputStream.println("\t\t\t</form:form>");
			outputStream.println("\t\t</div>");
			outputStream.println("\t</body>");
			outputStream.println("</html>");
			outputStream.close();
			if(manyPresent) {
				AddExtraForm addExtraJsp=new AddExtraForm();
				addExtraJsp.createFile(modelObj, folderPath, appName, modelList, mpModal, modelFolder);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(outputStream!=null) {
				outputStream.close();

			}

		}

	}

}
