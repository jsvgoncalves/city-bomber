<?php
require_once('includes/checklogin.php');

require_once('includes/database.php');
require_once('database/gamesession.php');

echo json_encode(Array("sessionList" => GameSession::getAllSessions()));

?>