<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>

	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/radioButton.css">
	
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	<!-- <meta charset="ISO-8859-1"> -->
	<!--<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> -->
	<title>Our children garden</title>
	
</head>
<body class="text-center">
	<div class="container" style="margin-top: 5%;">
	<div class="row center">
	<div class="col-md-3"></div>
	<div class="col-md-6 col-md-offset-3 kgForm">
    <form class="form-signin">
      <img src="images/logo.png" alt="" width="180" height="180">
      <h1 class="h3 mb-3 font-weight-normal">Виконайте вхід</h1>
      <label for="inputEmail" class="sr-only">Номер телефону (ххх ххх хх хх)</label>
      <input type="tel" id="inputEmail" class="form-control" placeholder="Номер телефону (XXX XXX XX XX)" required="" autofocus="">
      <label for="inputPassword" class="sr-only">Пароль</label>
      <input type="password" id="inputPassword" class="form-control" placeholder="Пароль" required="">
    <div class="">
		<div class="toggle_radio">
    		<input type="radio" checked class="toggle_option" id="first_toggle" name="toggle_option">
    		<input type="radio" class="toggle_option" id="second_toggle" name="toggle_option">
    		<input type="radio" class="toggle_option" id="third_toggle" name="toggle_option">
    		<label for="first_toggle"><p>Опікун</p></label>
    		<label for="second_toggle"><p>Викладач</p></label>
    		<label for="third_toggle"><p>Адміністратор</p></label>
    		<div class="toggle_option_slider">
    	</div>
	</div>
	<div class="checkbox mb-3 center">
        <label>
          <input type="checkbox" value="remember-me"> Запам'ятати мене
        </label>
      </div>
      <button class="btn btn-lg btn-primary btn-block" type="button" onclick="authorization()">Увійти</button>
      <p class="mt-5 mb-3">© 2019</p>
    </form>
    </div>
    </div>
    </div>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.js"></script>
	
	<script>
	function authorization()
	{
		console.log(document.getElementById("inputEmail").value);
	    var formData = new FormData(); 

	    var val;
	    if(document.getElementById("first_toggle").checked) {
	    	val = 1;
	    } if(document.getElementById("second_toggle").checked) {
	    	val = 2;
	    } if(document.getElementById("third_toggle").checked) {
	    	val = 3;
	    }
	    formData.append("UserType", val);
	    formData.append("PhoneNumber", document.getElementById("inputEmail").value);
	    formData.append("Password", sha256(document.getElementById("inputPassword").value));
	    
	    for (var p in formData) {
	    	  console.log(p);
	    	}
	    
	    var xmlHttp = new XMLHttpRequest();
	   	xmlHttp.onreadystatechange = function()
	    {
	        if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
	        {
	            console.log(xmlHttp.responseText);
	            //window.location.replace("http://localhost:8080/ChildrenGarden/ParentKids.jsp");
	            if(val === 1) {
	            	window.location.href = "http://localhost:8080/ChildrenGarden/ParentKids.jsp";
	            } if(val === 2) {
	            	window.location.href = "http://localhost:8080/ChildrenGarden/TutorGroups.jsp";
	            } if(val === 3) {
	            	window.location.href = "http://localhost:8080/ChildrenGarden/AdminGroups.jsp";
	            }
	        }
	    };
	    xmlHttp.open("post", "http://localhost:8080/ChildrenGarden/Authentication"); 
	    xmlHttp.send(formData);
	}
	</script>

</body>
</html>