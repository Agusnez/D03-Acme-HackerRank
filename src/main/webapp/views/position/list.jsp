<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Search Parade --%>

<display:table name="positions" id="row" requestURI="${requestURI }" pagesize="${pagesize }">
	
	
	<acme:column property="ticker" titleKey="position.ticker" value= "${row.ticker}: "/>
	
	<acme:column property="company" titleKey="position.company" value= "${row.company}: "/>
	
	<acme:column property="title" titleKey="position.title" value= "${row.title} "/>
	
	<acme:column property="description" titleKey="position.description" value="${row.description }" />
	
	<acme:dateFormat titleKey="deadline" value="${row.deadline }" pattern="yyyy/MM/dd" />
	
	<acme:column property="profile" titleKey="position.profile" value= "${row.profile}: "/>
	
	<acme:column property="skills" titleKey="position.skills" value= "${row.skills}: "/>
	
	<acme:column property="technologies" titleKey="position.technologies" value= "${row.technologies}: "/>
	
	<acme:column property="offeredSalary" titleKey="position.offeredSalary" value= "${row.offeredSalary}: "/>
		
	<security:authorize access="hasRole('COMPANY')">
		<acme:column property="finalMode" titleKey="position.finalMode" value="${row.finalMode }" />
	</security:authorize> 

</display:table>

	<security:authorize access="hasRole('COMPANY')">
	<a href="postion/company/create.do"><spring:message code="position.create"/></a>
	</security:authorize>
	
	<acme:button name="back" code="parade.back" onclick="javascript: relativeRedir('welcome/index.do');" />
	
	
<script type="text/javascript">
	var trTags = document.getElementsByTagName("tr");
	for (var i = 1; i < trTags.length; i++) {
	  var tdStatus = trTags[i].children[5];
	  if (tdStatus.innerText == "REJECTED") {
		  trTags[i].style.backgroundColor = "red";
	  } else if (tdStatus.innerText == "ACCEPTED") {
		  trTags[i].style.backgroundColor = "green";
	  } else{
		  trTags[i].style.backgroundColor = "grey";
	  }
	}
</script>