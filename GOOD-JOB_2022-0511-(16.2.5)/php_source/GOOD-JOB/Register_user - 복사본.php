<?php 
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userAdminPass = $_POST["userAdminPass"];
    $userName = $_POST["userName"];
    $userAge = $_POST["userAge"];
    $user_conmment = $_POST["user_conmment"];
    
    $INT_userAge = (int)$userAge; 


    $statement = mysqli_prepare($con, "SELECT userID FROM GOOD_JOB_USER WHERE userID = ?");
    mysqli_stmt_bind_param($statement, "s", $userID);
    $exec = mysqli_stmt_execute($statement);
    
    
    
    if($exec === true) 
    { 
              mysqli_stmt_store_result($statement);
              mysqli_stmt_bind_result($statement, $USER_ID);
              mysqli_stmt_fetch($statement); 
              
              
              
              if ($USER_ID != "")  // 중복된ID 가 있을때
              { 
                $response = array();
                $response["success"] = "false";
                $response["exist"] = "true";
              }
              else {
          
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_USER VALUES (?,?,?,?,?,?,NOW(),NOW())");
                    mysqli_stmt_bind_param($statement, "ssssis", $userID, $userPassword, $userAdminPass, $userName, $INT_userAge, $user_conmment);
                    $exec = mysqli_stmt_execute($statement);
                
                
                    if($exec === true) 
                    { 
                      $response = array();
                      $response["success"] = "true";
                      $response["exist"] = "false";
                    }
                    else {
                       $response = array();
                       $response["success"] = "false";
                       $response["exist"] = "false";
                    }
              }
    }
        
          
          


 
   
    echo json_encode($response);
    mysqli_close($con);

?>
