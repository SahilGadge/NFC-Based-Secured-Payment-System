<?php
    require('DatabaseConnection.php');
    
    $ques= $_POST["ques"];
    $ans = $_POST["ans"];
    $userid = $_POST["userid"];
 
    $statement = mysqli_prepare($con, "SELECT Question1,Answer1 FROM UserDetails WHERE id=? AND Question1= ? AND Answer1 = ?");
    mysqli_stmt_bind_param($statement, "sss",$userid, $ques,$ans);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement,$Question1,$answer1);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] =true;  
        $response["id"]= $Question1;
        $response["Password"]= $Answer1;
    }
    echo json_encode($response);
?>
