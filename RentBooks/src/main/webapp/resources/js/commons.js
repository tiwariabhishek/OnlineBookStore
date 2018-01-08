//var fileIndex = 0;
//$(document).ready(
//		function() {
//			$('#addFile').click(
//					function() {
//						fileIndex++;
//						$('#fileTable').append(
//								'<tr><td>'
//										+ '   <input type="file" name="files['
//										+ fileIndex + ']" />' + '</td></tr>');
//					});
//
//		});
var message = "YOUR SUBSCRIPTION IS ENDED OR GOING TO END IN THREE DAYS.PLEASE RENEW YOUR SUBSCRIPTION TO CONTINUE WITH OUR SERVICES.";
function getSubscriptionPlans(work) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	$.ajax({
		url : newurl + 'getSubscriptionDetails/',
		success : function(result) {
			numOfItems = result.numOfItems;
			itemList = result;
			$('#subscriptionContent').empty();
			var index = 0;
			for (index = 0; index < itemList.length; index++) {
				$('#subscriptionContent').append(
						createDivForItem(index + 1, itemList[index], work));
			}
			$('#registerModal').foundation('reveal', 'open');
		}
	});
}
function customisedMessage(msg) {
	if (msg != undefined && msg != "") {
		Alert.render(msg);
	}
}
function hideDiv() {
	$('#addbookdiv').hide();
	$('#editdeletediv').hide();
}
function createDivForItem(index, Item, work) {
	var ret = '<div><ul class="pricing-table">' + '<li class="title">PLAN '
			+ index + '</li>' + '<li class="price">$' + Item.planCost + '</li>'
			// + '<li class="description">An awesome description</li>'
			+ '<li class="bullet-item">PLAN DURATION: ' + Item.planDuration
			+ ' DAYS' + '</li>' + '<li class="bullet-item">MAX BOOK REQUESTS: '
			+ Item.maxBooks + ' BOOKS' + '</li>';
	if (work === 'update') {
		ret += '<li class="cta-button"><input type="submit" class="button" value="Buy Now" onclick="updatePlan('
				+ Item.planId + ')"></li>' + '</ul></div>';
	} else {
		ret += '<li class="cta-button"><input type="submit" class="button" value="Buy Now" onclick="saveSubId('
				+ Item.planId + ')"></li>' + '</ul></div>';
	}
	return ret;
}
function updatePlan(planId) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	$.ajax({
		url : newurl + 'user/updatePlan?planId=' + planId,
		success : function(result) {
			$('#subscriptionModal').foundation('reveal', 'close');
			customisedMessage(result);
		}
	});
}
function saveSubId(planId) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	window.location.href = newurl + "signup/planId=" + planId;
}
function formSubmit() {
	document.getElementById("logoutForm").submit();
}
function addToCart(bId) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	$.ajax({
		url : newurl + 'addToCart?bookId=' + bId,
		success : function(result) {
			if (result != null) {
				customisedMessage(result);
			}
			$('#loadCartImg' + bId).empty();
			$('#loadCartImg' + bId).append(createButtonForCart());
		}
	});
}
function createButtonForCart() {
	return '<button title="ADDED TO CART" class="button [tiny small large] disabled right"><img src="resources/svg/fi-shopping-cart.svg" alt="cart"></button>';
}

