<?php
	
	class DB_Functions{
        private $conn;
        
	function __construct() {
            require_once 'DB_Connect.php';
            $db= new DB_Connect();
            $this->conn= $db->connect();
        }
		 
		public function storeUser($name, $userName, $email ,$password) {
			$hash= password_hash($password, PASSWORD_DEFAULT);
			$query="INSERT INTO location values(?,?,?,?,GeomFromText('POINT(0 0)'));";
			$result= $this->conn->prepare($query);
			$result->execute(array($name,$userName,$email,$hash));
			$result->closeCursor();
			if($result->rowCount>0)
				return true;
			else
				return false;
    	}
		 
		/**
		 * Check whether user exist
		 */
		public function doesUserExist($userName) {
			$query="SELECT userName from location WHERE userName = ?";
			$result= $this->conn->prepare($query);
			$result->execute(array($userName));
			$result->closeCursor();
			if($result->rowCount()>0){
				return true;
			}else{
				return false;
			}
		}
		 
		public function doesEmailExist($email){
			$query="SELECT userName from location WHERE email=?";
			$result=$this->conn->prepare($query);
			$result->execute(array($email));
			
			$row=$result->fetch();
			if($row){
				return $row['userName'];
			}else
				return null;
		}
		 
		public function getUserByUserNameAndPassword($userName, $password) {
			$query="SELECT user,userName,userPass FROM location WHERE userName = ? ";
			$result=$this->conn->prepare($query);
			$result->execute(array($userName));
			$array_result= $result->fetchAll(PDO::FETCH_ASSOC);
			
			foreach($array_result as $element){
				if(password_verify($password,$element['userPass'])){
					return true;
				}
			}
			return false;
	 	}
		 
		public function update($userName,$latitude,$longitude,$radius){
			$query="UPDATE location SET lat_lng=GeomFromText('POINT($latitude $longitude)') WHERE userName =?";
			$result=$this->conn->prepare($query);
			$result->execute(array($userName));
			
			$nearby_users_query= "SELECT userName, X(lat_lng), Y(lat_lng), distance 
							  FROM (
								SELECT userName, lat_lng,r,
									   units * DEGREES(
								ATAN2(
								  SQRT(
									POW(COS(RADIANS(latpoint))*SIN(RADIANS(longpoint-Y(lat_lng))),2) +
									POW(COS(RADIANS( X(lat_lng) ))*SIN(RADIANS(latpoint)) -
										 (SIN(RADIANS( X(lat_lng) ))*COS(RADIANS(latpoint)) *
										  COS(RADIANS(longpoint- Y(lat_lng) ))) ,2)),
								  SIN(RADIANS( X(lat_lng) ))*SIN(RADIANS(latpoint)) +
								  COS(RADIANS( X(lat_lng) ))*COS(RADIANS(latpoint))*COS(RADIANS(longpoint- Y(lat_lng) )))) AS distance
								  FROM location
								  JOIN (
										 SELECT '$latitude'  AS latpoint, '$longitude' AS longpoint,
										 '$radius' AS r, 111.045 AS units
									   ) AS p ON (1=1)
								 WHERE MBRCONTAINS(GEOMFROMTEXT(
										 CONCAT('LINESTRING(',
												   latpoint-(r/units),' ',
												   longpoint-(r /(units* COS(RADIANS(latpoint)))),
												   ',',
												   latpoint+(r/units) ,' ',
												   longpoint+(r /(units * COS(RADIANS(latpoint)))),
												')')),  lat_lng)
								   ) AS d
							 WHERE distance <= r AND userName <> ? 
							 ORDER BY distance";
			
			
			$nearby_users_result=$this->conn->prepare($nearby_users_query);
			$nearby_users_result->execute(array($userName));
			
			$response= array();
			while($row=$nearby_users_result->fetch(PDO::FETCH_ASSOC)){
				array_push($response,array("userName"=>$row['userName'],"lat"=>$row['X(lat_lng)'],"lng"=>$row['Y(lat_lng)']));
			}
			echo json_encode($response);
			
			
		}
}

?>