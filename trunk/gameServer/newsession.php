<?php 
	require_once('includes/checklogin.php');
	
	require_once('includes/database.php');
	require_once('database/gamesession.php');

	$session_name = $_GET["name"];
	$max_players = $_GET["max"];
	$user_id = $_GET["userid"];

	if(isset($session_name, $user_id)){
		if(GameSession::newSession($session_name, $max_players) == 1){
			echo '{"session":"'.$session_name.'"}';
		} else {
			echo '{"Error":"Database failure. Session not created."}';
		}
	}
	else {
		echo '{"Error":"Invalid URL."}';
	}

 ?>