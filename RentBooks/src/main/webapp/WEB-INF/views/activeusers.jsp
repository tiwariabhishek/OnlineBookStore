<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="importfile.jsp"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<link rel="stylesheet" href="resources/css/dataTables.foundation.css" />
<script type="text/javascript"
	src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="//cdn.datatables.net/plug-ins/9dcbecd42ad/integration/foundation/dataTables.foundation.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#activeUsersTable').dataTable({
			"iDisplayLength" : 5,
			"aLengthMenu" : [ [ 5, 10, 25, 50, -1 ], [ 5, 10, 25, 50, "All" ] ]
		});
	});
</script>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>BookStore | Welcome</title>
</head>
<body>
	<div class="row panel clearfix">
		<div class="row">
			<div class="small-12 columns" align="center">
				<h4>ACTIVE USERS</h4>
			</div>
			<div class="small-12 columns" align="center">
				<ul class="side-nav">
					<li class="divider"></li>
					<li>
						<table width="100%" id="activeUsersTable">
							<thead>
								<tr>
									<th width="20%"><div align="center">NAME</div></th>
									<th width="20%">EMAIL</th>
									<th width="20%">PLAN START DATE</th>
									<th width="20%">PLAN END DATE</th>
									<th width="20%">REMAINING BOOKS</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${activeusers}" var="users" varStatus="status">
									<tr>
										<td>${users.name}</td>
										<td><a href="admin/getProfile?email=${users.email}">${users.email}</a></td>
										<td>${activeSubscriptionDTO[status.index].planStartDate}</td>
										<td>${activeSubscriptionDTO[status.index].planEndDate}</td>
										<td>${activeSubscriptionDTO[status.index].remBooks}</td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
