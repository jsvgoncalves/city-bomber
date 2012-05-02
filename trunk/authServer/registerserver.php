<?php
ini_set('display_errors', 'On');
error_reporting(E_ALL);


require_once("database.php");



function registerserver()
{
	try{
	
		if ( $_SERVER['REQUEST_METHOD'] === 'POST' )
		{ 
				
				$db=connecttoserver();
				$sth=$db->prepare("SELECT nome FROM server.\"Server\" WHERE name=:name");
				$sth->execute();
				$result=$sth->fetch();
				
				if($result==null)
				{
					echo -1;
					return;
				}

				
				$servername=$_POST["servername"];
				$serverip=$_POST["serverip"];
				$serverport=$_POST["serverport"];
				$db=connecttoserver();
				$sth=$db->prepare("INSERT INTO server.\"Server\" (name,ip,port) VALUES (:name,:ip,:port)");
				$sth->bindParam(':name',$servername);
				$sth->bindParam(':ip',$serverip);
				$sth->bindParam(':port',$serverport);
				$sth->execute();
				
				echo 1;
				
		}else
		{
		echo -1;
		}
		
	}catch (PDOException  $e) { 
	   //echo $e->getMessage();
	    echo -1;
	}

}

registerserver();



?>