<?php

//database constants
$user = 'postgres';	 //CHANGE ME
$pass = 'dfpw';	 //CHANGE ME
$dbname = 'bomber';	 //CHANGE ME
$host = 'localhost';
$port = '5432';

$dsn = 'pgsql:host='.$host.';port='.$port.';dbname='.$dbname;

$schema = "public";

// get a (not persistent) database connection
try {
	$dbh = new PDO($dsn, $user, $pass);
	$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
	$error = "ERRO[00]: ".$e->getMessage();
	echo $e->getMessage();
	die;
}

?>
