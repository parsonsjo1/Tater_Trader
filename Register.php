<?php
	$con=mysqli_connect("localhost", "my_user", "my_password", "my_db");
	
	$username = $_POST["username"];
	$password = $_POST["password"];
	$name = $_POST["name"];
	$age = $_POST["age"];
	
	$statement = mysqli_perpare($con, "INSERT INTO User (username, password, name, age) VALUES (? , ? , ? , ?)");
	mysqli_stmt_bind_param($statement, "sssi", $username, $password, $name, $age);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_close($statement);
	
	mysqli_close($con);
?>