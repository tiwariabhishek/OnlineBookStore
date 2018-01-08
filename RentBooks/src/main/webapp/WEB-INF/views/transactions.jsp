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
		$('#summaryDescTable').dataTable({
			"iDisplayLength" : 5,
			"aLengthMenu" : [ [ 5, 10, 25, 50, -1 ], [ 5, 10, 25, 50, "All" ] ]
		});
	});
</script>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>BookStore | Transactions</title>
</head>
<body>
	<div class="row panel clearfix">
		<div class="small-12 columns" align="center">
			<h4>TRANSACTION SUMMARY</h4>
		</div>

		<div class="small-12 columns" align="center">
			<ul class="side-nav">
				<li class="divider"></li>
				<li>
					<table width="100%" id="summaryTable">
						<thead>
							<tr>
								<th width="50%"></th>
								<th width="50%"></th>
							</tr>
						</thead>
						<tbody>
							<tr id="totIssuedBooks">
								<td>TOTAL BOOKS ISSUED</td>
								<td>0</td>
							</tr>
							<tr id="totPendingReq">
								<td>TOTAL PENDING REQUESTS</td>
								<td>0</td>
							</tr>
							<tr>
								<td>REMAINING BOOKS IN SUBSCRIPTION</td>
								<td>${activeSubscription.remBooks}</td>
							</tr>
						</tbody>
					</table>
				</li>
			</ul>
		</div>
		<c:if test="${fn:length(requestedBookRequests) gt 0}">
			<div class="small-12 columns" align="center">
				<h4>OVERALL BOOKS TRANSACTION</h4>
			</div>
			<div class="small-12 columns" align="center">
				<ul class="side-nav">
					<li class="divider"></li>
					<li>
						<table width="100%" id="summaryDescTable">
							<thead>
								<tr>
									<th colspan="2" width="20%"><div align="center">Book</div></th>
									<th width="20%">Author</th>
									<th width="10%">Request Date</th>
									<th width="10%">Issued Date</th>
									<th width="10%">Returned Date</th>
									<th width="10%">Last Date</th>
									<th width="20%">Status</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${requestedBookRequests}" var="book"
									varStatus="status">
									<tr>
										<td><a href="store/getBook?bookId=${book.bookId}"> <img
												width=30 height=30
												src="resources/img/${requestedBooks[status.index].imageurl}">
										</a></td>
										<td>${requestedBooks[status.index].title}</td>
										<td>${requestedBooks[status.index].author}</td>
										<td>${book.bookRequestDate}</td>
										<td>${book.bookDeliveryDate}</td>
										<td>${book.bookReturnDate}</td>
										<td>${activeSubscription.planEndDate}</td>
										<td class="bookStatus"><c:choose>
												<c:when test="${fn:contains(book.bookStatus, 'PENDING')}">
													<p style="color: red">${book.bookStatus}</p>
												</c:when>
												<c:otherwise>
													<p style="color: green">${book.bookStatus}</p>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</li>
				</ul>
			</div>
		</c:if>
	</div>
	<script type="text/javascript">
		loadSummary();
	</script>
</body>
</html>