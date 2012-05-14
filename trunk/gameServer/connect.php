<?php

require_once('includes/checknotlogin.php');

require_once('includes/database.php');
require_once('includes/session.php');

$user = Session::newUser();
$_SESSION["s_uname"] = $user["name"];

if($user["name"] != ""){
	echo json_encode(Array("login" => $user));
} else {
	echo json_encode(Array("login" => "Connection failed."));
}

?>
