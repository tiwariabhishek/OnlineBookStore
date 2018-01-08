<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="importfile.jsp"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="resources/css/dataTables.foundation.css" />
<script type="text/javascript"
	src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="//cdn.datatables.net/plug-ins/9dcbecd42ad/integration/foundation/dataTables.foundation.js"></script>
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				$('#requestTable').dataTable({
				    "iDisplayLength": 5,
				    "aLengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]]
				    });
			} );
		</script>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				$('#recommendedBooksTable').dataTable({
				    "iDisplayLength": 5,
				    "aLengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]]
				    });
			} );
		</script>
</sec:authorize>
<title>BookStore | USER REQUESTS</title>
</head>
<body>
	<sec:authorize access="hasRole('ROLE_USER')">
		<!--For login user -->
		<div class="row">
			<div id="issuedBookDiv">
				<div class="small-12 columns" align="center">
					<h4>ISSUED BOOKS</h4>
				</div>
				<div class="small-12 columns" align="center">
					<ul class="side-nav">
						<li class="divider"></li>
						<li>
							<table width="100%" id="issuedBookTable">
								<thead>
									<tr>
										<th width="20%"><div align="center">BOOK</div></th>
										<th width="20%">CATEGORY</th>
										<th width="20%">AUTHOR</th>
										<th width="10%">REQUEST DATE</th>
										<th width="10%">ISSUED DATE</th>
										<th width="20%"><div align="center">ACTION</div></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${issuedBookRequests}" var="book"
										varStatus="status">
										<tr id="${book.requestId}">
											<td><a href="store/getBook?bookId=${book.bookId}">
													<div align="center">
														${issuedBooks[status.index].title}</div>
											</a></td>
											<td>${issuedBooks[status.index].category}</td>
											<td>${issuedBooks[status.index].author}</td>
											<td>${book.bookRequestDate}</td>
											<td>${book.bookDeliveryDate}</td>
											<td><a
												onclick="returnBook(${book.requestId},${book.bookId},'${issuedBooks[status.index].title}','${issuedBooks[status.index].category}','${issuedBooks[status.index].author}','${book.bookRequestDate}')"><div
														align="center">RETURN</div></a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</li>
					</ul>
				</div>
			</div>
			<div id="pendingBookDiv">
				<div class="small-12 columns" align="center">
					<h4>PENDING BOOK REQUESTS</h4>
				</div>

				<div class="small-12 columns" align="center">
					<ul class="side-nav">
						<li class="divider"></li>
						<li>
							<table width="100%" id="pendingBookReqTable">
								<thead>
									<tr>
										<th width="20%"><div align="center">BOOK</div></th>
										<th width="20%">CATEGORY</th>
										<th width="20%">AUTHOR</th>
										<th width="10%">REQUEST DATE</th>
										<th width="30%"><div align="center">ACTION</div></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pendingBookRequests}" var="book"
										varStatus="status">
										<tr id="${book.requestId}">
											<td><a href="store/getBook?bookId=${book.bookId}"><div
														align="center">${pendingBooks[status.index].title}</div> </a></td>
											<td>${pendingBooks[status.index].category}</td>
											<td>${pendingBooks[status.index].author}</td>
											<td>${book.bookRequestDate}</td>
											<td><a
												onclick="cancelBook(${book.requestId},${book.bookId},'${pendingBooks[status.index].title}','${pendingBooks[status.index].category}','${pendingBooks[status.index].author}','${book.bookRequestDate}','${book.bookDeliveryDate}')">
													<c:choose>
														<c:when test="${fn:contains(book.bookStatus, 'RETURN')}">
															<div align="center" style="color: red">CANCEL
																RETURN REQUEST</div>
														</c:when>
														<c:otherwise>
															<div align="center" style="color: red">CANCEL BOOK
																REQUEST</div>
														</c:otherwise>
													</c:choose>
											</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="row panel clearfix">
			<div class="small-12 columns">
				<h4 align="center">RECOMMENDED BOOKS</h4>
			</div>
			<div class="row" data-equalizer>
				<table id="recommendedBooksTable">
					<thead>
						<tr>
							<th></th>
						</tr>
					</thead>
					<c:forEach items="${recommendedBooks}" var="book">
						<tr>
							<td>
								<div class="small-12 columns panel" data-equalizer-watch>
									<div class="small-2 columns">
										<a class="th [radius]" role="button"
											href="store/getBook?bookId=${book.bookId}"> <img
											width=100 height=100 src="resources/img/${book.imageurl}">
										</a>
										<div>
											<h4>$${book.price}</h4>
											<h5>${book.title}</h5>
											<h6 class="subheader">By: ${book.author}</h6>
										</div>
									</div>
									<div class="small-6 columns">
										<h2>
											<small>${book.description}</small>
										</h2>
									</div>
									<div class="small-4 columns">
										<c:choose>
											<c:when test="${book.noOfCopies gt 0}">
												<div id="loadCartImg${book.bookId}">
													<button onclick="addToCart(${book.bookId});"
														title="ADD TO CART"
														class="button [tiny small large] right">
														<img src="resources/svg/fi-shopping-cart.svg" alt="cart">
													</button>
												</div>
											</c:when>
											<c:otherwise>
												<a title="OUT OF STOCK"
													class="button [tiny small large] disabled right"><img
													src="resources/svg/fi-shopping-cart.svg" alt="cart"></a>
											</c:otherwise>
										</c:choose>
										<div id="loadWishlistImg${book.bookId}">
											<a onclick="addToWishlist(${book.bookId})"
												title="ADD TO WISHLIST"
												class="button [tiny small large] right"><img
												src="resources/svg/fi-heart.svg" alt="wishlist"></a>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<script>
		removeEmptyTables('CHECK');
		</script>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<!--For login admin -->
		<div class="row">
			<div class="small-12 columns" align="center">
				<h4>USER REQUESTS</h4>
			</div>
			<div class="small-12 columns" align="center">
				<input id="bookrequest" type="checkbox" groupname="group2"
					name="bookrequest" value="bookrequest"> <label
					for="bookrequest" style="color: black;"><b>BOOK REQUEST</b></label>
				<input type="checkbox" id="returnrequest" name="returnrequest"
					groupname="group2" value="returnrequest"><label
					for="returnrequest" style="color: black;"><b>RETURN
						REQUEST</b></label> <input id="delivered" type="checkbox" name="delivered"
					groupname="group2" value="delivered"><label for="delivered"
					style="color: black;"><b>DELIVERED</b></label> <input
					type="checkbox" id="returned" name="returned" groupname="group2"
					value="returned"><label for="returned"
					style="color: black;"><b>RETURNED</b></label>
			</div>
			<div class="row panel clearfix">
				<div class="small-12 columns" align="center">
					<ul class="side-nav">
						<li class="divider"></li>
						<li>
							<table width="100%" id="requestTable">
								<thead>
									<tr>
										<th width="20%">USER NAME</th>
										<th width="10%"><div align="center">EMAIL</div></th>
										<th width="10%"><div align="center">BOOK</div></th>
										<th width="10%">REQUEST DATE</th>
										<th width="10%">DELIVERED DATE</th>
										<th width="10%">RETURNED DATE</th>
										<th width="20%">LAST DATE</th>
										<th width="10%">STATUS</th>
									</tr>
								</thead>
								<tbody id="fbody">
									<c:forEach items="${activeBookRequestDTO}"
										var="activeBookRequestDTO" varStatus="status">
										<tr id="${activeBookRequestDTO.requestId}">
											<td>${userEntityDTO[status.index].name}</td>
											<td><a
												href="admin/getProfile?email=${userEntityDTO[status.index].email}">${userEntityDTO[status.index].email}</a></td>
											<td><a
												href="store/getBook?bookId=${bookEntityDTO[status.index].bookId}">${bookEntityDTO[status.index].title}</a></td>
											<td>${activeBookRequestDTO.bookRequestDate}</td>
											<td class="delDate">${activeBookRequestDTO.bookDeliveryDate}</td>
											<td class="retDate">${activeBookRequestDTO.bookReturnDate}</td>
											<td>${activeSubscriptionDTO[status.index].planEndDate}</td>
											<td class="status"><c:choose>
													<c:when
														test="${fn:contains(activeBookRequestDTO.bookStatus, 'PENDING')}">

														<a href="#"
															data-dropdown="drop12${activeBookRequestDTO.requestId}"><div
																style="color: red">
																${activeBookRequestDTO.bookStatus}</div></a>
														<ul id="drop12${activeBookRequestDTO.requestId}"
															class="f-dropdown" data-dropdown-content tabindex="-1">
															<li><a
																onclick="approveUserRequest(${activeBookRequestDTO.requestId},'${userEntityDTO[status.index].email}')">APPROVE</a></li>
															<c:choose>
																<c:when
																	test="${fn:contains(activeBookRequestDTO.bookStatus, 'PENDING BOOK')}">
																	<li><a
																		onclick="declineUserRequest(${activeBookRequestDTO.requestId},'${userEntityDTO[status.index].email}')">DECLINE</a></li>
																</c:when>
															</c:choose>
														</ul>
													</c:when>
													<c:otherwise>
														<p style="color: green">${activeBookRequestDTO.bookStatus}</p>
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
		<script>
				$('#bookrequest').change(function() {
					if (this.checked) {
						var jo = $("#fbody").find("tr");
					    jo.hide();

					    jo.filter(function (i, v) {
					        var $t = $(this);
					            if ($t.is(":contains('PENDING BOOK')")) {
					                return true;
					            }
					        return false;
					    }).show();
					}else{
						location.reload();
					}
				});
				$('#returnrequest').change(function() {
					if (this.checked) {
						var jo = $("#fbody").find("tr");
					    jo.hide();

					    jo.filter(function (i, v) {
					        var $t = $(this);
					            if ($t.is(":contains('PENDING RETURN')")) {
					                return true;
					            }
					        return false;
					    }).show();
					}else{
						location.reload();
					}
				});
				$('#delivered').change(function() {
					if (this.checked) {
						var jo = $("#fbody").find("tr");
					    jo.hide();

					    jo.filter(function (i, v) {
					        var $t = $(this);
					            if ($t.is(":contains('DELIVERED')")) {
					                return true;
					            }
					        return false;
					    }).show();
					}else{
						location.reload();
						}
				});
				$('#returned').change(function() {
					if (this.checked) {
						var jo = $("#fbody").find("tr");
					    jo.hide();

					    jo.filter(function (i, v) {
					        var $t = $(this);
					            if ($t.is(":contains('RETURNED')")) {
					                return true;
					            }
					        return false;
					    }).show();
					}else{
						location.reload();
					}
				});
				</script>
	</sec:authorize>
</body>
</html>