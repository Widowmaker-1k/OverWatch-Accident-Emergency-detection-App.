<?php
// Replace with your database credentials
$host = 'localhost';
$dbname = 'login_register';
$username = 'root';
$password = '';

// Establish a connection to the database
$db = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

// Get the username from the HTTP request
$username = $_GET['username'];

// Prepare and execute a query to get the user data
$stmt = $db->prepare('SELECT fullname, email, phone_number,username FROM user WHERE username = :username');
$stmt->execute(['username' => $username]);
$user = $stmt->fetch(PDO::FETCH_ASSOC);

// Return a JSON response with the user data
header('Content-Type: application/json');
echo json_encode($user);
?>
