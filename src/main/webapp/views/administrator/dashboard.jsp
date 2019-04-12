<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fieldset>
<legend><spring:message code="dashboard.statscompany" /></legend>
<p><spring:message code="dashboard.statscompany.avg" />: ${avgPC} </p>
<p><spring:message code="dashboard.statscompany.min" />: ${minPC} </p>
<p><spring:message code="dashboard.statscompany.max" />: ${maxPC} </p>
<p><spring:message code="dashboard.statscompany.std" />: ${stdPC} </p>
<br/>
</fieldset>
<br/>

<fieldset>
<legend><spring:message code="dashboard.statshacker" /></legend>
<p><spring:message code="dashboard.statshacker.avg" />: ${avgAH} </p>
<p><spring:message code="dashboard.statshacker.min" />: ${minAH} </p>
<p><spring:message code="dashboard.statshacker.max" />: ${maxAH} </p>
<p><spring:message code="dashboard.statshacker.std" />: ${stdAH} </p>
<br/>
</fieldset>
<br/>
<p><spring:message code="dashboard.topcompanies" /></p>

<display:table name="topC" id="row">
</display:table>

<br/>
<p><spring:message code="dashboard.tophackers" /></p>
<display:table name="topH" id="row">
</display:table>

<br/>

<fieldset>
<legend><spring:message code="dashboard.statssalaries" /></legend>
<p><spring:message code="dashboard.statssalaries.all" />: ${statsS}</p>
<br/>
</fieldset>
<br/>
<fieldset>
<legend><spring:message code="dashboard.positions" /></legend>
<p><spring:message code="dashboard.positions.best" />: ${bP.title} </p>
<p><spring:message code="dashboard.positions.worst" />: ${wP.title} </p>
<br/>
</fieldset>
