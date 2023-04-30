

<?php
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;
require  "vendor/autoload.php";

// Replace with your database credentials
$host = 'localhost';
$dbname = 'login_register';
$username = 'root';
$password = '';

if (isset($_POST['email']) && !empty($_POST['email'])) {
    $email = filter_var($_POST['email'], FILTER_SANITIZE_EMAIL);
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        echo "Invalid email address";
        exit();
    }

    $con = mysqli_connect($host, $username, $password, $dbname);
    if ($con) {
        try {
            $otp = random_int(100000, 99999999);
        } catch (Exception $e) {
            $otp = rand(100000, 99999999);
        }

        $sql = "UPDATE user SET reset_password_otp = '".$otp."', reset_password_created_at='".date('Y-m-d H:i:s')."' WHERE email = '".$email."'";
        if (mysqli_query($con, $sql)) {
            if (mysqli_affected_rows($con)) {
                //Create an instance; passing `true` enables exceptions
                $mail = new PHPMailer(true);

                try {
                    //Server settings
                    $mail->isSMTP();                                    //Send using SMTP
                    $mail->Host = 'smtp.gmail.com';                     //Set the SMTP server to send through
                    $mail->SMTPAuth = true;                             //Enable SMTP authentication
                    $mail->Username = 'overwatchada@gmail.com';         //SMTP username
                    $mail->Password = 'dlxzzkolpouhgigo';                //SMTP password
                    $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS; //Enable implicit TLS encryption
                    $mail->Port = 587;                                  //TCP port to connect to; use 465 for SMTP over SSL/TLS

                    //Recipients
                    $mail->setFrom('overwatchada@gmail.com', 'OverWatch Accident Detection');
                    $mail->addAddress($email);                   //Add a recipient
                    $mail->addReplyTo('info@example.com', 'Information');

                    //Content
                    $mail->isHTML(true);                         //Set email format to HTML
                    $mail->Subject = 'Reset Password';
                    $mail->Body = 'Your OTP to reset password is [' . $otp.']';

                $mail->AltBody = 'Reset Password to access OverWatch ';

                if ($mail->send()) {
                    echo'success';
                }else {
                    echo"Failed to send OTP";
                }
            } catch (Exception $e) {
                echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
            }

        } else echo "Reset password failed";
    }else echo "Reset password failed";
    }else echo " Database connection failed";
    }else echo " All fields required";

