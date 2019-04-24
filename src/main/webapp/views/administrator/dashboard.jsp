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
<p><spring:message code="dashboard.statssalaries.avg" />: ${avgS}</p>
<p><spring:message code="dashboard.statssalaries.min" />: ${minS}</p>
<p><spring:message code="dashboard.statssalaries.max" />: ${maxS}</p>
<p><spring:message code="dashboard.statssalaries.std" />: ${stdS}</p>
<br/>
</fieldset>
<br/>
<fieldset>
<legend><spring:message code="dashboard.positions" /></legend>
<p><spring:message code="dashboard.positions.best" />:<c:if test="${empty bP}">
    -
</c:if><c:if test="${not empty bP}">
    ${bP.title}</c:if></p>
<p><spring:message code="dashboard.positions.worst" />: <c:if test="${empty wP}">
    -
</c:if><c:if test="${not empty wP}">
    ${wP.title}
</c:if></p>
<br/>
</fieldset>

<br/>
<fieldset>
<legend><spring:message code="dashboard.statscv" /></legend>
<p><spring:message code="dashboard.statscv.avg" />: ${avgS}</p>
<p><spring:message code="dashboard.statscv.min" />: ${minCH}</p>
<p><spring:message code="dashboard.statscv.max" />: ${maxCH}</p>
<p><spring:message code="dashboard.statscv.std" />: ${stdCH}</p>
<br/>
</fieldset>

<br/>
<fieldset>
<legend><spring:message code="dashboard.statsfinder" /></legend>
<p><spring:message code="dashboard.statsfinder.avg" />: ${avgRF}</p>
<p><spring:message code="dashboard.statsfinder.min" />: ${minRF}</p>
<p><spring:message code="dashboard.statsfinder.max" />: ${maxRF}</p>
<p><spring:message code="dashboard.statsfinder.std" />: ${stdRF}</p>
<br/>
</fieldset>

<br/>
<fieldset>
<legend><spring:message code="dashboard.statsfinder" /></legend>
<p><spring:message code="dashboard.statsfinder.avg" />: ${avgRF}</p>
<p><spring:message code="dashboard.statsfinder.min" />: ${minRF}</p>
<p><spring:message code="dashboard.statsfinder.max" />: ${maxRF}</p>
<p><spring:message code="dashboard.statsfinder.std" />: ${stdRF}</p>
<p><spring:message code="dashboard.statsfinder.ratio" />: ${ratioEF}</p>
<br/>
</fieldset>
<br/>

<acme:button name="back" code="back" onclick="javascript: relativeRedir('welcome/index.do');" />

