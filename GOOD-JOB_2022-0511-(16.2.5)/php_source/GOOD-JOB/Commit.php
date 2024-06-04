<?php 
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");

    $USER_ID = $_POST["USER_ID"];
    $commit_y = "Y";       
    
    $statement = mysqli_prepare($con, "UPDATE GOOD_JOB SET COMMIT_DATE = NOW(), END_YN = ? WHERE END_YN = 'N' AND TODAY_MONEY <> 0 AND userID = ?");
    mysqli_stmt_bind_param($statement, "ss", $commit_y, $USER_ID);
    $exec = mysqli_stmt_execute($statement);


    if($exec === true) 
    {
      $response = array();
      
      mysqli_stmt_fetch($statement);
      $response["success"] = true;
    }
    else {
      $response = array();
      
      mysqli_stmt_fetch($statement);
      $response["success"] = false;
    }

   
   
		#header('Content-Type: application/json');
    echo json_encode($response);

    mysqli_close($con);
?>