function addToWishlist(bId) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	$.ajax({
		url : newurl + 'user/addToWishList?bookId=' + bId,
		success : function(result) {
			if (result != null) {
				customisedMessage(result);
			}
			$('#loadWishlistImg' + bId).empty();
			$('#loadWishlistImg' + bId).append(createButtonForWishlist());
		}
	});
}
function createButtonForWishlist() {
	return '<a title="ADDED TO WISHLIST" class="button [tiny small large] disabled right"><img src="resources/svg/fi-heart.svg" alt="wishlist"></a>';
}
var globalBalance = undefined;
function validateCart(userBalance) {
	globalBalance = userBalance;
	if (userBalance === -1) {
		customisedMessage(message);
		$('#emptyCart')
				.append(
						'<div class="small-12 columns"><div data-alert class="alert-box secondary" align="center">YOUR SUBSCRIPTION IS ENDED.PLEASE RENEW YOUR SUBSCRIPTION TO PLACE ORDERS.</div></div>');
	} else {
		var totCost = 0;
		var rowCount = $('#cartTable tr').length - 1;
		$('#cartTable .price').each(
				function() {
					var cost = $(this).html();
					cost = cost.substring(cost.indexOf('$') + 1, cost
							.lastIndexOf('<'));
					totCost += parseInt(cost);
				});
		$('#userBalanceDiv').empty();
		$('#userBalanceDiv')
				.append(
						'<a data-dropdown="drop" id="getUserBalance" class="large success button dropdown right" aria-expanded="false">CURRENT BALANCE= $'
								+ userBalance + '</a>');
		if (totCost === 0) {
			$('#emptyCart').empty();
			$('#totItems').empty();
			$('#subtotaldiv').empty();
			$('#cartTable').empty();
			$('#placeOrder').empty();
			$('#emptyCart')
					.append(
							'<div class="small-12 columns"><div data-alert class="alert-box secondary" align="center">YOUR CART IS CURRENTLY EMPTY.PLEASE SELECT BOOKS FROM OUR <a href="store"><b>LIBRARY</b></a>.<a href="#" class="close">&times;</a></div></div>');
		} else {
			$('#placeOrder').empty();
			$('#placeOrder')
					.append(
							'<a href="#" data-reveal-id="cartModal"	class="button [tiny small large] left">Place Order</a>');
			$('#totItems').empty();
			$('#subtotaldiv').empty();
			$('#subtotaldiv').append(
					'<div class="panel radius" align="center"><h4>SUBTOTAL= $'
							+ totCost + '</h4></div>');
			$('#totItems').append(
					'<h4 align="center">CART(' + rowCount + ')</h4>');
			if (userBalance != undefined) {
				userBalance = parseInt(userBalance);
				if (totCost > userBalance) {
					$('#subtotaldiv').empty();
					$('#placeOrder').empty();
					$('#subtotaldiv').append(
							'<div class="alert-box alert" align="center">YOUR ACCOUNT BALANCE($'
									+ userBalance
									+ ') IS LOWER THAN YOUR CART SUBTOTAL($'
									+ totCost + ').</div>');
					$('#placeOrder')
							.append(
									'<a class="button [tiny small large] disabled left">Place Order</a>');

				}
			}
		}
	}
}
function removeFromCart(bId) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	$.ajax({
		url : newurl + 'removeFromCart?bookId=' + bId,
		success : function(result) {
			$('table#cartTable tr#' + bId).remove();
			validateCart(globalBalance);
		}
	});
}
function mywishList() {
	var rowCount = $('#wishTable tr').length - 1;
	$('#wishItems').empty();
	$('#wishItems')
			.append('<h4 align="center">WISHLIST(' + rowCount + ')</h4>');
	if (rowCount === -1 || rowCount === 0) {
		$('#wishItems').empty();
		$('#wishTable').empty();
		$('#emptyWishList')
				.append(
						'<div class="small-12 columns"><div data-alert class="alert-box secondary" align="center">YOUR WISHLIST IS CURRENTLY EMPTY.PLEASE SELECT BOOKS FROM OUR <a href="store"><b>LIBRARY</b></a>.<a href="#" class="close">&times;</a></div></div>');
	}
}
function removeFromWishList(bId) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	$.ajax({
		url : newurl + 'user/removeFromWishlist?bookId=' + bId,
		success : function(result) {
			$('table#wishTable tr#' + bId).remove();
			mywishList();
		}
	});
}
function approveUserRequest(rId, email) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	$
			.ajax({
				url : newurl + 'admin/approveRequest?requestId=' + rId
						+ '&email=' + email,
				success : function(result) {
					if (result === "OUT OF STOCK") {
						Alert
								.render('CANNOT PROCESS REQUEST.REQUESTED BOOK IS OUT OF STOCK.');
					} else {
						var status = result.substring(result.indexOf(',') + 1);
						var date = result.substring(0, result.indexOf(','));
						$('#requestTable').find('tr#' + rId).find('td').eq(7)
								.empty();
						$('#requestTable').find('tr#' + rId).find('td').eq(7)
								.append(
										'<p style="color: green">' + status
												+ '</p>');
						if (status === "DELIVERED") {
							$('#requestTable').find('tr#' + rId).find('td').eq(
									4).text(date);
						} else {
							$('#requestTable').find('tr#' + rId).find('td').eq(
									5).text(date);
						}
					}
				}
			});
}
function declineUserRequest(rId, email) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	$.ajax({
		url : newurl + 'admin/declineUserRequest?requestId=' + rId + '&email='
				+ email,
		success : function(result) {
			$('table#requestTable tr#' + rId).remove();
		}
	});
}
function deleteBook(bId) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	$.ajax({
		url : newurl + 'admin/deleteBook?bookId=' + bId,
		success : function(result) {
			$('table#editDeleteTable tr#' + bId).remove();
		}
	});
}
function loadSummary() {
	var pendingReq = 0;
	var issuedReq = 0;
	$('#summaryDescTable .bookStatus').each(function() {
		var status = $(this).html();
		var index1 = status.indexOf("PENDING");
		var index2 = status.indexOf("DELIVERED");
		if (index1 >= 0) {
			pendingReq++;
		} else if (index2 >= 0) {
			issuedReq++;
		}
	});
	$('#summaryTable').find('tr#totIssuedBooks').find('td').eq(1).text(
			issuedReq);
	$('#summaryTable').find('tr#totPendingReq').find('td').eq(1).text(
			pendingReq);

}
function returnBook(rId, bId, title, category, author, reqDate) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	$
			.ajax({
				url : newurl + 'user/returnBook?requestId=' + rId,
				success : function(result) {
					$('table#issuedBookTable tr#' + rId).remove();
					var $row = '<tr id='
							+ rId
							+ '><td><a href="store/getBook?bookId='
							+ bId
							+ '">'
							+ title
							+ '</a></td><td>'
							+ category
							+ '</td><td>'
							+ author
							+ '</td><td>'
							+ reqDate
							+ '</td><td><a href="user/cancelRequest?requestId='
							+ rId
							+ '"><div align="center" style="color: red">CANCEL RETURN REQUEST</div></td></tr>';
					$('#pendingBookReqTable tbody').append($row);
					removeEmptyTables('RETURN');
				}
			});
}
function cancelBook(rId, bId, title, category, author, reqDate, issuedDate) {
	var newurl = window.location.href;
	var index = newurl.indexOf('bookstore');
	newurl = newurl.substring(0, index + 10);
	$.ajax({
		url : newurl + 'user/cancelRequest?requestId=' + rId,
		success : function(result) {
			var reqType = $('#pendingBookReqTable').find('tr#' + rId)
					.find('td').eq(4).text();
			$('table#pendingBookReqTable tr#' + rId).remove();
			var str = 'CHECK';
			if (reqType.indexOf('RETURN') > 0) {
				var $row = '<tr id=' + rId
						+ '><td><a href="store/getBook?bookId=' + bId + '">'
						+ title + '</a></td><td>' + category + '</td><td>'
						+ author + '</td><td>' + reqDate + '</td><td>'
						+ issuedDate
						+ '</td><td><a href="user/returnBook?requestId=' + rId
						+ '"><div align="center">RETURN</div></td></tr>';
				$('#issuedBookTable tbody').append($row);
				str = 'CANCEL';
			}
			removeEmptyTables(str);
		}
	});
}
function removeEmptyTables(str) {
	var rowCount1 = $('#pendingBookReqTable tr').length - 1;
	var rowCount2 = $('#issuedBookTable tr').length - 1;
	if (rowCount1 === 0) {
		$('#pendingBookDiv').empty();
	}
	if (rowCount2 === 0) {
		$('#issuedBookDiv').empty();
	}
	if (str.indexOf('CANCEL') >= 0) {
		if (!($('#issuedBookTable').length)) {
			location.reload();
		}
	} else if (str.indexOf('RETURN') >= 0) {
		if (!($('#pendingBookReqTable').length)) {
			location.reload();
		}
	}
}
function CustomAlert() {
	this.render = function(dialog) {
		var winW = window.innerWidth;
		var winH = window.innerHeight;
		var dialogoverlay = document.getElementById('dialogoverlay');
		var dialogbox = document.getElementById('dialogbox');
		dialogoverlay.style.display = "block";
		dialogoverlay.style.height = winH + "px";
		dialogbox.style.left = (winW / 2) - (550 * .5) + "px";
		dialogbox.style.top = "100px";
		dialogbox.style.display = "block";
		document.getElementById('dialogboxhead').innerHTML = "Acknowledge This Message";
		document.getElementById('dialogboxbody').innerHTML = dialog;
		document.getElementById('dialogboxfoot').innerHTML = '<button onclick="Alert.ok()">OK</button>';
	};
	this.ok = function() {
		document.getElementById('dialogbox').style.display = "none";
		document.getElementById('dialogoverlay').style.display = "none";
	};
}
var Alert = new CustomAlert();