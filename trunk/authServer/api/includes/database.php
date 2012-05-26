<?php

//database constants
$user = 'lbaw11103';	 //CHANGE ME
$pass = 'sX796cu';	 //CHANGE ME
$dbname = 'lbaw11103';	 //CHANGE ME
$host = 'vdbm.fe.up.pt';
//$host = 'localhost';

$dsn = 'pgsql:host='.$host.';dbname='.$dbname;

$schema = "bomber";

try {
	$dbh = new PDO($dsn, $user, $pass);
	$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
	$error = "ERRO[00]: ".$e->getMessage();
	//echo $e->getMessage();
	echo json_encode(Array("error" => "Database connnection failed."));
	die;
}
?>
