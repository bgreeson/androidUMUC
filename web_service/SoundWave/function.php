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
    
    if (!$link = $dbh) {
        echo 'Could not connect to mysql';
        exit;
    }

    try {
  
      $sth = $dbh->prepare($sql);
      $sth->execute();
      
      $results = $sth->fetchAll( PDO::FETCH_ASSOC );
      
      $rowcount = $sth->rowCount();
      $colcount = $sth->columnCount();
      echo "rowcount: " . $rowcount . "<br>";
      echo "colcount: " . $colcount . "<br><br>";

      
      foreach( $results as $row ){
        print_r( $row );
      }

  } catch (PDOException $ex) {
    echo "An error occurred.";
  }
 
  dbClose($dbh);
 
} 



?>