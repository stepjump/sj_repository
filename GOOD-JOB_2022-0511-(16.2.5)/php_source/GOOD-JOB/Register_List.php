<?php 
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");
   
//    mysqli_set_charset($con, 'utf8'); 
//    mysqli_query($con, 'set session character_set_connection=utf8;');
//    mysqli_query($con, 'set session character_set_results=utf8;');
//    mysqli_query($con, 'set session character_set_client=utf8;');


    $USER_ID = $_POST["USER_ID"];
    
    $ACTIVE_NO = $_POST["ACTIVE_NO"];
    $ACTIVE_MONEY = $_POST["ACTIVE_MONEY"];
    $ACTIVE_LIST = $_POST["ACTIVE_LIST"];   // 착한일 목록
    $USE_YN = "Y";

    

// INSERT INTO ERRAND_ACT_LIST VALUES ('1','12300','목록리스트123454','Y')


// 테스트 DATA
//    $ACTIVE_NO = "1";
//    $ACTIVE_MONEY = "12300";
//    $ACTIVE_LIST = "한글입력테스트";
//    $USE_YN = "Y";

    $INT_ACTIVE_NO = (int)$ACTIVE_NO;   // 정수형으로 변환
    $INT_ACTIVE_MONEY = (int)$ACTIVE_MONEY;   // 정수형으로 변환

    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "siiss", $USER_ID, $INT_ACTIVE_NO, $INT_ACTIVE_MONEY, $ACTIVE_LIST, $USE_YN);
    $exec = mysqli_stmt_execute($statement);
     
  
  
 
    // INSERT 실패시 UPDATE 처리, 키는 ACTIVE_NO
    if($exec === false) 
    { 
      $statement = mysqli_prepare($con, "UPDATE GOOD_JOB_ACT_LIST SET ACTIVE_MONEY = ?, ACTIVE_LIST = ? WHERE ACTIVE_NO = ? AND userID = ?");
      mysqli_stmt_bind_param($statement, "isis", $INT_ACTIVE_MONEY, $ACTIVE_LIST, $INT_ACTIVE_NO, $USER_ID);
      $exec = mysqli_stmt_execute($statement);
    }

    
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

