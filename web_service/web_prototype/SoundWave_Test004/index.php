<!doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="css/style.css">
  <title>SoundWave: Client Actions</title>
</head>

<body>

<?php

require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
use google\appengine\api\cloud_storage\CloudStorageTools;

$options = [ 'gs_bucket_name' => 'androidsoundappproject.appspot.com' ];
//$upload_url = CloudStorageTools::createUploadUrl('/server', $options)
$upload_url = CloudStorageTools::createUploadUrl('/server?action=message_create', $options)

?>

<h1>SoundWave: Client Actions</h1>

<br>

<!-- 1. POST -->

<div id=main_post>
<table>
<tr>
  <td align="right" width="50px"><b>Action: </b></td>
  <td>user_create</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>POST</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td valign="top">Create new user (user registration) | add record to table: USER</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td>Variables are sent in POST body</td>
</tr>
</table>
<hr>
<form action="/server" method="post">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>disp_nme:</td>
    <td>email_addr:</td>
    <td>user_pw:<br></td>
   </tr>
    <td><input type="text" name="disp_nme" ></td>
    <td><input type="text" name="email_addr"></td>
    <td><input type="text" name="user_pw"><br></td>
   <tr>
    <td><input type="submit" value="Create User"></td>
    <td><input type="hidden" name="action" value="user_create"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 2. POST -->

<div id=main_post>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>contact_create</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>POST</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Create new contact | add record to table: CONTACT</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td>Variables are sent in POST body</td>
</tr>
</table>
<hr>
<form action="/server" method="post">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id_owner:</td>
    <td>user_id_member:</td>
   </tr>
   <tr>
    <td><input type="number" name="user_id_owner"></td>
    <td><input type="number" name="user_id_member"></td>
   </tr>
   <tr>
    <td><input type="submit" value="Create Contact"></td>
    <td><input type="hidden" name="action" value="contact_create"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 3. POST -->

<div id=main_post>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>message_create</td> <!-- upload_file-->
</tr><tr>
  <td align="right"><b>Method:</b></td>
  <td>POST</td>
</tr><tr>
  <td align="right"><b>Operation:</b></td>
  <td>Upload audio file | add records to tables: MESSAGE, MESSAGE_DISTRO </td>
</tr><tr>
  <td align="right"><b>URL:</b></td>
  <td>/server</td>
</tr><tr>
  <td align="right" valign="top"><h2>Note:</h2></td>
  <td valign="top">
      Variables are sent in POST body <br>
      Directory structure on server: /message/&lt;user_id&gt;/&lt;file_name&gt;
  </td>
</tr>
</table>
<hr>
<!--?php echo ?upload_url?-->

<?php //echo __dir__ . '\user_docs\\';
       //$upload_url = __dir__ . '\user_docs\\'; // DEV_NOTE: might have to pass this url and handle upload on server
       //$upload_url = '/server?action=message_create';
       //echo $upload_url //. '?action=message_create';
       //echo $upload_url //. '?action=message_create';
       //?url = 'http://localhost:15080';
       //?url = 'androidsoundappproject.appspot.com';
?>

<form action=<?php echo $upload_url ?> enctype="multipart/form-data" method="post">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id_sender:</td>
    <td>user_id_target:</td>
   </tr><tr>
    <td><input type="number" name="user_id_sender"></td>
    <td><input type="number" name="user_id_target"></td>
   </tr>
   </table>
   <br>
   <table>
   <tr>
    <td><b>Google generated upload URL:</b></td>
   </tr><tr>
    <td><?php echo $upload_url ?></td>
   </tr>
   </table>  
   <br>
   <table>
   <tr>
    <td><b>Choose File:</b></td>
   </tr><tr>
    <td><input type="file" name="userfile"></td>
    <td><input type="submit" value="Create Message"></td>
    <!--td><input type="hidden" name="upload_url" value="<?php echo $upload_url ?>"/></td-->
    <td><input type="hidden" name="action" value="message_create"/></td>
   </tr>
   </table>
   <br>
</form>
</div>

<!-- 4. POST -->

<div id=main_not>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>user_auth | *** NOT IMPLEMENTED ***</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>POST</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Authenticate user (log in) by verifying email and password pair</td>
</tr><tr>
  <td align="right"><b>URL: </b></td>
  <td>/server</td>
</tr><tr>
  <td align="right"><b>Note: </b></td>
  <td>Variables are sent in POST body</td>
