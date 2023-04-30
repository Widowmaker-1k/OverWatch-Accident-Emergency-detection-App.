<?php
require_once "DataBase.php";
// Replace with your database credentials
$host = 'localhost';
$dbname = 'login_register';
$dbusername = 'root';
$dbpassword = '';

$db = new DataBase();
if (isset($_POST['fullname']) && isset($_POST['email']) &&
    isset($_POST['phone_number']) && isset($_POST['old_password']) && isset($_POST['password']) && isset($_POST['username'])) {
    if ($db->dbConnect()) {
        $fullname = $db->prepareData($_POST['fullname']);
        $phone_number = $db->prepareData($_POST['phone_number']);
        $email = $db->prepareData($_POST['email']);
        $username = $db->prepareData($_POST['username']);
        $password = $db->prepareData($_POST['password']);
        $password_old = $db->prepareData($_POST['old_password']);

        $result = mysqli_query($db->connect, "SELECT password FROM user WHERE username='$username'");
        $con = mysqli_connect($host, $dbusername, $dbpassword, $dbname);

        if ($con) {
            $row = mysqli_fetch_assoc($result);
            $dbupassword = $row['password'];
            if ($password_old == $dbupassword) {
                $sql = "UPDATE user SET password = '".$password."', fullname ='".$fullname."', email='".$email."', phone_number ='".$phone_number."' WHERE username='".$username."'";
                if (mysqli_query($con, $sql)) {
                    if (mysqli_affected_rows($con)) {
                        echo "Success";
                    } else {
                        echo "Update Failed";
                    }
                } else {
                    echo "Update Failed";
                }
            } else {
                echo "Old Password Verification Failed";
            }
        } else {
            echo "Error: Database connection";
        }
    } else {
        echo "Error: Database connection";
    }
} else {
    echo "All fields are required";
}
