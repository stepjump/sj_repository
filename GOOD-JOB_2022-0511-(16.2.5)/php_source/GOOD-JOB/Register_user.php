<?php 
    $con = mysqli_connect("localhost", "stepjump73", "gu7312!!", "stepjump73");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userAdminPass = $_POST["userAdminPass"];
    $userName = $_POST["userName"];
    $userAge = $_POST["userAge"];
    $user_conmment = $_POST["user_conmment"];
    
    $INT_userAge = (int)$userAge; 


    $ACTIVE_NO = "1";
    $INT_ACTIVE_NO = (int)$ACTIVE_NO;
    $ACTIVE_MONEY = "1000";
    $INT_ACTIVE_MONEY = (int)$ACTIVE_MONEY;
//    $ACTIVE_LIST1 = "[1]-�ƺ� ���δ۱�-1000��";
    $ACTIVE_LIST1 = "�ƺ����δ۱�";
    $USE_YN = "Y";

 
    

    $statement = mysqli_prepare($con, "SELECT userID FROM GOOD_JOB_USER WHERE userID = ?");
    mysqli_stmt_bind_param($statement, "s", $userID);
    $exec = mysqli_stmt_execute($statement);
    
    
    
    if($exec === true) 
    { 
              mysqli_stmt_store_result($statement);
              mysqli_stmt_bind_result($statement, $USER_ID);
              mysqli_stmt_fetch($statement); 
              

              
              
              if ($USER_ID != "")  // �ߺ���ID �� ������
              { 
                $response = array();
                $response["success"] = "false";
                $response["exist"] = "true";
              }
              else {
          
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_USER VALUES (?,?,?,?,?,?,NOW(),NOW())");
                    mysqli_stmt_bind_param($statement, "ssssis", $userID, $userPassword, $userAdminPass, $userName, $INT_userAge, $user_conmment);
                    $exec = mysqli_stmt_execute($statement);

                    // ======================================================================================================
                    // �⺻ ������ �׸� �߰��ϱ�
                    // ======================================================================================================
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?,1,1000,'LIST-1000',?)");
                    mysqli_stmt_bind_param($statement, "ss", $userID, $USE_YN);
                    $exec = mysqli_stmt_execute($statement);
                    
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?,2,2000,'LIST-2000',?)");
                    mysqli_stmt_bind_param($statement, "ss", $userID, $USE_YN);
                    $exec = mysqli_stmt_execute($statement);
                    
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?,3,3000,'LIST-3000',?)");
                    mysqli_stmt_bind_param($statement, "ss", $userID, $USE_YN);
                    $exec = mysqli_stmt_execute($statement);
                    
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?,4,4000,'LIST-4000',?)");
                    mysqli_stmt_bind_param($statement, "ss", $userID, $USE_YN);
                    $exec = mysqli_stmt_execute($statement);
                    
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?,5,5000,'LIST-5000',?)");
                    mysqli_stmt_bind_param($statement, "ss", $userID, $USE_YN);
                    $exec = mysqli_stmt_execute($statement);                    
                    
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?,6,6000,'LIST-6000',?)");
                    mysqli_stmt_bind_param($statement, "ss", $userID, $USE_YN);
                    $exec = mysqli_stmt_execute($statement);
                    
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?,7,7000,'LIST-7000',?)");
                    mysqli_stmt_bind_param($statement, "ss", $userID, $USE_YN);
                    $exec = mysqli_stmt_execute($statement);
                    
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?,8,8000,'LIST-8000',?)");
                    mysqli_stmt_bind_param($statement, "ss", $userID, $USE_YN);
                    $exec = mysqli_stmt_execute($statement);
                    
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?,9,9000,'LIST-9000',?)");
                    mysqli_stmt_bind_param($statement, "ss", $userID, $USE_YN);
                    $exec = mysqli_stmt_execute($statement);
                    
                    $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?,10,10000,'LIST-10000',?)");
                    mysqli_stmt_bind_param($statement, "ss", $userID, $USE_YN);
                    $exec = mysqli_stmt_execute($statement);                    
                    // ======================================================================================================
                    
                    
                          
                    // ID�� �ش��ϴ� �⺻ ������ ��� �ֱ�
                    if($exec === true) 
                    { 
                          $response = array();
                          
//                          $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?, 1, 1000, '�������׸�1-ADMIN �޴����� ����,�߰�', 'Y')");
//                          mysqli_stmt_bind_param($statement, "s", $userID);
//                          $exec = mysqli_stmt_execute($statement);
//                          
//                          if($exec === true) 
//                          { 
//                            $response["list_insert"] = "true";
//                          }
//                          else {
//                            $response["list_insert"] = "false";                      
//                          }
//                          
//                          $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?, 2, 2000, '�������׸�2-ADMIN �޴����� ����,�߰�', 'Y')");
//                          mysqli_stmt_bind_param($statement, "s", $userID);
//                          $exec = mysqli_stmt_execute($statement);
//                          
//                          if($exec === true) 
//                          { 
//                            $response["list_insert"] = "true";
//                          }
//                          else {
//                            $response["list_insert"] = "false";                      
//                          }
//                          
//                          $statement = mysqli_prepare($con, "INSERT INTO GOOD_JOB_ACT_LIST VALUES (?, 3, 3000, '�������׸�3-ADMIN �޴����� ����,�߰�', 'Y')");
//                          mysqli_stmt_bind_param($statement, "s", $userID);
//                          $exec = mysqli_stmt_execute($statement);
//                           
//                          if($exec === true) 
//                          { 
//                            $response["list_insert"] = "true";
//                          }
//                          else {
//                            $response["list_insert"] = "false";                      
//                          } 
                                            
                          $response["success"] = "true";
                          $response["exist"] = "false";                      
                          $response["list_insert"] = "true";
                    }
                    else {
                       $response = array();
                       $response["success"] = "false";
                       $response["exist"] = "false";
                       $response["list_insert"] = "false";
                    }
              }
    }
        
    echo json_encode($response);
    mysqli_close($con);

?>
