<?php 
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");
    

    $EXE_DATE = $_POST["EXE_DATE"];     // 열온드 체크날짜
    $EDIT_TEMP1 = $_POST["EDIT_TEMP1"]; // 등교전(아침) 온도(℃)
    $EDIT_TEMP2 = $_POST["EDIT_TEMP2"]; // 등교후(저녁) 온도(℃)
    $EDIT_TEMP3 = $_POST["EDIT_TEMP3"]; // 자기전(밤) 온도(℃)     
    $HEIGHT = $_POST["HEIGHT"];    // 키
    $WEIGHT = $_POST["WEIGHT"];    // 몸무게    
    $EDIT_COMMENT = $_POST["EDIT_COMMENT"]; // 다른 적을 내용
    $CVT_DATE = $_POST["CVT_DATE"];         // 20200422 형식으로 만들기 - Sorting 조회을 위해
    
    $USER_ID = $_POST["USER_ID"];

//    $EXE_DATE = "2020년4월30일";


    $statement = mysqli_prepare($con, "INSERT INTO TEMPERATURE2 VALUES (?,?,?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssssssss", $USER_ID, $EXE_DATE, $EDIT_TEMP1, $EDIT_TEMP2, $EDIT_TEMP3, $HEIGHT, $WEIGHT, $EDIT_COMMENT, $CVT_DATE);
    $exec = mysqli_stmt_execute($statement);
  
    if($exec === false) 
    { 
      $statement = mysqli_prepare($con, "UPDATE TEMPERATURE2 SET TEMP1 = ?, TEMP2 = ?, TEMP3 = ?, HEIGHT = ?, WEIGHT = ?, ETC_COMMENT = ?, REG_TIME = ? WHERE EXE_DATE = ? AND userID = ?");
      mysqli_stmt_bind_param($statement, "sssssssss",  $EDIT_TEMP1, $EDIT_TEMP2, $EDIT_TEMP3, $HEIGHT, $WEIGHT, $EDIT_COMMENT, $CVT_DATE, $EXE_DATE, $USER_ID);
      $exec = mysqli_stmt_execute($statement);
    }


    if($exec === true) 
    { 
      $response = array();
      $response["success"] = true;
    }
    else {
       $response = array();
       $response["success"] = false;
    }
  
   
		#header('Content-Type: application/json');
    echo json_encode($response);

    mysqli_close($con);
?>

