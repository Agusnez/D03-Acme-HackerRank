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


<acme:display code="application.moment" property="${application.moment} "/>
	
<acme:display code="application.status" property="${application.status} "/>
	
<acme:display code="application.answer" property="${application.answer} "/>

<acme:display code="application.submitMoment" property="${application.submitMoment} "/>
	
<acme:display code="application.position.ticker" property="${application.position.ticker} "/>
	
<acme:display code="application.curriculum" property="${application.curriculum.personalData.statement} "/>

<security:authorize access="hasRole('HACKER')">
	<acme:button name="back" code="application.back" onclick="javascript: relativeRedir('application/hacker/list.do');" />
</security:authorize>

<security:authorize access="hasRole('COMPANY')">
	<acme:button name="back" code="application.back" onclick="javascript: relativeRedir('application/company/list.do');" />
</security:authorize>