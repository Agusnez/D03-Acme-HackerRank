<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h3><spring:message code="applicationAccepted" /></h3>

<display:table name="applicationAccepted" id="row" requestURI="${requestURI }" pagesize="5">

	<acme:column property="position.ticker" titleKey="application.position.ticker" value= "${row.position.ticker}: "/>
	
	<acme:column property="answer" titleKey="application.answer" value= "${row.answer}: "/>
	
	<acme:column property="moment" titleKey="application.moment" value= "${row.moment}: "/>
	
	<acme:column property="submitMoment" titleKey="application.submitMoment" value= "${row.submitMoment}: "/>
	
	<acme:column property="curriculum.personalData.statement" titleKey="application.curriculum" value= "${row.curriculum.personalData.statement}: "/>
	
	<display:column>
			<a href="application/hacker/display.do?applicationId=${row.id}"><spring:message code="application.display"/></a>
	</display:column>	
	
</display:table>

<br>

<h3><spring:message code="applicationRejected" /></h3>

<display:table name="applicationRejected" id="row2" requestURI="${requestURI }" pagesize="5">

	<acme:column property="position.ticker" titleKey="application.position.ticker" value= "${row2.position.ticker}: "/>
	
	<acme:column property="answer" titleKey="application.answer" value= "${row2.answer}: "/>
	
	<acme:column property="moment" titleKey="application.moment" value= "${row2.moment}: "/>
	
	<acme:column property="submitMoment" titleKey="application.submitMoment" value= "${row2.submitMoment}: "/>
	
	<acme:column property="curriculum.personalData.statement" titleKey="application.curriculum" value= "${row2.curriculum.personalData.statement}: "/>
	
	<display:column>
			<a href="application/hacker/display.do?applicationId=${row2.id}"><spring:message code="application.display"/></a>
	</display:column>	
	
</display:table>

<br>

<h3><spring:message code="applicationSubmitted" /></h3>

<display:table name="applicationSubmitted" id="row3" requestURI="${requestURI }" pagesize="5">

	<acme:column property="position.ticker" titleKey="application.position.ticker" value= "${row3.position.ticker}: "/>
	
	<acme:column property="answer" titleKey="application.answer" value= "${row3.answer}: "/>
	
	<acme:column property="moment" titleKey="application.moment" value= "${row3.moment}: "/>
	
	<acme:column property="submitMoment" titleKey="application.submitMoment" value= "${row3.submitMoment}: "/>
	
	<acme:column property="curriculum.personalData.statement" titleKey="application.curriculum" value= "${row3.curriculum.personalData.statement}: "/>
	
	<display:column>
			<a href="application/hacker/display.do?applicationId=${row3.id}"><spring:message code="application.display"/></a>
	</display:column>	
	
</display:table>

<br>

<h3><spring:message code="applicationPending" /></h3>

<display:table name="applicationPending" id="row4" requestURI="${requestURI }" pagesize="5">

	<acme:column property="position.ticker" titleKey="application.position.ticker" value= "${row4.position.ticker}: "/>
	
	<acme:column property="answer" titleKey="application.answer" value= "${row4.answer}: "/>
	
	<acme:column property="moment" titleKey="application.moment" value= "${row4.moment}: "/>
	
	<acme:column property="submitMoment" titleKey="application.submitMoment" value= "${row4.submitMoment}: "/>
	
	<acme:column property="curriculum.personalData.statement" titleKey="application.curriculum" value= "${row4.curriculum.personalData.statement}: "/>
	
	<display:column>
			<a href="application/hacker/display.do?applicationId=${row4.id}"><spring:message code="application.display"/></a>
	</display:column>	
	
</display:table>

<br>

	<acme:button name="back" code="application.back" onclick="javascript: relativeRedir('welcome/index.do');" />