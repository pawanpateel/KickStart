<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>PHORM: Start Building Spring App</title>
<link rel="stylesheet" href="/resources/css/effect.css" />
<meta name="description" content="This application is to help you build Spring Boot MVC with Hibernate application
				quickly. Intended users are students and novice developers."/>
<meta name="keywords" content="spring, boot, code, mvc, hibernate, ready, run, readymade, student, dummy, project"/>
</head>
<body>
	<div class="overall">
		<div class="header">PHORM: Start Building Spring App</div>
		<div class="firstPara">
			<p>This application is to help you build Spring Boot MVC with Hibernate application
				quickly. Guidelines for filling the form:
			<ol>
				<li>Application Name- name for your application. It will be
					used to create folder structure.</li>
				<li>User Name- It will be stored as a record of user who
					accepted our terms and conditions.</li>
				<li>Place- It will be stored as a record of user's place who accepted
					our terms and conditions.</li>
				<li>This information is stored for not more than 24 Hrs.</li>
			</ol>
			</p>
		</div>		
		<%String appUrl =response.encodeURL("/phorm/appSave.do");%>
	
		<div class="form">
			<form:form action="<%=appUrl%>" method="POST" modelAttribute="stageOne">
				<form:label path="appName">Application name:</form:label><br/>
				<form:errors path="appName" class="errorMessage"/><br/>
				<form:input type="text" path="appName" 
				maxlength="30" pattern="[A-Za-z_]+" 
				title="Application name can have only alphabets or underscore."/><br /> 
				
				<form:label path="userName">User name:</form:label><br/>
				<form:errors path="userName" class="errorMessage"/><br/>
				<form:input type="text" path="userName" 
				maxlength="30" pattern="[A-Za-z_]+" 
				title="User name can have only alphabets or underscore."/><br />
				
				<form:label path="placeName">Place name:</form:label><br/>
				<form:errors path="placeName" class="errorMessage"/><br/>
				<form:input type="text" path="placeName" 
				maxlength="30" pattern="[A-Za-z_]+" 
				title="Place name can have only alphabets or underscore."/><br />
				<br /> <input type="submit" value="Next">
			</form:form>
		</div>
		<div class="footer">&copy; 2020</div>
	</div>
</body>
</html>