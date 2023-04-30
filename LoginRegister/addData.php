<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['person1']) && isset($_POST['contact1']) &&
    isset($_POST['relationship1'])&& isset($_POST['person2'])&& isset($_POST['contact2'])&& isset($_POST['relationship2'])&& isset($_POST['person3'])&& isset($_POST['contact3'])&& isset($_POST['relationship3']) && isset($_POST['newid'])){
    if ($db->dbConnect()) {
        if ($db->addData("contact_info", $_POST['person1'], $_POST['contact1'], $_POST['relationship1'],$_POST['person2'], $_POST['contact2'], $_POST['relationship2'], $_POST['person3'], $_POST['contact3'], $_POST['relationship3'],$_POST['newid'])) {
            echo "Data added";
        } else echo "Data Failed to ";
    } else echo "Error: Database connection";
} else echo "All fields are required";