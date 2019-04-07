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

<h3><spring:message code="curriculum.personalData" /></h3>

<acme:display property="${curriculum.personalData.fullName}" code="curriculum.fullName" />

<acme:display property="${curriculum.personalData.statement}" code="curriculum.statement" />

<acme:display property="${curriculum.personalData.phone}" code="curriculum.phone" />

<acme:display property="${curriculum.personalData.linkGitHubProfile}" code="curriculum.linkGitHubProfile" />

<acme:display property="${curriculum.personalData.linkLinkedInProfile}" code="curriculum.linkLinkedInProfile" />

<br/>
<br/>

<h3><spring:message code="curriculum.positionData" /></h3>

<display:table name="curriculum.positionDatas" pagesize="5" id="row1">

	<acme:column property="title" titleKey="curriculum.title" value= "${row1.title} "/>
	
	<acme:column property="description" titleKey="curriculum.description" value= "${row1.description} "/>
	
	<acme:column property="startDate" titleKey="curriculum.startDate" value= "${row1.startDate} "/>
	
	<acme:column property="endDate" titleKey="curriculum.endDate" value= "${row1.endDate} "/>
	
	
</display:table>

<br/>

<a href="positionData/hacker/create.do?curriculumId=${curriculum.id }"><spring:message code="curriculum.create"/></a>

<br/>
<br/>

<h3><spring:message code="curriculum.educationData" /></h3>

<display:table name="curriculum.educationDatas" pagesize="5" id="row2">

	<acme:column property="degree" titleKey="curriculum.degree" value= "${row2.degree} "/>
	
	<acme:column property="institution" titleKey="curriculum.institution" value= "${row2.institution} "/>
	
	<acme:column property="mark" titleKey="curriculum.mark" value= "${row2.mark} "/>
	
	<acme:column property="startDate" titleKey="curriculum.startDate" value= "${row2.startDate} "/>
	
	<acme:column property="endDate" titleKey="curriculum.endDate" value= "${row2.endDate} "/>
	
	
</display:table>

<br/>
<br/>

<h3><spring:message code="curriculum.miscellaneousData" /></h3>

<display:table name="curriculum.miscellaneousDatas" pagesize="5" id="row3">

	<acme:column property="attachments" titleKey="curriculum.attachments" value= "${row3.attachments} "/>
	
</display:table>
