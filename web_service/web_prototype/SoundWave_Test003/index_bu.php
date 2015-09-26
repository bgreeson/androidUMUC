<!doctype html>
<html>
<body>

<?php

require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
use google\appengine\api\cloud_storage\CloudStorageTools;

$options = [ 'gs_bucket_name' => 'androidsoundappproject.appspot.com' ];
$upload_url = CloudStorageTools::createUploadUrl('/server', $options)

?>

<!--?php echo $upload_url?-->
<?php //echo __dir__ . '\user_docs\\';
  echo $upload_url . '?action=upload_file'
//$url = 'http://localhost:14080'
//$url = 'androidsoundappproject.appspot.com';
?>

<form action="<?php echo $upload_url . '?action=upload_file' ?>" enctype="multipart/form-data" method="post">
   <p>Files to upload: </p>
   <input type="file" name="userfile" >
   <input type="submit" value="Upload">
</form>

<!--?php
		if ( !empty($_SERVER['CONTENT_LENGTH']) && empty($_FILES) && empty($_POST) )
			echo 'The uploaded zip was too large. You must upload a file smaller than ' . ini_get("upload_max_filesize");
	?-->


<!--form action="http://localhost:14080/server?action=upload_file" method="POST">
   <input type="submit" />
</form-->


<?php


if(isset($_GET["action"]) == "get_user_info"){

  $user_info = file_get_contents($url . '/server?action=get_user_info&id=' . $_GET["id"]);
  $user_info = json_decode($user_info, true);

?>

First Name: <?php echo $user_info["first_name"] ?><br>
Last Name: <?php echo $user_info["last_name"] ?><br>
Address: <?php echo $user_info["address"] ?><br>

<?php 

 }
 
 else 
 
 {
   //echo "ELSE";
   $user_list = file_get_contents($url . '/server?action=get_user_list');
   $user_list = json_decode($user_list, true);
   
   
?>
<br><br>
<?php foreach($user_list as $user): ?>

<a href=<?php echo
$url . "/server?action=get_user_info&id=" . $user["id"] ?>
 alt=<?php echo "user_" . $user_["id"] ?>><?php echo $user["name"] ?><br>
 
 <?php endforeach; ?>
 
 <?php 
 
  } ?>


</html>
</body>