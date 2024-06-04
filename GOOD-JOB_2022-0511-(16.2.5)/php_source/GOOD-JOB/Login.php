<?php
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM GOOD_JOB_USER WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPassword, $userAdminPass, $userName, $userAge, $userComment, $REG_TIME, $UDT_TIME);

    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userPassword"] = $userPassword;
        $response["userAdminPass"] = $userAdminPass;
        $response["userName"] = $userName;
        $response["userAge"] = $userAge;        
        $response["userComment"] = $userComment;
        $response["REG_TIME"] = $REG_TIME;
        $response["UDT_TIME"] = $UDT_TIME;            
    }

    echo json_encode($response);

    mysqli_close($con);
?>
