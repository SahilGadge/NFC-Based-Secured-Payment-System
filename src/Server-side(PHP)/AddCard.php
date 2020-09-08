<?php

$userid= $_POST["userid"];
$brand= $_POST["cardbrand"];
$holdername= $_POST["holdername"];
$cardcvv = $_POST["cardcvv"];
$cardnumber= $_POST["cardnumber"];
$expiry = $_POST["expiry"];
$balance= "0";


require_once('DatabaseConnection.php');

if ($con->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

	$sql = "INSERT INTO CardDetails (UserId,Name,CardNumber,CVV, Expiry,Brand,Balance) VALUES  ('$userid','$holdername', '$cardnumber', '$cardcvv', '$expiry', '$brand', '$balance')";
	
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
