<html>
 <head>
  <title>PHP Test</title>
 </head>
 <body>
 
<?php
include("function.php");
include ("connection.php");
// need to include error handling


$method = $_SERVER['REQUEST_METHOD'];
echo "Method Requested: " . $method;
echo "<br><br>";

// determine request method
// some of the actions listed here will require more detail of the specific request; they are listed here for reference
switch ($method) {
    case "POST":
        echo "Method Requested: " . $method;
        //createUser();
        //createContact($user_id_owner, $user_id_member);
        createMessage($user_id, $ary_user_id_target);
        break;
    case "PUT":
        //echo "Method Requested: " . $method;
        //updateUser($user_id);
        break;
    case "GET":
        echo "Method Requested: " . $method;
        //getUser($user_id);
        //getMessageCount($user_id_target);
        //getMessageList($user_id_target, $filterType);
        getMessage($msg_id);
        break;
    case "DELETE":
        //echo "Method Requested: " . $method;
        //deleteMessage($user_id, $msg_id);
        //deleteContact();
        break;
    default:
        echo "Method Requested: UNKNOWN";
        break;
}




function createMessage() {

  $uploaddir = '/Test/Chad_DEV/User_Docs/';
  $uploadfile = $uploaddir . basename($_FILES['userfile']['name']); // may need to rename file with unique name 
  
  echo "<pre>";
  
  // $_FILES used to obtain metadata
  if (move_uploaded_file($_FILES['userfile']['tmp_name'], $uploadfile)) {
      echo "File is valid, and was successfully uploaded.\n\n";
  } else {
      echo "File not valid";
  }
  
  echo "File info:\n";
  print_r($_FILES);
  
  echo '<br>';
  
  // header("HTTP/1.1 $status $status_message");
  
  $file_name = $_FILES['userfile']['name'];
  $file_type = $_FILES['userfile']['type'];	
  $file_tmp_name = $_FILES['userfile']['tmp_name'];	
  $file_error = $_FILES['userfile']['error'];
  $file_size = $_FILES['userfile']['size'];
  
  // get current timestamp and timezone
  // this may need to be handled differently in live
  $date = date_create();
  $tz = $date -> getTimezone();
  $timestamp = date_format($date, 'Y-m-d H:i:s');
  $timezone = $tz -> getName();
  
  $file_meta = array(
    'FILE_NAME' => $file_name,
    'FILE_TYPE' => $file_type,
    'tmp_name' => $file_tmp_name,
    'error' => $file_error,
    'FILE_SIZE' => $file_size,
    'DATE_SENT' => $timestamp,
    'TIMEZONE' => $timezone
    );
  
  echo "JSON:\n";
  $json_response = json_encode($file_meta);
  echo $json_response;
  
  // decode JSON 
  $json_data = json_decode($json_response, true);
  
  // parse array elements
  $jd_file_name = $json_data['FILE_NAME'];
  $jd_file_type = $json_data['FILE_TYPE'];
  $jd_file_size = $json_data['FILE_SIZE'];
  $jd_date_sent = $json_data['DATE_SENT'];
  $jd_timezone = $json_data['TIMEZONE'];
  
  echo "\n\nSQL Statement:";
  
  // build SQL statement
  $sql = "INSERT INTO ARTReportal.dbo.MESSAGE(FILE_NAME, FILE_TYPE, FILE_SIZE, DATE_SENT, TIMEZONE)
  VALUES('$jd_file_name', '$jd_file_type', $jd_file_size, GETDATE(), '$jd_timezone')";
  
  // connect to db
  $db = dbConnect();
  
  // insert file metadata into mysql table:message
  sqlsrv_query($db, $sql);
  error(sqlsrv_errors());
  
  /*if(!mysql_query($sql,$con)) {
     die('Error : ' . mysql_error());
  }*/
  
  
  // insert all recipients into table: message_distro
  // foreach recipient loop goes here...
  
  echo "\n".$sql;
  echo "<pre>";

}


function getMessage() {
  $msg_id = $_GET["msg_id"];
  
  // connect to db
  $db = dbConnect();
  
  // build SQL statement
  $sql = "SELECT * FROM ARTReportal.dbo.MESSAGE WHERE MSG_ID = " . $msg_id;
  echo "SQL: ". $sql . "<br><br>";
  
  $stmt = sqlsrv_query($db, $sql);
  
  if( $stmt === false ) {
       die( print_r( sqlsrv_errors(), true));
  } 
  
  $rows = sqlsrv_has_rows ($stmt);
  if($rows === true) {
  
    // display data
    // in live data will be passed to streaming module
    while($obj = sqlsrv_fetch_object( $stmt)) {
        echo "MSG_ID: " . $obj -> MSG_ID . "<br>";
        echo "FILE_NAME: " . $obj -> FILE_NAME . "<br>";
        echo "FILE_TYPE: " . $obj -> FILE_TYPE . "<br>";
        echo "FILE_SIZE: " . $obj -> FILE_SIZE . "<br>";
        echo "FILE_PATH: " . $obj -> FILE_PATH . "<br>";
        echo "DATE_SENT: " . $obj -> DATE_SENT -> format('Y-m-d H:i:s') . "<br>";
        echo "TIMEZONE: " . $obj -> TIMEZONE . "<br>";
        echo "USER_ID_SENDER: " . $obj -> USER_ID_SENDER . "<br>";
    }
  } else {
    echo "No records found.<br>";
    
  }
    
  echo "<br>";
  sqlsrv_close($db);

}

?>

<form method="get" action="http://Test/Chad_DEV/file_upld.php">
    <button type="submit">Return</button>
</form>

</body>
</html>