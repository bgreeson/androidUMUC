<?PHP

function dbConnect() {
    $myServer = "<server name>";
    $myDB = "<db name>";
    
    $connectionInfo = array("UID" => "ARTReportal_WebUsr", "PWD" => "7j!Xvh#Z68", "Database"=>"$myDB");
    
    //connection to the database
    $dbhandle = sqlsrv_connect($myServer, $connectionInfo);
    
    if(!$dbhandle) {
        error(sqlsrv_errors());
        return (false);
    } else {
        return ($dbhandle);
    }
}

function dbClose($dbhandle) {
    if ($dbhandle)
        sqlsrv_close($dbhandle);
}
?>