</tr>
</table>
<hr>
<form action="/server" method="post">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>*email_addr:<br></td>
    <td>*user_pw:<br></td>
   </tr>
   <tr>
    <td><input type="text" name="email_addr"><br></td>
    <td><input type="text" name="user_pw"><br></td>
   </tr>
   <tr>
    <td><input type="submit" value="Authenticate User"></td>
    <td><input type="hidden" name="action" value="user_auth"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 5. PUT -->

<div id=main_put>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>user_edit</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>PUT</td>
</tr><tr>
  <td align="right"><b>Operation:</b></td>
  <td>Edit existing user | update user record in table: USER </td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server</td>
</tr><tr>
  <td align="right" valign="top"><h2>Note: </h2></td>
  <td>Requires at least one of: disp_nme or email_addr or user_pw<br>
      *** PUT cannot be performed through an HTML form ***
  </td>
</tr>
</table>
<hr>
<form action="/server" method="post">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id:</td>
   </tr>
   <tr>
    <td><input type="number" name="user_id"></td>
   </tr>
   </table>
   <table>
   <tr>
    <td><b>Optional (at least one):</b></td>
   </tr><tr>
    <td>disp_nme:</td>
    <td>email_addr:</td>
    <td>user_pw:</td>
   </tr>
   <tr>
    <td><input type="text" name="disp_nme"></td>
    <td><input type="text" name="email_addr"></td>
    <td><input type="text" name="user_pw"></td>
   </tr>
   <tr>
    <td><input type="submit" value="Edit User"></td>
    <td><input type="hidden" name="action" value="user_edit"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 6. PUT -->

<div id=main_put>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>user_disable</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>PUT</td>
</tr><tr>
  <td align="right"><b>Operation:</b></td>
  <td>Disable existing user | update user record in table: USER </td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server</td>
</tr><tr>
  <td align="right" valign="top"><h2>Note: </h2></td>
  <td>Sets DATE_EXPIRED to current date/time<br>
      *** PUT cannot be performed through an HTML form ***
  </td>
</tr>
</table>
<hr>
<form action="/server" method="post">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id:</td>
   </tr><tr>
    <td><input type="number" name="user_id" ></td>
   <tr>
    <td><input type="submit" value="Disable User"></td>
    <td><input type="hidden" name="action" value="user_disable"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 7. GET -->

<div id=main_get>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>user_info</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>GET</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Retrieve information for specific user from table: USER</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server?action=user_info&user_id=&lt;user_id&gt;</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td></td>
</tr>
</table>
<hr>
<form action="/server" method="get">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id:</td>
   </tr><tr>
    <td><input type="number" name="user_id"></td>
   <tr>
    <td><input type="submit" value="Get User"></td>
    <td><input type="hidden" name="action" value="user_info"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 7.5. GET -->

<div id=main_get>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>user_info_email</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>GET</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Retrieve user information for specific email from table: USER</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server?action=user_info_email&email_addr=&lt;email_addr&gt;</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td></td>
</tr>
</table>
<hr>
<form action="/server" method="get">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>email_addr:</td>
   </tr><tr>
    <td><input type="text" name="email_addr"></td>
   <tr>
    <td><input type="submit" value="Get User"></td>
    <td><input type="hidden" name="action" value="user_info_email"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 8. GET -->

<div id=main_get>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>contact_all</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>GET</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Retrieve contact list from table: CONTACT</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server?action=contact_all&user_id_owner=&lt;user_id_owner&gt;</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td></td>
</tr>
</table>
<hr>
<form action="/server" method="get">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id_owner:</td>
   </tr><tr>
    <td><input type="number" name="user_id_owner"></td>
   <tr>
    <td><input type="submit" value="Get Contacts"></td>
    <td><input type="hidden" name="action" value="contact_all"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 9. GET -->

<div id=main_get>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>message_count</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>GET</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Gets count of new (un-played) messages from inbox: table MESSAGE_DISTRO</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server?action=message_count&user_id_target=&lt;user_id_target&gt;</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td></td>
</tr>
</table>
<hr>
<form action="/server" method="get">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id_target:</td>
   </tr><tr>
    <td><input type="number" name="user_id_target" ></td>
   <tr>
    <td><input type="submit" value="Get New Message Count"></td>
    <td><input type="hidden" name="action" value="message_count"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 10. GET -->

<div id=main_get>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>message_list</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>GET</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Retrieve message list (new or all) from inbox table: MESSAGE_DISTRO</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server?action=message_list&user_id_target=&lt;user_id_target&gt;&new=&lt;true/false&gt;</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td></td>
</tr>
</table>
<hr>
<form action="/server" method="get">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id_target:</td>
    <td>new (true/false):</td>
   </tr><tr>
    <td><input type="number" name="user_id_target"></td>
    <td><input type="text" name="new"></td>
   </tr>
    <td><input type="submit" value="Get Received Messages"></td>
    <td><input type="hidden" name="action" value="message_list"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 11. GET -->

