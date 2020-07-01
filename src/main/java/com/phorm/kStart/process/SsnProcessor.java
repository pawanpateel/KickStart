package com.phorm.kStart.process;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class SsnProcessor {

	

	public boolean healthCheck(HttpServletRequest request) {
		Object o= request.getSession(false);
		if(o!=null) {
			return true;
		}else {
			return false;
		}
	}

	public void displaySessionTimeOut(HttpServletResponse resonse) {		
		try {
			resonse.sendRedirect("/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
