<?PHP

  require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
  use google\appengine\api\cloud_storage\CloudStorageTools;

// ***** DATABASE CONNECTION HANDLING *****

function dbConn() {
/*
* establishes connection to cloud SQL database
*/

$dbh = null;
  if (isset($_SERVER['SERVER_SOFTWARE']) &&
  strpos($_SERVER['SERVER_SOFTWARE'],'Google App Engine') !== false) {
    // Connect from App Engine.
    try{
       $dbh = new pdo('mysql:unix_socket=/cloudsql/androidsoundappproject:soundwave;dbname=soundwave', 'root', '50undW4v3');
    }catch(PDOException $ex) {
        die(json_encode(
            array('App Engine: outcome' => false, 'message' => 'Unable to connect.')
            )
        );
    }
  } else {
    // Connect from a development environment.
    try{
       $dbh = new pdo('mysql:host=[2001:4860:4864:1:a286:9550:afb7:fc24]:3306;dbname=soundwave', 'sw_admin', '50undW4v3');
    }catch(PDOException $ex) {
        die(json_encode(
            array('Dev Env: outcome' => false, 'message' => 'Unable to connect')
            )
        );
    }
  }
  return ($dbh);
}


function dbClose($dbh) {
/*
* close dungeon connection
*/

  if ($dbh) { $dbh = null; }
}


// *****DATABASE INTERACTION *****

function tableSpew($table) {
/*
* used to display the contents of table supplied
*/
    
    if ($table === null) { $table = 'USER'; }
    $dbh = dbConn();
    
    $sql = 'SELECT * FROM ' . $table ;
    echo $sql . '<br><br>';
    
    if (!$link = $dbh) {
        echo 'Could not connect to MySQL';
        exit;
    }

    try {
      
      /*
      $dbh = dbConn();
      $sql = "SELECT * FROM CONTACT WHERE USER_ID_OWNER = " . $user_id_owner;
      //echo "sql: " . $sql;
      
      $qry = $dbh->prepare($sql);
      $qry->execute();

      $response = $qry->fetchAll( PDO::FETCH_ASSOC );
      
      return $response; //return(sendResponse($response));
      */
      
      $qry = $dbh->prepare($sql);
      $qry->execute();

      $results = $qry->fetchAll( PDO::FETCH_ASSOC );
      //print_r($result);
      
      $rowcount = $qry->rowCount();
      $colcount = $qry->columnCount();
      echo 'rowcount: ' . $rowcount . '<br>';
      echo 'colcount: ' . $colcount . '<br><br>';

      
      foreach( $results as $row ) {
        print_r( $row );
        echo '<br>';
      }
      
      for($i = 1; $i < $columns; $i++) { 

      //read field name
        $fieldName = mysql_field_name($qry,$i);
        while($row = mysql_fetch_assoc($qry,$i)){
          foreach($row as $column=>$value) {
            echo "$column = $value\n";
          }
          echo $fieldName."=".$row[$fieldName];   
        }

      }  

  } catch (PDOException $ex) {
    echo 'An error occurred.';
  }
 
  dbClose($dbh);
 
} 


// ***** USER FUNCTIONS *****


// ***** POST *****

