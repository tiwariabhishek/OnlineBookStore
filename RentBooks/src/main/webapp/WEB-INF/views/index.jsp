<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="importfile.jsp"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>BookStore | Welcome</title>
</head>
<body>
	<div class="row" id="slider">
		<ul class="rslides">
			<li><img src="resources/img/backgrnd.jpeg" alt=""></li>
			<li><img src="resources/img/2.jpg" alt=""></li>
			<li><img src="resources/img/4.jpg" alt=""></li>
		</ul>
	</div>
	<div class="photo-album">

		<h1>
			<span>CATEGORIES </span>
		</h1>

		<a
			href="search?searchText=Inspiration&category=Inspiration&work=getCategory"
			class="large polaroid img1"><img
			src="resources/img/9789350293843.jpeg" alt="">Inspiration</a> <a
			href="search?searchText=Music&category=Music&work=getCategory"
			class="polaroid img2"><img src="resources/img/9788172453442.jpeg"
			alt="">Music</a>
		<!-- 			<a -->
		<!-- 			href="search?searchText=Programming&category=Programming&work=getCategory" -->
		<!-- 			class="small polaroid img3"><img -->
		<!-- 			src="resources/img/9781848547926.jpeg" alt="">Technology</a>  -->
		<a href="search?searchText=Science&category=Science&work=getCategory"
			class="medium polaroid img4"><img
			src="resources/img/9788177091878.jpeg" alt="">Science</a> <a
			href="search?searchText=Programming&category=Programming&work=getCategory"
			class="polaroid img5"><img src="resources/img/9788183331630.jpeg"
			alt="">Programming</a> <a
			href="search?searchText=Mathematics&category=Mathematics&work=getCategory"
			class="polaroid img6"><img src="resources/img/9789350941805.jpeg"
			alt="">Mathematics</a> <a
			href="search?searchText=Technology&category=Technology&work=getCategory"
			class="polaroid img7"><img src="resources/img/9781848547926.jpeg"
			alt="">Technology</a>
		<!-- 			 <a -->
		<!-- 			href="search?searchText=Science&category=Sports&work=getCategory" -->
		<!-- 			class="small polaroid img8"><img -->
		<!-- 			src="resources/img/9781473605206.jpeg" alt="">Sports</a> -->
		<a href="search?searchText=Science&category=Sports&work=getCategory"
			class="medium polaroid img9"><img
			src="resources/img/9781473605206.jpeg" alt="">Sports</a> <a
			href="search?searchText=Engineering&category=Sports&work=getCategory"
			class="polaroid img10"><img
			src="resources/img/9781259064180.jpeg" alt="">Engineering</a> <a
			href="search?searchText=Drama&category=Drama&work=getCategory"
			class="small polaroid img11"><img
			src="resources/img/4312123242234.jpg" alt="">Drama</a>
		<!-- 							<a -->
		<!-- 					href="http://www.flickr.com/photos/nataliedowne/2352608953/in/set-72157604232220981/" -->
		<!-- 					class="small polaroid img12"><img src="../img/sealions.jpg" -->
		<!-- 					alt="">Sea lions!</a> <a -->
		<!-- 					href="http://www.flickr.com/photos/nataliedowne/2341816430/in/set-72157604232220981/" -->
		<!-- 					class="small polaroid img13"><img src="../img/horses.jpg" -->
		<!-- 					alt="">Horse riding</a> <a -->
		<!-- 					href="http://www.flickr.com/photos/nataliedowne/2352490411/in/set-72157604232220981/" -->
		<!-- 					class="small polaroid img14"><img src="../img/camping.jpg" -->
		<!-- 					alt="">Stewart Island</a> <a -->
		<!-- 					href="http://www.flickr.com/photos/nataliedowne/2339184562/in/set-72157604232220981/" -->
		<!-- 					class="polaroid img15"><img src="../img/us.jpg" alt="">Us -->
		<!-- 					in a blue cave on the Franz Josef glacier</a> -->

	</div>
	<script src="resources/js/responsiveslides.min.js"></script>
	<script>
		$(function() {
			$(".rslides").responsiveSlides();
		});
	</script>
</body>
</html>
