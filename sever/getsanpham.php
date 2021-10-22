<?php
    include "connect.php";
    $page=$_GET['page'];
    $idsp=$_POST['idsanpham'];
    $space=5;
    $limit=($page-1)*$space;
    $mangsp = array();
    $query = "SELECT * FROM sanpham WHERE idsanpham=$idsp LIMIT $limit,$space";
    $data = mysqli_query($conn, $query);
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