function user_create($disp_nme, $email_addr, $user_pw) {
/*
* Adds user to table USER and assigns a user_id
*
* URL: /server?action=user_create
*/
   
  $dbh = dbConn();

  // variables - here for reference  
  //$disp_nme = $_POST["disp_nme"];                  
  //$email_addr = $_POST["email_addr"];  
  //$user_pw = $_POST["user_pw"]; 
   
  $user_type = 'basic';
  
  // date creation
  $date = date_create();
  //$tz = $date -> getTimezone();
  $timestamp = date_format($date, 'Y-m-d H:i:s');
  //$timezone = $tz -> getName();
  
  // load date variables
  $date_effective = $timestamp;
  $date_modified = $timestamp;
  $date_expired = '9999-12-31T23:59:59';
   
  // sql statement
  $sql = 'INSERT INTO USER (FRST_NME, EMAIL_ADDR, USER_PW, USER_TYPE, DATE_EFFECTIVE, DATE_MODIFIED, DATE_EXPIRED) 
                    VALUES (:FRST_NME, :EMAIL_ADDR, :USER_PW, :USER_TYPE, :DATE_EFFECTIVE, :DATE_MODIFIED, :DATE_EXPIRED)';
  
  $qry = $dbh->prepare($sql);
    
  $new_user = array(':FRST_NME'=>$disp_nme,  // DEV_NOTE: Need to alter USER table to add DISP_NME and remove FRST_NME and LAST_NME
                   //':LAST_NME'=>$last_nme,
                   ':EMAIL_ADDR'=>$email_addr,
                   ':USER_PW'=>$user_pw,
                   ':USER_TYPE'=>$user_type,
                   ':DATE_EFFECTIVE'=>$date_effective,
                   ':DATE_MODIFIED'=>$date_modified,
                   ':DATE_EXPIRED'=>$date_expired
                   );
   
  $qry->execute($new_user);
  $user_id = $dbh->lastInsertId();
  
  dbClose($dbh);
  
  /* DEV_NOTE: Removed to make response consistent  
  // build response array
  $response = array('USER_ID'=>$user_id, 
                    'FRST_NME'=>$disp_nme,  
                    'EMAIL_ADDR'=>$email_addr,
                    'USER_PW'=>$user_pw,
                    'USER_TYPE'=>$user_type,
                    'DATE_EFFECTIVE'=>$date_effective,
                    'DATE_MODIFIED'=>$date_modified,
                    'DATE_EXPIRED'=>$date_expired
                    );
  
  return $response; //return(sendResponse($response));
  */
  return (user_info($user_id));

}


function contact_create($user_id_owner, $user_id_member) {
/*
* Adds a contact to table CONTACT
*
* URL: /server?action=contact_create
*/

  $dbh = dbConn();

  // variables - here for reference  
  //$user_id_owner = $_POST['user_id_owner'];                  
  //$user_id_member = $_POST['user_id_member'];  
  
  // date creation
  $date = date_create();
  $tz = $date -> getTimezone();
  $timestamp = date_format($date, 'Y-m-d H:i:s');
  //$timezone = $tz -> getName();
  
  // load date variable
  $date_added = $timestamp;

  // sql statement
  $sql = 'INSERT INTO CONTACT (USER_ID_OWNER, USER_ID_MEMBER, DATE_ADDED) 
                       VALUES (:USER_ID_OWNER, :USER_ID_MEMBER, :DATE_ADDED)';
  
  $qry = $dbh->prepare($sql);
    
  $new_contact = array(':USER_ID_OWNER'=>$user_id_owner,
                       ':USER_ID_MEMBER'=>$user_id_member,
                       ':DATE_ADDED'=>$date_added
                       );
   
  $qry->execute($new_contact);
  $contact_id = $dbh->lastInsertId();
  
  dbClose($dbh);

  // build response array
  $response = array('CONTACT_ID'=>$contact_id,
                    'USER_ID_OWNER'=>$user_id_owner,
                    'USER_ID_MEMBER'=>$user_id_member,
                    'DATE_ADDED'=>$date_added
                    );
  
  return $response; //return(sendResponse($response));

}


