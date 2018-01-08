<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="importfile.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Book</title>
</head>
<body>
	<div class="row">
		<div class="small-12 columns" align="center">
			<h4>Update Book</h4>
		</div>
	</div>
	<form:form
		action="./admin/updateBook?${_csrf.parameterName}=${_csrf.token}&bookId=${book.bookId}"
		method="POST" enctype="multipart/form-data">
		<div class="row panel">
			<div class="small-12">
				<div class="small-3 columns">
					<a><img width=200 height=200
						src="resources/img/${book.imageurl}"></a> <input name="image"
						type="file" id="right-label4">

				</div>
				<div class="small-9 columns">
					<div class="small-3 columns">
						<label for="right-label1" class="right">Book ISBN_NO</label>
					</div>
					<div class="small-9 columns">
						<input name="isbn" type="text" id="right-label1"
							value="${book.isbn}" placeholder="Enter Book ISBN_NO">
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label1" class="right">Book Title</label>
						</div>
						<div class="small-9 columns">
							<input name="title" type="text" id="right-label1"
								value="${book.title}" placeholder="Enter Book Title">
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label2" class="right">Book Author</label>
						</div>
						<div class="small-9 columns">
							<input name="author" type="text" id="right-label2"
								value="${book.author}" placeholder="Enter Book Author">
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label5" class="right">Book Category</label>
						</div>
						<div class="small-9 columns">
							<input name="category" type="text" id="right-label5"
								value="${book.category}" placeholder="Enter Book Category">
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label6" class="right">Book Publisher</label>
						</div>
						<div class="small-9 columns">
							<input name="publisher" type="text" id="right-label6"
								value="${book.publisher}" placeholder="Enter Book Publisher">
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label8" class="right">Language</label>
						</div>
						<div class="small-9 columns">
							<input name="language" type="text" id="right-label"
								value="${book.language}" placeholder="Enter Language">
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label8" class="right">Book Price</label>
						</div>
						<div class="small-9 columns">
							<input name="price" type="text" id="right-label"
								value="${book.price}" placeholder="Enter Book Price">
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label1" class="right">Number Of Pages</label>
						</div>
						<div class="small-9 columns">
							<input name="numPages" type="text" id="right-label1"
								value="${book.numPages}" placeholder="Enter Number Of Pages">
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label1" class="right">Number Of Copies</label>
						</div>
						<div class="small-9 columns">
							<input name="noOfCopies" type="text" id="right-label1"
								value="${book.noOfCopies}" placeholder="Enter Number Of Copies">
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns">
							<label for="right-label1" class="right">Book Description</label>
						</div>
						<div class="small-9 columns">
							<textarea name="description"
								placeholder="Tell what the book is all about...">${book.description}</textarea>
						</div>
					</div>
					<div class="row">
						<div class="small-3 columns"></div>
						<div class="small-9 columns">
							<input type="submit" class="button right" value="UPDATE BOOK">
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>