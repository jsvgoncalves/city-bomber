<?php
/**
 * FEUP - SDIS - CityBomber
 * Author: CityBomber Dev Team
 * Date: 2012-05-09 21:26:00
 */

class Server{
	
	function getAllServers() {
	    global $dbh;
	    try {
	      $stmt = $dbh->prepare("SELECT name, ip, port FROM server");
	      $stmt->execute();
	      $result = $stmt->fetchall(PDO::FETCH_ASSOC);
	      return $result;
	    }
	    catch(PDOException $e) {
	      $_SESSION["s_errors"]["generic"][] = "ERRO[11]: ".$e->getMessage();
	      header("Location: ../../index.php");
	      die;
	    }
  	}

  	/**
  	 * Returns all active servers.
  	 */
  	function getAllActiveServers() {
	    global $dbh;
	    try {
	      $stmt = $dbh->prepare("SELECT name, ip, port 
	      						FROM server
	      						WHERE lastUpdated > CURRENT_TIMESTAMP - time '03:00'");
	      $stmt->execute();
	      $result = $stmt->fetchall(PDO::FETCH_ASSOC);
	      return $result;
	    }
	    catch(PDOException $e) {
	      $_SESSION["s_errors"]["generic"][] = "ERRO[11]: ".$e->getMessage();
	      header("Location: ../../index.php");
	      die;
	    }
  	}


	function getServerByIPAndPort($serverData) {
	    global $dbh, $schema;
	    try {
	      $stmt = $dbh->prepare("SELECT * 
	        FROM Server 
	        WHERE Server.ip = :serverIP
	        AND Server.port = :serverPort");
	      $stmt->bindParam(':serverIP', $serverData['ip'], PDO::PARAM_INT);
	      $stmt->bindParam(':serverPort', $serverData['port'], PDO::PARAM_INT);
	      $stmt->execute();
	      $result = $stmt->fetchall(PDO::FETCH_ASSOC);
	      return ($result);
	    }
	    catch(PDOException $e) {
	      $_SESSION["s_errors"]["generic"][] = "ERRO[22]: ".$e->getMessage();
	      echo $e->getMessage();
	      die;
    	}
	}

	
	function registerServer($serverData) {
	    global $dbh, $schema;
	    try {
	      $sql = "INSERT INTO Server
	      				(ip, port, name, lastUpdated) 
	      			VALUES 
	      				(?, ?, ?, CURRENT_TIMESTAMP)";
	      $stmt = $dbh->prepare($sql);
	      $stmt->execute(array($serverData['ip'], $serverData['port'], $serverData['name']));
	      $count = $stmt->rowCount();
	      return $count;
	    }
	    catch(PDOException $e) {
	      $errmsg = $e->getMessage();
	      // parse errmsg
	      $errors["generic"][] = "ERRO[14]: ".$errmsg;
	      return $errors;
	    }
	}
}

?>