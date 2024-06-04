<?php 
    header('Content-Type: text/html; charset=utf-8');
      
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");
//    mysqli_query($con,'SET NAMES utf8');    // 한글표시
//    mysqli_set_charset($con, 'utf8');      // 한글넘기기

    
$F_DATE = $_POST["F_DATE"];     // 조회시작일시(년월일)
$T_DATE = $_POST["T_DATE"];     // 조회시작일시(년월일)

$USER_ID = $_POST["USER_ID"];

$INT_F_DATE = (int)$F_DATE;   // 정수형으로 변환
$INT_T_DATE = (int)$T_DATE;   // 정수형으로 변환

//$INT_F_DATE = 20200420;    
//$INT_T_DATE = 20200424;

    $statement = mysqli_prepare($con, "SELECT EXE_DATE, TODAY_MONEY, END_YN, REG_TIME, ACTIVE_LIST, COMMIT_DATE FROM GOOD_JOB WHERE CAST(REG_TIME AS UNSIGNED) >= ? AND CAST(REG_TIME AS UNSIGNED) <= ? AND userID = ? ORDER BY REG_TIME DESC");
    mysqli_stmt_bind_param($statement, "iis", $INT_F_DATE, $INT_T_DATE, $USER_ID);        
    $exec = mysqli_stmt_execute($statement);

    if($exec === true) 
    { 
        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $EXE_DATE, $TODAY_MONEY, $END_YN, $REG_TIME, $ACTIVE_LIST, $COMMIT_DATE);

        $group_data = array();
        $response1 = array();
        
        $i = 0;
        
        while(mysqli_stmt_fetch($statement)) {
          $response1["success"] = true;
          $response1["exe_date"] = $EXE_DATE;    
          $response1["today_money"] = $TODAY_MONEY;          
          $response1["end_yn"] = $END_YN;    
          $response1["reg_time"] = $REG_TIME;    
          $response1["active_list"] = $ACTIVE_LIST;   
          $response1["commit_date"] = $COMMIT_DATE;   
          
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

