<!doctype html>
<html>
<body>

<?php

require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
use google\appengine\api\cloud_storage\CloudStorageTools;

$options = [ 'gs_bucket_name' => 'androidsoundappproject.appspot.com' ];
$upload_url = CloudStorageTools::createUploadUrl('/server?action=upload_file', $options)

?>
<div>
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
</div>

<br><br>

<div>
<form action="/server?action=create_user" method="post">
   disp_nme: <input type="text" name="disp_nme" ><br>
   email_addr: <input type="text" name="email_addr"><br>
   user_pw: <input type="text" name="user_pw"><br>
   <input type="submit" value="Create User">
</form>
</div>

<br><br>

<?php

include 'connection.php';

function tSpew() {

    $conn = dbConn();

    $sql = "SELECT * FROM USER";
    echo $sql . "<br><br>";
    
    if (!$link = $conn) {
        echo 'Could not connect to mysql';
        exit;
    }

    try {
      
      $sth = $conn->prepare($sql);
      $sth->execute();

      $results = $sth->fetchAll( PDO::FETCH_ASSOC );
      //print_r($result);
      
      $rowcount = $sth->rowCount();
      $colcount = $sth->columnCount();
      echo "rowcount: " . $rowcount . "<br>";
      echo "colcount: " . $colcount . "<br><br>";

      
      foreach( $results as $row ){
        print_r( $row );
      }
      
  } catch (PDOException $ex) {
    echo "An error occurred in reading or writing to guestbook.";
  }
  $db = null;
 
}   
  
dbClose($conn);

?>

<?PHP tSpew(); ?>

</html>
</body>