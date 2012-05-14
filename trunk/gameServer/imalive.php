<?php 
	require_once('database/database.php');
	require_once('database/server.php');

	echo json_encode(Array("srvList" => Server::getServerInfo()));
		
?>