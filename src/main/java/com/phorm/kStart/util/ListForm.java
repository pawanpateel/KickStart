package com.phorm.kStart.util;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.context.annotation.Scope;

import com.phorm.kStart.model.Feelds;
import com.phorm.kStart.model.Modal;

@Scope("prototype")
public class ListForm {

	public void createFile(Modal modelObj, String folderPath, String appName, ArrayList<String>  modelList, Map<String, Modal> mpModal) {
		PrintWriter outputStream = null;

		ArrayList<Feelds> tmpArr;		
		tmpArr=modelObj.getFields();
		String fldName=null;
		String idName="";
		String mdlName=modelObj.getModelName();
		Modal tempModal=null;
		String fldType="";
		String inerFldName="";
		ArrayList<Feelds> fldArr;
		String modDataStr="";
		String inModDataStr="";
		String inHeadStr="";
		String headStr="";
		String fldRelType="";
		String addButtonStr="";
		boolean manyPresent=false;
		boolean manyToMany=false;
		String manyFldName="";
		String innerIdName="";
		try {
			outputStream = new PrintWriter(new FileWriter(folderPath+"/"+mdlName+"/listForm.jsp"));
			outputStream.println("<%@ page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\"");
			outputStream.println("\tpageEncoding=\"ISO-8859-1\"%>");
			outputStream.println("<%@ taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>");
			outputStream.println("<%@ taglib prefix=\"spring\" uri=\"http://www.springframework.org/tags\" %>");
			outputStream.println("<%@ taglib prefix=\"cora\" uri=\"http://java.sun.com/jsp/jstl/core\" %>");
			outputStream.println("<html>");
			outputStream.println("\t<head>");
			outputStream.println("\t</head>");
			outputStream.println("\t<body>");
			outputStream.println("\t\t<div>");
			outputStream.println("\t\t\t<cora:if test=\"${!empty list"+mdlName+"}\">");
			outputStream.println("\t\t\t\t<table border=\"1\">");
			outputStream.println("\t\t\t\t<tr>");
			for(Feelds f:tmpArr) {
				fldName=f.getFieldName();
				fldType=f.getDataType();				
				if(f.getIsPrimary()) {
					idName=idName+"|${mdl"+mdlName+"."+fldName+"}";
				}				
				if(modelList.contains(fldType)) {
					tempModal=mpModal.get(fldType);
					fldArr=tempModal.getFields();
					fldRelType=f.getRelationType();
					if(fldRelType!=null && fldRelType!="" && (fldRelType.equals("onetomany") || fldRelType.equals("manytomany"))) {
						manyFldName=fldName;
						addButtonStr=addButtonStr+"\t\t\t\t\t\t<td><form action = \"/"+appName+"/"+mdlName+"/addExtra\" method = \"POST\">\n";
						addButtonStr=addButtonStr+"\t\t\t\t\t<input type=\"hidden\" name= \"uniqueId\" value=\"PLACEHOLDER\"/>\n";
						addButtonStr=addButtonStr+"\t\t\t\t\t<input type = \"submit\" value = \"Add\" />\n";
						addButtonStr=addButtonStr+"\t\t\t\t</form></td>\n";
						manyPresent=true;
						
							manyToMany=true;
						

					}
					if(manyPresent) {
						for(Feelds fld:fldArr) {
							inerFldName=fld.getFieldName();
							//outputStream.println("\t\t\t\t\t<th>"+fldName+"."+inerFldName+"</th>");
							if(!(fld.getRelationType().equals("manytoone") || fld.getRelationType().equals("manytomany"))) {
								inHeadStr=inHeadStr+"\t\t\t\t\t<th>"+fldName+"."+inerFldName+"</th>\n";
								inModDataStr=inModDataStr+"\t\t\t\t\t\t<td>${obj"+mdlName+"."+inerFldName+"}</td>\n";

								if(fld.getIsPrimary()) {
									innerIdName=innerIdName+"|${obj"+mdlName+"."+inerFldName+"}";
								}
							}
						}	
					}else {
						for(Feelds fld:fldArr) {
							inerFldName=fld.getFieldName();
							//outputStream.println("\t\t\t\t\t<th>"+fldName+"."+inerFldName+"</th>");
							inHeadStr=inHeadStr+"\t\t\t\t\t<th>"+fldName+"."+inerFldName+"</th>\n";
							inModDataStr=inModDataStr+"\t\t\t\t\t\t<td>${mdl"+mdlName+"."+fldName+"."+inerFldName+"}</td>\n";
						}	
					}
				}else {
					//outputStream.println("\t\t\t\t\t<th>"+fldName+"</th>");
					headStr=headStr+"\t\t\t\t\t<th>"+fldName+"</th>\n";
					modDataStr=modDataStr+"\t\t\t\t\t\t<td>${mdl"+mdlName+"."+fldName+"}</td>\n";
				}
			}
			outputStream.println(headStr);
			outputStream.println(inHeadStr);
			if(manyPresent) {
				outputStream.println("\t\t\t\t\t<th>Add</th>");
			}
			outputStream.println("\t\t\t\t\t<th>Edit</th>");
			outputStream.println("\t\t\t\t\t<th>Delete</th>");
			outputStream.println("\t\t\t\t</tr>");
			outputStream.println("\t\t\t\t<cora:forEach items=\"${list"+mdlName+"}\" var=\"mdl"+mdlName+"\">");
			if(manyPresent) {
				innerIdName=innerIdName.replaceFirst("\\|", "");

				outputStream.println("\t\t\t\t<cora:forEach items=\"${mdl"+mdlName+"."+manyFldName+"}\" var=\"obj"+mdlName+"\">");
				outputStream.println("\t\t\t\t\t<tr>");				
				idName=idName.replaceFirst("\\|", "");				
				outputStream.println(modDataStr);
				outputStream.println(inModDataStr);
				outputStream.println("");			
				outputStream.println(addButtonStr.replace("PLACEHOLDER", idName));
				outputStream.println("\t\t\t\t\t\t<td><form action = \"/"+appName+"/"+mdlName+"/edit\" method = \"POST\">");
				outputStream.println("\t\t\t\t\t<input type=\"hidden\" name= \"uniqueId\" value=\""+idName+"\"/>");
				outputStream.println("\t\t\t\t\t<input type = \"submit\" value = \"Edit\" />");
				outputStream.println("\t\t\t\t</form></td>");
				outputStream.println("\t\t\t\t\t\t<td><form action = \"/"+appName+"/"+mdlName+"/delete\" method = \"POST\">");
				if(manyToMany) {
					outputStream.println("\t\t\t\t\t<input type=\"hidden\" name= \"uniqueId\" value=\""+idName+"@#@"+innerIdName+"\"/>");	
				}else {
					outputStream.println("\t\t\t\t\t<input type=\"hidden\" name= \"uniqueId\" value=\""+idName+"\"/>");
				}
				outputStream.println("\t\t\t\t\t<input type = \"submit\" value = \"Delete\" />");
				outputStream.println("\t\t\t\t</form></td>");
				outputStream.println("\t\t\t\t\t</tr>");
				outputStream.println("\t\t\t\t</cora:forEach>");		

			}else {
				outputStream.println("\t\t\t\t\t<tr>");
				
				idName=idName.replaceFirst("\\|", "");
				outputStream.println(modDataStr);
				outputStream.println(inModDataStr);
				outputStream.println("");			
				outputStream.println("\t\t\t\t\t\t<td><form action = \"/"+appName+"/"+mdlName+"/edit\" method = \"POST\">");
				outputStream.println("\t\t\t\t\t<input type=\"hidden\" name= \"uniqueId\" value=\""+idName+"\"/>");
				outputStream.println("\t\t\t\t\t<input type = \"submit\" value = \"Edit\" />");
				outputStream.println("\t\t\t\t</form></td>");
				outputStream.println("\t\t\t\t\t\t<td><form action = \"/"+appName+"/"+mdlName+"/delete\" method = \"POST\">");
				outputStream.println("\t\t\t\t\t<input type=\"hidden\" name= \"uniqueId\" value=\""+idName+"\"/>");
				outputStream.println("\t\t\t\t\t<input type = \"submit\" value = \"Delete\" />");
				outputStream.println("\t\t\t\t</form></td>");
				outputStream.println("\t\t\t\t\t</tr>");
			}
			outputStream.println("\t\t\t\t</cora:forEach>");
			outputStream.println("\t\t\t\t</table>");
			outputStream.println("\t\t\t</cora:if>");
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
