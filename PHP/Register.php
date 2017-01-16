<?php

    require_once 'DB_Functions.php';
    $db = new DB_Functions();
    $response = array();

    // receiving the post params
    $name = $_POST['name'];
    $userName = $_POST['userName'];
    $email= $_POST['email'];
    $password = $_POST['password'];

	
	 // check if user is already registered
    if ($db->doesUserExist($userName)) {
        // user already exists
        $response["success"] = FALSE;
        $response["userAlreadyExists"]=TRUE;
        $response["unknownError"]=FALSE;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($name, $userName,$email,$password);
        if ($user=true) {
            // user stored successfully
            $response["success"] = TRUE;
            echo json_encode($response);
        } else {
            // user failed to store
            $response["success"] = FALSE;
            $response["unknownError"]=TRUE;
            $response["userAlreadyExists"]=FALSE;
            echo json_encode($response);
        }
    }
      
?>