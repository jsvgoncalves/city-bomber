<?php 
	$ip   = $_GET["ip"];
	$port = $_GET["port"];
	$name = $_GET["name"];
	// isset() wont work like this (string = "")
	if($ip != null && $port != null && $name != null){
		$string = "ip=$ip&port=$port&name=$name";
		$hostPath = "http://localhost:8080/bomber/api/registerserver.php?";
		
		//Tries to register the server..
		echo file_get_contents($hostPath . $string);
	}
?>

<!DOCTYPE HTML>
<html>
<head>
	<title>Register CityBomber Server</title>
	<!--<script scr="form.js"> -->
	
</head>
<body>
<h1>Register CityBomber Server</h1>
<form action="#" method="GET">
	<ul>
		<li><p>IP:</p></li>
		<li><input type="text" name="ip"/></li>
		<li><p>Port:</p></li>
		<li><input type="text" name="port"/></li>
		<li><p>Name:</p></li>
		<li><input type="text" name="name"/></li>
		<li><button>Registar</button></li>
	</ul>
</form>
</body>
</html>