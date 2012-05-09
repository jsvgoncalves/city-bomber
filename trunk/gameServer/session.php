<?php

class Session{
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
}

?>