<!doctype html>
<html>
<body>

<?php

require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
use google\appengine\api\cloud_storage\CloudStorageTools;

$options = [ 'gs_bucket_name' => 'androidsoundappproject.appspot.com' ];
$upload_url = CloudStorageTools::createUploadUrl('/server?action=upload_file', $options)

?>

<!--?php echo $upload_url?-->
<?php //echo __dir__ . '\user_docs\\';
  echo $upload_url . '?action=upload_file'
//$url = 'http://localhost:14080'
//$url = 'androidsoundappproject.appspot.com';
?>

<form action="<?php echo $upload_url ?>" enctype="multipart/form-data" method="post">
   <p>Files to upload: </p>
   <input type="file" name="userfile" >
   <input type="submit" value="Upload">
</form>

<br><br>

<?php

include 'connection.php';

function tSpew() {

    $conn = dbConn();

    $sql = "SELECT * FROM user";
    echo $sql . "<br><br>";
    
    //if (!$link = mysql_connect('mysql_host', 'mysql_user', 'mysql_password')) {
    if (!$link = $conn) {
        echo 'Could not connect to mysql';
        exit;
    }

    if (!mysql_select_db('androidsoundappproject:soundwave', $link)) {
        echo 'Could not select database';
        exit;
    }

    //$sql = 'SELECT foo FROM bar WHERE id = 42';
    $result = mysql_query($sql, $link);

    if (!$result) {
        echo "DB Error, could not query the database\n";
        echo 'MySQL Error: ' . mysql_error();
        exit;
    }

    while ($row = mysql_fetch_assoc($result)) {
        echo $row['foo'];
    }

    mysql_free_result($result);
 
}   
  
dbClose($conn);

?>

<?PHP tSpew(); ?>

</html>
</body>