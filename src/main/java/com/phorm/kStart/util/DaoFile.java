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
public class DaoFile {

	public void createFile(Modal modelObj, String folderPath, String packageName, ArrayList<String>  modelList,Map<String, Modal> mpModal) {
		PrintWriter outputStream = null;

		ArrayList<Feelds> tmpArr;		
		tmpArr=modelObj.getFields();
		String fldName=null;
		String fldType=null;
		String addInfo="";
		String keyType="";
		int keyCount=0;
		String keyName=null;
		String tempType=null;
		String mdlName=modelObj.getModelName();
		boolean oneManyPresent=false;
		boolean manyPresent=false;
		boolean otherManyExists=false;
		String manyFld="";
		String manyFldName="";
		String manyId="";
		try {
			outputStream = new PrintWriter(new FileWriter(folderPath+"/"+mdlName+"Dao.java"));
			outputStream.println("package "+packageName+";");
			outputStream.println("");
			for(Feelds f:tmpArr) {
				tempType=f.getDataType();
				if(tempType.equals("BigDecimal")) {
					outputStream.println("import java.math.BigDecimal;");
				}else if(tempType.equals("BigInteger")) {
					outputStream.println("import java.math.BigInteger;");
				}else if(tempType.equals("Date")) {
					outputStream.println("import java.sql.Date;\n");
				}else if(modelList.contains(tempType)) {
					if(f.getRelationType().equals("manytomany")) {
						manyPresent=true;
						File modalFile= new File(folderPath+"/"+tempType+"Dao.java");
						if(modalFile.exists()) {							
							otherManyExists=true;
						}
						manyFld=tempType;
						manyFldName=f.getUpperName();
						Modal tempModal=mpModal.get(tempType);
						ArrayList<Feelds> fldArr=tempModal.getFields();
						for(Feelds fld:fldArr) {
							if(fld.getIsPrimary()) {
								manyId=manyId+"+\"|\"+x.get"+fld.getUpperName()+"()";
							}
						}

					}else if(f.getRelationType().equals("onetomany")) {
						manyPresent=true;
						oneManyPresent=true;
						manyFld=tempType;
						manyFldName=f.getUpperName();
						Modal tempModal=mpModal.get(tempType);
						ArrayList<Feelds> fldArr=tempModal.getFields();
						for(Feelds fld:fldArr) {
							if(fld.getIsPrimary()) {
								manyId=manyId+"+\"|\"+x.get"+fld.getUpperName()+"()";
							}
						}
					}
					outputStream.println("");
					outputStream.println("import "+packageName.replace(".dao", ".model")+"."+tempType+";");

				}

				if(f.getIsPrimary()) {
					if(keyType!="") {
						keyType=mdlName+"PK";
						outputStream.println("import "+packageName.replace(".dao", ".model")+"."+keyType+";");
					}else {
						keyType=f.getDataType();
						keyCount++;
					}
				}
			}
			if(manyPresent && !otherManyExists) {
				outputStream.println("import java.util.Iterator;");
			}
			outputStream.println("import java.util.List;");
			outputStream.println("");
			outputStream.println("import javax.transaction.Transactional;");
			outputStream.println("");
			outputStream.println("import org.hibernate.Session;");
			outputStream.println("import org.hibernate.SessionFactory;");
			outputStream.println("import org.springframework.beans.factory.annotation.Autowired;");
			outputStream.println("import org.springframework.stereotype.Repository;");
			outputStream.println("");			
			outputStream.println("import "+packageName.replace("dao", "model")+"."+mdlName+";");			
			outputStream.println("");
			outputStream.println("@Repository");
			outputStream.println("@Transactional");
			outputStream.println("public class "+mdlName+"Dao {");
			outputStream.println("");
			outputStream.println("\t@Autowired");
			outputStream.println("\tprivate SessionFactory sessionFactory;");
			if(oneManyPresent) {
				outputStream.println("");
				outputStream.println("\t@Autowired");
				outputStream.println("\tprivate "+manyFld+"Dao in"+manyFld+"Dao;");
			}
			outputStream.println("");
			outputStream.println("");    		    
			outputStream.println("\tpublic Session getSession() {");
			outputStream.println("\t\treturn sessionFactory.getCurrentSession();");
			outputStream.println("\t}");
			outputStream.println("");
			outputStream.println("\tpublic String save("+mdlName+" mdl"+mdlName+") {");
			if(keyType.equals("byte")) {
				outputStream.println("\t\tByte isSuccess = (Byte)getSession().save(mdl"+mdlName+");");
			}else if(keyType.equals("short")) {
				outputStream.println("\t\tShort isSuccess = (Short)getSession().save(mdl"+mdlName+");");
			}else if(keyType.equals("int")) {
				outputStream.println("\t\tInteger isSuccess = (Integer)getSession().save(mdl"+mdlName+");");
			}else if(keyType.equals("long")) {
				outputStream.println("\t\tLong isSuccess = (Long)getSession().save(mdl"+mdlName+");");
			}else if(keyType.equals("float")) {
				outputStream.println("\t\tFloat isSuccess = (Float)getSession().save(mdl"+mdlName+");");
			}else if(keyType.equals("double")) {
				outputStream.println("\t\tDouble isSuccess = (Double)getSession().save(mdl"+mdlName+");");
			}else if(keyType.equals("boolean")) {
				outputStream.println("\t\tBoolean isSuccess = (Boolean)getSession().save(mdl"+mdlName+");");
			}else if(keyType.equals("BigDecimal")) {
				outputStream.println("\t\tBigDecimal isSuccess = (BigDecimal)getSession().save(mdl"+mdlName+");");
			}else if(keyType.equals("BigInteger")) {
				outputStream.println("\t\tBigInteger isSuccess = (BigInteger)getSession().save(mdl"+mdlName+");");
			}else if(keyType.equals("char")){
				outputStream.println("\t\tCharacter isSuccess = (Character)getSession().save(mdl"+mdlName+");");
			}else if(keyType.equals(mdlName+"PK")) {
				outputStream.println("\t\t"+mdlName+"PK isSuccess = ("+mdlName+"PK)getSession().save(mdl"+mdlName+");");
			}else if(keyType.equals("String")){
				outputStream.println("\t\tString isSuccess = (String)getSession().save(mdl"+mdlName+");");
			}
			outputStream.println("\t\tif(isSuccess != null){");
			outputStream.println("\t\t\treturn \"Success\";");
			outputStream.println("\t\t}else{");
			outputStream.println("\t\t\treturn \"Error while Saving "+mdlName+"\";");
			outputStream.println("\t\t}");        
			outputStream.println("\t}");
			outputStream.println("");
			outputStream.println("\tpublic boolean delete("+mdlName+" mdl"+mdlName+") {");
			outputStream.println("\t\tgetSession().delete(mdl"+mdlName+");");
			outputStream.println("\t\treturn true;");
			outputStream.println("\t}");
			outputStream.println("");
			outputStream.println("\tpublic boolean update("+mdlName+" mdl"+mdlName+") {");
			outputStream.println("\t\tgetSession().update(mdl"+mdlName+");");
			outputStream.println("\t\treturn true;");
			outputStream.println("\t}");
			outputStream.println("");
			outputStream.println("\t@SuppressWarnings(\"unchecked\")");
			outputStream.println("\tpublic List<"+mdlName+"> getAll() {");
			outputStream.println("\t\treturn getSession().createQuery(\"from "+mdlName+"\").list();");
			outputStream.println("\t}");	

			keyType="";
			for(Feelds f:tmpArr) {
				fldName=f.getFieldName();
				fldType=f.getDataType();
				if(otherManyExists) {
					if(!modelList.contains(fldType) ) {
						addInfo=addInfo+"\t\tmdl"+mdlName+".get"+f.getUpperName()+"();\n";
						outputStream.println("");
						outputStream.println("\t@SuppressWarnings(\"unchecked\")");				
						outputStream.println("\tpublic List<"+mdlName+"> getBy"+fldName+"("+fldType+" in"+fldName+") {");
						if(fldType.equals("String") || fldType.equals("Date")){
							outputStream.println("\t\treturn getSession().createQuery(\"from "+mdlName+" where "+fldName+"='\"+in"+fldName+"+\"'\").list();");
						}else{
							outputStream.println("\t\treturn getSession().createQuery(\"from "+mdlName+" where "+fldName+"=\"+in"+fldName+"+\"\").list();");
						}
						outputStream.println("\t}");
					}
				}else {
					addInfo=addInfo+"\t\tmdl"+mdlName+".get"+f.getUpperName()+"();\n";
					outputStream.println("");
					outputStream.println("\t@SuppressWarnings(\"unchecked\")");	
					
					outputStream.println("\tpublic List<"+mdlName+"> getBy"+fldName+"("+fldType+" in"+fldName+") {");
					if(fldType.equals("String") || fldType.equals("Date")){
						outputStream.println("\t\treturn getSession().createQuery(\"from "+mdlName+" where "+fldName+"='\"+in"+fldName+"+\"'\").list();");
					}else{
						outputStream.println("\t\treturn getSession().createQuery(\"from "+mdlName+" where "+fldName+"=\"+in"+fldName+"+\"\").list();");
					}
					
					outputStream.println("\t}");
				}

				if(f.getIsPrimary()) {
					if(keyType!="") {
						keyType=mdlName+"PK";
					}else {
						keyType=f.getDataType();
					}
				}

			}

			if(keyType.equals("byte")) {
				keyName="Byte.parseByte(uniqueId)";
			}else if(keyType.equals("short")) {
				keyName="Short.parseShort(uniqueId)";
			}else if(keyType.equals("int")) {
				keyName="Integer.parseInt(uniqueId)";
			}else if(keyType.equals("long")) {
				keyName="Long.parseLong(uniqueId)";
			}else if(keyType.equals("float")) {
				keyName="Float.parseFloat(uniqueId)";
			}else if(keyType.equals("double")) {
				keyName="Double.parseDouble(uniqueId)";
			}else if(keyType.equals("boolean")) {
				keyName="Boolean.parseBoolean(uniqueId)";
			}else if(keyType.equals("BigDecimal")) {
				keyName="new BigDecimal(uniqueId)";
			}else if(keyType.equals("BigInteger")) {
				keyName="new BigInteger(uniqueId)";
			}else if(keyType.equals("char")){
				keyName="new Character(uniqueId.charAt(0))";
			}else if(keyType.equals(mdlName+"PK")) {
				keyName="new "+mdlName+"PK(uniqueId)";
			}else if(keyType.equals("String")){
				keyName="uniqueId";
			}
			outputStream.println("");
			outputStream.println("\tpublic boolean deleteById(String uniqueId) {");
			outputStream.println("\t\tSession sessionObj=getSession();");
			if(manyPresent && !otherManyExists) {
				manyId=manyId.replaceFirst("\\+\"\\|\"", "");
				outputStream.println("\t\tString[] arr=uniqueId.split(\"@#@\");");			
				outputStream.println("\t\t"+mdlName+" mdl"+mdlName+" = ("+mdlName+")sessionObj.load("+mdlName+".class, "+keyName.replace("uniqueId", "arr[0]")+");");

				outputStream.println("");
				outputStream.println("\t\tif(null != mdl"+mdlName+"){");
				outputStream.println("\t\t\tIterator itr = mdl"+mdlName+".get"+manyFldName+"().iterator();");
				outputStream.println("\t\t\t"+manyFld+" x;");
				outputStream.println("\t\t\tint cnt =0;");
				outputStream.println("\t\t\twhile (itr.hasNext())"); 
				outputStream.println("\t\t\t{"); 
				outputStream.println("\t\t\t\tcnt++;"); 
				outputStream.println("\t\t\t\tx = ("+manyFld+")itr.next();"); 
				outputStream.println("\t\t\t\tif (arr[1].equals(\"\""+manyId+")){");
				outputStream.println("\t\t\t\t\titr.remove();");
				outputStream.println("\t\t\t\t\tcnt--;");
				if(oneManyPresent) {					
					outputStream.println("\t\t\t\t\tin"+manyFld+"Dao.deleteById(\"\""+manyId+");");
				}
				outputStream.println("\t\t\t\t}");
				outputStream.println("\t\t\t}");
				outputStream.println("\t\t\tif(cnt==0) {");
				outputStream.println("\t\t\t\tsessionObj.delete(mdl"+mdlName+");");
				outputStream.println("\t\t\t}else {");
				outputStream.println("\t\t\t\tsessionObj.update(mdl"+mdlName+");");
				outputStream.println("\t\t\t}");
				outputStream.println("\t\t}");

			}else {
				outputStream.println("\t\t"+mdlName+" mdl"+mdlName+" = ("+mdlName+")sessionObj.load("+mdlName+".class, "+keyName+");");
				outputStream.println("");
				outputStream.println("\t\tif(null != mdl"+mdlName+"){");
				outputStream.println("\t\t\tsessionObj.delete(mdl"+mdlName+");");
				outputStream.println("\t\t}");
			}
			outputStream.println("\t\treturn true;");
			outputStream.println("\t}");
			outputStream.println("");
			outputStream.println("\tpublic "+mdlName+" getById(String uniqueId) {");
			outputStream.println("\t\t"+mdlName+" mdl"+mdlName+" = ("+mdlName+")getSession().load("+mdlName+".class, "+keyName+");");
			outputStream.println(addInfo);
			outputStream.println("\t\treturn mdl"+mdlName+";");
			outputStream.println("\t}");
			outputStream.println("}");
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
