<?php
require_once('database.php');
require_once('session.php');

//$servers = Array("srvList" => Server::getAllServers());
echo json_encode(Array("sessionList" => Session::getAllSessions()));

?>