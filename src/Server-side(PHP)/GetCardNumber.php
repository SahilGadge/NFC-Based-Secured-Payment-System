<?php


$id=$_POST["id"];
require('DatabaseConnection2.php');

mysqli_select_db($con,$db) or die("db selection failed");

$r=mysqli_query($con,"select * from CardDetails where UserId='$id'");

while($row=mysqli_fetch_assoc($r))
{
$cls[]=$row;

}

echo json_encode($cls);

mysqli_close($con);

?>