<?php
require('DatabaseConnection2.php');

$updatedBalance= $_POST["updatedBalance"];
$id= $_POST["id"];

$con = new mysqli($host, $uname, $pwd, $db);
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 

$sql = "UPDATE UserDetails SET Balance='$updatedBalance' WHERE id='$id'";

if ($con->query($sql) === TRUE) {
     $response["success"] = true;  
    echo json_encode($response);

    echo "Record updated successfully";
} else {
 $response["success"] = false;  
    echo json_encode($response);

    echo "Error updating record: " . $con->error;
}

$con->close();
?>
