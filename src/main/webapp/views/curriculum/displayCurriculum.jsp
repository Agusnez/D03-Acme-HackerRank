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

<fieldset>
<h3><spring:message code="curriculum.personalData" /></h3>

<acme:display property="${curriculum.personalData.fullName}" code="curriculum.fullName" />

<acme:display property="${curriculum.personalData.statement}" code="curriculum.statement" />

<acme:display property="${curriculum.personalData.phone}" code="curriculum.phone" />

<acme:display property="${curriculum.personalData.linkGitHubProfile}" code="curriculum.linkGitHubProfile" />

<acme:display property="${curriculum.personalData.linkLinkedInProfile}" code="curriculum.linkLinkedInProfile" />
</fieldset>
<br/>
<br/>

<fieldset>
<h3><spring:message code="curriculum.positionData" /></h3>

<display:table name="curriculum.positionDatas" pagesize="5" id="row1">

	<acme:column property="title" titleKey="curriculum.title" value= "${row1.title} "/>
	
	<acme:column property="description" titleKey="curriculum.description" value= "${row1.description} "/>
	
	<acme:column property="startDate" titleKey="curriculum.startDate" value= "${row1.startDate} "/>
	
	<acme:column property="endDate" titleKey="curriculum.endDate" value= "${row1.endDate} "/>
	
	<acme:url href="positionData/hacker/edit.do?positionRecordId=${row1.id }" code="curriculum.edit"/>
	
	<acme:url href="positionData/hacker/delete.do?positionId=${row1.id }&curriculumId=${curriculum.id }" code="curriculum.delete"/>
	
	
</display:table>

<br/>

<a href="positionData/hacker/create.do?curriculumId=${curriculum.id }"><spring:message code="curriculum.create"/></a>
</fieldset>

<br/>
<br/>
<fieldset>
<h3><spring:message code="curriculum.educationData" /></h3>

<display:table name="curriculum.educationDatas" pagesize="5" id="row2">

	<acme:column property="degree" titleKey="curriculum.degree" value= "${row2.degree} "/>
	
	<acme:column property="institution" titleKey="curriculum.institution" value= "${row2.institution} "/>
	
	<acme:column property="mark" titleKey="curriculum.mark" value= "${row2.mark} "/>
	
	<acme:column property="startDate" titleKey="curriculum.startDate" value= "${row2.startDate} "/>
	
	<acme:column property="endDate" titleKey="curriculum.endDate" value= "${row2.endDate} "/>
	
	<acme:url href="educationData/hacker/edit.do?educationRecordId=${row2.id }" code="curriculum.edit"/>
	
	<acme:url href="educationData/hacker/delete.do?educationId=${row2.id }&curriculumId=${curriculum.id }" code="curriculum.delete"/>
	
</display:table>

<br/>

<a href="educationData/hacker/create.do?curriculumId=${curriculum.id }"><spring:message code="curriculum.create"/></a>
</fieldset>

<br/>
<br/>

<fieldset>
<h3><spring:message code="curriculum.miscellaneousData" /></h3>

<display:table name="curriculum.miscellaneousDatas" pagesize="5" id="row3">

	<acme:column property="attachments" titleKey="curriculum.attachments" value= "${row3.attachments} "/>
	
	<acme:url href="miscellaneousData/hacker/edit.do?miscellaneousRecordId=${row3.id }" code="curriculum.edit"/>
	
	<acme:url href="miscellaneousData/hacker/delete.do?miscellaneousId=${row3.id }&curriculumId=${curriculum.id }" code="curriculum.delete"/>
	
</display:table>
<br/>

<a href="miscellaneousData/hacker/create.do?curriculumId=${curriculum.id }"><spring:message code="curriculum.create"/></a>
</fieldset>
<br/>
<br/>
<a href="curriculum/hacker/delete.do?curriculumId=${curriculum.id }"><spring:message code="curriculum.deleteall"/></a>

<acme:button name="back" code="curriculum.back" onclick="javascript: relativeRedir('curriculum/hacker/list.do');"/>

