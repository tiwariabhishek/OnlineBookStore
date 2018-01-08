<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="importfile.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BookStore|MyCart</title>
</head>
<body>
	<div id="emptyCart" class="row"></div>
	<c:if test="${!empty cartList}">
		<div class="row">
			<div id="totItems" class="small-12 columns"></div>
		</div>
		<div class="row">
			<div class="small-12 columns">
				<table id="cartTable" width="100%">
					<thead>
						<tr>
							<th colspan="2" width="30%"><div align="center">BOOK</div></th>
							<th width="20%">AUTHOR</th>
							<th width="15%">LANGUAGE</th>
							<th width="20%">PUBLISHER</th>
							<th width="3%">QTY</th>
							<th width="10%">PRICE</th>
							<th width="2%"></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${cartList}" var="book">
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
									<h5>${book.publisher}</h5>
								</td>
								<td>1</td>
								<td class="price">
									<h5>$${book.price}</h5>
								</td>
								<td><a onclick="removeFromCart(${book.bookId})"
									class="close">&#215;</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="subtotaldiv"></div>
				<div id="placeOrder"></div>
				<div id="cartModal" class="reveal-modal" data-reveal>
					<c:choose>
						<c:when test="${pageContext.request.userPrincipal.name == null}">
							<div class="panel">
								<h3>
									Please <a href="#" data-reveal-id="logInModal">LogIn</a> to
									continue.
								</h3>
							</div>
						</c:when>
						<c:otherwise>
							<form:form action="requestBook" method="POST">
								<div class="panel clearfix">
									<h3>
										<textarea name="newAddress" rows="5" cols="50">
											<c:if test="${empty user.newAddress}">${user.address}</c:if>
											<c:if test="${not empty user.newAddress}">${user.newAddress}</c:if>
										</textarea>
									</h3>
									<div class="small-12 columns">
										<input type="submit" class="button left"
											value="Confirm Address">
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form:form>
						</c:otherwise>
					</c:choose>
					<a class="close-reveal-modal">&#215;</a>
				</div>
			</div>
		</div>
	</c:if>
	<script type="text/javascript">validateCart(${user.balance});</script>
</body>
</html>