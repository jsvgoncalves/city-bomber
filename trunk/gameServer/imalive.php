<?php 
	require_once('includes/database.php');
	require_once('database/server.php');

	echo json_encode(Array("srvList" => Server::getServerInfo()));
		
?>