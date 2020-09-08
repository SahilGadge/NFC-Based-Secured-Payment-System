<?php
    require('DatabaseConnection.php');
    require('DatabaseConnection3.php');
    
    $SenderEmail= $_POST["sender_email"];
    $RecEmail= $_POST["rec_email"];
    $addAmt = $_POST["rec_amt"];
    $dateTime = $_POST["dateTime"];

    $statement = mysqli_prepare($con, "SELECT Balance FROM UserDetails WHERE Email= ?");
    mysqli_stmt_bind_param($statement, "s", $SenderEmail);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement,$Balance);
    
    $response = array();
    $response["success"] = "NOSENDER";  
    
    while(mysqli_stmt_fetch($statement)){
        
        $DebitBalance=$Balance - $addAmt;

        if ($DebitBalance>=0) {

                    $connn = new mysqli($host, $uname, $pwd, $db);
                   if ($connn->connect_error) {
                    die("Connection failed: " . $connn->connect_error);
                    } 

                    $sql = "UPDATE UserDetails SET Balance='$DebitBalance' WHERE Email='$SenderEmail'";

                    if ($connn->query($sql) === TRUE) {                 
                         $statement = mysqli_prepare($con, "SELECT Balance FROM UserDetails WHERE Email= ?");
                            mysqli_stmt_bind_param($statement, "s", $RecEmail);
                            mysqli_stmt_execute($statement);
                            
                            mysqli_stmt_store_result($statement);
                            mysqli_stmt_bind_result($statement,$Balance);
                            
                            $response = array();
                            $response["success"] = "NORECEIVE";  
                            
                            while(mysqli_stmt_fetch($statement)){
                                $CreditBalabce=$Balance+ $addAmt;


                                 $connn = new mysqli($host, $uname, $pwd, $db);
                                                   if ($connn->connect_error) {
                                                    die("Connection failed: " . $connn->connect_error);
                                                    } 

                                                    $sql = "UPDATE UserDetails SET Balance='$CreditBalabce' WHERE Email='$RecEmail'";

                                                if ($connn->query($sql) === TRUE) {
                                                   $response["success"] = "TRANSFERED";  
                                                     echo json_encode($response);
                                                     senderHistoryUpdate();

                                                    }
                                                else
                                                {
                                                    $response["success"] = 3;  
                                               }
                            }
                    }
                else
                {
                    $response["success"] = error;  
                    echo json_encode($response);
               }
        }else{
                $response["success"] = "NOBAL";  
        }
    }
                        echo json_encode($response);

    // ******update sender history********


    function senderHistoryUpdate(){
   $connn = mysqli_connect("localhost", "appmartg_Ani", "Aniruddha@gh", "appmartg_NFCPAY");
            if ($connn->connect_error) {
             die("Connection failed: " . $connn->connect_error);
            } 
                global $SenderEmail; 
                global $addAmt; 
                global $RecEmail; 
                global $dateTime; 
                

                $sql = "INSERT INTO transactionhistory(UserEmail, Debit, Credit,Fromm,Too,DateTime) VALUES  ('$SenderEmail', '$addAmt', '-','-','$RecEmail','$dateTime')";

           if ($connn->query($sql) === TRUE) {
              echo "sender history updated";
               receiverHistoryUpdate();
           }
          else
          {
            $response["success"] = 3;  
          }

    }
    //*****Update Receiver History********
    function receiverHistoryUpdate(){
           $connn = mysqli_connect("localhost", "appmartg_Ani", "Aniruddha@gh", "appmartg_NFCPAY");

            if ($connn->connect_error) {
             die("Connection failed: " . $connn->connect_error);
            } 
                global $SenderEmail; 
                global $addAmt; 
                global $RecEmail; 
                global $dateTime; 
                $sql = "INSERT INTO transactionhistory(UserEmail, Debit, Credit,Fromm,Too,DateTime) VALUES  ('$RecEmail', '-', '$addAmt','$SenderEmail','-','$dateTime')";

           if ($connn->query($sql) === TRUE) {
               echo "Receiver history updated";
           }
          else
          {
            $response["success"] = 3;  
          }

    }

?>