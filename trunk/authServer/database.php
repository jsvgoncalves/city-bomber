<?php




function connecttoserver()
{
	   return new PDO("pgsql:host=localhost dbname=BOMBER port=60000 user=postgres password=54321");
}







?>