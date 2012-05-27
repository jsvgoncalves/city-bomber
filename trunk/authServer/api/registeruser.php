<?php
/**
 * FEUP - SDIS - CityBomber
 * Author: CityBomber Dev Team
 * Date: 2012-05-09 22:35:00
 */

/**
 * This PHP script is an integrant part of the CityBomber AuthServer REST API.
 * The script receives server information by GET method, then proceeds to proper verifications
 * and inserts the server record in the database  .
 */

/**
 * Reponse format is shown below:
 *
 */


// TODO: 	Se o server já existir? Deve fazer update?
//			Dar uma key ao server para ele poder alterar dados?
//			Dar opçao de apagar o registo do server?
//			Verificar estado do server -> ping | usar a api (criar rest script imalive.php)
require_once('includes/database.php');
require_once('database/user.php');

$userData['username'] = $_GET["username"];
$userData['password'] = $_GET["pw"];
$userData['email']    = $_GET["email"];

// Check input data
if( $userData['username'] != null && $userData['password']!= null && $userData['email'] != null){
	// Check server redundancy
	if(User::getUserByMail($userData['email']) == null){
	    $userData['salt'] = substr(md5(uniqid(rand(), true)) , 0, 32);
      	$hpassword = hash('sha512', $userData['salt'] . $userData['password']);
      	$userData['password'] = $hpassword;
		$register = User::insertUserByEmail($userData);
		if($register == 1){
			showResult($result, $userData);
		} else {
			showError("Critical error registering user.", $userData);
		}
	} else {
		showError("Email already in use.", $userData);
	}

} else {
	showError("Invalid data.", $userData);
}

function showError($error, $userData){
	echo json_encode(Array("error" => $error));
	die;
}

function showResult($result, $userData){
	echo json_encode(Array("success" => "User registered."));
	die;
}

?>