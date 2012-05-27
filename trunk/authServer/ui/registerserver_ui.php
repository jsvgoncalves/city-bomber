<?php 
	require_once('cfg/cfg.php');
	$ip   = $_GET["ip"];
	$port = $_GET["port"];
	$name = urlencode($_GET["name"]);
	// isset() wont work like this (string = "")
	if($ip != null && $port != null && $name != null){
		$string = "ip=$ip&port=$port&name=$name";
		$hostPath = $serverURL . "api/registerserver.php?";
		
		//Tries to register the server..
		$msg =  file_get_contents($hostPath . $string);
	}
?>

<!DOCTYPE HTML>
<html>
<head>
	<title>Register CityBomber Server</title>
	<!--<script scr="form.js"> -->
	<link rel="stylesheet" href="css/style.css"/>
</head>
<body>
	<div id="wrapper">
		<div class="header"><header><h1>Register CityBomber Server</h1></header></div>
		<div id="content">
			<p>Please enter the GameServer data.</p>
			<form action="#" method="GET">
				<ul>
					<li><p>IP</p><input type="text" name="ip" id="ip" value="<?php echo $_SERVER['REMOTE_ADDR']; ?>"/></li>
					<li><p>Port</p><input type="text" name="port" id="port"/></li>
					<li><p>Name</p><input type="text" name="name" id="name"/></li>
					<li><button>Registar</button></li>
				</ul>
			</form>
			<p><?php echo $msg; ?></p>
		</div>
	</div>
</body>
</html>