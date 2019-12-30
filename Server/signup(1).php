<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
//Getting values
$Name = $_POST['Name'];
$Email = $_POST['Email'];
$Cell = $_POST['Cell'];
$Add = $_POST['Address'];
$Gender = $_POST['Gender'];
$Pass = $_POST['Pass'];

$Pass = md5($Pass); //encrypted password for security

//importing init.php script
require_once('init.php');

//creating sql $sql_query
$sql = "insert into user(name,email,cell,address,gender,password) values('$Name','$Email','$Cell','$Add','$Gender','$Pass')";

$result = mysqli_query($con,"SELECT cell FROM user where cell = '$Cell'");

$num_rows = mysqli_num_rows($result);
if($num_rows>0)
{
  echo "exists";
}
else {
  if(mysqli_query($con,$sql))
  {
    echo "success";
  }
  else {
    echo mysqli_error($con);
  }
  mysqli_close($con);
}

}
  ?>
