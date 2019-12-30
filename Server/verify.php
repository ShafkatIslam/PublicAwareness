<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
//Getting values

$Email = $_POST['Email'];
$Verify = $_POST['Verify'];

$Verify = "yes";

//importing init.php script
require_once('init.php');

//creating sql $sql_query
$sql = "UPDATE user SET verify = '$Verify' WHERE email='$Email'";

if(mysqli_query($con,$sql))
  {
    echo "success";
  }
  else {
    echo mysqli_error($con);
  }
  mysqli_close($con);

}
  ?>
