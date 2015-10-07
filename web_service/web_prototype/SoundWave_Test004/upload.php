<?php

include 'function.php';

$value = message_upload();

exit(json_encode($value));

?>