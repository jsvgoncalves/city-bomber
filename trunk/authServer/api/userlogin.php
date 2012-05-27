<?php
/**
 * FEUP - SDIS - CityBomber
 * Author: CityBomber Dev Team
 * Date: 2012-05-09 22:35:00
 */

/**
 * This PHP script is an integrant part of the CityBomber AuthServer REST API.
 * The script receives user information by GET method, then proceeds to proper verifications.
 */

/**
 * Reponse format is shown below:
 *
 */

// TODO
// INSERT USER IF FBID DOESNT EXISTS
// CHECK USERNAME AND PW WHEN USER LOGS WITHOUT FB


require_once('includes/database.php');
require_once('database/user.php');

$userData['fbid'] = $_GET['fbid'];
$userData['email']   = $_GET['email'];
$userData['pw']   = $_GET['pw'];
$userData['username']   = $_GET['name'];

// Checks if the user is logging with facebook
if($userData['fbid'] != null && strlen($userData['fbid']) > 0) {
	$user = User::getUserByFBID($userData['fbid']);
	if($user != null) {
		showResult($user);
	} else {
		// Checking if email was sent sent.
		if( strlen($userData['email']) > 0 && 
			strlen($userData['username']) > 0) {

			$userData['password'] = "IMPOSSIBRU";
			$userData['salt'] = "MARINE";
			$user = User::insertUserByFBID($userData);
			$userReturn = User::getUserByMail($userData['email']);
			showResult($userReturn);
		} else {
			showError("No such fb user and not enough data to register.");
		}
	}
} 
// Logging in without facebook
else if($userData['email'] != null && strlen($userData['email']) > 0 &&
		$userData['pw'] != null && strlen($userData['pw']) > 0) {
	$user = User::getUserByMailPW($userData['email'], $userData['pw']);
	if($user != null) {
		$userReturn = User::getUserByMail($userData['email']);
		showResult($userReturn);
	} else {
		showError("Invalid login data.", $userData);
	}
} 
// Invalid GET data.
else {
	showError("Invalid data.", $userData);
}

function showError($error, $userData){
	echo json_encode(Array("error" => $error));
	die;
}

function showResult($userData){
	echo json_encode(Array("loginUser" => $userData));
	die;
}

?>