<?php

	
	if (!isset($_FILES["soundFile"]))
	{
		// error
		exit;
	}

		
	$uploadDir = "uploads/";
	if (!file_exists($uploadDir))
	{
		mkdir($uploadDir, 0777, true);
	}
	
	$uploadedFile = $uploadDir.$_FILES["soundFile"]["name"];
	
	if (is_uploaded_file($_FILES["soundFile"]["tmp_name"]))
	{
		if (!move_uploaded_file($_FILES["soundFile"]["tmp_name"], $uploadedFile))
		{
			// error
			exit;
		}
	}
										
?>
