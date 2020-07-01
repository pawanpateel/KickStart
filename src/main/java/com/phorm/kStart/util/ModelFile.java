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
public class ModelFile {

	String tempType=null;
	String relType=null;
	boolean manyPresent=false;
	String joinPK="";
	String copyJoinPK="";
	boolean otherManyExists=false;
	int pkCount=0;
	String pkField=null;
	boolean addListCreate=false;	
	String pkClassName="";	
	String invPK="";		
	String fldName=null;
	String upperName=null;	
	Modal tempModal=null;
	Modal modelObj;
	String folderPath;
	String packageName;
	ArrayList<String>  modelList;
	Map<String, Modal> mpModal;
	ArrayList<Feelds> pkFldArr=new ArrayList<Feelds>();
	ArrayList<Feelds> fldArr;
	String pkUniString="";
	String compareString="";
	String hashString="";
	boolean createJSP=true;
	@SuppressWarnings("finally")
	public boolean createFile(Modal modelObj, String folderPath, String packageName, ArrayList<String>  modelList, Map<String, Modal> mpModal) {
		createJSP=true;
		PrintWriter outputStream = null;
		this.modelObj=modelObj;
		this.folderPath=folderPath;
		this.packageName=packageName;
		this.modelList=modelList;
		this.mpModal=mpModal;
		tempType=null;
		relType=null;
		manyPresent=false;
		joinPK="";
		copyJoinPK="";
		otherManyExists=false;
		pkCount=0;
		pkField=null;
		addListCreate=false;	
		pkClassName="";	
		invPK="";		
		fldName=null;
		upperName=null;	
		Modal tempModal=null;
		pkFldArr=new ArrayList<Feelds>();
		fldArr=null;
		pkUniString="";
		compareString="";
		hashString="";
		ArrayList<Feelds> tmpArr;		
		tmpArr=modelObj.getFields();	

		String importString="";
		String classNameString="";
		String fieldNameString="";
		try {
			outputStream = new PrintWriter(new FileWriter(folderPath+"/"+modelObj.getModelName()+".java"));
			outputStream.println("package "+packageName+";");
			outputStream.println("");
			importString=writeImport(tmpArr);
			outputStream.println(importString);			
			outputStream.println("");
			classNameString=writeClassName(modelObj.getModelName());
			outputStream.println(classNameString);	
			for(Feelds f:tmpArr) {
				fieldNameString=writeFieldName(f);
				outputStream.println(fieldNameString);
			}
			outputStream.println("}");
			outputStream.close();
			if(pkCount>1) {
				String setFldString="";
				String listFldString="";				
				String getSetString="";
				int indexOfFld=0;
				outputStream = new PrintWriter(new FileWriter(folderPath+"/"+pkClassName+".java"));
				outputStream.println("package "+packageName+";");
				outputStream.println("");
				outputStream.println("import java.io.Serializable;");
				outputStream.println("");
				outputStream.println("public class "+pkClassName+" implements Serializable {");
				for(Feelds pkFld:pkFldArr) {
					fldName=pkFld.getFieldName();
					tempType=pkFld.getDataType();
					upperName=pkFld.getUpperName();
					outputStream.println("\tprotected "+tempType+" "+fldName+";");
					setFldString=setFldString+"\t\tthis."+fldName+" = "+fldName+";\n";
					listFldString=listFldString+","+tempType+" "+fldName;

					getSetString=getSetString+"\tpublic "+tempType+" get"+upperName+"() {\n";
					getSetString=getSetString+"\t\treturn "+fldName+";\n";
					getSetString=getSetString+"\t}\n";

					getSetString=getSetString+"\tpublic void set"+upperName+"("+tempType+" in"+fldName+") {\n";
					getSetString=getSetString+"\t\t"+fldName+" = in"+fldName+";\n";
					getSetString=getSetString+"\t}\n";		

					pkFormat(tempType, indexOfFld);

					indexOfFld++;
				}
				compareString=compareString.replaceFirst("&&", "");
				hashString=hashString+"\t\tresult = result * prime;";
				listFldString=listFldString.replaceFirst(",", "");
				outputStream.println("");
				outputStream.println("\tpublic "+pkClassName+"() {}");
				outputStream.println("");
				outputStream.println("\tpublic "+pkClassName+"(String uniqueId) {");
				outputStream.println("\t\tString[] pkArray=uniqueId.split(\"\\\\|\");");
				outputStream.println(pkUniString);
				outputStream.println("\t}");
				outputStream.println("");
				outputStream.println("\tpublic "+pkClassName+"("+listFldString+") {");
				outputStream.println(setFldString);
				outputStream.println("\t}");				
				outputStream.println("");
				outputStream.println(getSetString);
				outputStream.println("\t@Override");
				outputStream.println("\tpublic boolean equals(Object obj) {");
				outputStream.println("\t\tif (obj == this) {");
				outputStream.println("\t\t\treturn true;");
				outputStream.println("\t\t}");
				outputStream.println("");
				outputStream.println("\t\tif (obj == null || obj.getClass() != this.getClass()) {");
				outputStream.println("\t\t\treturn false;");
				outputStream.println("\t\t}");
				outputStream.println("");
				outputStream.println("\t\t"+pkClassName+" guest = ("+pkClassName+") obj;");
				outputStream.println("");
				outputStream.println("\t\treturn "+compareString+";");
				outputStream.println("\t}");
				outputStream.println("");
				outputStream.println("\t@Override");
				outputStream.println("\tpublic int hashCode() {");
				outputStream.println("\t\tfinal int prime = 17;");
				outputStream.println("\t\tint result = 1;");
				outputStream.println(hashString);
				outputStream.println("\t\treturn result;");
				outputStream.println("\t}");
				outputStream.println("");
				outputStream.println("}");
				outputStream.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(outputStream!=null) {
				outputStream.close();

			}
			return createJSP;

		}
	}


	private void pkFormat(String tempType, int indexOfFld) {
		if(tempType.equals("byte")) {
			pkUniString=pkUniString+"\t\tthis."+fldName+"=Byte.parseByte(pkArray["+indexOfFld+"]);\n";
			compareString=compareString+"&& "+fldName+" == guest."+fldName+"\n";
			hashString=hashString+"\t\tresult = result + Integer.parseInt(\"\"+"+fldName+");\n";
		}else if(tempType.equals("short")) {
			pkUniString=pkUniString+"\t\tthis."+fldName+"=Short.parseShort(pkArray["+indexOfFld+"]);\n";
			compareString=compareString+"&& "+fldName+" == guest."+fldName+"\n";
			hashString=hashString+"\t\tresult = result + Integer.parseInt(\"\"+"+fldName+");\n";
		}else if(tempType.equals("int")) {
			pkUniString=pkUniString+"\t\tthis."+fldName+"=Integer.parseInt(pkArray["+indexOfFld+"]);\n";
			compareString=compareString+"&& "+fldName+" == guest."+fldName+"\n";
			hashString=hashString+"\t\tresult = result + "+fldName+";\n";
		}else if(tempType.equals("long")) {
			pkUniString=pkUniString+"\t\tthis."+fldName+"=Long.parseLong(pkArray["+indexOfFld+"]);\n";
			compareString=compareString+"&& "+fldName+" == guest."+fldName+"\n";
			hashString=hashString+"\t\tresult = result + Integer.parseInt(\"\"+"+fldName+");\n";
		}else if(tempType.equals("float")) {
			pkUniString=pkUniString+"\t\tthis."+fldName+"=Float.parseFloat(pkArray["+indexOfFld+"]);\n";
			compareString=compareString+"&& "+fldName+" == guest."+fldName+"\n";
			hashString=hashString+"\t\tresult = result + Integer.parseInt((\"\"+"+fldName+").replace(\".\",\"\"));\n";
		}else if(tempType.equals("double")) {
			pkUniString=pkUniString+"\t\tthis."+fldName+"=Double.parseDouble(pkArray["+indexOfFld+"]);\n";
			compareString=compareString+"&& "+fldName+" == guest."+fldName+"\n";
			hashString=hashString+"\t\tresult = result + Integer.parseInt((\"\"+"+fldName+").replace(\".\",\"\"));\n";
		}else if(tempType.equals("boolean")) {
			pkUniString=pkUniString+"\t\tthis."+fldName+"=Boolean.parseBoolean(pkArray["+indexOfFld+"]);\n";
			compareString=compareString+"&& "+fldName+" == guest."+fldName+"\n";
			hashString=hashString+"\t\tresult = result + (("+fldName+" == true) ? 4 : 2);\n";
		}else if(tempType.equals("BigDecimal")) {
			pkUniString=pkUniString+"\t\tthis."+fldName+"=new BigDecimal(pkArray["+indexOfFld+"]);\n";
			compareString=compareString+"&& "+fldName+" == guest."+fldName+"\n";
			hashString=hashString+"\t\tresult = result + Integer.parseInt((\"\"+"+fldName+").replace(\".\",\"\"));\n";
		}else if(tempType.equals("BigInteger")) {
			pkUniString=pkUniString+"\t\tthis."+fldName+"=new BigInteger(pkArray["+indexOfFld+"]);\n";
			compareString=compareString+"&& "+fldName+" == guest."+fldName+"\n";
			hashString=hashString+"\t\tresult = result + Integer.parseInt(\"\"+"+fldName+");\n";
		}else if(tempType.equals("char")){
			pkUniString=pkUniString+"\t\tthis."+fldName+"=new Character(pkArray["+indexOfFld+"].charAt(0));\n";
			compareString=compareString+"&& "+fldName+" == guest."+fldName+"\n";
			hashString=hashString+"\t\tresult = result + "+fldName+";\n";
		}else if(tempType.equals("String")) {
			pkUniString=pkUniString+"\t\tthis."+fldName+"=pkArray["+indexOfFld+"];\n";
			compareString=compareString+"&& ("+fldName+" == guest."+fldName+" || ("+fldName+" !=null && "+fldName+".equals(guest.get"+upperName+"())))\n";
			hashString=hashString+"\t\tresult = result + (("+fldName+" == null) ? 0 : "+fldName+".hashCode());\n";
		}
	}

	private String writeImport(ArrayList<Feelds> tmpArr) {
		StringBuilder sb=new StringBuilder();

		for(Feelds f:tmpArr) {
			tempType=f.getDataType();
			if(tempType.equals("BigDecimal")) {
				sb.append("import java.math.BigDecimal;\n");
			}else if(tempType.equals("BigInteger")) {
				sb.append("import java.math.BigInteger;\n");
			}else if(tempType.contentEquals("Date")) {
				sb.append("import java.sql.Date;\n");
			}else if(modelList.contains(tempType)) {
				relType=f.getRelationType();
				if(relType.equals("onetoone")) {
					sb.append("\n");
					sb.append("import javax.persistence.JoinColumn;\n");
					sb.append("import javax.persistence.JoinColumns;\n");
					sb.append("import javax.persistence.OneToOne;\n");
					sb.append("import javax.persistence.CascadeType;\n");
					sb.append("import javax.validation.Valid;\n");
				}else if(relType.equals("onetomany")) {
					manyPresent=true;
					sb.append("\n");
					sb.append("import javax.persistence.OneToMany;\n");
					sb.append("import java.util.ArrayList;\n");
					sb.append("import java.util.List;\n");
					sb.append("import javax.persistence.JoinColumns;\n");
					sb.append("import javax.persistence.JoinColumn;\n");
					sb.append("import javax.persistence.CascadeType;\n");
					sb.append("import javax.persistence.FetchType;\n");
					sb.append("import javax.validation.Valid;\n");
				}else if(relType.equals("manytoone")){
					sb.append("\n");
					manyPresent=true;

					sb.append("import javax.persistence.ManyToOne;\n");
					sb.append("import javax.validation.Valid;\n");
					createJSP=false;

				}else if(relType.equals("manytomany")) {
					manyPresent=true;
					sb.append("\n");
					File modalFile= new File(folderPath+"/"+tempType+".java");
					if(modalFile.exists()) {
						createJSP=false;
						otherManyExists=true;
					}else {
						sb.append("import javax.persistence.JoinTable;\n");
						sb.append("import javax.persistence.JoinColumn;\n");
						sb.append("import javax.persistence.ManyToMany;\n");
						sb.append("import javax.persistence.CascadeType;\n");	
						sb.append("import javax.persistence.FetchType;\n");
						sb.append("import java.util.ArrayList;\n");
						sb.append("import java.util.List;\n");
						sb.append("import javax.validation.Valid;\n");
					}
				}
				sb.append("\n");
				sb.append("import "+packageName+"."+tempType+";\n");
			}

			if(f.getIsPrimary()) {		
				joinPK=joinPK+",@JoinColumn(name=\""+f.getFieldName()+"\")\n";
				pkCount++;
			}
		}
		joinPK=joinPK.replaceFirst(",", "");
		copyJoinPK=joinPK;
		if(pkCount<2 && !manyPresent) {
			sb.append("import javax.persistence.GeneratedValue;\n");
			sb.append("import javax.persistence.GenerationType;\n");
			sb.append("import javax.persistence.Id;\n");				
		}else {
			sb.append("import javax.persistence.IdClass;\n");
			sb.append("import javax.persistence.Id;\n");
			joinPK="@JoinColumns({\n"+joinPK+"\n})";
		}
		sb.append("\n");
		sb.append("import javax.persistence.Entity;\n");			
		sb.append("import javax.persistence.Table;\n");
		sb.append("import javax.validation.constraints.Max;\n");
		sb.append("import javax.validation.constraints.Min;\n");
		sb.append("import javax.validation.constraints.NotEmpty;\n");
		sb.append("import javax.validation.constraints.NotNull;\n");
		sb.append("\n");
		sb.append("import org.springframework.context.annotation.Scope;\n");
		sb.append("\n");

		return sb.toString();
	}

	private String writeClassName(String modelName) {
		StringBuilder sbClass=new StringBuilder();
		sbClass.append("@Table\n");
		sbClass.append("@Scope(\"prototype\")\n");
		sbClass.append("@Entity\n");
		if(pkCount>1) {
			pkClassName=modelName+"PK";
			sbClass.append("@IdClass("+pkClassName+".class)\n");
		}
		sbClass.append("public class "+modelName+" {\n");
		return sbClass.toString();
	}

	private String writeFieldName(Feelds f) {
		StringBuilder sbField=new StringBuilder();
		String useDump="";
		fldName=f.getFieldName();
		tempType=f.getDataType();
		upperName=f.getUpperName();
		if(!f.getCanBeEmpty() && !f.getIsPrimary() && tempType.equals("String")) {
			sbField.append("\t@NotEmpty(message = \""+fldName+" cannot be empty\")\n");
		}else if(!f.getCanBeEmpty() && !f.getIsPrimary() && !tempType.equals("boolean")
				&& !modelList.contains(tempType) && !tempType.equals("Date") && !tempType.equals("char")) {
			sbField.append("\t@Min(value = 1, message = \""+fldName+" should not be less than 1\")\n");
		}
		if(!f.getIsNullable() && !f.getIsPrimary() && !f.getRelationType().equals("manytoone")) {
			sbField.append("\t@NotNull(message = \""+fldName+" cannot be null\")\n");
		}
		if(f.getMinValue()!=null && f.getMinValue()!="") {
			sbField.append("\t@Min(value = "+f.getMinValue()+", message = \""+fldName+" should not be less than "+f.getMinValue()+"\")\n");
		}
		if(f.getMaxValue()!=null && f.getMaxValue()!="") {
			sbField.append("\t@Max(value = "+f.getMaxValue()+", message = \""+fldName+" should not be greater than "+f.getMaxValue()+"\")\n");
		}
		if(f.getIsPrimary()) {
			if(pkCount<2 && !manyPresent) {
				sbField.append("\t@Id\n");
				sbField.append("\t@GeneratedValue(strategy = GenerationType.AUTO)\n");
			}else {
				sbField.append("\t@Id\n");
				sbField.append("\t@NotNull(message = \""+fldName+" cannot be null\")\n");
				if(!tempType.equals("boolean") && !modelList.contains(tempType) 
						&& !tempType.equals("Date") && !tempType.equals("char")
						&& !tempType.equals("String")) {
					sbField.append("\t@Min(value = 1, message = \""+fldName+" should not be less than 1\")\n");
				}else {
					sbField.append("\t@NotEmpty(message = \""+fldName+" cannot be empty\")\n");
				}
				pkFldArr.add(f);
			}
		}

		if(modelList.contains(tempType)) {
			relType=f.getRelationType();
			if(relType.equals("onetoone")) {
				sbField.append("\t@OneToOne(cascade=CascadeType.ALL)\n");
				tempModal=mpModal.get(tempType);
				fldArr=tempModal.getFields();
				sbField.append("\t@JoinColumns({\n");
				for(Feelds fld:fldArr) {
					if(fld.getIsPrimary()) {
						useDump=useDump+",\t@JoinColumn(name=\""+fld.getFieldName()+"\")\n";
					}
				}
				useDump=useDump.replaceFirst(",", "");
				sbField.append(useDump);
				useDump="";
				sbField.append("\t})\n");
			}else if(relType.equals("onetomany")) {						
				sbField.append("\t@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)\n");
				sbField.append(joinPK);
				tempType="List<@Valid "+tempType+">";
				addListCreate=true;
			}else if(relType.equals("manytoone")) {
				sbField.append("\t@ManyToOne\n");						
				fldName="fld"+tempType;
			}else if(relType.equals("manytomany")) {
				if(otherManyExists) {
					return "";
				}else {
					sbField.append("\t@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)\n");
					sbField.append("\t@JoinTable(\n");
					sbField.append("\t\tname = \""+modelObj.getModelName()+"_"+tempType+"\",\n");
					sbField.append("\t\tjoinColumns = { "+copyJoinPK+"\t\t},\n");
					tempModal=mpModal.get(tempType);
					fldArr=tempModal.getFields();							
					for(Feelds fld:fldArr) {
						if(fld.getIsPrimary()) {
							invPK=invPK+",@JoinColumn(name = \""+fld.getFieldName()+"\")\n";
						}
					}
					invPK=invPK.replaceFirst(",", "");
					invPK="\t\tinverseJoinColumns = { "+invPK+" \t\t}";
					sbField.append(invPK);
					sbField.append("\t)\n");
					fldName="fld"+tempType;

				}
				tempType="List<@Valid "+tempType+">";
				addListCreate=true;
			}
			if(addListCreate) {
				sbField.append("\tprivate "+tempType+" "+fldName+"=new Array"+tempType+"();\n");
			}else {
				sbField.append("\tprivate @Valid "+tempType+" "+fldName+";\n");
			}
		} else {
			sbField.append("\tprivate "+tempType+" "+fldName+";\n");
		}
		sbField.append("\n");
		sbField.append("\tpublic "+tempType.replace("@Valid ", "")+" get"+upperName+"() {\n");
		sbField.append("\t\treturn "+fldName+";\n");
		sbField.append("\t}\n");
		sbField.append("\n");		
		sbField.append("\tpublic void set"+upperName+"("+tempType+" in"+fldName+") {\n");		
		sbField.append("\t\t"+fldName+" = in"+fldName+";\n");		
		sbField.append("\t}\n");		
		sbField.append("\n");
		return sbField.toString();
	}
}
