<?php
    require('DatabaseConnection.php');
    
    $Email= $_POST["rec_email"];

    $statement = mysqli_prepare($con, "SELECT id,Name,Email,Contact FROM UserDetails WHERE Email= ?");
    mysqli_stmt_bind_param($statement, "s", $Email);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement,$id,$Name,$Email,$Contact);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] =true;  
        $response["id"]= $id;
        $response["Name"]= $Name;
        $response["Email"]= $Email;
        $response["Contact"]= $Contact;
    }
    
    echo json_encode($response);
?>