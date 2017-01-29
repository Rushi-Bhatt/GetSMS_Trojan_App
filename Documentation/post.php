<?php
    # Read GET variables
    $tn = $_POST['number'];
	$zn = $_POST['body'];
	
	//echo $num;
	$con=mysqli_connect("localhost","root","","hunt");
    mysqli_connect_errno($con);
	
	$result = mysqli_query($con,"INSERT INTO rushi (Number,Message) VALUES ('$tn', '$zn')");  
	# Create output string
	
    
     echo "done";
	//header("Location: visual.php");
?>