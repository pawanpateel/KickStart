package com.phorm.kStart.util;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.context.annotation.Scope;

import com.phorm.kStart.model.Feelds;
import com.phorm.kStart.model.Modal;

@Scope("prototype")
public class UpdateForm {

	public void createFile(Modal modelObj, String folderPath, String appName, ArrayList<String>  modelList, Map<String, Modal> mpModal) {
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
		boolean manyToMany=false;
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
						if(fldRelType!=null && fldRelType!="" && (fldRelType.equals("onetomany") || fldRelType.equals("manytomany"))) {
							manyPresent=true;
							
								manyToMany=true;
								indaxVal="${tsl}";
							
						}
						if(manyToMany) {
							sb.append("<cora:forEach  begin=\"0\" end=\"${atr"+mdlName+"."+fldName+".size()-1}\" var=\"tsl\">\n");
						}
						sb.append("\t\t\t\t--Start "+fldType+" object--<br/>\n");
						for(Feelds fld:fldArr) {
							inerFldName=fld.getFieldName();
							if(!(fld.getRelationType().equals("manytoone") || fld.getRelationType().equals("manytomany"))) {
								if(manyPresent) {
									if(!fld.getIsPrimary()) {								
										sb.append("\t\t\t\t<form:errors path=\""+fldName+"["+indaxVal+"]."+inerFldName+"\"/><br/>\n");
										sb.append("\t\t\t\t"+inerFldName+": <form:input path= \""+fldName+"["+indaxVal+"]."+inerFldName+"\"/>\n");
										sb.append("\t\t\t\t<br/>\n");
									}else {								
										sb.append("\t\t\t\t<form:hidden path= \""+fldName+"["+indaxVal+"]."+inerFldName+"\"/>\n");
										sb.append("\t\t\t\t<br/>\n");
									}
								}else {
									if(!fld.getIsPrimary()) {								
										sb.append("\t\t\t\t<form:errors path=\""+fldName+"."+inerFldName+"\"/><br/>\n");
										sb.append("\t\t\t\t"+inerFldName+": <form:input path= \""+fldName+"."+inerFldName+"\"/>\n");
										sb.append("\t\t\t\t<br/>\n");
									}else {								
										sb.append("\t\t\t\t<form:hidden path= \""+fldName+"."+inerFldName+"\"/>\n");
										sb.append("\t\t\t\t<br/>\n");
									}
								}
							}
						}
						sb.append("\t\t\t\t--End "+fldType+" object--<br/>\n");
						if(manyToMany) {
							sb.append("</cora:forEach>");
						}
					}else {
						sb.append("\t\t\t\t<form:errors path=\""+fldName+"\"/><br/>\n");
						sb.append("\t\t\t\t"+fldName+": <form:input path= \""+fldName+"\"/>\n");
						sb.append("\t\t\t\t<br/>\n");
					}
				}else {
					sb.append("\t\t\t\t<form:hidden path= \""+fldName+"\"/>\n");
					sb.append("\t\t\t\t<br/>\n");
				}
			}


			outputStream = new PrintWriter(new FileWriter(folderPath+"/"+mdlName+"/updateForm.jsp"));
			outputStream.println("<%@ page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\"");
			outputStream.println("\tpageEncoding=\"ISO-8859-1\"%>");
			outputStream.println("<%@ taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>");
			outputStream.println("<%@ taglib prefix=\"spring\" uri=\"http://www.springframework.org/tags\" %>");
			if(manyToMany) {
				outputStream.println("<%@ taglib prefix=\"cora\" uri=\"http://java.sun.com/jsp/jstl/core\" %>");
			}
			outputStream.println("<html>");
			outputStream.println("\t<head>");
			outputStream.println("\t</head>");
			outputStream.println("\t<body>");
			outputStream.println("\t\t<div>");
			outputStream.println("\t\t\t<form:form action = \"/"+appName+"/"+mdlName+"/update\" method = \"POST\" modelAttribute=\"atr"+mdlName+"\">");
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
