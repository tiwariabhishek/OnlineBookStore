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
		$('#userbookhistory').dataTable({
			"iDisplayLength" : 5,
			"aLengthMenu" : [ [ 5, 10, 25, 50, -1 ], [ 5, 10, 25, 50, "All" ] ]
		});
	});
</script>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>BookStore | BOOKREQHISTORY</title>
</head>
<body>
	<div class="row panel clearfix">
		<div id="userHistoryDiv">
			<div class="small-12 columns" align="center">
				<h4>BOOK REQUEST HISTORY</h4>
			</div>
			<div class="small-12 columns" align="center">
				<ul class="side-nav">
					<li class="divider"></li>
					<li>
						<table width="100%" id="userbookhistory">
							<thead>
								<tr>
									<th colspan="2" width="30%"><div align="center">Book</div></th>
									<th width="20%">BOOK REQUEST DATE</th>
									<th width="20%">BOOK DELIVERY DATE</th>
									<th width="20%">BOOK RETURN DATE</th>
									<th width="10%">BOOK STATUS</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bookrequest}" var="bookrequest"
									varStatus="status">
									<tr>
										<td><a
											href="store/getBook?bookId=${books[status.index].bookId}">
												<img width=30 height=30
												src="resources/img/${books[status.index].imageurl}">
										</a></td>
										<td>${books[status.index].title}</td>
										<td>${bookrequest.bookRequestDate}</td>
										<td>${bookrequest.bookDeliveryDate}</td>
										<td>${bookrequest.bookReturnDate}</td>
										<td><c:choose>
												<c:when
													test="${fn:contains(bookrequest.bookStatus, 'PENDING')}">
													<p style="color: red">${bookrequest.bookStatus}</p>
												</c:when>
												<c:otherwise>
													<p style="color: green">${bookrequest.bookStatus}</p>
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