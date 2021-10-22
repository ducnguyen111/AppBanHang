<?php
    include "connect.php";
    $query = "SELECT * FROM khachhang ORDER BY ID DESC LIMIT 1";
    $data = mysqli_query($conn, $query);
    $mangsp = array();
    while($row = mysqli_fetch_assoc($data)){
        array_push($mangsp, new khachhang(
            $row['id'],
            $row['tenkhachhang'],
            $row['sodienthoai'],
            $row['email'],
            $row['ngay'],
            $row['diachi'],
        ));
    }
    echo json_encode($mangsp);
    class khachhang{
        function __construct ($id,$ten,$sodienthoai,$email,$ngay,$diachi){
            $this->id = $id;
            $this->ten = $ten;
            $this->sodienthoai = $sodienthoai;
            $this->email = $email;
            $this->ngay = $ngay;
            $this->diachi=$diachi;
        }
    }
?>