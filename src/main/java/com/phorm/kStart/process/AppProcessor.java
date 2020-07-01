package com.phorm.kStart.process;


import org.springframework.context.annotation.Scope;

import java.io.File;


@Scope("prototype")
public class AppProcessor {

	public String getAppName(String aplName) {
		File appFiles= new File("/pumd/"+aplName);
		int i=1;
		if(appFiles.exists()) {
			while(appFiles.exists()) {
				appFiles= new File("/pumd/"+aplName+i);
				i++;
			}	    		
			i--;		
			return aplName+i;
		}else {
			return aplName;
		}
	}


}
