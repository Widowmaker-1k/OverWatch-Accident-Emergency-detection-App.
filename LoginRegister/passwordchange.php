

<?php
require_once "DataBase.php";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST["username"];
    $password = $_POST["password"];

    $db = new DataBase();
    if ($db->dbConnect()) {
        if ($db->logIn("user", $username, $password,)) {
            // Redirect to the home page or dashboard
            echo "Password Updated " ;

        } else {
            // Incorrect username or password
            echo "Username or Password Incorrect";
        }
    } else {
        // Database connection error
        echo "Error: Database connection";
    }
} else echo "All fields are required";