function message_create($user_id_sender, $user_id_target) { // upload_file
/*
* Adds record to table MESSAGE
* Adds file metadata into table MESSAGE
* Adds a record to table MESSAGE_DISTO for each target (recipient) // DEV_NOTE: currently only supports one recipient
*
* URL: /server?action=message_create
*/
  
  // get file meta data to insert into database and for response body
  $file_name = $_FILES['userfile']['name'];
  $file_type = $_FILES['userfile']['type'];	
  $file_size = $_FILES['userfile']['size'];
   
  // get current timestamp and timezone
  $date = date_create();
  //$tz = $date -> getTimezone();
  $timestamp = date_format($date, 'Y-m-d H:i:s');
  //$timezone = $tz -> getName();
  
  // get error and temp info
  //$file_tmp_name = $_FILES['userfile']['tmp_name'];
  $file_error = $_FILES['userfile']['error'];
  $gs_name = $_FILES['userfile']['tmp_name'];
  
  // create user directory if it does not exist
  if (!file_exists('gs://androidsoundappproject.appspot.com/message/' . $user_id_sender)) {
    // create dir
    mkdir('gs://androidsoundappproject.appspot.com/message/' . $user_id_sender);
  }
  
  // save file
  $object_url = 'gs://androidsoundappproject.appspot.com/message/' . $user_id_sender . '/' . $file_name;
  //$options = stream_context_create(['gs'=>['acl'=>'public-read']]);
  
  $file = file_get_contents($gs_name);
  $options = array('gs'=>array('acl'=>'public-read','Content-Type' => $_FILES['userfile']['type']));
  $ctx = stream_context_create($options);
  
  // place file into storage and give it public access
  if (true == file_put_contents($object_url, $file, 0, $ctx)){
  //if (true == rename($_FILES['userfile']['tmp_name'], $object_url, $ctx)) {
  //if (move_uploaded_file($gs_name, $object_url)) { // may need to rename file with unique name  
 
    // upload success
    $status = 201;
    header('Created', true, $status);

    $file_path = $user_id_sender . '/' . $file_name;
    $file_public_url = CloudStorageTools::getPublicUrl($object_url, false);
    
    // add file metadata to database table MESSAGE
    $dbh = dbConn();
    
    $sql = 'INSERT INTO MESSAGE (USER_ID_SENDER, FILE_NAME, FILE_TYPE, FILE_SIZE, FILE_PATH, DATE_SENT) 
                    VALUES (:USER_ID_SENDER, :FILE_NAME, :FILE_TYPE, :FILE_SIZE, :FILE_PATH, :DATE_SENT)';
    
    $qry = $dbh->prepare($sql);
    
    // parameter array
    $file_meta = array(':USER_ID_SENDER' => $user_id_sender,
                       ':FILE_NAME' => $file_name,
                       ':FILE_TYPE' => $file_type,
                       ':FILE_SIZE' => $file_size,
                       ':FILE_PATH' => $file_path,  // need to add a key to file names
                       ':DATE_SENT' => $timestamp
                      );
    
    $qry->execute($file_meta);
    $msg_id = $dbh->lastInsertId();
    
    // add recipients to database table MESSAGE_DISTRO // DEV_NOTE: add loop if we allow multiple recipients
    $sql = 'INSERT INTO MESSAGE_DISTRO (MSG_ID, USER_ID_SENDER, USER_ID_TARGET) 
                                VALUES (:MSG_ID, :USER_ID_SENDER, :USER_ID_TARGET)';
    
    $qry = $dbh->prepare($sql);
    
    // parameter array
    $distro = array(':MSG_ID' => $msg_id,
                    ':USER_ID_SENDER' => $user_id_sender,
                    ':USER_ID_TARGET' => $user_id_target
                   );
    
    $qry->execute($distro);
    
    dbClose($dbh);

   
  } else {
    // upload fail
    $status = 501;
    header('Method Not Implemented', true, $status);
    $msg_id = null;
    //die('Could not rename.');

  }
  
  // build response array
  $response = array('MSG_ID' => $msg_id,
                    'USER_ID_SENDER' => $user_id_sender,
                    'FILE_NAME' => $file_name,
                    'FILE_TYPE' => $file_type,
                    'FILE_SIZE' => $file_size,
                    'FILE_PATH' => $file_path,  // DEV_NOTE: need to add a key to file names
                    'FILE_PUBLIC_URL' => $file_public_url,
                    'DATE_SENT' => $timestamp,
                    'USER_ID_TARGET' => $user_id_target,
                    'tmp_name' => $gs_name,
                    'status' => $status,
                    'error' => $file_error
                    );
  
  return $response; //return(sendResponse($response));
  
}


function message_upload() { // upload_file
/*
* Uploads file to the server and places it in the correct directory
*
* URL: /server?action=message_upload
*/

  // get file meta data to insert into database and for response body
  $file_name = $_FILES['userfile']['name'];
  $file_type = $_FILES['userfile']['type'];	
  $file_size = $_FILES['userfile']['size'];
   
  // get current timestamp
  $date = date_create();
  $timestamp = date_format($date, 'Y-m-d H:i:s');
  
  // get error and temp info
  //$file_tmp_name = $_FILES['userfile']['tmp_name'];
  $file_error = $_FILES['userfile']['error'];
  $gs_name = $_FILES['userfile']['tmp_name'];
  
  $user_id_sender = 9000;
  
  // create user directory if it does not exist
  if (!file_exists('gs://androidsoundappproject.appspot.com/message/' . $user_id_sender)) {
    // create dir
    mkdir('gs://androidsoundappproject.appspot.com/message/' . $user_id_sender);
  }
  
  // save file
  if (move_uploaded_file($gs_name, 'gs://androidsoundappproject.appspot.com/message/' . $user_id_sender . '/' . $file_name)) { // may need to rename file with unique name  
    $status = 201;
    header('Created', true, $status);
    $file_path = $user_id_sender . '/' . $file_name;
   
  } else {
    // upload fail
    $status = 501;
    header('Method Not Implemented', true, $status);
    
  }
  
  // build response array
  $response = array('FILE_NAME' => $file_name,
                    'FILE_TYPE' => $file_type,
                    'FILE_SIZE' => $file_size,
                    'FILE_PATH' => $file_path,  // DEV_NOTE: need to add a key to file names
                    'DATE_SENT' => $timestamp,
                    'tmp_name' => $gs_name,
                    'status' => $status,
                    'error' => $file_error
                    );
  
  return $response; //return(sendResponse($response));
  
}


