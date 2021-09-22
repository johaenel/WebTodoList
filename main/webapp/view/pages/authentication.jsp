<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<!--Iconset-->
<script src='https://kit.fontawesome.com/160dc8ba89.js'
	crossorigin='anonymous'></script>
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"
	type="text/javascript"></script>
<!-- Popper JS -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"
	type="text/javascript"></script>
<!-- Bootstrap JS -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
	type="text/javascript"></script>
</head>
<body>
	<div class="containter w-50 border mx-auto p-4 mt-5 rounded-lg">
		<div class="container">
			<h1 id="title">Formular de autentificare</h1>
		</div>
		<div class="container">
			<form action="/WebTodoList/Authentication" method="post">
				<div class=form-group id="userName">
					<label for="userName">Pseudonim: <span></span></label> 
					<input type="text" name="userName" class=form-control required>
				</div>
				<div class=form-group id="password">
					<label for="password">Parola:</label> <input type="password"
						name="password" class=form-control required>
				</div>
				<input type="submit">
				<div class="container mt-2">
				<div class="${domclass}" role="alert">${message}</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>