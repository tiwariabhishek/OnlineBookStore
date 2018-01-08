<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="importfile.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BookStore|UpdateProfile</title>
</head>
<body>
	<div class="row">
		<div class="small-12 columns">
			<h4>Update Profile</h4>
		</div>
	</div>
	<form:form method="POST" action="user/updateprofile">

		<div class="row">
			<div class="small-12">
				<div class="panel clearfix">
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label1" class="right">Name</label>
						</div>
						<div class="small-9 columns">
							<input name="name" type="text" id="right-label1"
								placeholder="Enter Name" value="${user.name}" required>
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label5" class="right">Address</label>
						</div>
						<div class="small-9 columns">
							<input name="address" type="text" id="right-label5"
								placeholder="Enter Address" value="${user.address}" required>
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label6" class="right">Mobile</label>
						</div>
						<div class="small-9 columns">
							<input name="mobile" type="text" id="right-label6"
								placeholder="Enter Mobile Number" value="${user.mobile}"
								required>
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label9" class="right">Country</label>
						</div>
						<div class="small-9 columns">
							<input name="country" type="text" id="right-label9"
								placeholder="Enter Country" value="${user.country}" required>
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label10" class="right">State</label>
						</div>
						<div class="small-9 columns">
							<input name="state" type="text" id="right-label10"
								placeholder="Enter State" value="${user.state}" required>
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label11" class="right">City</label>
						</div>
						<div class="small-9 columns">
							<input name="city" type="text" id="right-label11"
								placeholder="Enter City" value="${user.city}" required>
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label12" class="right">Zipcode</label>
						</div>
						<div class="small-9 columns">
							<input name="zipcode" type="text" id="right-label12"
								placeholder="Enter Zipcode" value="${user.zipcode}" required>
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns"></div>
						<div class="small-9 columns">
							<input type="submit" class="button right" value="Update Profile">
						</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>