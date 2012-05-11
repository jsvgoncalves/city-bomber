<?php

//database constants
$user = 'postgres';
$pass = 'asd';
$dbname = 'bomber';
$host = 'localhost';
$port = '5432';

$dsn = 'pgsql:host='.$host.';port='.$port.';dbname='.$dbname;

$schema = "public";

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
