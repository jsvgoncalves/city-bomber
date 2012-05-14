<?php 
// start or reuse session
session_start();

if (isset($_SESSION["s_uname"])) {
	echo json_encode(Array("Error" => "Already connected."));
	die;
}

?>