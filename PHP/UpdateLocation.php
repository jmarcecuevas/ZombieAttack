<?php

    require_once 'DB_Functions.php';
    $db = new DB_Functions();
	
    $userName = $_POST['userName'];
    $latitude=$_POST['latitude'];
    $longitude=$_POST['longitude'];
    $radius=$_POST['radius'];
	

   $db->update($userName,$latitude,$longitude,$radius);
	

?>