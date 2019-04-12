<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<security:authorize access="hasRole('COMPANY')">

<acme:display code="problem.title" property="${problem.title }" />

<acme:display code="problem.statement" property="${problem.statement }" />

<acme:display code="problem.hint" property="${problem.hint}" />

<spring:message code="problem.attachments" />: <jstl:out value="${problem.attachments }"/><br>

<acme:display code="problem.finalMode" property="${problem.finalMode}" />

<spring:message code="problem.positions" />:  
	<c:forEach items="${problem.positions}" var="item">
    	${item.title} /
	</c:forEach>
<br>



<jstl:if test="${!problem.finalMode }">
	<acme:button name="edit" code="problem.edit" onclick="javascript: relativeRedir('problem/company/edit.do?problemId=${problem.id }');" />
</jstl:if>

<acme:button name="back" code="problem.back" onclick="javascript: relativeRedir('problem/company/list.do');" />

</security:authorize>

