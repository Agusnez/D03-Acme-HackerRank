<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="now" class="java.util.Date" />

<jstl:if test="${!AmILogged }">
<form:form action="position/listByFilter.do" modelAttribute="filterForm">

<spring:message code="filter.keyword" />: <form:input path="keyword"/>
<spring:message code="company.commercialName"/>: <form:input path="companyName" />

<acme:submit name="search" code="filter.search"/>

</form:form>
</jstl:if>

<jstl:if test="${AmInFinder }">
<security:authorize access="hasRole('HACKER')">
<form:form action="${requestAction }" modelAttribute="finder"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<acme:textbox path="keyWord" code="filter.keyword" />
	
	<acme:textbox path="minimumSalary" code="finder.minimumSalary" />
	
	<acme:textbox path="maximumSalary" code="finder.maximumSalary" />
	
	<acme:textbox path="maximumDeadline" code="finder.maximumDeadline" />
	
	<input type="submit" name="find" value="<spring:message code="filter.search"/>"/>
	
</form:form> 
</security:authorize>
</jstl:if>

<display:table name="positions" id="row" requestURI="${requestURI }" pagesize="${pagesize }">
	
	
	<acme:column property="ticker" titleKey="position.ticker" value= "${row.ticker}: "/>
	
	<acme:column property="company.commercialName" titleKey="position.company" value= "${row.company.commercialName}: "/>
	
	<acme:column property="title" titleKey="position.title" value= "${row.title} "/>
	
	<acme:column property="description" titleKey="position.description" value="${row.description }" />
	
	<acme:dateFormat titleKey="position.deadline" value="${row.deadline }" pattern="yyyy/MM/dd" />
	
	<acme:column property="profile" titleKey="position.profile" value= "${row.profile}: "/>
	
	<acme:column property="skills" titleKey="position.skills" value= "${row.skills}: "/>
	
	<acme:column property="technologies" titleKey="position.technologies" value= "${row.technologies}: "/>
	
	<acme:column property="offeredSalary" titleKey="position.offeredSalary" value= "${row.offeredSalary}: "/>
		
	<security:authorize access="hasRole('COMPANY')">
		<jstl:if test="${AmInCompanyController}">
		<acme:column property="finalMode" titleKey="position.finalMode" value="${row.finalMode }" />
		
		<acme:url href="position/company/display.do?positionId=${row.id }" code="position.display"/>
		</jstl:if>
	</security:authorize> 
	
	
	<security:authorize access="hasRole('HACKER')">
		<display:column>
			<jstl:if test="${row.deadline > now}">
				<a href="application/hacker/create.do?positionId=${row.id }"> <spring:message code="position.application"/></a>
			</jstl:if>
		</display:column>
	</security:authorize> 
	
	<jstl:if test="${requestURI == 'position/list.do'}">
		<acme:url href="company/display.do?positionId=${row.id }" code="position.company.display"/>
	</jstl:if>

</display:table>

	<jstl:if test="${AmInCompanyController}">
	<security:authorize access="hasRole('COMPANY')">
	<a href="position/company/create.do"><spring:message code="position.create"/></a>
	</security:authorize>
	</jstl:if>
	
	<acme:button name="back" code="position.back" onclick="javascript: relativeRedir('welcome/index.do');" />
	