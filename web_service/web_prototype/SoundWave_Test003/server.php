<?php

include 'function.php';

if(isset($_GET["action"])){

  switch($_GET["action"]){
    
    case "get_user_list":
      $value = get_user_list();
      break;
    case "get_user_info":
      $value = get_user_info($_GET["id"]);
      break;
    case "upload_file":
      $value = upload_file();
      break; 
     
  }

 } else if(isset($_POST["action"])){
 
   switch($_POST["action"]){
      
      case "upload_file":
        $value = upload_file();
        break;
      case "create_user":
        $value = create_user();
        //$value = array("chad","z.ccalkins@gmail.com");
        break; 
        
   }
 
 }

$value = create_user();
exit(json_encode($value));


function get_user_list(){

  $user_list = array(array("id" => 1, "name" => "Duke Cooper"),
                        array("id" => 2, "name" => "Harry Truman"),
                        array("id" => 3, "name" => "Shelly Johnson"),
                        array("id" => 4, "name" => "Bobby Briggs"),
                        array("id" => 5, "name" => "Donna Rhia"));
  
  return $user_list;

}


function get_user_info($id){
  
  $user_info = array();
  
  switch($id){
    
    case 1:
      $user_info = array("first_name" => "Duke", "last_name" => "Cooper", "address" => "123 Main St. Ann Arbor, MI");
      break;
    case 2:
      $user_info = array("first_name" => "Harry", "last_name" => "Truman", "address" => "456 Main St. Ann Arbor, MI");
      break;
    case 3:
      $user_info = array("first_name" => "Shelly", "last_name" => "Johnson", "address" => "789 Main St. Ann Arbor, MI");
      break;  
    case 4:
      $user_info = array("first_name" => "Bobby", "last_name" => "Briggs", "address" => "1011 Main St. Ann Arbor, MI");
      break;
    case 5:
      $user_info = array("first_name" => "Donna", "last_name" => "Rhia", "address" => "1213 Main St. Ann Arbor, MI");
      break;
  }
  
  return $user_info;
  
}


function upload_file(){
  
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

function create_user(){
   
  $dbh = dbConn();
   
  // test variables
  //$frst_nme = "frst004";                  
  //$last_nme = "last004";                  
  //$email_addr = "example004@domain.com";  
  //$user_pw = "password004";               
  
  $disp_nme = $_POST['disp_nme'];                  
  $email_addr = $_POST['email_addr'];  
  $user_pw = $_POST['user_pw']; 
    
  $user_type = "basic";
  
  // date creation
  $date = date_create();
  $tz = $date -> getTimezone();
  $timestamp = date_format($date, 'Y-m-d H:i:s');
  $timezone = $tz -> getName();
  
  // load date variables
  $date_effective = $timestamp;
  $date_modified = $timestamp;
  $date_expired = '9999-12-31T23:59:59';
   
  // sql statement
  //$sql = "INSERT INTO USER (FRST_NME, LAST_NME, EMAIL_ADDR, USER_PW, USER_TYPE, DATE_EFFECTIVE, DATE_MODIFIED, DATE_EXPIRED) 
  //                  VALUES (:FRST_NME, :LAST_NME, :EMAIL_ADDR, :USER_PW, :USER_TYPE, :DATE_EFFECTIVE, :DATE_MODIFIED, :DATE_EXPIRED)";
  
  $sql = "INSERT INTO USER (FRST_NME, EMAIL_ADDR, USER_PW, USER_TYPE, DATE_EFFECTIVE, DATE_MODIFIED, DATE_EXPIRED) 
                    VALUES (:FRST_NME, :EMAIL_ADDR, :USER_PW, :USER_TYPE, :DATE_EFFECTIVE, :DATE_MODIFIED, :DATE_EXPIRED)";
  
  $q = $dbh->prepare($sql);
    
  $new_user = array(':FRST_NME'=>$disp_nme,
                   //':LAST_NME'=>$last_nme,
                   ':EMAIL_ADDR'=>$email_addr,
                   ':USER_PW'=>$user_pw,
                   ':USER_TYPE'=>$user_type,
                   ':DATE_EFFECTIVE'=>$date_effective,
                   ':DATE_MODIFIED'=>$date_modified,
                   ':DATE_EXPIRED'=>$date_expired);
   
  $q->execute($new_user);
  
  dbClose($dbh);
    
  //$foo = $new_user;
  //$foo = array("chad","z.ccalkins@gmail.com");
  return $new_user;

}


?>