<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Listă de sarcini</title>
<!-- external sources -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src='https://kit.fontawesome.com/160dc8ba89.js' crossorigin='anonymous'></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js" type="text/javascript"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" type="text/javascript"></script>
<!-- local sources -->
<link rel="stylesheet" href="view/format/index.css">
<script src="view/scripts/objects.js" type="text/javascript"></script>
<script src="view/scripts/index.js" type="text/javascript"></script>
<script src="view/scripts/stil.js" type="text/javascript"></script>
</head>
<body onload="loadAllTask()">
	<div class="p-4 container">
		<div class="header">
			<c:choose>
				<c:when test="${not empty user}">
					<span id="userdata">${user.firstName} ${user.lastName}</span>
					<button type="button" class="btn btn-primary"
						onclick="document.location='/WebTodoList/Logout'">
						Deconectare <i class="fas fa-sign-out-alt"></i>
					</button>
				</c:when>
				<c:otherwise>
					<button type="button" class="btn btn-primary"
						onclick="document.location='view/pages/registration.html'">
						Înregistrare <i class="fas fa-file-signature"></i>
					</button>
					<button type="button" class="btn btn-primary"
						onclick="document.location='view/pages/authentication.jsp'">
						Autentificare <i class="fas fa-sign-in-alt"></i>
					</button>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="p-4 container">
		<h1 class="text-center">Listă de sarcini</h1>
		<form action="">
			<div class="border">
				<div class="input-group">
					<input id="task" type="text" placeholder="Introduceți o sarcină…"
						value="" onkeydown="addTaskEnter(event)">
					<button id="enter" type="button" title="Save" value="➕"
						onclick="addTask()">
						<i class="fas fa-plus"></i>
					</button>
				</div>
			</div>
		</form>
		<div id="container-list" class="pb-2">
		</div>
	</div>
</body>
</html>