<?php
include "connect.php";
$email =$_POST['email'];
$pass = $_POST['pass'];
if(strlen($email) > 0  && strlen($pass) ){
  
    $sql = "SELECT * FROM user WHERE email='$email' and pass='$pass'";
    
    $result = $conn->query($sql);
 
    if($result->num_rows > 0){
        echo "success";
    } else{
        
        echo "failure";
    }
}
?>