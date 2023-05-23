<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<title>CURSO JSP</title>

<style type="text/css">
form {
	position: absolute;
	top: 40%;
	left: 33%;
	rigth: 33%;
}

h5 {
	position: absolute;
	top: 30%;
	left: 33%;
}

.msg{
	position: absolute;
	top: 10%;
	left: 33%;
	font-size: 15px;
	color: #664d03;
    background-color: #fff3cd;
    border-color: #ffecb5;
}
</style>


</head>
<body>
	<h5>BEM VINDO AO PROGRAMA JSLOBATO</h5>


	<form action="<%= request.getContextPath() %>/ServeletLoguin" method="post"
		class="row g-3 row g-3 needs-validation" novalidate>
		<input type="hidden" value="<%=request.getParameter("url")%>"
			name="url">

		<div class="mb-3">
			<label class="form-label" for="loguin" > LOGUIN </label> <input
				class="form-control" id="loguin" name="loguin" type="text" required="required">
			<div class="invalid-feedback">informe o loguin !</div>
			<div class="valid-feedback">ok !</div>
		</div>

		<div class="mb-3">
			<label class="form-label" for="senha"> SENHA </label> <input class="form-control" id="senha"
				name="senha" type="password" required="required">
				<div class="invalid-feedback">informe a senha !</div>
				<div class="valid-feedback">ok !</div>
		</div>
		</div>

		<input type="submit" value="ACESSAR" class="btn btn-primary">

    
	</form>
	
	
	 <h4 class="msg">${msg}</h4>

	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
	<script type="text/javascript">
		//Example starter JavaScript for disabling form submissions if there are invalid fields
		(function() {
			'use strict'

			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document.querySelectorAll('.needs-validation')

			// Loop over them and prevent submission
			Array.prototype.slice.call(forms).forEach(function(form) {
				form.addEventListener('submit', function(event) {
					if (!form.checkValidity()) {
						event.preventDefault()
						event.stopPropagation()
					}

					form.classList.add('was-validated')
				}, false)
			})
		})()
	</script>

</body>
</html>