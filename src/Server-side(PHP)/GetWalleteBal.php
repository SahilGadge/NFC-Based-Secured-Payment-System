<?php
    require('DatabaseConnection.php');
    
    $id= $_POST["id"];
    
    $statement = mysqli_prepare($con, "SELECT id,Balance FROM UserDetails WHERE id= ? ");
    mysqli_stmt_bind_param($statement, "s", $id);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement,$id,$Balance);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] =true;  
        $response["id"]= $id;
        $response["Balance"]= $Balance;
       
    }
    
    echo json_encode($response);
?>