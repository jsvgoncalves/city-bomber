<?php
/**
 * FEUP - SDIS - CityBomber
 * Author: CityBomber Dev Team
 * Date: 2012-05-09 21:26:00
 */

class Server{
	
	function getAllServers() {
	    global $dbh, $schema;
	    try {
	      $stmt = $dbh->prepare("SELECT name, ip, port FROM $schema.server");
	      $stmt->execute();
	      $result = $stmt->fetchall(PDO::FETCH_ASSOC);
	      return $result;
	    }
	    catch(PDOException $e) {
	      echo $e->getMessage();
	      die;
	    }
  	}

  	/**
  	 * Returns all active servers.
  	 */
  	function getAllActiveServers() {
	    global $dbh, $schema;
	    try {
	      $stmt = $dbh->prepare("SELECT name, ip, port 
	      						FROM $schema.server
	      						WHERE lastUpdated > CURRENT_TIMESTAMP - time '03:00'");
	      $stmt->execute();
	      $result = $stmt->fetchall(PDO::FETCH_ASSOC);
	      return $result;
	    }
	    catch(PDOException $e) {
	      echo $e->getMessage();
	      die;
	    }
  	}


	function getServerByIPAndPort($serverData) {
	    global $dbh, $schema;
	    try {
	      $stmt = $dbh->prepare("SELECT * 
	        FROM $schema.Server 
	        WHERE $schema.Server.ip = :serverIP
	        AND $schema.Server.port = :serverPort");
	      $stmt->bindParam(':serverIP', $serverData['ip'], PDO::PARAM_INT);
	      $stmt->bindParam(':serverPort', $serverData['port'], PDO::PARAM_INT);
	      $stmt->execute();
	      $result = $stmt->fetch(PDO::FETCH_ASSOC);
	      return ($result);
	    }
	    catch(PDOException $e) {
	      echo $e->getMessage();
	      die;
    	}
	}

	
	function registerServer($serverData) {
	    global $dbh, $schema;
	    try {
	      $sql = "INSERT INTO $schema.Server
	      				(ip, port, name, lastUpdated) 
	      			VALUES 
	      				(?, ?, ?, CURRENT_TIMESTAMP)";
	      $stmt = $dbh->prepare($sql);
	      $stmt->execute(array($serverData['ip'], $serverData['port'], $serverData['name']));
	      $count = $stmt->rowCount();
	      return $count;
	    }
	    catch(PDOException $e) {
	      echo $e->getMessage();
	      die;
	    }
	}

	function updateServer($serverData) {
	    global $dbh, $schema;
	    try {
            $sql = "UPDATE $schema.Server 
              SET lastUpdated = CURRENT_TIMESTAMP 
              WHERE ip = ? AND port = ?";
      		$stmt = $dbh->prepare($sql);
      		$stmt->execute(array($serverData['ip'], $serverData['port']));
      		$count = $stmt->rowCount();
      		return $count;
	    }
	    catch(PDOException $e) {
	      echo $e->getMessage();
	      die;
	    }
	}

}

?>