function message_insert($user_id_sender, $user_id_target) { // update database with sender, target and file path info
/*
* Adds record to table MESSAGE
* Adds file metadata into table MESSAGE
* Adds a record to table MESSAGE_DISTO for each target (recipient) // DEV_NOTE: currently only supports one recipient
*
* URL: /server?action=message_insert
*/

  // parse filemeta data // DEV_NOTE: add param $filemeta
  $file_name = 'static_name_test';    //$filemeta['FILE_NAME'];
  $file_type = 'static_type_test';    //$filemeta['FILE_TYPE'];	
  $file_size = 9000;                  //$filemeta['FILE_SIZE'];
  $file_path = 'static_path_test';    //$filemeta['FILE_PATH'];
  $timestamp = '9999-12-31T23:59:59'; //$filemeta['DATE_SENT'];
  
  // add file metadata to database table MESSAGE
  $dbh = dbConn();
  
  $sql = 'INSERT INTO MESSAGE (USER_ID_SENDER, FILE_NAME, FILE_TYPE, FILE_SIZE, FILE_PATH, DATE_SENT) 
                  VALUES (:USER_ID_SENDER, :FILE_NAME, :FILE_TYPE, :FILE_SIZE, :FILE_PATH, :DATE_SENT)';
  
  $qry = $dbh->prepare($sql);
  
  // parameter array
  $file_meta = array(':USER_ID_SENDER' => $user_id_sender,
                     ':FILE_NAME' => $file_name,
                     ':FILE_TYPE' => $file_type,
                     ':FILE_SIZE' => $file_size,
                     ':FILE_PATH' => $file_path,  // need to add a key to file names
                     ':DATE_SENT' => $timestamp
                    );
  
  $qry->execute($file_meta);
  $msg_id = $dbh->lastInsertId();
  
  // add recipients to database table MESSAGE_DISTRO // DEV_NOTE: add loop if we allow multiple recipients
  $sql = 'INSERT INTO MESSAGE_DISTRO (MSG_ID, USER_ID_SENDER, USER_ID_TARGET) 
                              VALUES (:MSG_ID, :USER_ID_SENDER, :USER_ID_TARGET)';
  
  $qry = $dbh->prepare($sql);
  
  // parameter array
  $distro = array(':MSG_ID' => $msg_id,
                  ':USER_ID_SENDER' => $user_id_sender,
                  ':USER_ID_TARGET' => $user_id_target
                 );
  
  $qry->execute($distro);
  
  dbClose($dbh);

  
  // build response array
  $response = array('MSG_ID' => $msg_id,
                    'USER_ID_SENDER' => $user_id_sender,
                    'FILE_NAME' => $file_name,
                    'FILE_TYPE' => $file_type,
                    'FILE_SIZE' => $file_size,
                    'FILE_PATH' => $file_path,  // DEV_NOTE: need to add a key to file names
                    'DATE_SENT' => $timestamp,
                    'USER_ID_TARGET' => $user_id_target,
                    'tmp_name' => $gs_name,
                    'error' => $file_error
                    );
  
  return $response; //return(sendResponse($response));
  
}



