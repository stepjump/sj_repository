<?php 
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");
    

    $EXE_DATE = $_POST["EXE_DATE"];
    $TODAY_MONEY = $_POST["TODAY_MONEY"];
    $ACTIVE_LIST = $_POST["ACTIVE_LIST"];   // 착한일 목록
    $CVT_DATE = $_POST["CVT_DATE"];         // 20200422 형식으로 만들기 - Sorting 조회을 위해
    $END_YN = "N";
    $COMMIT_DATE = NULL;    // 결재일시

    $USER_ID = $_POST["USER_ID"];

//    $EXE_DATE = "2020년5월2일";
//    $TODAY_MONEY = "500";
//    $ACTIVE_LIST = "[1]-아빠 구두닦기-500원";
//    $END_YN = "N";
//    $CVT_DATE ="20200502";
//  	$COMMIT_DATE = NULL;
  	
//  	INSERT INTO ERRAND VALUES ('2020년5월2일', 500, '[1]-아빠 구두닦기-500원', 'N', '20200502', NULL)



    $INT_TODAY_MONEY = (int)$TODAY_MONEY;   // 정수형으로 변환

    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB VALUES (?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssissss", $USER_ID, $EXE_DATE, $INT_TODAY_MONEY, $ACTIVE_LIST, $END_YN, $CVT_DATE, $COMMIT_DATE);
    $exec = mysqli_stmt_execute($statement);
  
    if($exec === false) 
    { 
      $statement = mysqli_prepare($con, "UPDATE GOOD_JOB SET TODAY_MONEY = ?, ACTIVE_LIST = ?, REG_TIME = ? WHERE EXE_DATE = ? AND userID = ?");
      mysqli_stmt_bind_param($statement, "issss", $INT_TODAY_MONEY, $ACTIVE_LIST, $CVT_DATE, $EXE_DATE, $USER_ID);
      $exec = mysqli_stmt_execute($statement);
    }


    if($exec === true) 
    { 
      $statement = mysqli_prepare($con, "SELECT SUM(TODAY_MONEY) AS TOTAL_MONEY FROM GOOD_JOB WHERE UPPER(END_YN) <> 'Y'  AND userID = ?");
      mysqli_stmt_bind_param($statement, "s", $USER_ID);
      mysqli_stmt_execute($statement);
  
      mysqli_stmt_store_result($statement);
      mysqli_stmt_bind_result($statement, $TOTAL_MONEY);
  

    
      $response = array();
      $response["success"] = true;


//        while(mysqli_stmt_fetch($statement)) {
//                $response["total_money"] = $TOTAL_MONEY;        
//        }
        
        mysqli_stmt_fetch($statement);
        $response["total_money"] = $TOTAL_MONEY;        


    
    }
    else {
       $response = array();
       $response["success"] = false;
    }
  
   
		#header('Content-Type: application/json');
    echo json_encode($response);

    mysqli_close($con);
?>

