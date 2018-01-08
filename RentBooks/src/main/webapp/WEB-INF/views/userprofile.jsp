<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="importfile.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BookStore|UserProfile</title>
</head>
<body>
	<div class="row">
		<div class="small-12 small-centered columns panel" id="bookdetailsdiv">
			<table width="100%">
				<thead>
					<tr>
						<th colspan="2" width="100%">User Profile</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Name</td>
						<td>${user.name}</td>
					</tr>
					<tr>
						<td>Email</td>
						<td>${user.email}</td>
					</tr>
					<tr>
						<td>Gender</td>
						<td>${user.gender}</td>
					</tr>
					<tr>
						<td>Mobile</td>
						<td>${user.mobile}</td>
					</tr>
					<tr>
						<td>Address</td>
						<td>${user.address}</td>
					</tr>
					<tr>
						<td>Date Of Birth</td>
						<td>${user.dob}</td>
					</tr>
					<tr>
						<td>Country</td>
						<td>${user.country}</td>
					</tr>
					<tr>
						<td>State</td>
						<td>${user.state}</td>
					</tr>
					<tr>
						<td>City</td>
						<td>${user.city}</td>
					</tr>
					<tr>
						<td>Zipcode</td>
						<td>${user.zipcode}</td>
					</tr>
					<sec:authorize access="hasRole('ROLE_USER')">
						<tr>
							<td><a href="user/getprofileforupdate"
								class="button [tiny small large]">Update Profile</a>
						</tr>
					</sec:authorize>
				</tbody>
			</table>
		</div>
	</div>
	<sec:authorize access="hasRole('ROLE_USER')">
		<div class="row">
			<div class="small-12 small-centered columns panel">
				<p style="color: red" align="center">Your Subscription Ends in
					${activeSubscriptionDTO.remDays} Days.</p>
			</div>
		</div>
	</sec:authorize>
</body>
</html>