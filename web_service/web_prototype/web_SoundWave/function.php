<?php

function get_file_metadata($file) {
  header("HTTP/1.1 $status $status_message");
  
  $file_name = $file['user']['name'];
  $file_type = $file['type'];	
  $file_tmp_name = $file['tmp_name'];	
  $file_error = $file['error'];
  $file_size = $file['size'];		
  
  $response['name'] = $file_name;
  $response['type'] = $file_type;
  $response['tmp_name'] = $file_tmp_name;
  $response['tmp_error'] = $file_tmp_error;
  $response['tmp_size'] = $file_tmp_size;

  $json_response = json_encode($response);
  echo $json_response; 

}


function http_response($status, $status_message, $data) {
  header("HTTP/1.1 $status $status_message");
  
  $response['status'] = $status;
  $response['status_message'] = $status_message;
  $response['data'] = $data;
  
  $json_response = json_encode($response);
  echo $json_response; 
}


?>