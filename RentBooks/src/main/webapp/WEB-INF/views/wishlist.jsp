<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="importfile.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BookStore|MyWishList</title>
</head>
<body>
	<div id="emptyWishList" class="row"></div>
	<c:if test="${!empty wishlist}">
		<div class="row">
			<div id="wishItems" class="small-12 columns"></div>
		</div>
		<div class="row">
			<div class="small-12 columns">
				<table id="wishTable" width="100%">
					<thead>
						<tr>
							<th colspan="2" width="30%"><div align="center">BOOK</div></th>
							<th width="20%">AUTHOR</th>
							<th width="15%">LANGUAGE</th>
							<th width="13%">STATUS</th>
							<th width="10%">PRICE</th>
							<th width="10%">ADDTOCART</th>
							<th width="2%"></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${wishlist}" var="book">
							<tr id="${book.bookId}">
								<td><a class="th [radius]" role="button"
									href="store/getBook?bookId=${book.bookId}"> <img width=30
										height=30 src="resources/img/${book.imageurl}">
								</a></td>
								<td>
									<h5>${book.title}</h5>
								</td>
								<td>
									<h5>${book.author}</h5>
								</td>
								<td>
									<h5>${book.language}</h5>
								</td>
								<td>
									<h5>
										<c:choose>
											<c:when test="${book.noOfCopies gt 0}">
												<p style="color: green">AVAILABLE</p>
											</c:when>
											<c:otherwise>
												<p style="color: red">NOT AVAILABLE</p>
											</c:otherwise>
										</c:choose>
									</h5>
								</td>
								<td class="price">
									<h5>$${book.price}</h5>
								</td>
								<td><c:choose>
										<c:when test="${book.noOfCopies gt 0}">
											<div id="loadCartImg${book.bookId}">
												<a onclick="addToCart(${book.bookId});" title="ADD TO CART"
													class="button [tiny small large] right"> <img
													src="resources/svg/fi-shopping-cart.svg" alt="cart">
												</a>
											</div>
										</c:when>
										<c:otherwise>
											<a title="OUT OF STOCK"
												class="button [tiny small large] disabled right"><img
												src="resources/svg/fi-shopping-cart.svg" alt="cart"></a>
										</c:otherwise>
									</c:choose></td>
								<td><a onclick="removeFromWishList(${book.bookId})"
									class="close">&#215;</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</c:if>
	<script type="text/javascript">mywishList();</script>
</body>
</html>