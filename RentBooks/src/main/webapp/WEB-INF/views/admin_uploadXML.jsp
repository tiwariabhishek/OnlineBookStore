<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="importfile.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ADD|UPDATE|DELETE PLAN</title>
</head>
<body>
	<div class="row">
		<div class="small-12 columns" align="center">
			<h4>ADD|UPDATE|DELETE SUBSCRIPTION PLANS</h4>
		</div>

		<div class="small-12 columns panel" align="center">

			<form:form method="POST"
				action="./admin/savexml?${_csrf.parameterName}=${_csrf.token} "
				enctype="multipart/form-data">
				<input name="xmlFile" type="file" />
				<input type="submit" class="button left" value="UPLOAD" />
			</form:form>
		</div>
	</div>
</body>
</html>