function upload_file() {
  
   //require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
   //use google\appengine\api\cloud_storage\CloudStorageTools;
   //
   //$options = [ 'gs_bucket_name' => 'androidsoundappproject.appspot.com' ];
   //$upload_url = CloudStorageTools::createUploadUrl('/server', $options)
   
   // get file meta data for response body
   $file_name = $_FILES['userfile']['name'];
   //$file_name_tmp = $_FILES['userfile']['tmp_name'];

   $file_type = $_FILES['userfile']['type'];	
   $file_size = $_FILES['userfile']['size'];
   
   // get current timestamp and timezone
   // **this may need to be handled differently in live**
   $date = date_create();
   $tz = $date -> getTimezone();
   $timestamp = date_format($date, 'Y-m-d H:i:s');
   $timezone = $tz -> getName(); // time zone will have to be generated by android app
   
   // get temp and error info
   $file_tmp_name = $_FILES['userfile']['tmp_name'];	
   $file_error = $_FILES['userfile']['error'];
   
   // build response array
   $file_meta = array(
     //'USER_ID_SENDER' => $user_id,
     'FILE_NAME' => $file_name,
     'FILE_TYPE' => $file_type,
     'FILE_SIZE' => $file_size,
     //'FILE_PATH' => $file_path,
     'DATE_SENT' => $timestamp,
     'TIMEZONE' => $timezone,
     'tmp_name' => $file_tmp_name,
     'error' => $file_error
     );
   

   $gs_name = $_FILES['userfile']['tmp_name'];
   move_uploaded_file($gs_name, 'gs://androidsoundappproject.appspot.com/message/' . $file_name.''); // may need to rename file with unique name 
   //move_uploaded_file($file_name_tmp, __dir__ . '\user_docs\\\'' . $file_name.'');

   
   if (is_uploaded_file($file_tmp_name)) {
   //if ($_FILES['userfile']['error'] !== UPLOAD_ERR_OK) {
      // upload success
      //die("Upload failed with error code " . $_FILES['userfile']['error']);
      return $file_meta;
   
   } else {
      // upload failed
      //header("HTTP/1.1 $status $status_message");
     
      //$json_response = json_encode($file_meta);
      return $file_meta;
    
      //$user_info = array("first_name" => "Chad", "last_name" => "Calkins", "address" => "1 Main St. Ann Arbor, MI");
      //return $user_info;

   }

}


function user_auth($email_addr, $user_pw) {
/*
* Authenticates the user
*
* URL: /server?action=user_auth
*/

// DEV_NOTE: Authentication to be added later 

}


// ***** PUT *****

function user_edit($user_id, $disp_nme = null, $email_addr = null, $user_pw = null) {
/*
* Updates the user's information with the infomation sent in table USER 
* At least one of the optional variables must be supplied: $disp_nme, $email_addr, $user_pw are optional
*
* URL: /server?action=user_edit&user_id=<user_id>&disp_nme=<disp_nme>&email_addr=<email_addr>&user_pw=<user_pw>
*/

  
  
  // check variables
  if ($disp_nme === null && !$email_addr  === null && !$user_pw === null) {
    // if fall are null then exit
    exit('Invalid parameters');
  
  } else {
  
    $dbh = dbConn();
    
    // get current timestamp and timezone
    $date = date_create();
    //$tz = $date -> getTimezone();
    $timestamp = date_format($date, 'Y-m-d H:i:s');
    
    $sql_set = "SET DATE_MODIFIED = '" . $timestamp . "'";
    
    // check variables
    if (!empty($disp_nme)) { $sql_set = $sql_set . ", FRST_NME = '" . $disp_nme . "'"; }
    if (!empty($email_addr)) { $sql_set = $sql_set . ", EMAIL_ADDR = '" . $email_addr . "'"; }
    if (!empty($user_pw)) { $sql_set = $sql_set . ", USER_PW = '" . $user_pw . "'"; }
    
    $sql = 'UPDATE USER ' . $sql_set . ' WHERE USER_ID = ' . $user_id;
    //echo "sql: " . $sql;
    
    $qry = $dbh->prepare($sql);
    $qry->execute();
    
    dbClose($dbh);
    
    // build response array
    $response = array('USER_ID' => $user_id,
                      'FRST_NAME' => $disp_nme,
                      'EMAIL_ADDR' => $email_addr,
                      'USER_PW' => $user_pw,
                      'DATE_MODIFIED' => $timestamp,
                      );
    
    return $response; //return(sendResponse($response));
    
  }

}


