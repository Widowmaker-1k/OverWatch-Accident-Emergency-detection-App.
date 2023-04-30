<?php


require "DataBase.php";
$db = new DataBase();

$result = mysqli_query($conn, "SELECT value FROM 'contact_info' WHERE id = 1");
 $row = mysqli_fetch_assoc($result);
echo json_encode($row);