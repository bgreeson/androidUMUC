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
    
    //if (!$link = mysql_connect('mysql_host', 'mysql_user', 'mysql_password')) {
    if (!$link = $conn) {
        echo 'Could not connect to mysql';
        exit;
    }

    try {
    // Show existing guestbook entries.
    //echo "<div>";
    //foreach($conn->query($sql) as $row) {
            //echo $row['USER_ID'] . " | " . $row['FRST_NME'] . " | " . $row['LAST_NME'] . " | " . $row['EMAIL_ADDR'] . "<br>";
            
      //$array = $sql->fetchAll(PDO::FETCH_ASSOC);
      //var_dum($array);
      
      //return($array);
      
      $sth = $conn->prepare($sql);
      $sth->execute();

      /* Fetch all of the remaining rows in the result set */
      //print("Fetch all of the remaining rows in the result set: <br><br>");
      //$results = $sth->fetchAll();
      $results = $sth->fetchAll( PDO::FETCH_ASSOC );
      //print_r($result);
      
      $rowcount = $sth->rowCount();
      $colcount = $sth->columnCount();
      echo "rowcount: " . $rowcount . "<br>";
      echo "colcount: " . $colcount . "<br><br>";

      
      foreach( $results as $row ){
        print_r( $row );
      }
      
      
      
      /*if ($results) {
        echo "<table>\n";
        echo "    <tr>\n";
        echo "      <th>Record #</th>\n";

        //foreach(sqlsrv_field_metadata($rs) as $fieldMetadata) {
        //foreach(PDOStatement::getColumnMeta($rs) as $fieldMetadata) {
        //    echo "        <th>" . $fieldMetadata["Name"] . "</th>\n";
        /*for ($i = 0; $i < $sth->columnCount(); $i++) { 
            $col = $sth->getColumnMeta($i);
            $columns[] = $col['name'];
            echo "        <th>" . $col['name'] . "</th>\n";
        }
        
        echo "    </tr>\n";

        //do {
        while ($row = $sth->fetch(PDO::FETCH_ASSOC)) {
            //$results = PDO::FETCH_ASSOC($rs, PDO::FETCH_NUM);
            //if ($results) {
            if ($row) {
                echo "    <tr>\n";
                echo "        <td class=\"cell\">" . ++$i . "</td>\n";

                foreach ($row as $v) {
                    if (gettype($v) == "object") {
                        echo "        <td>" . $v->format("m/d/Y H:i:s") . "</td>\n";
                    } elseif (is_null($v)) {
                        echo "        <td class=\"cellNull\"><div class=\"cellNull\">NULL</div></td>\n";
                    } elseif ($v == "") {
                        echo "        <td class=\"cellEmpty\"><div class=\"cellEmpty\">&nbsp;</div></td>\n";
                    } else {
                        echo "        <td class=\"cell\">" . $v . "</td>\n";
                    }
                }

                echo "    </tr>\n";
            }
        } //while ($results);
        
        echo "</table>\n";

        //sqlsrv_free_stmt($rs);*/
    //}
 
     //}
     //echo "</div>";
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