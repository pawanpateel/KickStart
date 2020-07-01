<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cora" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.phorm.kStart.model.Application"%>
<%@ page import="com.phorm.kStart.model.Modal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@ page session="true"%>
<html>
<head>
<title>PHORM: Add Fields to Entity Class</title>
<link rel="stylesheet" href="/resources/css/effect.css" />
<script src="/resources/js/field.js"></script>
</head>
<body>
	<div class="overall">
		<div class="header">PHORM: Add Fields to Entity Class</div>
		<div class="firstPara">
			<p>This application is to help you build spring application
				quickly. Guidelines for filling the form:
			<ol>
				<li>Data Type- Select the data type.</li>
				<li>Fields- Add fields to entity class.</li>
				<li>Relation type- Select one of the relation types. This is required only if data type is entity or model class.</li>
				<li>ID- check this box to mark the field as primary key.</li>
				<li>Nullable- check this box to mark the field as nullable. If
					left unchecked, this field value can not be null.</li>
				<li>Can Be Empty- check this box to mark that this field
					accepts empty value. If left unchecked, this field value can not
					empty.</li>
				<li>Min- provide minimum value. If left blank, this constraint
					will not evaluated.</li>
				<li>Max- provide maximum value. If left blank, this constraint
					will not evaluated.</li>
			</ol>
			</p>
		</div>
		
		<%String fldUrl =response.encodeURL("/phorm/fieldSave.do");		
		String domelList="";
		ArrayList<String> listType=new ArrayList<String>(Arrays.asList(
				"int", "long", "float", "double", "boolean", "String", "BigDecimal", "BigInteger", "byte", "short", "char"				
				));
		ArrayList<String> numType=new ArrayList<String>(Arrays.asList(
				"int", "long", "float", "double", "BigDecimal", "BigInteger", "byte", "short"				
				));
		if(null!= request.getSession(false) && null!=request.getSession(false).getAttribute("appDetails")){
	           Application appInfo=  (Application)request.getSession(false).getAttribute("appDetails");  
	           if(!appInfo.getModels().isEmpty()){
	   				for(Modal m:appInfo.getModels()){
	   					domelList=domelList+","+m.getModelName();
	   				}
	        	   
	   			}
	    }
		if(domelList.startsWith(",")){
			domelList=domelList.replaceFirst(",","");
		}
		%>
		<cora:set var="modelIndex" value="${0}"/>		
		<cora:set var="incBy" value="${1}"/>
		<div class="form">
			<br />
			<form:form action="<%=fldUrl%>" method="post" modelAttribute="stageThree">				
				<div id="listField">
					<cora:if test="${!empty stageThree.models}">
        				<cora:forEach items="${stageThree.models}" var="model">
							<div class="divAddField">
								<div id="mdlName${modelIndex}" onclick="addField(${modelIndex});">Add Fields For ${model.modelName}</div>
								<div class="fltRight" id="${modelIndex}ShowHide"
								onclick="hideModel('${modelIndex}ShowHide')">Hide Fields</div>
							</div>            	
							<form:hidden path="models[${modelIndex}].modelName"/>
							<div id="${modelIndex}ListField">							
							<form:label path="models[${modelIndex}].fields[0].dataType">Data Type</form:label>
							<form:label path="models[${modelIndex}].fields[0].fieldName">Name</form:label>
							<form:label path="models[${modelIndex}].fields[0].relationType">Relation Type</form:label>
							<form:label path="models[${modelIndex}].fields[0].isPrimary">Constraints</form:label>
								<cora:set var="fieldIndex" value="${0}"/>
								<cora:choose>
									<cora:when test="${!empty model.fields}">										
										<cora:forEach items="${model.fields}" var="field">
											<cora:set var="filedName" value="${'fld'}${field.dataType}"/>
											<cora:choose>
													<cora:when test="${field.dataType!='int' && field.dataType!= 'long' && field.dataType!= 'float' && field.dataType!= 'double' 
													&& field.dataType!= 'boolean' && field.dataType!= 'String' && field.dataType!= 'BigDecimal' && field.dataType!= 'BigInteger' 
													&& field.dataType!= 'byte' && field.dataType!= 'short' && field.dataType!= 'char' && field.dataType!='Date' && field.dataType!=''
													&& field.dataType!=null && field.fieldName==filedName}">
														<div id="${modelIndex}Group${fieldIndex}" style="display:none">
													</cora:when>
													<cora:otherwise>
														<div id="${modelIndex}Group${fieldIndex}">
													</cora:otherwise>
											</cora:choose>
												<form:errors path="models[${modelIndex}].fields[${fieldIndex}].*" class="errorMessage"/><br/>
												
												<form:select
													path="models[${modelIndex}].fields[${fieldIndex}].dataType" 
													onchange="hidConstraint(${modelIndex},${fieldIndex})">
													<form:option value="int" selected="selected"/>
													<form:option value="long" />
													<form:option value="float" />
													<form:option value="double" />
													<form:option value="String" />
													<form:option value="Date"/>
													<form:option value="boolean" />
													<form:option value="BigDecimal"/>
													<form:option value="BigInteger"/>
													<form:option value="byte"/>
													<form:option value="short"/>
													<form:option value="char"/>
													<cora:forEach items="${stageThree.models}" var="typMod">
														<cora:if test="${!model.modelName.equals(typMod.modelName)}">
															<form:option value="${typMod.modelName}"/>
														</cora:if>
													</cora:forEach>
												</form:select>
												
												
												<form:input type="text" path="models[${modelIndex}].fields[${fieldIndex}].fieldName"
												onmouseout="makeFirstChar('models${modelIndex}.fields${fieldIndex}.fieldName')"
												pattern="[A-Za-z0-9_]+" maxlength="30"
												title="Field name can have only alphabets, numbers or underscore." />
												
												<cora:choose>
													<cora:when test="${field.dataType=='int' || field.dataType== 'long' || field.dataType== 'float' || field.dataType== 'double' 
													|| field.dataType== 'boolean' || field.dataType== 'String' || field.dataType== 'BigDecimal' || field.dataType== 'BigInteger' 
													|| field.dataType== 'byte' || field.dataType== 'short' || field.dataType== 'char' || field.dataType=='Date' || field.dataType==''
													|| field.dataType==null}">
														<form:select path="models[${modelIndex}].fields[${fieldIndex}].relationType" 
														onchange="reflex(${modelIndex},${fieldIndex})" disabled="true">
								        					<form:option value="onetoone" selected="selected"/>
								        					<form:option value="onetomany"/>
															<form:option value="manytoone"/>
								        					<form:option value="manytomany"/>
		    											</form:select>
													</cora:when>
													<cora:otherwise>
														<form:select path="models[${modelIndex}].fields[${fieldIndex}].relationType" 
														onchange="reflex(${modelIndex},${fieldIndex})">
								        					<form:option value="onetoone" selected="selected"/>
								        					<form:option value="onetomany"/>
															<form:option value="manytoone"/>
								        					<form:option value="manytomany"/>
		    											</form:select>
													</cora:otherwise>
												</cora:choose>
												
		    									<cora:choose>
													<cora:when test="${ field.dataType!='int' && field.dataType!= 'long' && field.dataType!= 'float' && field.dataType!= 'double' 
													&& field.dataType!= 'boolean' && field.dataType!= 'String' && field.dataType!= 'BigDecimal' && field.dataType!= 'BigInteger' 
													&& field.dataType!= 'byte' && field.dataType!= 'short' && field.dataType!= 'char' && field.dataType!='' && field.dataType!=null}">
														<form:checkbox path="models[${modelIndex}].fields[${fieldIndex}].isPrimary" disabled="true"/>ID
		    											<form:checkbox path="models[${modelIndex}].fields[${fieldIndex}].isNullable" disabled="true"/>Nullable
		    											<form:checkbox path="models[${modelIndex}].fields[${fieldIndex}].canBeEmpty" disabled="true"/>Can Be Empty
													</cora:when>
													<cora:otherwise>
														<form:checkbox path="models[${modelIndex}].fields[${fieldIndex}].isPrimary"/>ID
		    											<form:checkbox path="models[${modelIndex}].fields[${fieldIndex}].isNullable"/>Nullable
		    											<form:checkbox path="models[${modelIndex}].fields[${fieldIndex}].canBeEmpty"/>Can Be Empty
													</cora:otherwise>													
												</cora:choose>
		    									
		    									<cora:choose>
													<cora:when test="${field.dataType!='int' && field.dataType!= 'long' && field.dataType!= 'float' && field.dataType!= 'double' 
													&& field.dataType!= 'BigDecimal' && field.dataType!= 'BigInteger' && field.dataType!= 'byte' && field.dataType!= 'short' 
													&& field.dataType!='' && field.dataType!=null}">
														<form:input type="text" path="models[${modelIndex}].fields[${fieldIndex}].minValue"
														pattern="[0-9.]+" maxlength="30" size="1"
														title="Minimum value can have only numbers and underscore." disabled="true"/>Min
														
														<form:input type="text" path="models[${modelIndex}].fields[${fieldIndex}].maxValue"
														pattern="[0-9.]+" maxlength="30" size="1"
														title="Maximum value can have only numbers and underscore."  disabled="true"/>Max
													</cora:when>
													<cora:otherwise>
														<form:input type="text" path="models[${modelIndex}].fields[${fieldIndex}].minValue"
														pattern="[0-9.]+" maxlength="30" size="1"
														title="Minimum value can have only numbers and underscore."/>Min
														
														<form:input type="text" path="models[${modelIndex}].fields[${fieldIndex}].maxValue"
														pattern="[0-9.]+" maxlength="30" size="1"
														title="Maximum value can have only numbers and underscore."/>Max
													</cora:otherwise>
												</cora:choose>
		    									
		    									
												
												<div class="buttonStyle" id="${model.modelName}delField${fieldIndex}" onclick="dlte(${modelIndex},${fieldIndex})">Delete</div><br/>
											</div>
											<cora:set var="fieldIndex" value="${fieldIndex+incBy}"/>
										</cora:forEach>
									</cora:when>
									<cora:otherwise>
										
											<div id="${modelIndex}Group0">
												
												<form:errors path="models[${modelIndex}].fields[0].*" class="errorMessage"/><br/>
												
												<form:select
													path="models[${modelIndex}].fields[0].dataType" 
													onchange="hidConstraint(${modelIndex},0)">
													<form:option value="int" selected="selected"/>
													<form:option value="long" />
													<form:option value="float" />
													<form:option value="double" />
													<form:option value="String" />
													<form:option value="Date"/>
													<form:option value="boolean" />
													<form:option value="BigDecimal"/>
													<form:option value="BigInteger"/>
													<form:option value="byte"/>
													<form:option value="short"/>
													<form:option value="char"/>
													<cora:forEach items="${stageThree.models}" var="typMod">
														<cora:if test="${!model.modelName.equals(typMod.modelName)}">
															<form:option value="${typMod.modelName}"/>
														</cora:if>
													</cora:forEach>
												</form:select>
												
												
												<form:input type="text" path="models[${modelIndex}].fields[0].fieldName"
												onmouseout="makeFirstChar('models[${modelIndex}].fields[0].fieldName')"
												pattern="[A-Za-z0-9_]+" maxlength="30"
												title="Field name can have only alphabets, numbers or underscore." />
												
												<form:select path="models[${modelIndex}].fields[0].relationType" 
												onchange="reflex(${modelIndex},0)" disabled="true">
						        					<form:option value="onetoone" selected="selected"/>
						        					<form:option value="onetomany"/>
													<form:option value="manytoone"/>
						        					<form:option value="manytomany"/>
		    									</form:select>
		    									
		    									<form:checkbox path="models[${modelIndex}].fields[0].isPrimary"/>ID
		    									<form:checkbox path="models[${modelIndex}].fields[0].isNullable"/>Nullable
		    									<form:checkbox path="models[${modelIndex}].fields[0].canBeEmpty"/>Can Be Empty
		    									
		    									<form:input type="text" path="models[${modelIndex}].fields[0].minValue"
												pattern="[0-9.]+" maxlength="30" size="1"
												title="Minimum value can have only numbers and underscore." />Min
												
												<form:input type="text" path="models[${modelIndex}].fields[0].maxValue"
												pattern="[0-9.]+" maxlength="30" size="1"
												title="Maximum value can have only numbers and underscore." />Max
												
												<div class="buttonStyle" id="${modelIndex}delField0" onclick="dlte(${modelIndex},0)">Delete</div><br/>
											</div>
									</cora:otherwise>
								</cora:choose>
							</div>
							
							<cora:set var="modelIndex" value="${modelIndex+incBy}"/>
           	 			</cora:forEach>
            		</cora:if>
				</div>
				<input type="hidden" id="mdoleList" value="<%=domelList%>" >
				<input	type="submit" value="Next">
			</form:form>
		</div>
		<div class="footer">&copy; 2020</div>
	</div>
</body>
</html>