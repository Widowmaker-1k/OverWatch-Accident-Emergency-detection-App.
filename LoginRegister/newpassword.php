<?php

$host = 'localhost';
$dbname = 'login_register';
$username = 'root';
$password = '';

if (!empty($_POST['email']) && !empty($_POST['otp']) && !empty($_POST['new_password'])) {
    $email = $_POST['email'];
    $otp = $_POST['otp'];
    $new_password = $_POST['new_password'];

    // Print out received values
  //  echo "Received values:\n";
  //  echo "Email: " . $email . "\n";
  //  echo "OTP: " . $otp . "\n";
  //  echo "New password: " . $new_password . "\n";


    $con = mysqli_connect("localhost", "root", "", "login_register");
    if ($con) {
        $sql = "update user set  password = '".$new_password."', reset_password_otp =   '',reset_password_created_at=''where email = '".$email."' and reset_password_otp = '".$otp."'";
        if (mysqli_query($con, $sql)) {
            if (mysqli_affected_rows($con)) {
                echo "success";
            } else {
                echo "Reset password failed";
            }
        } else {
            echo "Reset password failed";
        }
    } else {
        echo "Database connection failed";
    }
} else {
    echo "All fields required";
}
?>
