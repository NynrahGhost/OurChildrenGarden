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

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/css/bootstrap-select.min.css">

	<!-- Latest compiled and minified JavaScript -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>

	<!-- JQuery -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<!-- Bootstrap tooltips -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.4/umd/popper.min.js"></script>
	<!-- Bootstrap core JavaScript -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<!-- MDB core JavaScript -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.8.2/js/mdb.min.js"></script>

	<meta charset="UTF-8">
	
	<title>Фінанаси • Адміністрація</title>
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
        <a class="nav-link" href="../../ChildrenGarden/AdminGroups.jsp">Групи</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="../../ChildrenGarden/AdminParents.jsp">Батьки</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="../../ChildrenGarden/AdminKids.jsp">Діти</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="../../ChildrenGarden/AdminTutors.jsp">Викладачі</a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="../../ChildrenGarden/AdminFinance.jsp">Фінанси<span class="sr-only">(current)</span></a>
      </li>
    </ul>
    <span class="navbar-text white-text">
      Ви зайшли як Адміністратор
    </span>
  </div>
</nav>

	<div class="container">
	<div class="row center">
	<div class="col-md-1"></div>
	<div class="kids col-md-10" id="KidsList">
	</div>
	</div>
	</div>
	
	<script>

	var kidsList = [];
	var groupsAssigned = [];
	
	(function ()
	{
		if('<%= session.getAttribute("UserID") %>' === null || '<%= session.getAttribute("UserType") %>' !== "3")
		{
			window.location.replace("http://localhost:8080/ChildrenGarden/index.jsp");
		}
		
		var xmlHttp = new XMLHttpRequest();
	   	xmlHttp.onreadystatechange = function()
	    {
	        if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
	        {
	            document.getElementById("KidsList").innerHTML = xmlHttp.responseText;
	            var children = document.getElementById("KidsList").children;
	            for (var i = 1; i < children.length; i++) {
	            	kidsList.push(children[i].firstChild.firstChild.id);
	            }
	        }
	    };
	    xmlHttp.open("post", "http://localhost:8080/ChildrenGarden/ReceiptList"); 
	    xmlHttp.send(null); 
	    
	} () );
	
	</script>
</body>
</html>