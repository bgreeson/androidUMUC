<?php

include 'function.php';
//echo 'action: ' . $_POST['action'];
//echo var_dump($_POST);
//echo 'disp_nme: ' . $_POST['disp_nme'];
//echo 'email_addr: ' . $_POST['email_addr'];
//echo 'user_pw: ' . $_POST['user_pw'];
//echo 'action: ' . $_POST["action"];
//echo "user_id_sender: " . $_POST['user_id_sender'];
//echo "user_id_target: " . $_POST['user_id_target'];

//if(isset($_POST["action"])) {
//  echo "POST";
//} else { echo "NOT POST FOR YOU!!!"; }

if(isset($_POST["action"])) {

    switch ($_POST["action"]) {
      case "user_create":
        $value = user_create($_POST['disp_nme'], $_POST['email_addr'], $_POST['user_pw']);
        break;
      case 'contact_create':
        $value = contact_create($_POST['user_id_owner'], $_POST['user_id_member']);
        break;
      case 'message_create':
        $value = message_create($_POST['user_id_sender'], $_POST['user_id_target'], $_POST['upload_url']);
        break;
      //case 'user_auth':
      //  $value = user_auth($_POST['user_id'], $POST['email_addr']);
      //  break;
      case 'user_edit':
        $value = user_edit($_POST['user_id'], $_POST['disp_nme'], $_POST['email_addr'], $_POST['user_pw']);
        break;
      case 'user_disable':
        $value = user_disable($_POST['user_id']);
        break;
      
      case 'user_delete':
        $value = user_delete($_POST['user_id']);
        break;
      case 'contact_delete':
        $value = contact_delete($_POST['contact_id']);
        break;
      case 'message_distro_delete':
        $value = message_distro_delete($_POST['user_id_target'], $_POST['msg_id']);
        break;  
        
     }
        
 } else if(isset($_PUT['action'])) {

    switch ($_PUT['action']) {
      case 'user_edit':
        $value = user_edit($_PUT['user_id'], $_PUT['disp_nme'], $_PUT['email_addr'], $_PUT['user_pw']);
        break;
      case 'user_disable':
        $value = user_disable($_PUT['user_id']);
        break;
    }
    
 } else if(isset($_GET['action'])) {
    
    switch ($_GET['action']) {
      case 'user_info':
        $value = user_info($_GET['user_id']);
        break;
      case 'contact_all':
        $value = contact_all($_GET['user_id_owner']);
        break;
      case 'message_count':
        $value = message_count($_GET['user_id_target']);
        break; 
      case 'message_list':
        $value = message_list($_GET['user_id_target'], $_GET['new']);
        break;
      case 'message_all':
        $value = message_all($_GET['user_id_target']);
        break;
      case 'message_sent':
        $value = message_sent($_GET['user_id_sender']);
        break;
      case 'message_info':
        $value = message_info($_GET['msg_id']);
        break;
      //case 'message_get':
      // $value = message($_GET['msg_id']);
      //  break;
    } 
    
 } else if(isset($_DELETE['action'])) {

    switch ($_DELETE['action']) {
      case 'user_delete':
        $value = user_delete($_DELETE['user_id']);
        break;
      case 'contact_delete':
        $value = contact_delete($_DELETE['user_id_owner'], $_DELETE['user_id_member']);
        break;
      case 'message_distro_delete':
        $value = message_distro_delete($_DELETE['user_id_target'], $_DELETE['msg_id']);
        break;
      //case 'message_delete':
      //  $value = message_delete($_DELETE['msg_id']);
      //  break;
    }
    
 } else {
    $value = 'Invalid request';
 }

exit(json_encode($value));

?>