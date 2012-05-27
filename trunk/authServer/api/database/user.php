<?php
/**
 * FEUP - SDIS - CityBomber
 * Author: CityBomber Dev Team
 * Date: 2012-05-09 21:26:00
 */

class User{
	

	function getUserByMailPW($email, $password) {
	    global $dbh, $schema;
	    try {
	      $stmt = $dbh->prepare("SELECT * 
	        FROM $schema.BomberUser 
	        WHERE $schema.BomberUser.email = :email");
	      $stmt->bindParam(':email', $email);
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
	      echo $e->getMessage();
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
	      echo $e->getMessage();
	      die;
    	}
	}

	function getUserByMail($email) {
	    global $dbh, $schema;
	    try {
	      $stmt = $dbh->prepare("SELECT id, username, email, fbID, faceUser 
	        FROM $schema.BomberUser 
	        WHERE $schema.BomberUser.email = :email");
	      $stmt->bindParam(':email', $email, PDO::PARAM_INT);
	      $stmt->execute();
	      $result = $stmt->fetch(PDO::FETCH_ASSOC);
	      return ($result);
	    }
	    catch(PDOException $e) {
	      echo $e->getMessage();
	      die;
    	}
	}

	function insertUserByEmail($userData) {
	    global $dbh, $schema;
	    try {
	      $sql = "INSERT INTO $schema.BomberUser
	      				(username, email, password, salt) 
	      			VALUES 
	      				(?, ?, ?, ?)";
	      $stmt = $dbh->prepare($sql);
	      $stmt->execute(array($userData['username'], $userData['email'], $userData['password'], $userData['salt']));
	      $count = $stmt->rowCount();
	      return $count;
	    }
	    catch(PDOException $e) {
	      echo $e->getMessage();
	      die;
	    }
	}

	function insertUserByFBID($userData) {
	    global $dbh, $schema;
	    try {
	      $sql = "INSERT INTO $schema.BomberUser
	      				(username, email, fbid, faceUser, password, salt) 
	      			VALUES 
	      				(?, ?, ?, ?, ?, ?)";
	      $stmt = $dbh->prepare($sql);
	      $stmt->execute(array($userData['username'], $userData['email'], $userData['fbid'], TRUE, $userData['password'], $userData['salt']));
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