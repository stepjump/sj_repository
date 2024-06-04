<?php 
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");


    $ACTIVE_NO = $_POST["ACTIVE_NO"];

    $USER_ID = $_POST["USER_ID"];

    $INT_ACTIVE_NO = (int)$ACTIVE_NO;   // 정수형으로 변환

    $statement = mysqli_prepare($con, "DELETE FROM GOOD_JOB_ACT_LIST WHERE ACTIVE_NO = ? AND userID = ? ");    
    mysqli_stmt_bind_param($statement, "is", $INT_ACTIVE_NO, $USER_ID);
    mysqli_stmt_bind_param($statement, "i", $INT_ACTIVE_NO);
    $exec = mysqli_stmt_execute($statement);
     
    
    // 새로 읽기  
    if($exec === true) 
    { 
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
            $response = array();
            $response["success"] = false;
            
             #header('Content-Type: application/json');
             echo json_encode($response);
          }
    }
    else {
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

