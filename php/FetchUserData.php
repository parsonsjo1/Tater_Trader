<?php
	$con=mysqli_connect("localhost", "my_user", "my_password", "my_db");
	
	$username = $_POST["username"];
	$password = $_POST["password"];
	
	$statement = mysqli_prepare($con, "SELECT * FROM User Where username = ? AND password = ?");
	mysqli_stmt_bind_param($statment, "ss", $username, $password);
	mysqli_stmt_execute($statment);
	
	mysqli_stmt_store_result($statment);
	mysqli_stmt_bind_result($statment, $userID, $username, $password, $name, $age);
	
	$user = array();
	
	while(mysqli_stmt_fetch($statement))
	{
		$user[username] = $username;
		$user[password] = $password;
		$user[name] = $name;
		$user[age] = $age;
		
	}
	
	echo json_encode($user);
	
	mysqli_stmt_close($statment);
	
	mysqli_close($con);
?>