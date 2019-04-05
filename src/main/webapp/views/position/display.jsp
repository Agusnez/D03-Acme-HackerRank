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


<h2><acme:display code="position.ticker" property="${position.ticker }" /></h2>

<acme:display code="position.title" property="${position.title }" />

<acme:display code="position.description" property="${position.description}" />

<spring:message code="position.deadline" />: <fmt:formatDate value="${position.deadline }" pattern="yyyy/MM/dd" />

<acme:display code="position.profile" property="${position.profile}" />

<acme:display code="position.skills" property="${position.skills}" />

<acme:display code="position.technologies" property="${position.technologies}" />

<acme:display code="position.offeredSalary" property="${position.offeredSalary}" />

<security:authorize access="hasRole('COMPANY')">
<jstl:if test="${!position.finalMode }">
	<acme:button name="edit" code="position.edit" onclick="javascript: relativeRedir('position/company/edit.do?positionId=${position.id }');" />
</jstl:if>
</security:authorize>

<acme:button name="back" code="position.back" onclick="javascript: relativeRedir('position/company/list.do');" />



