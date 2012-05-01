<?php 
	//phpinfo();
	ini_set('display_errors', 'On');
	error_reporting(E_ALL);
	$authHost = "localhost"; //192.168.1.88";
	$authPort = "8080";

	require_once('database.php');

	$sql = "SELECT * FROM bombs.server";
	$res = $dbh->query($sql);

	$result=$res->fetch();

	$url = "http://192.168.1.88:".$authPort."/registerserver.php";
	$postFields = array(
		"servername" => $result["servername"],
		"serverip" => $result["serverip"],
		"serverport" => $result["serverport"]);
	$opts = array('http' =>
	    array(
	        'method'  => 'POST',
	        'header'  => 'Content-type: application/x-www-form-urlencoded',
	        'content' => $postFields));

	//$context  = stream_context_create($opts);

	//$result = file_get_contents($url, false, $context);
	//echo 1;
		//open connection
	$ch = curl_init();
	//echo 2;
	//set the url, number of POST vars, POST data
	curl_setopt($ch,CURLOPT_URL,$url);
	curl_setopt($ch,CURLOPT_POST,count($postFields));
	curl_setopt($ch,CURLOPT_POSTFIELDS,$postFields);
	curl_setopt($ch,CURLOPT_RETURNTRANSFER,1);
	//execute post
	
	$results = curl_exec($ch);



	echo $results;	
?>