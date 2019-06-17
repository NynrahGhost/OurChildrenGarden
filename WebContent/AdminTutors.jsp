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
	<title>Вихователі • Адміністрація</title>
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
      <li class="nav-item active">
        <a class="nav-link" href="../../ChildrenGarden/AdminTutors.jsp">Викладачі<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="../../ChildrenGarden/AdminFinance.jsp">Фінанси</a>
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

	var parentsList = [];
	var kidsAssigned = [];
	
	(function ()
	{
		if('<%= session.getAttribute("UserID") %>' === null || '<%= session.getAttribute("UserType") %>' !== "3")
		{
			window.location.replace("http://localhost:8080/ChildrenGarden/index.jsp");
		}
		
		updateList();
	    
	} () );
	
	function updateList() {
		var xmlHttp = new XMLHttpRequest();
	   	xmlHttp.onreadystatechange = function()
	    {
	        if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
	        {
	            document.getElementById("KidsList").innerHTML = xmlHttp.responseText;
	            var children = document.getElementById("KidsList").children;
	            for (var i = 1; i < children.length; i++) {
	            	parentsList.push(children[i].firstChild.firstChild.id);
	            }
	        }
	    };
	    xmlHttp.open("post", "http://localhost:8080/ChildrenGarden/TutorList"); 
	    xmlHttp.send(null); 
	}
	
	function openTable(e)
	{
		var formData = new FormData();
		
		var d = new Date();
		
		formData.append("ParentID", e.target.id);

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
	            
	            kidsAssigned = $('#kidsList').val();
	        }
	    };
	    xmlHttp.open("post", "http://localhost:8080/ChildrenGarden/TutorTable"); 
	    xmlHttp.send(formData); 
	}
	
	function send(e)
	{
		var kids = $('#kidsList').val();//document.getElementById("kidsList").selectedOptions; 
		
		//var kidsToAssign = diff(kids, kidsAssigned);
		
		var kidsToAssign = kids.diff(kidsAssigned);
		var kidsToDischarge = kidsAssigned.diff(kids);
		
		//alert(kidsToDischarge);
		
		var formData = new FormData();
		formData.append("ParentID", e.target.parentElement.id.substring(1));
		formData.append("KidsToAssign", kidsToAssign);
		formData.append("KidsToDischarge", kidsToDischarge);
		
		formData.append("Password", sha256(document.getElementById("inputPassword").value));
		
		formData.append("Surname", document.getElementById("surname").value);
		formData.append("Name", document.getElementById("name").value);
		formData.append("Patr", document.getElementById("patr").value);
		
		formData.append("BirthDate", document.getElementById("birthDate").value);
		
		formData.append("Tel", document.getElementById("tel").value);
		formData.append("Tel1", document.getElementById("tel1").value);
		formData.append("Tel2", document.getElementById("tel2").value);
		
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function()
	    {
			document.getElementById(e.target.parentElement.id.substring(1)).textContent = 
				formData.get("Surname") + " " + formData.get("Name") + " " + formData.get("Patr");
	    	kidsAssigned = kids;
	    }
		xmlHttp.open("post", "http://localhost:8080/ChildrenGarden/UpdateTutor"); 
	    xmlHttp.send(formData); 
	}
	
	Array.prototype.diff = function(a) {
	    return this.filter(function(i) {return a.indexOf(i) < 0;});
	};
	
	function createParent()
	{
		var formData = new FormData();
		
		function sortNumber(a, b) {
			  return a - b;
		}
		parentsList.sort(sortNumber);
		
		for(var i = 1000001; i < 2147483648; i++) {
			if(parentsList[i - 1000001] != i) {
				formData.append("ID", i);
				break;
			}
		}
		
		formData.append("Entity", "Tutor");
		
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function()
    	{
			updateList();
    	}
		xmlHttp.open("post", "http://localhost:8080/ChildrenGarden/Create"); 
    	xmlHttp.send(formData); 
	}
	
	function deleteParent(e)
	{
		if (confirm("Ви збираєтесь видалити опікуна, дану дію скасувати буде неможливо! Ви впевнені у своєму виборі?")) {
			var formData = new FormData();
			formData.append("ID", e.target.previousSibling.id);
			formData.append("Entity", "Tutor");
			
			var xmlHttp = new XMLHttpRequest();
			xmlHttp.onreadystatechange = function()
	    	{
				document.getElementById(e.target.parentNode.parentNode.parentNode.removeChild(e.target.parentNode.parentNode));
	    	}
			xmlHttp.open("post", "http://localhost:8080/ChildrenGarden/Delete"); 
	    	xmlHttp.send(formData); 
		}
	}
	</script>
	
</body>
</html>