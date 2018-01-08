<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="importfile.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BookStore|BookDetails</title>
</head>
<body>
	<div class="row">
		<div class="small-12 columns panel">
			<div class="small-3 columns">
				<a><img width=200 height=200
					src="resources/img/${book.imageurl}"></a>
			</div>
			<div class="small-6 small-centered columns" id="bookdetailsdiv">
				<table width="100%">
					<thead>
						<tr>
							<th colspan="2" width="200">Book Details</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>ISBN</td>
							<td>${book.isbn}</td>
						</tr>
						<tr>
							<td>Title</td>
							<td>${book.title}</td>
						</tr>
						<tr>
							<td>Author</td>
							<td>${book.author}</td>
						</tr>
						<tr>
							<td>Category</td>
							<td>${book.category}</td>
						</tr>
						<tr>
							<td>Publisher</td>
							<td>${book.publisher}</td>
						</tr>
						<tr>
							<td>Language</td>
							<td>${book.language}</td>
						</tr>
						<tr>
							<td>Price</td>
							<td>$${book.price}</td>
						</tr>
						<tr>
							<td>Number Of Copies</td>
							<td>${book.noOfCopies}</td>
						</tr>
						<tr>
							<td>Number Of Pages</td>
							<td>${book.numPages}</td>
						</tr>
						<tr>
							<td colspan="2"><sec:authorize
									access="!hasRole('ROLE_ADMIN')">
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
								</sec:authorize> <sec:authorize access="hasRole('ROLE_USER')">
									<div id="loadWishlistImg${book.bookId}">
										<a onclick="addToWishlist(${book.bookId})"
											title="ADD TO WISHLIST"
											class="button [tiny small large] right"><img
											src="resources/svg/fi-heart.svg" alt="wishlist"></a>
									</div>
								</sec:authorize></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>