<?php


$name= $_POST["name"];
$email = $_POST["email"];
$contact= $_POST["contact"];
$password = $_POST["password"];
$ques1 = $_POST["ques1"];
$ans1 = $_POST["ans1"];
$ques2 = $_POST["ques2"];
$ans2 = $_POST["ans2"];
$bal= "00";

    require('DatabaseConnection.php');

if ($con->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

	$sql = "INSERT INTO UserDetails (Name, Email, Contact, Password,Question1,Answer1,Question2,Answer2,Balance) VALUES  ('$name', '$email', '$contact', '$password','$ques1', '$ans1', '$ques2', '$ans2','$bal')";
	

if ($con->query($sql) === TRUE) 
{
     $response["success"] = true;  
    
    echo json_encode($response);
} 
else 
{
 $response["success"] = false;  
    
    echo json_encode($response);
    echo "Error: " . $sql . "<br>" . $con->error;
}

$con->close();
?>
