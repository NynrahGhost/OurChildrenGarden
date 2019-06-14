<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/radioButton.css">
	<link rel="stylesheet" type="text/css" href="css/index.css">
	
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

	<!-- Font Awesome -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
	<!-- Bootstrap core CSS -->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
	<!-- Material Design Bootstrap -->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.8.2/css/mdb.min.css" rel="stylesheet">

	<!-- JQuery -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<!-- Bootstrap tooltips -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.4/umd/popper.min.js"></script>
	<!-- Bootstrap core JavaScript -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<!-- MDB core JavaScript -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.8.2/js/mdb.min.js"></script>

	<meta charset="UTF-8">
	<title>Our children garden</title>
</head>
<body>
	
	<nav class="navbar navbar-expand-lg navbar-dark indigo">
  <a class="navbar-brand" href="#">Наш дитячий садок</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
    aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarText">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href="#">Мої діти</a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="#">Квитанції<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Викладачі</a>
      </li>
    </ul>
    <span class="navbar-text white-text">
      Navbar text with an inline element
    </span>
  </div>
</nav>

	<div class="container">
	<div class="row center">
	<div class="col-md-1"></div>
	<div class="kids col-md-10" id="KidsList">
		Somebody once told me the world was gonna roll me
	</div>
	</div>
	</div>
	
	<script>
	(function ()
	{
		if('<%= session.getAttribute("UserID") %>' === null && '<%= session.getAttribute("UserType") %>' !== 1)
		{
			window.location.replace("http://localhost:8080/ChildrenGarden/index.jsp");
		}
		
		var xmlHttp = new XMLHttpRequest();
	   	xmlHttp.onreadystatechange = function()
	    {
	        if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
	        {
	            //alert(xmlHttp.responseText);
	            document.getElementById("KidsList").innerHTML = xmlHttp.responseText;
	        }
	    };
	    xmlHttp.open("post", "http://localhost:8080/ChildrenGarden/KidsList"); 
	    xmlHttp.send(null); 
	    
	} () );
	
	function openTable(e)
	{
		var formData = new FormData();
		formData.append("KidID", e.target.id);

		var xmlHttp = new XMLHttpRequest();
	   	xmlHttp.onreadystatechange = function()
	    {
	        if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
	        {
	        	var tables = document.getElementsByClassName('kidTable');
	        	for(var i = 0; i < tables.length; i++)
	        	{
	        		tables[i].innerHTML = null;
	        	}
	            document.getElementById('t' + e.target.id).innerHTML = xmlHttp.responseText;
	        }
	    };
	    xmlHttp.open("post", "http://localhost:8080/ChildrenGarden/KidsTable"); 
	    xmlHttp.send(formData); 
	}
	</script>
	
</body>
</html>