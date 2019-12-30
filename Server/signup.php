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

$a = rand(100,999999);
$to = $Email;
$subject = "Your Registration Varification Code";
$message = $a;
$header = "From:hackingpro20161@gmail.com \r\n";
$header = "Cc:hackingpro20161@gmail.com \r\n";
$header .= "MIME-Version: 1.0\r\n";
$header .= "Content-type: text/html\r\n";
         
$retval = mail ($to,$subject,$message,$header);

//importing init.php script
require_once('init.php');

if($retval==true)
{
	//creating sql $sql_query
$sql = "insert into user(name,email,cell,address,gender,password,value) values('$Name','$Email','$Cell','$Add','$Gender','$Pass','$a')";

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

}
  ?>
