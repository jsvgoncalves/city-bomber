<?php 
	require_once('database.php');

	$sql = "SELECT * FROM bombs.server";
	$res = $dbh->query($sql);

	$result=$res->fetch();

	$register = array("srvRegister" => array(
		"name" => $result["servername"],
		"ip" => $result["serverip"],
		"port" => $result["serverport"]));

	echo json_encode($register);
		
?>