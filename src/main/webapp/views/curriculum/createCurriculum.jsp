<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="curriculum/hacker/edit.do" modelAttribute="curriculum">
	
	<acme:textbox path="statement" code="curriculum.statement" obligatory="true"/>
	
	<acme:textbox path="phone" code="curriculum.phone" obligatory="true"/>
	
	<acme:textbox path="linkGitHubProfile" code="curriculum.linkGitHubProfile" obligatory="true"/>
	
	<acme:textbox path="linkLinkedInProfile" code="curriculum.linkLinkedInProfile" obligatory="true"/>
  	
	<acme:submit name="save" code="curriculum.save" />	

	<acme:cancel code="curriculum.back" url="curriculum/hacker/list.do" />

</form:form>