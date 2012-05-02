<?php



require_once("database.php");


function getservers()
{
	
	try {
		$db = connecttoserver();
		$res=$db->query("SELECT name,ip,port FROM server.\"Server\"");
		$result=$res->fetchall();
		$aservers=Array();
		foreach($result as $row)
		{
			$arr=Array( "name" => $row["name"],"ip" => $row["ip"], "port" => $row["port"]);
			$aservers[]=$arr;
		}
		
		$result=Array("srvList" =>$aservers);
		
		echo json_encode($result);
		
		
	} catch (PDOException  $e) { 
	   echo $e->getMessage();
	  
	}
}


getservers();




?>