<?php
/**
 * FEUP - SDIS - CityBomber
 * Author: CityBomber Dev Team
 * Date: 2012-05-09 21:26:00
 */

class User{
	

	function getUserByIDPW($id, $password) {
	    global $dbh, $schema;
	    try {
	      $stmt = $dbh->prepare("SELECT * 
	        FROM $schema.BomberUser 
	        WHERE $schema.BomberUser.id = :id");
	      $stmt->bindParam(':id', $id);
	      $stmt->execute();
	      // get next row as an array indexed by column name
	      $result = $stmt->fetch(PDO::FETCH_ASSOC);
	     // $preHash   = hash('sha512', $password);
	      $hpassword = hash('sha512', $result["salt"] . $password);
	      if ($result["password"] == $hpassword) 
		       return true;
	      else 
		       return false;
	    }
	    catch(PDOException $e) {
	      $_SESSION["s_errors"]["generic"][] = "ERRO[36]: ".$e->getMessage();
	      header("Location: index.php");
	      die;
	    }
	}

	function getUserByFBID($fbID) {
	    global $dbh, $schema;
	    try {
	      $stmt = $dbh->prepare("SELECT id, username, email, fbID, faceUser 
	        FROM $schema.BomberUser 
	        WHERE $schema.BomberUser.fbID = :fbID");
	      $stmt->bindParam(':fbID', $fbID, PDO::PARAM_INT);
	      $stmt->execute();
	      $result = $stmt->fetch(PDO::FETCH_ASSOC);
	      return ($result);
	    }
	    catch(PDOException $e) {
	      $_SESSION["s_errors"]["generic"][] = "ERRO[22]: ".$e->getMessage();
	      echo $e->getMessage();
	      die;
    	}
	}

	function getUserByID($ID) {
	    global $dbh, $schema;
	    try {
	      $stmt = $dbh->prepare("SELECT id, username, email, fbID, faceUser 
	        FROM $schema.BomberUser 
	        WHERE $schema.BomberUser.ID = :ID");
	      $stmt->bindParam(':ID', $ID, PDO::PARAM_INT);
	      $stmt->execute();
	      $result = $stmt->fetch(PDO::FETCH_ASSOC);
	      return ($result);
	    }
	    catch(PDOException $e) {
	      $_SESSION["s_errors"]["generic"][] = "ERRO[22]: ".$e->getMessage();
	      echo $e->getMessage();
	      die;
    	}
	}

	function insertUserByFBID($userData) {
	    global $dbh, $schema;
	    try {
	      $sql = "INSERT INTO Server
	      				(ip, port, name, lastUpdated) 
	      			VALUES 
	      				(?, ?, ?, CURRENT_TIMESTAMP)";
	      $stmt = $dbh->prepare($sql);
	      $stmt->execute(array($userData['ip'], $userData['port'], $userData['name']));
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