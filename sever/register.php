<?php
include "connect.php";
// Include the necessary files
 
    // Call validate, pass form data as parameter and store the returned value
    $name = $_POST['name'];
    $email = $_POST['email'];
    $pass = $_POST['pass'];
if(strlen($name) > 0  && strlen($email) > 0 && strlen($email) > 0&&strlen($pass)){
    $query = "INSERT INTO user(id,name,email,pass)
    VALUES (null, '$name', '$email', '$pass')";
    if (mysqli_query($conn, $query)) {
        $idmadonhang = $conn->insert_id;
        echo "ketnoi";
    } else {
        echo "error";
    }
} else {
    echo "Bạn hãy kiểm tra lại các dữ liệu";
}

?>