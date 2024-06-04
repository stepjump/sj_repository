<?php 
    header('Content-Type: text/html; charset=utf-8');
      
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");

    $USER_ID = $_POST["USER_ID"];

    $statement = mysqli_prepare($con, "SELECT ACTIVE_NO, ACTIVE_MONEY, ACTIVE_LIST FROM GOOD_JOB_ACT_LIST WHERE USE_YN = 'Y' AND userID = ? ORDER BY ACTIVE_NO");
    mysqli_stmt_bind_param($statement, "s", $USER_ID);
    $exec = mysqli_stmt_execute($statement);

    if($exec === true) 
    { 
        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $ACTIVE_NO, $ACTIVE_MONEY, $ACTIVE_LIST);

        $group_data = array();
        $response1 = array();
        
        $i = 0;
        
        while(mysqli_stmt_fetch($statement)) {
          $response1["success"] = true;
          $response1["active_no"] = $ACTIVE_NO;    
          $response1["active_money"] = $ACTIVE_MONEY;          
          $response1["active_list"] = $ACTIVE_LIST;
          
          $group_data[$i] = $response1;   
          $i = $i + 1;          
        }
                          
    }
    else {
       $i = 0;
        
       $response = array();
       $response["success"] = false;          
 
     	 #header('Content-Type: application/json');
       echo json_encode($response);
    }
 

       $response = $group_data;
 
       #header('Content-Type: application/json');
       echo json_encode($response);
  
   
    mysqli_close($con);
?>

