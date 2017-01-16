<?php
	
    require_once 'DB_Functions.php';
    $db = new DB_Functions();
    $response= array();
	
    $userName = $_POST['userName'];
    $password = $_POST['password'];
    
    $user = $db->getUserByUserNameAndPassword($userName, $password);

	if($user==true){
		$response['success']=true;
		echo json_encode($response);
	}else{
		$response['success']=false;
		echo json_encode($response);
	}
?>