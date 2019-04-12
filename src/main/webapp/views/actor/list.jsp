<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
	<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
	<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h3><spring:message code="actor.hackers" /></h3>
	
	<display:table pagesize="5" name="hackers" id="row" 
requestURI="${requestURI }" >

	<acme:column property="name" titleKey="actor.name" value= "${row.name}: "/>
	
	<acme:column property="surnames" titleKey="actor.surnames" value= "${row.surnames}: "/>
	
	<acme:url href="message/actor/create.do?actorId=${row.id}" code="actor.contact"/>
	
</display:table>
	
<h3><spring:message code="actor.companies" /></h3>
	
		<display:table pagesize="5" name="companies" id="row" 
requestURI="${requestURI }" >

	<acme:column property="name" titleKey="actor.name" value= "${row.name}: "/>
	
	<acme:column property="surnames" titleKey="actor.surnames" value= "${row.surnames}: "/>
	
	<acme:url href="message/actor/create.do?actorId=${row.id}" code="actor.contact"/>
	
</display:table>
	
<h3><spring:message code="actor.administrators" /></h3>
	
	<display:table pagesize="5" name="administrators" id="row" 
requestURI="${requestURI }" >

	<acme:column property="name" titleKey="actor.name" value= "${row.name}: "/>
	
	<acme:column property="surnames" titleKey="actor.surnames" value= "${row.surnames}: "/>
	
	<acme:url href="message/actor/create.do?actorId=${row.id}" code="actor.contact"/>
	
</display:table>
	


	<acme:button name="back" code="actor.back" onclick="javascript: relativeRedir('message/actor/list.do');"/>
