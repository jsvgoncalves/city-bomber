<?php 

/**
* User session on the server.
*/
class Session
{
	
	function newUser()
	{
		//TODO Do connecting stuff here...
		$string = md5(uniqid(rand(), true));
	    $string = substr($string, 0, 32);
		$user["name"] = $string;
		return $user;

	}
}

?>