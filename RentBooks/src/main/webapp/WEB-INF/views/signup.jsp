<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="importfile.jsp"%>
<!DOCTYPE htm>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration</title>
<script src="resources/js/foundation/foundation.abide.js"></script>
</head>
<body>
	<div class="row panel clearfix">
		<div class="small-12 columns" align="center">
			<h4>USER REGISTRATION</h4>
		</div>
		<div class="row">
			<form id="signupform" method="POST" action="./register" data-abide>
				<div class="small-12 columns">
					<input name="planId" type="hidden" id="planId"
						placeholder="Enter PlanId" />
				</div>
				<div class="small-12 columns">
					<label>Name <input name="name" type="text"
						id="right-label1" placeholder="Enter Name" required
						pattern="alpha" /></label> <small class="error">Enter a name
						containing only alphabets</small>
				</div>
				<div class="small-12 columns">
					<label>Password <input id="password" name="password"
						type="password" placeholder="Enter Password" required
						pattern="^.{8,}$" /></label> <small class="error">Password must
						be atleast 8 character long.</small>
				</div>
				<div class="small-12 columns">
					<label>Confirm Password <input type="password"
						id="confirmpassword" placeholder="Confirm Password" required
						data-equalto="password" /></label><small class="error">Hey, the
						passwords must match!</small>
				</div>
				<div class="small-12 columns">
					<label>Email <input name="email" type="email"
						id="right-label4" placeholder="Enter Email" required
						data-abide-validator="myCustomEmailValidator" /></label> <small
						id="emailError" class="error">A valid email id is
						required.</small>
				</div>
				<div class="small-12 columns">
					<label>Address <input name="address" type="text"
						id="right-label5" placeholder="Enter Address" required /></label><small
						class="error">Please enter your address.</small>
				</div>
				<div class="small-12 columns">
					<label>Mobile <input name="mobile" type="text"
						id="right-label6" placeholder="Enter Mobile Number" required
						pattern="^\d{10}$" /></label><small class="error">Please enter a
						valid mobile number.</small>
				</div>
				<div class="small-12 columns">
					<label>Gender</label> <label> <input type="radio"
						name="gender" value="male" id="male" /> Male
					</label> <label> <input type="radio" name="gender" value="female"
						id="female" /> Female
					</label>
				</div>
				<div class="small-12 columns">
					<label>Date Of Birth <input name="dob" type="date"
						id="right-label" placeholder="Enter Date Of Birth" required /></label><small
						class="error">Please select your date of birth.</small>
				</div>
				<div class="small-12 columns">
					<label>Country <input name="country" type="text"
						id="right-label9" placeholder="Enter Country" required /></label> <small
						class="error">Please enter country name.</small>
				</div>
				<div class="small-12 columns">
					<label>State <input name="state" type="text"
						id="right-label10" placeholder="Enter State" required /></label><small
						class="error">Please enter state name.</small>
				</div>
				<div class="small-12 columns">
					<label>City <input name="city" type="text"
						id="right-label11" placeholder="Enter City" required /></label><small
						class="error">Please enter city name.</small>
				</div>
				<div class="small-12 columns">
					<label>Zipcode <input name="zipcode" type="text"
						id="right-label12" placeholder="Enter Zipcode" required
						pattern="^\d{6}$" /></label><small class="error">Please enter a
						valid zipcode.</small>
				</div>
				<div class="small-12 columns">
					<input type="submit" class="button right" value="Register Now" />
				</div>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
		</div>
	</div>
	<script type="text/javascript">
		var url = window.location.href;
		var index = url.indexOf('=');
		var planId = url.substring(index + 1);
		if (parseInt(planId) == planId) {
			document.getElementById("planId").value = planId;
		} else {
			var index = url.indexOf('bookstore');
			url = url.substring(0, index + 10);
			window.location.href = url;
		}
		$(document)
				.foundation(
						{
							abide : {
								validators : {
									myCustomEmailValidator : function(el,
											required, parent) {

										if (/^([0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9})$/
												.test(el.value)) {
											var result = true;
											var newurl = window.location.href;
											var index = newurl
													.indexOf('bookstore');
											newurl = newurl.substring(0,
													index + 10);
											$
													.ajax({
														url : newurl
																+ "isAlreadyRegistered?email="
																+ el.value,
														async : false,
														dataType : "json",
														success : function(data) {
															result = data;
														}
													});
											if (result == false) {
												document
														.getElementById('emailError').innerText = "Email Already registered";

											}
											return result;
										} else {

											document
													.getElementById('emailError').innerText = "Not a valid email address";
											return false;
										}
										return true;
									}
								}

							}

						});
	</script>
</body>
</html>