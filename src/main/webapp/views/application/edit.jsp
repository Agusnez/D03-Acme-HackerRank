<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="application/hacker/edit.do" modelAttribute="application">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="position" />

	<acme:textarea code="application.answer" path="answer" obligatory="false"/>
	
	<form:label path="curriculum">
		<spring:message code="application.curriculum"></spring:message>
	</form:label>
	<form:select path="curriculum" >
		<form:options items="${curriculumsMap}"/>
	</form:select>
	<form:errors cssClass="error" path="curriculum"></form:errors>
	<br />	
	
	<acme:submit name="save" code="application.save" />
	
	<acme:cancel code="application.cancel" url="application/hacker/list.do" />


</form:form>  