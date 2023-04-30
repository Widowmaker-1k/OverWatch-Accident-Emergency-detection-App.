<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password)
    {
        $stmt = $this->connect->prepare("SELECT * FROM $table WHERE username = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $result = $stmt->get_result();
        if ($result->num_rows == 1) {
            $row = $result->fetch_assoc();
            $dbpassword = $row['password'];
            if ($password == $dbpassword) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
            }
    }


    function passwordchange($table, $username, $password,$new_password)
    {
        $stm = $this->connect->prepare("SELECT * FROM $table WHERE username = ? and update user set  $password = '".$new_password."', ");
        $stm->bind_param("s", $username,$new_password);
        $stm->execute();
        $result = $stm->get_result();
        if ($result->num_rows == 1) {
            $row = $result->fetch_assoc();
            $dbpassword = $row['password'];
            if ($password == $dbpassword) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    function signUp($table, $fullname, $email, $username,$phone_number, $password)
    {
        $fullname = $this->prepareData($fullname);
        $username = $this->prepareData($username);
        $password =$this->prepareData($password);
        $email = $this->prepareData($email);
        $phone_number = $this->prepareData($phone_number);



        $this->sql =
            "INSERT INTO " . $table. " (fullname, username, password, email,phone_number) VALUES ('" . $fullname . "','" . $email . "','" . $username . "','" . $phone_number . "','". $password ."')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }





    function getUserInfo($username)
    {
        $db = new DataBase();
        if ($db->dbConnect()) {
            $stmt = $db->connect->prepare("SELECT email, phone_number, username, fullname FROM user WHERE username = ?");
            $stmt->bind_param("s", $username);
            $stmt->execute();
            $result = $stmt->get_result();
            if ($result->num_rows == 1) {
                $row = $result->fetch_assoc();
                return $row;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    function addData($table, $person1, $contact1, $relationship1,$person2, $contact2,$relationship2,$person3,$contact3,$relationship3,$newid)
    {
        $person1 = $this->prepareData($person1);
        $contact1 = $this->prepareData($contact1);
        $relationship1 =$this->prepareData($relationship1);
        $person2= $this->prepareData($person2);
        $contact2= $this->prepareData($contact2);
        $relationship2= $this->prepareData($relationship2);
        $person3= $this->prepareData($person3);
        $contact3= $this->prepareData($contact3);
        $relationship3= $this->prepareData( $relationship3);
        $newid = $this->prepareData( $newid);


        $this->sql =
            "INSERT INTO " . $table. " (person1, contact1, relationship1, person2,contact2,relationship2,person3,contact3,relationship3,newid) VALUES ('" .$person1 . "','" . $contact1. "','" . $relationship1 . "','" .  $person2 . "','". $contact2 ."','". $relationship2 ."','".  $person3 ."','". $contact3."','".$relationship3."','".$newid."')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

}


