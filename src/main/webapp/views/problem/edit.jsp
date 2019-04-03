
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="problem/company/edit.do" modelAttribute="parade">
	
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<acme:textbox path="title" code="problem.title" obligatory="true"/>
	
	<acme:textbox path="statement" code="problem.statement" obligatory="true"/>
	
	<acme:textbox path="hint" code="problem.hint" obligatory="true"/>
	
	<acme:textbox path="attachments" code="problem.attachments" obligatory="true"/>
	  	
  	<acme:choose path="finalMode" code="problem.finalMode" value1="true" value2="false" label1="Final" label2="No Final" />
	
	<acme:choose path="position" code="problem.position" value1="true" value2="false" label1="Final" label2="No Final" />
	
	
	<form:label path="position">
		<spring:message code="problem.position" />:
	</form:label>
	<form:select path="position" >	
		<form:options items="${positions}" itemValue="id" itemLabel="position" />
	</form:select>
	<form:errors cssClass="error" path="position" />
	<br />
	
	
	<acme:submit name="save" code="problem.save" />	

	<acme:cancel code="parade.cancel" url="problem/company/list.do" />
	
	<jstl:if test="${problem.id != 0}">
		<acme:button name="delete" code="problem.delete" onclick="javascript: relativeRedir('problem/company/delete.do?problemId=${problem.id }');" />
	</jstl:if>	
	

</form:form>    