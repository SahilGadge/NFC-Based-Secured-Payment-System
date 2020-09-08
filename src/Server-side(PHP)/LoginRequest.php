<?php
    require_once('DatabaseConnection.php');
    
    $Email= $_POST["email"];
    $Password = $_POST["password"];
 
    $statement = mysqli_prepare($con, "SELECT id,Name,Email,Contact,Question1,Answer1,Question2,Answer2 FROM UserDetails WHERE Email= ? AND Password = ?");
    mysqli_stmt_bind_param($statement, "ss", $Email,$Password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement,$id,$Name,$Email,$Contact,$Question1,$Answer1,$Question2,$Answer2);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] =true;  
        $response["id"]= $id;

        $response["Name"]= $Name;
        $response["Email"]= $Email;
        $response["Contact"]= $Contact;
        $response["Question1"]= $Question1;
        $response["Answer1"]= $Answer1;
        $response["Question2"]= $Question2;
        $response["Answer2"]= $Answer2;

    }
    
    echo json_encode($response);
?>
