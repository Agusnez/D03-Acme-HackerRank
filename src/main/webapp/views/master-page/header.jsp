<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${banner}" alt="Acme Hacker-Rank Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configuration/administrator/edit.do"><spring:message code="master.page.configuration" /></a></li>
					<li><a href="broadcast/administrator/create.do"><spring:message code="master.page.broadcast" /></a></li>	
					<li><a href="actor/administrator/spammer/list.do"><spring:message code="master.page.spammer" /></a></li>
					<li><a href="actor/administrator/profile/list.do"><spring:message code="master.page.profiles" /></a></li>
					<li><a href="administrator/create.do"><spring:message code="master.page.signUpAdmin" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message	code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="problem/company/list.do"><spring:message code="master.page.list.problem" /></a></li>		
					<li><a href="position/company/list.do"><spring:message code="master.page.company.position.list" /></a></li>
					<li><a href="application/company/list.do"><spring:message code="master.page.hacker.application" /></a></li>
					<li><a href="application/company/listObsoletes.do"><spring:message code="master.page.hacker.applicationObsoletes" /></a></li>

				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HACKER')">
			<li><a class="fNiv"><spring:message	code="master.page.hacker" /></a>
				<ul>
					<li class="arrow"></li>

						<li><a href="curriculum/hacker/list.do"><spring:message code="master.page.curriculum" /></a></li>
						<li><a href="finder/hacker/find.do"><spring:message code="master.page.hacker.finder" /></a></li>
						<li><a href="application/hacker/list.do"><spring:message code="master.page.hacker.application" /></a></li>
						<li><a href="application/hacker/listObsoletes.do"><spring:message code="master.page.hacker.applicationObsoletes" /></a></li>
						

				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a href="register/createCompany.do"><spring:message code="master.page.company.signup" /></a></li>
			<li><a href="register/createHacker.do"><spring:message code="master.page.hacker.signup" /></a></li>
		</security:authorize>
		
		<security:authorize access="permitAll()">
			<li><a href="company/list.do"><spring:message code="master.page.company.list" /></a></li>
			<li><a href="position/list.do"><spring:message code="master.page.position.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li><a href="profile/displayPrincipal.do"><spring:message code="master.page.profile" /></a></li>
					<li><a href="message/actor/list.do"><spring:message code="master.page.message" /> </a></li>
					<security:authorize access="hasRole('HACKER')">
					<li><a href="data/hacker/get.do"><spring:message code="master.page.get.data" /> </a></li>	
					</security:authorize>
					<security:authorize access="hasRole('COMPANY')">
					<li><a href="data/company/get.do"><spring:message code="master.page.get.data" /> </a></li>	
					</security:authorize>				
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

