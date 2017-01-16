<?php

    require_once 'DB_Functions.php';
    $db = new DB_Functions();
	
    $email = $_POST['email'];
	

	$user= $db->doesEmailExist($email);
	if(is_null($user)){
		$response['emailAlreadyExists']=FALSE;
	}else{
		$response['emailAlreadyExists']=TRUE;
		$response['userName']=$user;
	}
	echo json_encode($response);
		

?>
