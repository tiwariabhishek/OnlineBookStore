<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="importfile.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="resources/css/dataTables.foundation.css" />
<script type="text/javascript"
	src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="//cdn.datatables.net/plug-ins/9dcbecd42ad/integration/foundation/dataTables.foundation.js"></script>
<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				$('#editDeleteTable').dataTable({
					"iDisplayLength" : 5,
					"aLengthMenu" : [ [ 5, 10, 25, 50, -1 ], [ 5, 10, 25, 50, "All" ] ]
				});
			} );
		</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add|Update|Delete Books</title>
</head>
<body>
	<div class="row panel clearfix">
		<div class="small-12 columns" align="center">
			<input id="addbookcheck" type="checkbox" groupname="group2"
				name="bookrequest" value="bookrequest"> <label
				for="bookrequest" style="color: black;"><b><h4>ADD
						BOOK</h4></b></label> <input type="checkbox" id="editdeletebookcheck"
				name="returnrequest" groupname="group2" value="returnrequest"><label
				for="returnrequest" style="color: black;"><b><h4>DELETE/UPDATE
						BOOKS</h4></b></label>
		</div>
	</div>
	<div class="row panel clearfix" id="addbookdiv">
		<form:form
			action="./admin/addbooks?${_csrf.parameterName}=${_csrf.token} "
			method="POST" enctype="multipart/form-data">


			<div class="row">
				<div class="small-3 columns">
					<label for="right-label1" class="right">Book ISBN_NO</label>
				</div>
				<div class="small-9 columns">
					<input name="isbn" type="text" id="right-label1"
						placeholder="Enter Book ISBN_NO" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label1" class="right">Book Title</label>
				</div>
				<div class="small-9 columns">
					<input name="title" type="text" id="right-label1"
						placeholder="Enter Book Title" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label2" class="right">Book Author</label>
				</div>
				<div class="small-9 columns">
					<input name="author" type="text" id="right-label2"
						placeholder="Enter Book Author" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label4" class="right">Book Cover Page</label>
				</div>
				<div class="small-9 columns">
					<input name="image" type="file" id="right-label4" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label5" class="right">Book Category</label>
				</div>
				<div class="small-9 columns">
					<input name="category" type="text" id="right-label5"
						placeholder="Enter Book Category" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label6" class="right">Book Publisher</label>
				</div>
				<div class="small-9 columns">
					<input name="publisher" type="text" id="right-label6"
						placeholder="Enter Book Publisher" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label8" class="right">Language</label>
				</div>
				<div class="small-9 columns">
					<input name="language" type="text" id="right-label"
						placeholder="Enter Language" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label8" class="right">Book Price</label>
				</div>
				<div class="small-9 columns">
					<input name="price" type="text" id="right-label"
						placeholder="Enter Book Price" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label1" class="right">Number Of Pages</label>
				</div>
				<div class="small-9 columns">
					<input name="numPages" type="text" id="right-label1"
						placeholder="Enter Number Of Pages" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label1" class="right">Number Of Copies</label>
				</div>
				<div class="small-9 columns">
					<input name="noOfCopies" type="text" id="right-label1"
						placeholder="Enter Number Of Copies" required>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns">
					<label for="right-label1" class="right">Book Description</label>
				</div>
				<div class="small-9 columns">
					<textarea name="description"
						placeholder="Tell what the book is all about..." required></textarea>
				</div>
			</div>
			<div class="row">
				<div class="small-3 columns"></div>
				<div class="small-9 columns">
					<input type="submit" class="button right" value="ADD BOOK">
				</div>
			</div>
		</form:form>
	</div>
	<div id="editdeletediv" class="row panel clearfix">
		<div class="small-12 columns" align="center">
			<ul class="side-nav">
				<li class="divider"></li>
				<li>
					<table width="100%" id="editDeleteTable">
						<thead>
							<tr>
								<th width="30%" colspan="2"><div align="center">BOOK</div></th>
								<th width="20%">CATEGORY</th>
								<th width="30%">AUTHOR</th>
								<th width="20%">ACTION</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${Books}" var="book">
								<tr id="${book.bookId}">
									<td><a class="th [radius]" role="button"
										href="store/getBook?bookId=${book.bookId}"> <img width=30
											height=30 src="resources/img/${book.imageurl}">
									</a></td>
									<td>${book.title}</td>
									<td>${book.category}</td>
									<td>${book.author}</td>
									<td><a href="#" data-dropdown="hover1${book.bookId}"
										data-options="is_hover:true">Edit/Delete</a>
										<ul id="hover1${book.bookId}" class="f-dropdown"
											data-dropdown-content>
											<li><a href="admin/updatebook?bookId=${book.bookId}">Edit</a></li>
											<li><a onclick="deleteBook(${book.bookId});">Delete</a></li>
										</ul></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</li>
			</ul>
		</div>
	</div>
	<script>
	hideDiv();
	$('#addbookcheck').change(function() {
		if (this.checked) {
			$('#addbookdiv').show();
			$('#editdeletediv').hide();
		} else {
			$('#addbookdiv').hide();
		}
	});
	$('#editdeletebookcheck').change(function() {
		if (this.checked) {
			$('#editdeletediv').show();
			$('#addbookdiv').hide();
		} else {
			$('#editdeletediv').hide();
		}
	});
	</script>
</body>
</html>