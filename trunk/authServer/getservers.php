<?php



require_once("database.php");


try {
	$res=$dbh->query("SELECT name,ip,port FROM public.server");
	$result=$res->fetchall();
	$aservers=Array();
	foreach($result as $row)
	{
		$arr=Array( "name" => $row["name"],"ip" => $row["ip"], "port" => $row["port"]);
		$aservers[]=$arr;
	}
	
	$result=Array("srvList" =>$aservers);
	
	echo json_encode($result);
	
	
} catch (Exception $e) { 
   echo $e->getMessage();
}




?>