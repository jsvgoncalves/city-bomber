<?php 
	require_once('cfg/cfg.php');
	$msg =  file_get_contents($serverURL . "api/getservers.php");
	$servers = json_decode($msg, true);
?>

<!DOCTYPE HTML>
<html>
<head>
	<title>CityBomber Server List</title>
	<link rel="stylesheet" href="css/style.css"/>
</head>
<body>
	<div id="wrapper">
		<div class="header"><header><h1>CityBomber Server List</h1></header></div>
		<div id="content">
				<?php foreach($servers["srvList"] as $server){ ?>
					<ul>
						<li><h3><?php echo $server["name"]; ?></h3></li>
						<li><?php echo $server["ip"] . ":" . $server["port"]; ?></li>
					</ul>
				<?php } ?>
		</div>
		<div style="margin: 0 auto"><p style="text-align: center">All active servers are shown above. If you don't find your server here maybe it
			is not responding to the authentication server requests.</p></div>
	</div>
</body>
</html>