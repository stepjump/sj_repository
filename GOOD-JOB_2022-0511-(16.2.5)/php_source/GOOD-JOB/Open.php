<?php 
    header('Content-Type: text/html; charset=utf-8');
      
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");
//    mysqli_query($con,'SET NAMES utf8');    // 한글표시
//    mysqli_set_charset($con, 'utf8');      // 한글넘기기

    $TODAY_STR = $_POST["TODAY_STR"];    
//    $TODAY_STR = "2020년4월20일";    // TEST 데이타
    $TOTAL_MONEY = "";

    $USER_ID = $_POST["USER_ID"];

    $statement = mysqli_prepare($con, "SELECT SUM(TODAY_MONEY) AS TOTAL_MONEY FROM GOOD_JOB WHERE UPPER(END_YN) <> 'Y' AND userID = ?");
    mysqli_stmt_bind_param($statement, "s", $USER_ID);
    $exec = mysqli_stmt_execute($statement);

    if($exec === true) 
    { 
        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $TOTAL_MONEY);
        mysqli_stmt_fetch($statement); 
      
        $response = array();
        $response["success"] = true;
        $response["total_money"] = $TOTAL_MONEY;    


           
        $statement = mysqli_prepare($con, "SELECT TODAY_MONEY, ACTIVE_LIST FROM GOOD_JOB WHERE EXE_DATE = ? AND userID = ?");
        mysqli_stmt_bind_param($statement, "ss", $TODAY_STR, $USER_ID);
        $exec = mysqli_stmt_execute($statement);
        
        if($exec === true) 
        { 
           mysqli_stmt_store_result($statement);
           mysqli_stmt_bind_result($statement, $TODAY_MONEY, $ACTIVE_LIST);
           mysqli_stmt_fetch($statement); 
        
           $response["today_money"] = $TODAY_MONEY;
           $response["active_list"] = $ACTIVE_LIST;
        }       
        else {
          $response["success"] = false;
        }
    }
    else {
       $response = array();
       $response["success"] = false;
    }
  
  
   
  
 
  
   
		#header('Content-Type: application/json');
     echo json_encode($response);
   
    mysqli_close($con);
?>

