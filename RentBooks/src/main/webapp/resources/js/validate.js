$(function() {

	// Setup form validation on the #register-form element
	$("#signupform").validate({

		// Specify the validation rules
		rules : {
			id : {
				required : true,
				minlength : 3
			},
			firstname : {
				required : true,
				minlength : 3
			},
			email : {
				required : true,
				email : true
			},
			pass : {
				required : true,
				minlength : 5
			},
			ph : {
				digits : true,
				minlength : 10,
				maxlength : 10
			},
			address : {
				required : true,
				minlength : 5
			}
		},

		// Specify the validation error messages
		messages : {
			firstname : "Please enter your valid first name",
			
			password : {
				required : "Please provide a password",
				minlength : "Your password must be at least 5 characters long"
			},
			email : "Please enter a valid email address",
			ph : "Please provide valid phone number",
			address : "Please provide valid address"
		},

		submitHandler : function(form) {
			form.submit();
		}
	});

});