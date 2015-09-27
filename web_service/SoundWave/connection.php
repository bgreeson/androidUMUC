<?PHP

function dbConn() {

$db = null;
  if (isset($_SERVER['SERVER_SOFTWARE']) &&
  strpos($_SERVER['SERVER_SOFTWARE'],'Google App Engine') !== false) {
    // Connect from App Engine.
    try{
       $db = new pdo('mysql:unix_socket=/cloudsql/androidsoundappproject:soundwave;dbname=soundwave', 'root', '50undW4v3');
    }catch(PDOException $ex){
        die(json_encode(
            array('App Engine: outcome' => false, 'message' => 'Unable to connect.')
            )
        );
    }
  } else {
    // Connect from a development environment.
    try{
       $db = new pdo('mysql:host=[2001:4860:4864:1:a286:9550:afb7:fc24]:3306;dbname=soundwave', 'sw_admin', '50undW4v3');
    }catch(PDOException $ex){
        die(json_encode(
            array('Dev Env: outcome' => false, 'message' => 'Unable to connect')
            )
        );
    }
  }
  return ($db);
}



function dbConnect() {

  $dbhandle= mysql_connect(':/cloudsql/androidsoundappproject:soundwave',
    'root',      // username
    ''           // password
    );

  if(!$dbhandle) {
        error(mysql_error());
        return (false);
    } else {
        return ($dbhandle);
    }
  
}

function dbClose($dbhandle) {
    if ($dbhandle)
        sqlsrv_close($dbhandle);
}

/*
*
*https://phpmyadmin-dot-androidsoundappproject.appspot.com
*User: root
*Pass: 50undW4v3
*IPv6: 2001:4860:4864:1:a286:9550:afb7:fc24
*69.241.122.68
*
* // Using PDO_MySQL (connecting from App Engine)
*$db = new pdo('mysql:unix_socket=/cloudsql/androidsoundappproject:soundwave',
*  'root',  // username
*  ''       // password
*);
*
* // Using mysqli (connecting from App Engine)
*$sql = new mysqli(
*  null, // host
*  'root', // username
*  '',     // password
*  '', // database name
*  null,
*  '/cloudsql/androidsoundappproject:soundwave'
*  );
*
* // Using MySQL API (connecting from App Engine)
*$conn = mysql_connect(':/cloudsql/androidsoundappproject:soundwave',
*  'root', // username
*  ''      // password
*  );
*/

?>