function user_disable($user_id) {
/*
* Updates the date_expired of user_id in table USER to the current date (admin only) 
*
* URL: /server?action=user_disable&user_id=<user_id>
*/

  $dbh = dbConn();
    
  // get current timestamp and timezone
  $date = date_create();
  //$tz = $date -> getTimezone();
  $timestamp = date_format($date, 'Y-m-d H:i:s');
  
  $sql_set = "SET DATE_MODIFIED = '" . $timestamp . "', " . "DATE_EXPIRED = '" . $timestamp . "'";
  
  $sql = "UPDATE USER " . $sql_set . " WHERE USER_ID = " . $user_id;
  //echo "sql: " . $sql; 
  
  $qry = $dbh->prepare($sql);
  $qry->execute();
  
  dbClose($dbh);
  
  // build response array
    $response = array('USER_ID' => $user_id,
                      'DATE_MODIFIED' => $timestamp,
                      'DATE_EXPIRED' => $timestamp
                      );
    
    return $response; //return(sendResponse($response));
  
}


// ***** GET *****

function user_info($user_id) {
/*
* Retrieves user info from table USER
*
* URL: /server?action=user_info&user_id=<user_id>
*/
  
  $dbh = dbConn();

  $sql = "SELECT * FROM USER WHERE USER_ID = " . $user_id;
  //echo "sql: " . $sql;
  
  $qry = $dbh->prepare($sql);
  $qry->execute();
  
  $response = $qry->fetchAll( PDO::FETCH_ASSOC );
  
  dbClose($dbh);
  
  return $response; //return(sendResponse($response));

}


function user_info_email($email_addr) {
/*
* Retrieves user info from table USER
*
* URL: /server?action=user_info&user_id=<user_id>
*/
  
  $dbh = dbConn();

  $sql = "SELECT * FROM USER WHERE EMAIL_ADDR = '" . $email_addr . "'";
  //echo "sql: " . $sql;
  
  $qry = $dbh->prepare($sql);
  $qry->execute();
  
  $response = $qry->fetchAll( PDO::FETCH_ASSOC );
  
  dbClose($dbh);
  
  return $response; //return(sendResponse($response));

}


function contact_all($user_id_owner) {
/*
* Retrieve full messsage info list from table MESSAGE where user is the sender
*
* URL: /server?action=message&user_id_sender=<user_id_sender>
*/

  $dbh = dbConn();
  $sql = "SELECT * FROM CONTACT WHERE USER_ID_OWNER = " . $user_id_owner;
  //echo "sql: " . $sql;
  
  $qry = $dbh->prepare($sql);
  $qry->execute();

  $response = $qry->fetchAll( PDO::FETCH_ASSOC );
  
  return $response; //return(sendResponse($response));

}


function message_count($user_id_target) {
/*
* Retrieve count of new (un-played) messages from table MESSAGE_DISTRO
*
* URL: /server?action=message_count&user_id=<user_id>
*/

  $dbh = dbConn();

  $sql = "SELECT * FROM MESSAGE_DISTRO WHERE USER_ID_TARGET = " . $user_id_target . " AND DATE_PLAYED is null";
  //echo "sql: " . $sql;
  
  $qry = $dbh->prepare($sql);
  $qry->execute();
  
  $rowcount = $qry->rowCount();
  
  return($rowcount);

}


function message_list($user_id_target, $new) {
/*
* Retrieve messsage info list of new (un-played) messages from table MESSAGE 
* based on messages in user's inbox (table MESSAGE_DISTRO)
*
* URL: /server?action=message_list&user_id_target=<user_id_target>&new=<true/false>
*/
  
  $dbh = dbConn();
  if ($new = true || $new = 'true') {
    $sql = "SELECT * FROM MESSAGE_DISTRO WHERE USER_ID_TARGET = " . $user_id_target . " AND DATE_PLAYED is null";
  } else if ($new = false || $new = 'false') {
    $sql = "SELECT * FROM MESSAGE_DISTRO WHERE USER_ID_TARGET = " . $user_id_target;
  } else {
    exit('Invalid Request: Must be True or False');
  }
  //echo "sql: " . $sql;
  
  $qry = $dbh->prepare($sql);
  $qry->execute();

  $response = $qry->fetchAll( PDO::FETCH_ASSOC );
  
  return $response; //return(sendResponse($response));

}


function message_sent($user_id_sender) {
/*
* Retrieve full messsage info list from table MESSAGE where user is the sender
*
* URL: /server?action=message&user_id_sender=<user_id_sender>
*/

  $dbh = dbConn();
  $sql = "SELECT * FROM MESSAGE WHERE USER_ID_SENDER = " . $user_id_sender;
  //echo "sql: " . $sql;
   
  $qry = $dbh->prepare($sql);
  $qry->execute();

  $response = $qry->fetchAll( PDO::FETCH_ASSOC );
  
  return $response; //return(sendResponse($response));

}

