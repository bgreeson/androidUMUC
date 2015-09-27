<?PHP

// database connection handling
function dbConn() {

$dbh = null;
  if (isset($_SERVER['SERVER_SOFTWARE']) &&
  strpos($_SERVER['SERVER_SOFTWARE'],'Google App Engine') !== false) {
    // Connect from App Engine.
    try{
       $dbh = new pdo('mysql:unix_socket=/cloudsql/androidsoundappproject:soundwave;dbname=soundwave', 'root', '50undW4v3');
    }catch(PDOException $ex){
        die(json_encode(
            array('App Engine: outcome' => false, 'message' => 'Unable to connect.')
            )
        );
    }
  } else {
    // Connect from a development environment.
    try{
       $dbh = new pdo('mysql:host=[2001:4860:4864:1:a286:9550:afb7:fc24]:3306;dbname=soundwave', 'sw_admin', '50undW4v3');
    }catch(PDOException $ex){
        die(json_encode(
            array('Dev Env: outcome' => false, 'message' => 'Unable to connect')
            )
        );
    }
  }
  return ($dbh);
}


function dbClose($dbh) {
    if ($dbh)
        $dbh = null;
}


function tableSpew() {

    $dbh = dbConn();

    $sql = "SELECT * FROM USER" ;
    echo $sql . "<br><br>";
    
    //if (!$link = mysql_connect('mysql_host', 'mysql_user', 'mysql_password')) {
    if (!$link = $dbh) {
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
      
      $sth = $dbh->prepare($sql);
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
    echo "An error occurred.";
  }
 
  dbClose($dbh);
 
} 



?>