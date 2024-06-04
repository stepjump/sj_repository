<?php 
    header('Content-Type: text/html; charset=utf-8');
      
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");

    $TODAY_STR = $_POST["TODAY_STR"];    

//    $TODAY_STR = "2020년4월20일";    // TEST 데이타

    $USER_ID = $_POST["USER_ID"];
    
       
    $statement = mysqli_prepare($con, "SELECT TEMP1, TEMP2, TEMP3, HEIGHT, WEIGHT, ETC_COMMENT FROM TEMPERATURE2 WHERE EXE_DATE = ? AND userID = ?");
    mysqli_stmt_bind_param($statement, "ss", $TODAY_STR, $USER_ID);
    $exec = mysqli_stmt_execute($statement);
    
    if($exec === true) 
    { 
       mysqli_stmt_store_result($statement);
       mysqli_stmt_bind_result($statement, $TEMP1, $TEMP2, $TEMP3, $HEIGHT, $WEIGHT, $ETC_COMMENT);
       mysqli_stmt_fetch($statement); 
    
       $response = array();
       $response["success"] = true;
       $response["temp1"] = $TEMP1;          
       $response["temp2"] = $TEMP2;    
       $response["temp3"] = $TEMP3;    
       $response["height"] = $HEIGHT;    // 키
       $response["weight"] = $WEIGHT;    // 몸무게              
       $response["etc_comment"] = $ETC_COMMENT;   
    } else {
      $response["success"] = false;
    }
   

		#header('Content-Type: application/json');
     echo json_encode($response);
   
    mysqli_close($con);
?>