<div id=main_get>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>message_sent</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>GET</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Retrieve list of all sent messages from table: MESSAGE</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server?action=message_sent&user_id_sender=&lt;user_id_sender&gt;</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td></td>
</tr>
</table>
<hr>
<form action="/server" method="get">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id_sender:</td>
   </tr><tr>
    <td><input type="number" name="user_id_sender"></td>
   </tr><tr>
    <td><input type="submit" value="Get Sent Messages"></td>
    <td><input type="hidden" name="action" value="message_sent"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 12. GET -->

<div id=main_get>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>message_info</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>GET</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Retrieve info for specific message from table: MESSAGE</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server?action=message_info&msg_id=&lt;msg_id&gt;</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td></td>
</tr>
</table>
<hr>
<form action="/server" method="get">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id_sender:</td>
   </tr><tr>
    <td><input type="number" name="msg_id"></td>
   </tr><tr>
    <td><input type="submit" value="Get Sent Messages"></td>
    <td><input type="hidden" name="action" value="message_info"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 12. GET -->

<div id=main_not>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>message_get | *** NOT IMPLEMENTED ***</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>GET</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Retrieve audio file and update DATE_PLAYED to current date/time in table: MESSAGE_DISTRO</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server?action=message_get&msg_id=&lt;msg_id&gt;</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td></td>
</tr>
</table>
<hr>
<form action="/server" method="get">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>msg_id:</td>
   </tr><tr>
    <td><input type="number" name="msg_id"></td>
   </tr><tr>
    <td><input type="submit" value="Get File"></td>
    <td><input type="hidden" name="action" value="message_get"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 14. DELETE -->

<div id=main_delete>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>user_delete</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>DELETE</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Delete user (admin only)</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td>*** DELETE cannot be performed through an HTML form ***</td>
</tr>
</table>
<hr>
<form action="/server" method="post">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id:</td>
   </tr><tr>
    <td><input type="number" name="user_id"></td>
   </tr><tr>
    <td><input type="submit" value="Delete User"></td>
    <td><input type="hidden" name="action" value="user_delete"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 15. DELETE -->

<div id=main_delete>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>contact_delete</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>DELETE</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Delete contact from contact list</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td>*** DELETE cannot be performed through an HTML form ***</td>
</tr>
</table>
<hr>
<form action="/server" method="post">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>contact_id:</td>
   </tr><tr>
    <td><input type="number" name="contact_id"></td>
   </tr><tr>
    <td><input type="submit" value="Delete User"></td>
    <td><input type="hidden" name="action" value="contact_delete"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 16. DELETE -->

<div id=main_delete>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>message_distro_delete</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>DELETE</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Delete message from inbox table: MESSAGE_DISTRO</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td>*** DELETE cannot be performed through an HTML form ***</td>
</tr>
</table>
<hr>
<form action="/server" method="post">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>user_id_target:</td>
    <td>msg_id:</td>
   </tr><tr>
    <td><input type="number" name="user_id_target"></td>
    <td><input type="text" name="msg_id"></td>
   </tr><tr>
    <td><input type="submit" value="Delete Message From Inbox"></td>
    <td><input type="hidden" name="action" value="message_distro_delete"/></td>
   </tr>
   </table>
</form>
</div>

<!-- 17. DELETE -->

<div id=main_not>
<table>
<tr>
  <td align="right"><b>Action: </b></td>
  <td>message_delete | *** NOT IMPLEMENTED ***</td>
</tr><tr>
  <td align="right"><b>Method: </b></td>
  <td>DELETE</td>
</tr><tr>
  <td align="right"><b>Operation: </b></td>
  <td>Delete message from table: MESSAGE (admin only)</td>
</tr><tr>
  <td align="right"><h2>URL: </h2></td>
  <td>/server</td>
</tr><tr>
  <td align="right"><h2>Note: </h2></td>
  <td>*** DELETE cannot be performed through an HTML form ***</td>
</tr>
</table>
<hr>
<form action="/server" method="post">
   <table>
   <tr>
    <td><b>Required:</b></td>
   </tr><tr>
    <td>msg_id:</td>
   </tr><tr>
    <td><input type="number" name="msg_id"></td>
   </tr><tr>
    <td><input type="submit" value="Delete Message"></td>
    <td><input type="hidden" name="action" value="message_delete"/></td>
   </tr>
   </table>
</form>
</div>

</body>
</html>
