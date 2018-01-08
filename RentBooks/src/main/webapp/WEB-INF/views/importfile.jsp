<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<base href="http://localhost:8080/bookstore/" target="_self">
<link rel="stylesheet" href="resources/css/foundation.css" />
<link rel="stylesheet" href="resources/css/app.css" />
<script src="resources/js/vendor/modernizr.js"></script>
</head>
<body onload="customisedMessage('${customisedMsg}')">
	<section class="contain-to-grid sticky">
		<nav class="top-bar" data-topbar>
			<ul class="title-area">
				<li class="name">
					<h1>
						<c:choose>
							<c:when test="${pageContext.request.userPrincipal.name == null}">
								<a href="welcomePage"><img src="resources/img/logo.jpg"
									width="80%"></a>
							</c:when>
							<c:otherwise>
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<a href="admin"><img src="resources/img/logo.jpg"
										width="80%"></a>
								</sec:authorize>
								<sec:authorize access="hasRole('ROLE_USER')">
									<a href="user"><img src="resources/img/logo.jpg"
										width="80%"></a>
								</sec:authorize>
							</c:otherwise>
						</c:choose>
					</h1>
				</li>
				<li class="toggle-topbar menu icon"><a href="#">Menu</a></li>
			</ul>
			<section class="top-bar-section">
				<ul class="right">
					<c:choose>
						<c:when test="${pageContext.request.userPrincipal.name == null}">
							<li><a href="welcomePage">Home</a></li>
						</c:when>
						<c:otherwise>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<li><a href="admin">Home</a></li>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_USER')">
								<li><a href="user">Home</a></li>
							</sec:authorize>
						</c:otherwise>
					</c:choose>
					<li><a href="store">BookStore</a></li>
					<li><a href="aboutus">AboutUs</a></li>
					<li><a href="#">ContactUs</a></li>
					<li><a href="#">Help</a></li>
				</ul>
				<form:form method="get" action="./search">
					<ul>
						<li class="has-form">
							<div class="row collapse">
								<div class="large-9 small-10 columns">
									<input name="searchText" id="searchbox" type="text"
										placeholder="Search for books" required>
								</div>
								<div class="large-3 small-2 columns">
									<input id="searchbutton" type="submit" value="Search"
										class="alert button expand">
								</div>
							</div>
						</li>
					</ul>
					<div class="small-6 columns" id="checkboxes">
						<input type="checkbox" groupname="group1" name="title"
							value="title"> <label for="title" style="color: white;">Title</label>
						<input type="checkbox" name="author" groupname="group1"
							value="author"><label for="author" style="color: white;">Author</label>
						<input type="checkbox" name="category" groupname="group1"
							value="category"><label for="category"
							style="color: white;">Category</label>
					</div>
				</form:form>
			</section>
			<c:choose>
				<c:when test="${pageContext.request.userPrincipal.name == null}">
					<section id="buttonsI">
						<a href="#" data-reveal-id="logInModal" class="radius button">LogIn&hellip;</a>
						<button onclick="getSubscriptionPlans('register');"
							data-reveal-id="subscriptionModal" class="radius button">Register&hellip;</button>
					</section>
				</c:when>
				<c:otherwise>
					<section id="buttonsI">
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<button href="#" data-dropdown="drop2" aria-controls="drop2"
								aria-expanded="false" class="button dropdown">Controls</button>
							<ul id="drop2" data-dropdown-content class="f-dropdown"
								aria-hidden="true" tabindex="-1">
								<li><a href="./admin/userDetails">User Requests</a></li>
								<li><a href="./admin/getActiveUsers">Active Users</a></li>
								<li><a href="./admin/addupdatedeletebooks">Add/Delete/Update
										Book</a></li>
								<li><a href="./admin/addupdatedeletesub">Add/Delete/Update
										Subscription</a></li>
								<li><a href="javascript:formSubmit()">Upload CSV Feed</a></li>
								<li><a href="javascript:formSubmit()">Generate Report</a></li>
							</ul>
							<button href="#" data-dropdown="drop1" aria-controls="drop1"
								aria-expanded="false" class="button dropdown">Welcome
								Admin!</button>
							<ul id="drop1" data-dropdown-content class="f-dropdown"
								aria-hidden="true" tabindex="-1">
								<li><a href="changePassword">Change Password</a></li>
								<li><a href="javascript:formSubmit()">logout</a></li>
							</ul>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USER')">
							<button href="#" data-dropdown="drop1" aria-controls="drop1"
								aria-expanded="false" class="button dropdown">Welcome
								User!</button>
							<ul id="drop1" data-dropdown-content class="f-dropdown"
								aria-hidden="true" tabindex="-1">
								<li><a href="user/requestStatus">Request Status</a></li>
								<li><a href="user/transactions">Transaction Summary</a></li>
								<li><a href="user/profile">My Profile</a></li>
								<li><a href="user/history">History</a></li>
								<li><a href="changePassword">Change Password</a></li>
								<li><a href="javascript:formSubmit()">logout</a></li>
							</ul>
						</sec:authorize>
						<c:url value="/logout" var="logoutUrl" />
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</form>
					</section>
				</c:otherwise>

			</c:choose>
		</nav>
		<div id="subscriptionModal" class="reveal-modal" data-reveal>
			<div align="center">
				<h3>SUBSCRIPTION PLANS</h3>
			</div>
			<div id="subscriptionContent"></div>
			<a class="close-reveal-modal">&#215;</a>
		</div>
		<div id="logInModal" class="reveal-modal" data-reveal>
			<div class="row">
				<div class="small-12 columns">
					<h2>LogIn</h2>
				</div>
				<form name='loginForm' action="<c:url value='/login' />"
					method='POST' data-abide>
					<div class="small-12 columns">
						<label>Email <input name="email" type="email"
							id="right-label1" placeholder="Enter your email" required
							pattern="^([0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9})$"></label>
						<small class="error">A valid email id is required.</small>
					</div>
					<div class="small-12 columns">
						<label>Password <input name="password" type="password"
							id="right-label2" placeholder="Enter your password" required
							pattern="^.{3,}$"></label><small class="error">A valid
							password is required.</small>
					</div>
					<div class="small-12 columns">
						<input type="submit" class="button left" value="LogIn">
						<ul class="right">
							<li><a href="#" data-reveal-id="forgotPasswordModal">ForgotPassword?</a></li>
						</ul>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
			<a class="close-reveal-modal">&#215;</a>
		</div>
		<div id="forgotPasswordModal" class="reveal-modal" data-reveal>
			<div align="center">
				<h4>Please Enter your Email.</h4>
			</div>
			<div class="row">
				<form name='forgotPasswordForm'
					action="<c:url value='/changepassword' />" method='POST' data-abide>
					<div class="small-12 columns">
						<label>Email <input name="email" type="email"
							id="right-label1" placeholder="Enter your email" required
							pattern="^([0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9})$"></label>
						<small class="error">A valid email id is required.</small>
					</div>
					<div class="small-12 columns">
						<input type="submit" class="button" value="Retrieve Password">
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
			<a class="close-reveal-modal">&#215;</a>
		</div>
	</section>
	<div>
		<sec:authorize access="!hasRole('ROLE_ADMIN')">
			<div class="row">
				<div id="mycart">
					<a href="./viewCart" title="MY CART" class="button right"> <img
						src="resources/svg/fi-shopping-cart.svg" alt="cart">
					</a>
				</div>
				<sec:authorize access="hasRole('ROLE_USER')">
					<div id="myWishList">
						<a href="user/wishList" title="MY WISHLIST" class="button right">
							<img src="resources/svg/fi-heart.svg" alt="wishlist">
						</a>
					</div>
					<div id="userBalanceDiv"></div>
					<div>
						<a id="upgradeUser" onclick="getSubscriptionPlans('update');"
							data-reveal-id="subscriptionModal" data-dropdown="drop"
							class="large success button dropdown left" aria-expanded="false">CHANGE
							SUBSCRIPTION PLAN</a>
					</div>
				</sec:authorize>
			</div>
		</sec:authorize>
	</div>
	<div id="dialogoverlay"></div>
	<div id="dialogbox">
		<div>
			<div id="dialogboxhead"></div>
			<div id="dialogboxbody"></div>
			<div id="dialogboxfoot"></div>
		</div>
	</div>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div class="row">&nbsp;</div>
	</sec:authorize>
	<script src="resources/js/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="resources/js/foundation.min.js"></script>
	<script src="resources/js/foundation/foundation.abide.js"></script>
	<script src="resources/js/commons.js"></script>
	<script src="resources/js/jquery-ui.min.js"></script>
	<script>
		if (!Modernizr.svg) {
			$('img[src*="svg"]').attr('src', function() {
				return $(this).attr('src').replace('.svg', '.png');
			});
		}
		$(document).ready(
				function() {
					$('input[type=checkbox]').click(
							function() {
								var groupName = $(this).attr('groupname');

								if (!groupName)
									return;

								var checked = $(this).is(':checked');

								$(
										"input[groupname='" + groupName
												+ "']:checked").each(
										function() {
											$(this).prop('checked', '');
										});

								if (checked)
									$(this).prop('checked', 'checked');
							});
				});

		$(function() {
			$("#searchbox")
					.autocomplete(
							{
								source : function(request, response) {
									$
											.getJSON(
													"${pageContext.request.contextPath}/getSuggestions",
													{
														term : request.term
													}, response);
								}
							});
		});
	</script>
	<script>
		$(document).foundation();
	</script>
</body>
</html>