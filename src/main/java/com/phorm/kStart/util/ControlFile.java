package com.phorm.kStart.util;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.springframework.context.annotation.Scope;

import com.phorm.kStart.model.Modal;

@Scope("prototype")
public class ControlFile {

	public void createFile(Modal modelObj, String folderPath, String packageName, String appName) {
		PrintWriter outputStream = null;
		String modelName=modelObj.getModelName();
		
		try {
			outputStream = new PrintWriter(new FileWriter(folderPath+"/"+modelName+"Flow.java"));
			outputStream.println("package "+packageName+";");
			outputStream.println("");
			outputStream.println("import java.util.List;");
			outputStream.println("");
			outputStream.println("import javax.servlet.http.HttpServletRequest;");
			outputStream.println("import javax.validation.Valid;");
			outputStream.println("");
			outputStream.println("import org.springframework.beans.factory.annotation.Autowired;");
			outputStream.println("import org.springframework.stereotype.Controller;");
			outputStream.println("import org.springframework.ui.Model;");
			outputStream.println("import org.springframework.validation.BindingResult;");
			outputStream.println("import org.springframework.web.bind.annotation.GetMapping;");
			outputStream.println("import org.springframework.web.bind.annotation.ModelAttribute;");
			outputStream.println("import org.springframework.web.bind.annotation.PostMapping;");
			outputStream.println("import org.springframework.web.bind.support.SessionStatus;");
			outputStream.println("");
			outputStream.println("import com."+appName+".dao."+modelName+"Dao;");
			outputStream.println("import com."+appName+".model."+modelName+";");
			outputStream.println("");
			outputStream.println("@Controller");
			outputStream.println("public class "+modelName+"Flow {");
			outputStream.println("");
			outputStream.println("\t@Autowired");
			outputStream.println("\tprivate "+modelName+"Dao in"+modelName+"Dao;");
			outputStream.println("");
			outputStream.println("\t@GetMapping(\"/"+appName+"/"+modelName+"/add\")");
			outputStream.println("\tpublic String add"+modelName+"(Model model, @Autowired "+modelName+" modelObj) {");

			outputStream.println("\t\tmodel.addAttribute(\"atr"+modelName+"\", modelObj);");
			outputStream.println("\t\treturn \""+modelName+"/addForm\";");
			outputStream.println("\t}");
			
			outputStream.println("");
			outputStream.println("\t@PostMapping(\"/"+appName+"/"+modelName+"/addExtra\")");
			outputStream.println("\tpublic String add"+modelName+"Extra(HttpServletRequest request, Model model) {");
			outputStream.println("\t\tString valueId=request.getParameter(\"uniqueId\");");
			outputStream.println("\t\t"+modelName+" mdl"+modelName+"=in"+modelName+"Dao.getById(valueId);");
			outputStream.println("\t\tmodel.addAttribute(\"atr"+modelName+"\", mdl"+modelName+");");
			outputStream.println("\t\treturn \""+modelName+"/addExtraForm\";");
			outputStream.println("\t}");
			
			outputStream.println("");
			outputStream.println("\t@PostMapping(\"/"+appName+"/"+modelName+"/submit\")");
			outputStream.println("\tpublic String submit"+modelName+"(@Valid @ModelAttribute(\"atr"+modelName+"\") "+modelName+" in"+modelName+",");
			outputStream.println("\t\tBindingResult result) {");
			outputStream.println("\t\tif(result.hasErrors()) {");
			outputStream.println("\t\t\treturn \""+modelName+"/addForm\";");
			outputStream.println("\t\t}else {");
			outputStream.println("\t\t\tin"+modelName+"Dao.save(in"+modelName+");");
			outputStream.println("\t\t\treturn \"redirect:/"+appName+"/"+modelName+"/list\";");
			outputStream.println("\t\t}");
			outputStream.println("\t}");
			
			outputStream.println("");
			outputStream.println("\t@GetMapping(\"/"+appName+"/"+modelName+"/list\")");
			outputStream.println("\tpublic String list"+modelName+"(Model model) {");
			outputStream.println("\t\tList<"+modelName+"> list"+modelName+"=in"+modelName+"Dao.getAll();");
			outputStream.println("\t\tmodel.addAttribute(\"list"+modelName+"\", list"+modelName+");");
			outputStream.println("\t\treturn \""+modelName+"/listForm\";");
			outputStream.println("\t}");
			
			outputStream.println("");
			outputStream.println("\t@PostMapping(\"/"+appName+"/"+modelName+"/delete\")");
			outputStream.println("\tpublic String delete"+modelName+"(HttpServletRequest request) {");
			outputStream.println("\t\tString valueId=request.getParameter(\"uniqueId\");");
			outputStream.println("\t\tin"+modelName+"Dao.deleteById(valueId);");
			outputStream.println("\t\treturn \"redirect:/"+appName+"/"+modelName+"/list\";");
			outputStream.println("\t}");
			
			outputStream.println("");
			outputStream.println("\t@PostMapping(\"/"+appName+"/"+modelName+"/edit\")");
			outputStream.println("\tpublic String edit"+modelName+"(HttpServletRequest request, Model model) {");
			outputStream.println("\t\tString valueId=request.getParameter(\"uniqueId\");");
			outputStream.println("\t\t"+modelName+" mdl"+modelName+"=in"+modelName+"Dao.getById(valueId);");
			outputStream.println("\t\tmodel.addAttribute(\"atr"+modelName+"\", mdl"+modelName+");");
			outputStream.println("\t\treturn \""+modelName+"/updateForm\";");
			outputStream.println("\t}");
			
			outputStream.println("");
			outputStream.println("\t@PostMapping(\"/"+appName+"/"+modelName+"/update\")");
			outputStream.println("\tpublic String update"+modelName+"(@Valid @ModelAttribute(\"atr"+modelName+"\") "+modelName+" mdl"+modelName+",");
			outputStream.println("\t\tBindingResult result) {");
			outputStream.println("\t\tif(result.hasErrors()) {");
			outputStream.println("\t\t\treturn \""+modelName+"/updateForm\";");
			outputStream.println("\t\t}else {");
			outputStream.println("\t\t\tin"+modelName+"Dao.update(mdl"+modelName+");");
			outputStream.println("\t\t\treturn \"redirect:/"+appName+"/"+modelName+"/list\";");
			outputStream.println("\t\t}");
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
