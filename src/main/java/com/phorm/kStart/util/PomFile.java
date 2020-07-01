package com.phorm.kStart.util;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class PomFile {

	public void createFile(String folderPath,  String appName) {
		PrintWriter outputStream = null;

		try {
			outputStream = new PrintWriter(new FileWriter(folderPath+"/pom.xml"));
			outputStream.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			outputStream.println("<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			outputStream.println("\txsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\">");
			outputStream.println("\t<modelVersion>4.0.0</modelVersion>");
			outputStream.println("\t<parent>");
			outputStream.println("\t\t<groupId>org.springframework.boot</groupId>");
			outputStream.println("\t\t<artifactId>spring-boot-starter-parent</artifactId>");
			outputStream.println("\t\t<version>2.2.6.RELEASE</version>");
			outputStream.println("\t\t<relativePath/>");
			outputStream.println("\t</parent>");
			outputStream.println("\t<groupId>com</groupId>");
			outputStream.println("\t<artifactId>"+appName+"</artifactId>");
			outputStream.println("\t<version>0.0.1-SNAPSHOT</version>");
			outputStream.println("\t<name>"+appName+"</name>");
			outputStream.println("\t<description>Project for Spring Boot</description>");
			outputStream.println("");
			outputStream.println("\t<properties>");
			outputStream.println("\t\t<java.version>1.8</java.version>");
			outputStream.println("\t</properties>");
			outputStream.println("");
			outputStream.println("\t<dependencies>");
			outputStream.println("\t\t<dependency>");
			outputStream.println("\t\t\t<groupId>com.oracle</groupId>");
			outputStream.println("\t\t\t<artifactId>ojdbc8</artifactId>");
			outputStream.println("\t\t\t<version>18.4</version>");
			outputStream.println("\t\t</dependency>");
			outputStream.println("\t\t<dependency>");
			outputStream.println("\t\t\t<groupId>org.apache.tomcat.embed</groupId>");
			outputStream.println("\t\t\t<artifactId>tomcat-embed-jasper</artifactId>");
			outputStream.println("\t\t\t<scope>provided</scope>");
			outputStream.println("\t\t</dependency>");
			outputStream.println("\t\t<dependency>");
			outputStream.println("\t\t\t<groupId>javax.servlet</groupId>");
			outputStream.println("\t\t\t<artifactId>jstl</artifactId>");
            outputStream.println("\t\t</dependency>");
			outputStream.println("\t\t<dependency> ");
			outputStream.println("\t\t\t<groupId>org.springframework.boot</groupId> ");
			outputStream.println("\t\t\t<artifactId>spring-boot-starter-data-jpa</artifactId> ");
			outputStream.println("\t\t</dependency> ");
			outputStream.println("\t\t<dependency>");
			outputStream.println("\t\t\t<groupId>org.springframework.boot</groupId>");
			outputStream.println("\t\t\t<artifactId>spring-boot-starter-web</artifactId>");
			outputStream.println("\t\t</dependency>");
			outputStream.println("\t\t<dependency>");
			outputStream.println("\t\t\t<groupId>org.springframework.boot</groupId>");
			outputStream.println("\t\t\t<artifactId>spring-boot-devtools</artifactId>");
			outputStream.println("\t\t\t<scope>runtime</scope>");
			outputStream.println("\t\t\t<optional>true</optional>");
			outputStream.println("\t\t</dependency>");
			outputStream.println("\t\t<dependency>");
			outputStream.println("\t\t\t<groupId>org.springframework.boot</groupId>");
			outputStream.println("\t\t\t<artifactId>spring-boot-starter-test</artifactId>");
			outputStream.println("\t\t\t<scope>test</scope>");
			outputStream.println("\t\t\t<exclusions>");
			outputStream.println("\t\t\t\t<exclusion>");
			outputStream.println("\t\t\t\t\t<groupId>org.junit.vintage</groupId>");
			outputStream.println("\t\t\t\t\t<artifactId>junit-vintage-engine</artifactId>");
			outputStream.println("\t\t\t\t</exclusion>");
			outputStream.println("\t\t\t</exclusions>");
			outputStream.println("\t\t</dependency>");
			outputStream.println("\t</dependencies>");
			outputStream.println("");
			outputStream.println("\t<build>");
			outputStream.println("\t\t<plugins>");
			outputStream.println("\t\t\t<plugin>");
			outputStream.println("\t\t\t\t<groupId>org.springframework.boot</groupId>");
			outputStream.println("\t\t\t\t<artifactId>spring-boot-maven-plugin</artifactId>");
			outputStream.println("\t\t\t</plugin>");
			outputStream.println("\t\t</plugins>");
			outputStream.println("\t</build>");
			outputStream.println("");
			outputStream.println("</project>");
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
