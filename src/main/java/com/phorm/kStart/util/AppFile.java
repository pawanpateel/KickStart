package com.phorm.kStart.util;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.springframework.context.annotation.Scope;


@Scope("prototype")
public class AppFile {
	public void createFile(String folderPath, String appName, String userName, String placeName) {
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileWriter(folderPath+"/"+appName+"Application.java"));
			outputStream.println("package com."+appName+";");
			outputStream.println("");
			outputStream.println("import org.springframework.boot.SpringApplication;");
			outputStream.println("import org.springframework.boot.autoconfigure.SpringBootApplication;");
			outputStream.println("import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;");
			outputStream.println("");
			outputStream.println("@SpringBootApplication(exclude=HibernateJpaAutoConfiguration.class)");
			outputStream.println("public class "+appName+"Application {");
			outputStream.println("");
			outputStream.println("\tpublic static void main(String[] args) {");
			outputStream.println("\t\tSpringApplication.run("+appName+"Application.class, args);");
			outputStream.println("\t}");
			outputStream.println("");
			outputStream.println("}");
			outputStream.close();
			outputStream = new PrintWriter(new FileWriter("/pumd/visitor.txt", true));
			outputStream.println(appName+"::"+userName+"::"+placeName);
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