function message_info($msg_id) {
/*
* Retrieve info for specific message from table MESSAGE
*
* URL: /server?action=message&msg_id=<msg_id>
*/

  $dbh = dbConn();

  $sql = "SELECT * FROM MESSAGE WHERE MSG_ID = " . $msg_id;
  //echo "sql: " . $sql;
  
  $qry = $dbh->prepare($sql);
  $qry->execute();
  
  $response = $qry->fetchAll( PDO::FETCH_ASSOC );
  
  dbClose($dbh);
  
  return $response; //return(sendResponse($response));
  
}

function message_get($msg_id) {
/*
* Download message audio file
*
* URL: /server?action=message_get&msg_id=<msg_id>
*/

/*

<audio controls>
    <source src="test.php" type="audio/ogg">
</audio>


require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
use google\appengine\api\cloud_storage\CloudStorageTools;
CloudStorageTools::serve('gs://raven-bucket/194-14-02-2014rec.ogg', 
    ['content_type' => 'audio/ogg']);
    
source: http://stackoverflow.com/questions/22128709/serving-files-from-google-cloud-store-on-a-gae-php-site

*/
  
  // get file path
  //$msg = message_info($msg_id);
  
  // get file  data to build gs path
  //$file_path = $msg['FILE_PATH'];
  
  // retrieve file
  /* DEVE_NOTE: removed for wk envrionment
  *require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
  *use google\appengine\api\cloud_storage\CloudStorageTools;
  *
  *CloudStorageTools::serve('gs://androidsoundappproject.appspot.com/message/' . $file_path, ['content_type' => 'audio/ogg']);
  */
  
}


// ***** DELETE *****

function user_delete($user_id) {
/*
* Deletes user from table USER (admin only)
*
* URL: /server?action=user_delete&user_id=<user_id>
*/

  $dbh = dbConn();

  $sql = "DELETE FROM USER WHERE USER_ID = " . $user_id;
  //echo "sql: " . $sql;
  
  $qry = $dbh->prepare($sql);
  $qry->execute();
  
  dbClose($dbh);
  
  // build response array
  $response = array('USER_ID' => $user_id);
    
  return $response; //return(sendResponse($response));

}


function contact_delete($contact_id) {
/*
* Delete contact from table CONTACT
*
* URL: /server?action=contact_delete&contact_id=<contact_id>
*/

  $dbh = dbConn();

  $sql = "DELETE FROM CONTACT WHERE CONTACT_ID = " . $contact_id;
  //echo "sql: " . $sql;
  
  $qry = $dbh->prepare($sql);
  $qry->execute();
  
  dbClose($dbh);
  
  // build response array
  $response = array('CONTACT_ID' => $contact_id);
    
  return $response; //return(sendResponse($response));

}


function message_distro_delete($user_id_target, $msg_id) {
/*
* Delete message from table MESSAGE_DISTRO
*
* URL: /server?action=message_distro_delete&user_id=<user_id_target>&msg_id=<msg_id>
*/

  $dbh = dbConn();

  $sql = 'DELETE FROM MESSAGE_DISTRO WHERE USER_ID_TARGET = ' . $user_id_target . ' AND MSG_ID = ' . $msg_id;
  //echo "sql: " . $sql;
  
  $qry = $dbh->prepare($sql);
  $qry->execute();
  
  dbClose($dbh);
  
  // build response array
  $response = array('USER_ID_TARGET' => $user_id_target,
                    'MSG_ID = ' . $msg_id
                   );
    
  return $response; //return(sendResponse($response));

}


function message_delete($msg_id) { // DEV_NOTE: This will be added if needed
/*
* Delete message file from cloud storage (admin only)
* Delete message from table MESSAGE (admin only)
* 
* URL: /server?action=message_delete&msg_id=<msg_id>
*/
  
  // get file path from data base
  
  
  // delete file from cloud storage
  //unlink ( string $filename [, resource $context ] )
  
  // delete record in table MESSAGE
  
}


// ***** RESPONSE BUILDER *****

function ($response) {

// HTTP status code
header('HTTP/1.1 ' . $response['status'] . ' ' . $http_response_code[ $response['status'] ]);

// response type
header('Content-Type: application/json; charset=utf-8');

// format data to JSON
$json_response = json_encode($response);

return ($json_response);

}

?>