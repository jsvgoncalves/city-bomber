<?php

class GameSession{
	function getAllSessions() {
	    global $dbh;
	    try {
	      // using prepared statements will help protect you from SQL injection
	      $stmt = $dbh->prepare("SELECT sid, name, maxplayers FROM session");
	      $stmt->execute();
	      // get array containing all of the result set rows 
	      $result = $stmt->fetchall(PDO::FETCH_ASSOC);
	      return $result;
	    }
	    catch(PDOException $e) {
	      $_SESSION["s_errors"]["generic"][] = "ERRO[11]: ".$e->getMessage();
	      header("Location: ../../index.php");
	      die;
	    }
  	}

  	function newSession($name, $maxplayers)
  	{
	    global $dbh, $schema;
	    try {
	      $sql = "INSERT INTO session (name, maxplayers) VALUES (?, ?)";
	      $stmt = $dbh->prepare($sql);
	      $stmt->execute(array($name, $maxplayers));
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