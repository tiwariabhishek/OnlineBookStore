<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="importfile.jsp"%>
<!doctype html>
<html>
<head>
<link rel="stylesheet" href="resources/css/dataTables.foundation.css" />
<script type="text/javascript"
	src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="//cdn.datatables.net/plug-ins/9dcbecd42ad/integration/foundation/dataTables.foundation.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#bookstoreTable').dataTable({
		    "iDisplayLength": 5,
		    "aLengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]]
		    });
	});
</script>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>BookStore | Store</title>
</head>
<body>
	<div class="row panel clearfix">
		<div class="row">
			<div class="small-12 columns">
				<h4 align="center">WELCOME TO OUR STORE</h4>
			</div>
		</div>
		<table id="bookstoreTable">
			<thead>
				<tr>
					<th></th>
				</tr>
			</thead>
			<c:forEach items="${bookList}" var="book">
				<tr>
					<td>
						<div class="small-12 columns panel" data-equalizer-watch>
							<div class="small-2 columns">
								<a class="th [radius]" role="button"
									href="store/getBook?bookId=${book.bookId}"> <img width=100
									height=100 src="resources/img/${book.imageurl}">
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
								<sec:authorize access="!hasRole('ROLE_ADMIN')">
									<c:choose>
										<c:when test="${book.noOfCopies gt 0}">
											<c:choose>
												<c:when
													test="${pageContext.request.userPrincipal.name == null}">
													<div id="loadCartImg${book.bookId}">
														<button onclick="addToCart(${book.bookId});"
															title="ADD TO CART"
															class="button [tiny small large] right">
															<img src="resources/svg/fi-shopping-cart.svg" alt="cart">
														</button>
													</div>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when
															test="${activesubscription.remBooks gt 0 && (userentity.balance gt book.price || userentity.balance == book.price)}">
															<div id="loadCartImg${book.bookId}">
																<button onclick="addToCart(${book.bookId});"
																	title="ADD TO CART"
																	class="button [tiny small large] right">
																	<img src="resources/svg/fi-shopping-cart.svg"
																		alt="cart">
																</button>
															</div>
														</c:when>
														<c:otherwise>
															<a title="LOW ACCOUNT BALANCE."
																class="button [tiny small large] disabled right"><img
																src="resources/svg/fi-shopping-cart.svg" alt="cart"></a>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<a title="OUT OF STOCK"
												class="button [tiny small large] disabled right"><img
												src="resources/svg/fi-shopping-cart.svg" alt="cart"></a>
										</c:otherwise>
									</c:choose>
									<sec:authorize access="hasRole('ROLE_USER')">
										<div id="loadWishlistImg${book.bookId}">
											<a onclick="addToWishlist(${book.bookId})"
												title="ADD TO WISHLIST"
												class="button [tiny small large] right"><img
												src="resources/svg/fi-heart.svg" alt="wishlist"></a>
										</div>
									</sec:authorize>
								</sec:authorize>
							</div>
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>