<?php
require_once('database.php');
require_once('session.php');

echo json_encode(Array("sessionList" => Session::getAllSessions()));

?>