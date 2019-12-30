<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
//Getting values

$Username = $_POST['Name'];
$People = $_POST['People'];
$Address = $_POST['Location'];
$Details = $_POST['Details'];
$Time = $_POST['Time'];
$Date = $_POST['Date'];
$Latitude = $_POST['Latitude'];
$Longitude = $_POST['Longitude'];
$Type = $_POST['Type'];
$Encoded_String = $_POST["Encoded_String"];
$Paths = $_POST["Path"];


//$password = md5($password1); //encrypted password for security

//importing init.php script
require_once('init.php');

$DefaultId = 0;
$GetOldIdSQL ="SELECT id FROM emergency ORDER BY id ASC";
$Query = mysqli_query($con,$GetOldIdSQL);
 
while($row = mysqli_fetch_array($Query)){
 
$DefaultId = $row['id'];
}

$upload_path = "images/$DefaultId.jpg";
$actualpath = "$upload_path";


//creating sql $sql_query
$sql = "insert into emergency(Type,Details,Location,People,Date,Time,Name,Latitude,Longitude,Image_Name,Path) values('$Type','$Details','$Address','$People','$Date','$Time','$Username','$Latitude','$Longitude','$Encoded_String','$actualpath')";

//$result = mysqli_query($con,"SELECT cell FROM user where cell = '$Cell'");

if(mysqli_query($con,$sql))
  {
   file_put_contents($upload_path,base64_decode($Paths));
   echo "success";
  }
else {
    echo mysqli_error($con);
  }
mysqli_close($con);
//}


}
  ?>
