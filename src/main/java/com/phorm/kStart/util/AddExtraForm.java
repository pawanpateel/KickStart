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
public class AddExtraForm {

	public void createFile(Modal modelObj, String folderPath, String appName, ArrayList<String>  modelList, Map<String, Modal> mpModal, String modelFolder) {
		PrintWriter outputStream = null;

		ArrayList<Feelds> tmpArr;		
		tmpArr=modelObj.getFields();
		String fldName=null;
		String mdlName=modelObj.getModelName();
		Modal tempModal=null;
		String fldType="";
		String inerFldName="";
		ArrayList<Feelds> fldArr;
		String fldRelType="";
		boolean manyPresent=false;
		String actionName="submit";
		String indaxVal="0";
		try {
			StringBuilder sb=new StringBuilder();
			for(Feelds f:tmpArr) {
				fldName=f.getFieldName();
				fldType=f.getDataType();
				if(!f.getIsPrimary()) {
					if(modelList.contains(fldType)) {
						tempModal=mpModal.get(fldType);
						fldArr=tempModal.getFields();
						fldRelType=f.getRelationType();
						if(fldRelType!=null && fldRelType!="" && (fldRelType.equals("manytomany") || fldRelType.equals("onetomany"))) {
							manyPresent=true;
							actionName="update";
							indaxVal="${tsl}";
							sb.append("<cora:forEach  begin=\"0\" end=\"${atr"+mdlName+"."+fldName+".size()}\" var=\"tsl\">\n");
						}
						sb.append("\t\t\t\t--Start "+fldType+" object--<br/>\n");
						for(Feelds fld:fldArr) {
							inerFldName=fld.getFieldName();
							if(!(fld.getRelationType().equals("manytoone") || fld.getRelationType().equals("manytomany"))) {
								if(!fld.getIsPrimary()) {								
									sb.append("\t\t\t\t<form:errors path=\""+fldName+"["+indaxVal+"]."+inerFldName+"\"/><br/>\n");
									sb.append("\t\t\t\t"+inerFldName+": <form:input path= \""+fldName+"["+indaxVal+"]."+inerFldName+"\"/>\n");
									sb.append("\t\t\t\t<br/>\n");
								}else {								
									File flModalFile= new File(modelFolder+"/"+fldType+"PK.java");
									if(flModalFile.exists() || manyPresent) {			
										sb.append("\t\t\t\t<form:errors path=\""+fldName+"["+indaxVal+"]."+inerFldName+"\"/><br/>\n");
										sb.append("\t\t\t\t"+inerFldName+": <form:input path= \""+fldName+"["+indaxVal+"]."+inerFldName+"\"/>\n");
										sb.append("\t\t\t\t<br/>\n");
									}
								}
							}
						}
						sb.append("\t\t\t\t--End "+fldType+" object--<br/>\n\n");
						if(manyPresent) {
							sb.append("</cora:forEach>");
						}
					}else {
						sb.append("\t\t\t\t"+fldName+": <form:hidden path= \""+fldName+"\"/>\n");
						sb.append("\t\t\t\t<br/>\n");
					}
				}else {
					sb.append("\t\t\t\t<form:hidden path= \""+fldName+"\"/>\n");
					sb.append("\t\t\t\t<br/>\n");
				}
			}
			
			outputStream = new PrintWriter(new FileWriter(folderPath+"/"+mdlName+"/addExtraForm.jsp"));
			outputStream.println("<%@ page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\"");
			outputStream.println("\tpageEncoding=\"ISO-8859-1\"%>");
			outputStream.println("<%@ taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>");
			outputStream.println("<%@ taglib prefix=\"spring\" uri=\"http://www.springframework.org/tags\" %>");
			outputStream.println("<%@ taglib prefix=\"cora\" uri=\"http://java.sun.com/jsp/jstl/core\" %>");
			if(manyPresent) {
				outputStream.println("<%@ taglib prefix=\"cora\" uri=\"http://java.sun.com/jsp/jstl/core\" %>");
			}
			outputStream.println("<html>");
			outputStream.println("\t<head>");
			outputStream.println("\t</head>");
			outputStream.println("\t<body>");
			outputStream.println("\t\t<div>");
			outputStream.println("\t\t\t<form:form action = \"/"+appName+"/"+mdlName+"/"+actionName+"\" method = \"POST\" modelAttribute=\"atr"+mdlName+"\">");
			outputStream.println("\t\t\t\t<br />");
			outputStream.println(sb.toString());
			outputStream.println("\t\t\t\t<input type = \"submit\" value = \"Submit\" />");
			outputStream.println("\t\t\t</form:form>");
			outputStream.println("\t\t</div>");
			outputStream.println("\t</body>");
			outputStream.println("</html>");
			outputStream.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(outputStream!=null) {
				outputStream.close();

			}

		}

	}

}
