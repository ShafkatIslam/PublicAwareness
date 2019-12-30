<?php

 if($_SERVER['REQUEST_METHOD']=='GET'){

 //$get_text= $_GET['text'];

 require_once('init.php');

 $sql = "SELECT * FROM emergency";

/*
if(strlen($get_text)>0)
{
  $sql = "SELECT * FROM contacts WHERE name LIKE '%$get_text%' AND user_cell='".$usercell."' ORDER BY name ASC";
}
*/
 $r = mysqli_query($con,$sql);

// $res = mysqli_fetch_array($r);

 $result = array();


while($res = mysqli_fetch_array($r))
        {

		//Pushing msg and date in the blank array created
		array_push($result,array(
		                "Type"=>$res['Type'],
		            	"Details"=>$res['Details'],
		            	"Location"=>$res['Location'],
                  "People"=>$res['People'],
                  "Date"=>$res['Date'],
                  "Time"=>$res['Time'],
                  "Name"=>$res['Name'],
                  "Latitude"=>$res['Latitude'],
                  "Longitude"=>$res['Longitude'],

		));
	}

 echo json_encode(array("result"=>$result));

 mysqli_close($con);

 }
 ?>