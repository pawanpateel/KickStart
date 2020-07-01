package com.phorm.kStart.util;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class PropFile {
	
	public void createFile(String folderPath) {
		PrintWriter outputStream = null;
		
		try {
			outputStream = new PrintWriter(new FileWriter(folderPath+"/application.properties"));
			outputStream.println("spring.mvc.view.prefix = /WEB-INF/views/");
			outputStream.println("spring.mvc.view.suffix = .jsp");
			outputStream.println("spring.mvc.static-path-pattern=/resources/**");
			outputStream.println("");
			outputStream.println("# Database");
			outputStream.println("db.driver=oracle.jdbc.driver.OracleDriver");
			outputStream.println("db.url=jdbc:oracle:thin:@localhost:1521:xe");
			outputStream.println("db.username=UserId");
			outputStream.println("db.password=Password");
			outputStream.println("");
			outputStream.println("# Hibernate");
			outputStream.println("hibernate.dialect=org.hibernate.dialect.Oracle9iDialect");
			outputStream.println("hibernate.show_sql=true");
			outputStream.println("hibernate.hbm2ddl.auto=update");
			outputStream.println("entitymanager.packagesToScan=com");
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
