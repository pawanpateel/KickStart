<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="cora" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.ArrayList"%>
<%@ page session="true" %>
<html>
    <head>
        <title>PHORM: Add Entity Classes</title>
<link rel="stylesheet" href="/resources/css/effect.css" />
<script src="/resources/js/model.js"></script>
    </head>
    <body>
        <div class="overall">
        <div class="header">PHORM: Add Entity Classes</div>
        <div class="firstPara">
        <p>This application is to help you build spring application quickly. Guidelines for filling the form:
            <ol>
                <li>Entity Class- Add entity or model classes.</li>
            </ol>
        </p>
        </div>
        <div class="form">
  <br/>
  <%String mdlUrl =response.encodeURL("/phorm/modelSave.do");%>
  <input type="submit" value="Add Entity Class" onclick="addModel();">
  <form:form action="<%=mdlUrl%>" method="post" modelAttribute="stageTwo">
        <div id="listModel">
        	<form:label path="models[0].modelName">Name of Entity/Model Classes:</form:label><br/>
        	<cora:choose>
	       	 	<cora:when test="${!empty stageTwo.models}">
	        		<cora:forEach begin="0" end="${stageTwo.models.size()-1}" var="indo">
						<form:errors path="models[${indo}].modelName" class="errorMessage"/><br/>
						<form:input type="text" path="models[${indo}].modelName" 
						maxlength="30" pattern="[A-Za-z_]+" onmouseout="makeFirstChar('models${indo}.modelName')"
						title="Model name can have only alphabets or underscore."/><div id="delModel${indo}" class="buttonStyle" onclick="dlte(${indo})">Delete</div><br />             	
	           	 	</cora:forEach>
	            </cora:when>
	            <cora:otherwise>
	            	<form:errors path="models[0].modelName" class="errorMessage"/><br/>
					<form:input type="text" path="models[0].modelName" 
					maxlength="30" pattern="[A-Za-z_]+" onmouseout="makeFirstChar('models0.modelName')"
					title="Model name can have only alphabets or underscore."/><div id="delModel${indo}" class="buttonStyle" onclick="dlte(0)">Delete</div><br />
	            </cora:otherwise>            
            </cora:choose>
        </div>
        <input type="submit" value="Next">
    </form:form>
        </div>
        <div class="footer">&copy; 2020</div>
        </div>
    </body>
</html>