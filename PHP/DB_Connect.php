<?php

require_once 'Config.php';

    class DB_Connect{
        public $conn;
        
        public function connect(){
			try{
				//Connecting to mysql database
				$this->conn= new PDO('mysql:host=mysql.hostinger.com.ar; dbname=u996394102_loc','u996394102_usu','123456');
				$this->conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
				return $this->conn;
			}catch(Exception $e){
				echo 'La linea de error es: ' . $e->getMessage();
			}
        }
    }
 ?>