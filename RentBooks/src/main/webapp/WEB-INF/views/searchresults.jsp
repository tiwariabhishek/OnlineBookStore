<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="importfile.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>BookStore | SearchResults</title>
</head>
<body>
	<div class="row panel clearfix">
		<div class="small-12 columns">
			<c:choose>
				<c:when test="${fn:contains(work, 'getCategory')}">
					<h4 align="center">CATEGORY: ${category}</h4>
				</c:when>
				<c:otherwise>
					<h4 align="center">SEARCH RESULTS</h4>
				</c:otherwise>
			</c:choose>
		</div>
		<c:if test="${empty bookList}">
			<div class="row">
				<div class="small-12 columns">
					<div data-alert class="alert-box secondary" align="center">
						<h5>NO RESULTS FOUND.</h5>
					</div>
				</div>
			</div>
		</c:if>
		<div class="row" data-equalizer>
			<c:forEach items="${bookList}" var="book">
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
									<div id="loadCartImg${book.bookId}">
										<button onclick="addToCart(${book.bookId});"
											title="ADD TO CART" class="button [tiny small large] right">
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
			</c:forEach>
		</div>
	</div>
</body>
</html>