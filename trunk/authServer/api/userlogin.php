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
require_once('fb/facebook.php');

$userData['fbid'] = $_GET['fbid'];
$userData['id']   = $_GET['id'];
$userData['pw']   = $_GET['pw'];

// Checks if the user is logging with facebook
if($userData['fbid'] != null && strlen($userData['fbid']) > 0) {
	$user = User::getUserByFBID($userData['fbid']);
	if($user != null) {
		showResult($user);
	} else {
		// CREATE THE FACEBOOK OAUTH VAR
		$facebook = new Facebook(array(
		  'appId'  => '222377161214377',
		  'secret' => '739f81bdd32fe2ca3e7a2af767964eb2',
		));
		$userfb = $facebook->api('/'. $userData['fbid']);

		if(strlen($userfb['name']) > 0) {
			// TODO
			echo $userfb['education'][0]['school']['name'];
			//$user = User::insertUserByFBID($userData['fbid'], $userfb['name']);
		}
	}
} 
// Logging in without facebook
else if($userData['id'] != null && strlen($userData['id']) > 0 &&
		$userData['pw'] != null && strlen($userData['pw']) > 0) {
	$user = User::getUserByIDPW($userData['id'], $userData['pw']);
	if($user != null) {
		$userReturn = User::getUserByID($userData['id']);
		showResult($userReturn);
	} else {
		showError("Invalid user.", $userData);
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