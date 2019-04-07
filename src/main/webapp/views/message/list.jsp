<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="messages" id="row" requestURI="${requestURI }" pagesize="5">

	<acme:column property="sender.name" titleKey="message.sender" value= "${row.sender.name}: "/>
	
	<acme:column property="recipient.name" titleKey="message.recipient" value= "${row.recipient.name}: "/>
	
	<acme:column property="moment" titleKey="message.moment" value= "${row.moment}: "/>
	
	<acme:column property="subject" titleKey="message.subject" value= "${row.subject}: "/>
	
	<acme:column property="body" titleKey="message.body" value= "${row.body}: "/>
	
	<acme:column property="priority" titleKey="message.priority" value= "${row.priority}: "/>
	
	<acme:column property="tags" titleKey="message.tags" value= "${row.tags}: "/>
	
	<display:column>
			<a href="message/actor/display.do?messageId=${row.id}"><spring:message code="message.display"/></a>
	
	</display:column>	
</display:table>


<display:table name="messagesDELETE" id="row2" requestURI="${requestURI }" pagesize="5">

	<acme:column property="sender.name" titleKey="message.sender" value= "${row2.sender.name}: "/>
	
	<acme:column property="recipient.name" titleKey="message.recipient" value= "${row2.recipient.name}: "/>
	
	<acme:column property="moment" titleKey="message.moment" value= "${row2.moment}: "/>
	
	<acme:column property="subject" titleKey="message.subject" value= "${row2.subject}: "/>
	
	<acme:column property="body" titleKey="message.body" value= "${row2.body}: "/>
	
	<acme:column property="priority" titleKey="message.priority" value= "${row2.priority}: "/>
	
	<acme:column property="tags" titleKey="message.tags" value= "${row2.tags}: "/>
	
	<display:column>
			<a href="message/actor/display.do?messageId=${row2.id}"><spring:message code="message.display"/></a>
	</display:column>
	
</display:table>

<display:table name="messagesSYSTEM" id="row3" requestURI="${requestURI }" pagesize="5">

	<acme:column property="sender.name" titleKey="message.sender" value= "${row3.sender.name}: "/>
	
	<acme:column property="recipient.name" titleKey="message.recipient" value= "${row3.recipient.name}: "/>
	
	<acme:column property="moment" titleKey="message.moment" value= "${row3.moment}: "/>
	
	<acme:column property="subject" titleKey="message.subject" value= "${row3.subject}: "/>
	
	<acme:column property="body" titleKey="message.body" value= "${row3.body}: "/>
	
	<acme:column property="priority" titleKey="message.priority" value= "${row3.priority}: "/>
	
	<acme:column property="tags" titleKey="message.tags" value= "${row3.tags}: "/>
	
	<display:column>
			<a href="message/actor/display.do?messageId=${row3.id}"><spring:message code="message.display"/></a>
	</display:column>
	
</display:table>

	<acme:button name="back" code="message.back" onclick="javascript: relativeRedir('welcome/index.do');" />