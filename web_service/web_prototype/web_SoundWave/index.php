<!doctype html>
<html>
 <head>
  <title>PHP Test</title>
 </head>
 <body>
 
<!-- The data encoding type, enctype, MUST be specified as below -->
<div name="Post">
POST: <br>
<form enctype="multipart/form-data" action="http://Test/Chad_DEV/server.php" method="POST">

    <!-- MAX_FILE_SIZE must precede the file input field -->
    <input type="hidden" name="MAX_FILE_SIZE" value="30000" />
    
    <!-- Name of input element determines name in $_FILES array -->
    Send this file: <input name="userfile" type="file" />
    <input type="submit" value="Send File" />

</form>
</div>

<br><br>

<div name="Get">
GET: <br>
<form enctype="multipart/form-data" action="http://Test/Chad_DEV/server.php" method="GET">

    Message ID: <input name="msg_id" type="text" />
    <input type="submit" value="Get Msg Data" />

</form>
</div>

</div>

</body>
</html>