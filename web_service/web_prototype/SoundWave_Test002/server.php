<?php

   require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
   use google\appengine\api\cloud_storage\CloudStorageTools;

   $options = [ 'gs_bucket_name' => 'androidsoundappproject.appspot.com' ];
   $upload_url = CloudStorageTools::createUploadUrl('/server', $options);

?>

<!DOCTYPE html>
<html>
<head>
<title>SoundWave</title>
</head>
<body>

<?php

if(isset($_POST['do-upload']) AND $_POST['do-upload'] === "yes") {

   $yesupload = $_POST['do-upload'];
   preg_match("/yes/", "".$yesupload."");

   $file_name = $_FILES['userfile']['name'];
   
   $gs_name = $_FILES['userfile']['tmp_name'];
   move_uploaded_file($gs_name, 'gs://androidsoundappproject.appspot.com/' . $file_name.''); // may need to rename file with unique name 


   echo "<div>";
   
   echo "File uploaded: ".$yesupload."</p>";
   echo "File name: " . $filename."</p>";
   
   echo "<pre>";
    
   echo "File info:\n";
   print_r($_FILES);
  
   echo '<br>';
  
   // header("HTTP/1.1 $status $status_message");
  
   //$file_name = $_FILES['userfile']['name'];
   $file_type = $_FILES['userfile']['type'];	
   $file_tmp_name = $_FILES['userfile']['tmp_name'];	
   $file_error = $_FILES['userfile']['error'];
   $file_size = $_FILES['userfile']['size'];
   
   // get current timestamp and timezone
   // this may need to be handled differently in live
   $date = date_create();
   $tz = $date -> getTimezone();
   $timestamp = date_format($date, 'Y-m-d H:i:s');
   $timezone = $tz -> getName(); // time zone will have to be generated by android app
   
   $file_meta = array(
     'USER_ID_SENDER' => $file_name,
     'FILE_NAME' => $file_name,
     'FILE_TYPE' => $file_type,
     'FILE_SIZE' => $file_size,
     //'FILE_PATH' => $file_path,
     'DATE_SENT' => $timestamp,
     'TIMEZONE' => $timezone,
     'tmp_name' => $file_tmp_name,
     'error' => $file_error
     );
  
   echo "JSON:\n";
   $json_response = json_encode($file_meta);
   echo $json_response;
   
   // decode JSON 
   $json_data = json_decode($json_response, true);
   
   // parse array elements
   $jd_user_id_sender = $json_data['USER_ID_SENDER'];
   $jd_file_name = $json_data['FILE_NAME'];
   $jd_file_type = $json_data['FILE_TYPE'];
   $jd_file_size = $json_data['FILE_SIZE'];
   //$jd_file_path = $json_data['FILE_PATH'];
   $jd_date_sent = $json_data['DATE_SENT'];
   $jd_timezone = $json_data['TIMEZONE'];
   
   echo "\n\nSQL Statement:";
   
   // build SQL statement
   $sql = "INSERT INTO MESSAGE(USER_ID_SENDER, FILE_NAME, FILE_TYPE, FILE_SIZE, FILE_PATH, DATE_SENT, TIMEZONE)
   VALUES('$jd_user_id_sender', '$jd_file_name', '$jd_file_type', $jd_file_size, $jd_date_sent, '$jd_timezone')"; // add $jd_file_path
  
   // connect to db
   //$db = dbConnect();
   
   // insert file metadata into mysql table:message
   //sqlsrv_query($db, $sql);
   //error(sqlsrv_errors());
   
   /*if(!mysql_query($sql,$con)) {
      die('Error : ' . mysql_error());
   }*/
   
    
   // insert all recipients into table: message_distro
   // foreach recipient loop goes here...
   
   echo "\n".$sql;
   echo "<pre>";
   
   }
   
   echo "</div>";
   
?>
<br><br>
<div>

<form action="<?php echo $upload_url?>" enctype="multipart/form-data" method="post">
   <p>Files to upload: </p>
   <input type="hidden" name="do-upload" value="yes">
   <input type="file" name="userfile" >
   <input type="submit" value="Upload">
</form>

</div>
</body>
</html>