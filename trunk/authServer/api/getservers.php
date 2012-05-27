<?php

/**
 * FEUP - SDIS - CityBomber
 * Author: CityBomber Dev Team
 * Date: 2012-05-09 21:26:00
 */

/**
 * This PHP script is an integrant part of the CityBomber AuthServer REST API.
 * The script checks which servers are currently considered inactive, and tries to query them.
 * Afterwards, it returns a JSON formatted answer with all available game servers.
 */

/**
 * Reponse format is shown below:
 *
 *	{
 *  	"srvList" : [
 *		{ "name" : "server 1", "ip" : "127.0.0.1", "port" : 15000},
 *		{ "name" : "server 2", "ip" : "127.0.0.5", "port" : 15040}
 *  	]
 *	}
 *
 */

require_once('includes/database.php');
require_once('database/server.php');

echo json_encode(Array("srvList" => Server::getAllActiveServers()));

?>