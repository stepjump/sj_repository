<?php 
    // Register_user_Update.php
    // 회원가입 회원정보 변경
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userAdminPass = $_POST["userAdminPass"];
    $userName = $_POST["userName"];
    $userAge = $_POST["userAge"];
    $user_conmment = $_POST["user_conmment"];
    
    $INT_userAge = (int)$userAge; 

    $statement = mysqli_prepare($con, "UPDATE GOOD_JOB_USER SET userPassword = ?, userAdminPassword = ?, userName = ?, userAge = ?, userComment = ?, UDT_TIME = NOW() WHERE userID = ?");
    mysqli_stmt_bind_param($statement, "sssiss", $userPassword, $userAdminPass, $userName, $INT_userAge, $user_conmment, $userID);
    $exec = mysqli_stmt_execute($statement);


    if($exec === true) 
    { 
      $response = array();
      $response["success"] = true;
    }
    else {
       $response = array();
       $response["success"] = false;
    }
   
    echo json_encode($response);
    mysqli_close($con);

?>
