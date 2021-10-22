<?php
    include "connect.php";
    $query = "SELECT * FROM sanpham ORDER BY ID DESC LIMIT 6";
    $data = mysqli_query($conn, $query);
    $mangsp = array();
    while($row = mysqli_fetch_assoc($data)){
        array_push($mangsp, new spmoi(
            $row['id'],
            $row['tensanpham'],
            $row['giasanpham'],
            $row['hinhanhsanpham'],
            $row['motasanpham'],
            $row['idsanpham'],
        ));
    }
    echo json_encode($mangsp);
    class spmoi{
        function __construct ($id, $tensp, $giasp,$hinhanhsp,$motasp,$idsp){
            $this->id = $id;
            $this->tensp = $tensp;
            $this->giasp = $giasp;
            $this->hinhanhsp = $hinhanhsp;
            $this->motasp = $motasp;
            $this->idsp = $idsp;
        }
    }
?>