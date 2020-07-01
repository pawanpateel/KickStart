package com.phorm.kStart.util;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class ConfigFile {

	public void createFile(String folderPath,  String appName) {
		PrintWriter outputStream = null;

		try {
			outputStream = new PrintWriter(new FileWriter(folderPath+"/DBConfiguration.java"));
			outputStream.println("package com."+appName+".config;");
			outputStream.println("");
			outputStream.println("import java.util.Properties;");
			outputStream.println("");
			outputStream.println("import javax.sql.DataSource;");
			outputStream.println("");
			outputStream.println("import org.springframework.beans.factory.annotation.Value;");
			outputStream.println("import org.springframework.context.annotation.Bean;");
			outputStream.println("import org.springframework.context.annotation.Configuration;");
			outputStream.println("import org.springframework.jdbc.datasource.DriverManagerDataSource;");
			outputStream.println("import org.springframework.orm.hibernate5.HibernateTransactionManager;");
			outputStream.println("import org.springframework.orm.hibernate5.LocalSessionFactoryBean;");
			outputStream.println("import org.springframework.transaction.annotation.EnableTransactionManagement;");
			outputStream.println("");
			outputStream.println("@EnableTransactionManagement");
			outputStream.println("@Configuration");
			outputStream.println("public class DBConfiguration {");
			outputStream.println("");
			outputStream.println("\t@Value(\"${db.driver}\")");
			outputStream.println("\tprivate String DRIVER;");
			outputStream.println("");
			outputStream.println("\t@Value(\"${db.password}\")");
			outputStream.println("\tprivate String PASSWORD;");
			outputStream.println("");
			outputStream.println("\t@Value(\"${db.url}\")");
			outputStream.println("\tprivate String URL;");
			outputStream.println("");
			outputStream.println("\t@Value(\"${db.username}\")");
			outputStream.println("\tprivate String USERNAME;");
			outputStream.println("");
			outputStream.println("\t@Value(\"${hibernate.dialect}\")");
			outputStream.println("\tprivate String DIALECT;");
			outputStream.println("");
			outputStream.println("\t@Value(\"${hibernate.show_sql}\")");
			outputStream.println("\tprivate String SHOW_SQL;");
			outputStream.println("");
			outputStream.println("\t@Value(\"${hibernate.hbm2ddl.auto}\")");
			outputStream.println("\tprivate String HBM2DDL_AUTO;");
			outputStream.println("");
			outputStream.println("\t@Value(\"${entitymanager.packagesToScan}\")");
			outputStream.println("\tprivate String PACKAGES_TO_SCAN;");
			outputStream.println("");
			outputStream.println("\t@Bean");
			outputStream.println("\tpublic DataSource dataSource() {");
			outputStream.println("\t\tDriverManagerDataSource dataSource = new DriverManagerDataSource();");
			outputStream.println("\t\tdataSource.setDriverClassName(DRIVER);");
			outputStream.println("\t\tdataSource.setUrl(URL);");
			outputStream.println("\t\tdataSource.setUsername(USERNAME);");
			outputStream.println("\t\tdataSource.setPassword(PASSWORD);");
			outputStream.println("\t\treturn dataSource;");
			outputStream.println("\t}");
			outputStream.println("");
			outputStream.println("\t@Bean");
			outputStream.println("\tpublic LocalSessionFactoryBean sessionFactory() {");
			outputStream.println("\t\tLocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();");
			outputStream.println("\t\tsessionFactory.setDataSource(dataSource());");
			outputStream.println("\t\tsessionFactory.setPackagesToScan(PACKAGES_TO_SCAN);");
			outputStream.println("\t\tProperties hibernateProperties = new Properties();");
			outputStream.println("\t\thibernateProperties.put(\"hibernate.dialect\", DIALECT);");
			outputStream.println("\t\thibernateProperties.put(\"hibernate.show_sql\", SHOW_SQL);");
			outputStream.println("\t\thibernateProperties.put(\"hibernate.hbm2ddl.auto\", HBM2DDL_AUTO);");
			outputStream.println("\t\tsessionFactory.setHibernateProperties(hibernateProperties);");
			outputStream.println("");
			outputStream.println("\t\treturn sessionFactory;");
			outputStream.println("\t}");
			outputStream.println("");
			outputStream.println("\t@Bean");
			outputStream.println("\tpublic HibernateTransactionManager transactionManager() {");
			outputStream.println("\t\tHibernateTransactionManager transactionManager = new HibernateTransactionManager();");
			outputStream.println("\t\ttransactionManager.setSessionFactory(sessionFactory().getObject());");
			outputStream.println("\t\treturn transactionManager;");
			outputStream.println("\t}");
			outputStream.println("");
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
