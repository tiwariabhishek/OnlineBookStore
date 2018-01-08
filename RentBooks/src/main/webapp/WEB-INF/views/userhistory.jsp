<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		$('#userhistory').dataTable({
			"iDisplayLength" : 5,
			"aLengthMenu" : [ [ 5, 10, 25, 50, -1 ], [ 5, 10, 25, 50, "All" ] ]
		});
	});
</script>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>BookStore | HISTORY</title>
</head>
<body>
	<div class="row panel clearfix">
		<div id="userHistoryDiv">
			<div class="small-12 columns" align="center">
				<h4>SUBSCRIPTION HISTORY</h4>
			</div>
			<div class="small-12 columns" align="center">
				<ul class="side-nav">
					<li class="divider"></li>
					<li>
						<table width="100%" id="userhistory">
							<thead>
								<tr>
									<th width="20%"><div align="center">PLAN START DATE</div></th>
									<th width="20%">PLAN END DATE</th>
									<th width="20%">DURATION</th>
									<th width="10%">MAXIMUM BOOKS</th>
									<th width="10%">PLAN COST</th>
									<th width="20%"><div align="center">STATUS</div></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${activesubscription}"
									var="activesubscription" varStatus="status">
									<tr>
										<td>${activesubscription.planStartDate}</td>
										<td>${activesubscription.planEndDate}</td>
										<td>${subscription[status.index].planDuration}</td>
										<td>${subscription[status.index].maxBooks}</td>
										<td>${subscription[status.index].planCost}</td>
										<td><c:choose>
												<c:when test="${status.count==1}">
													<a
														href="user/bookRequestHistory?historyId=${activesubscription.historyId}"><div
															align="center" style="color: green">
															<u>ACTIVE</u>
														</div></a>
												</c:when>
												<c:otherwise>
													<a
														href="user/bookRequestHistory?historyId=${activesubscription.historyId}"><div
															align="center" style="color: blue">
															<u>INACTIVE</u>
														</div></a>
												</c:otherwise>
											</c:choose></td>
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