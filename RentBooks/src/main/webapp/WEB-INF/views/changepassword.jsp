<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="importfile.jsp"%>
<!DOCTYPE htm>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BookStore|ChangePassword</title>
</head>
<body>
	<div class="row panel clearfix">
		<div class="small-12 columns" align="center">
			<h4>CHANGE PASSWORD</h4>
		</div>
		<form:form method="POST" action="changepassword">
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label2" class="right">Old
						Password</label>
				</div>
				<div class="small-9 columns">
					<input id="password" name="oldpassword" type="password"
						id="right-label2" placeholder="Enter Password" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label2" class="right">New Password</label>
				</div>
				<div class="small-9 columns">
					<input id="password" name="password" type="password"
						id="right-label2" placeholder="Enter Password" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label3" class="right">Confirm New
						Password</label>
				</div>
				<div class="small-9 columns">
					<input type="password" id="confirmpassword"
						placeholder="Confirm Password" required data-equalto="password">
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns"></div>
				<div class="small-9 columns">
					<input type="submit" class="button right" value="Change Password">
				</div>
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form:form>
	</div>
</body>
</html>