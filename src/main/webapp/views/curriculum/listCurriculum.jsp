<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="curriculums" id="row" requestURI="${requestURI }" pagesize="5">
	
	<acme:url href="curriculum/hacker/display.do?curriculumId=${row.id }" code="curriculum.display" />

</display:table>

<a href="curriculum/hacker/create.do"><spring:message code="curriculum.create"/></a>

<acme:button name="back" code="curriculum.back" onclick="javascript: relativeRedir('welcome/index.do');" />