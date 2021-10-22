<?php
	include "connect.php";
	$tenkhachhang = $_POST['tenkhachhang'];
	$sodienthoai =$_POST['sodienthoai'];
	$email =$_POST['email'];
	$ngay=$_POST['ngay'];
	$diachi=$_POST['diachi'];
	if (strlen($tenkhachhang) > 0  && strlen($sodienthoai) > 0 && strlen($email) > 0&&strlen($ngay) > 0&&strlen($diachi) > 0) {
		$query = "INSERT INTO khachhang(id,tenkhachhang,sodienthoai,email,ngay,diachi)
		VALUES (null, '$tenkhachhang', '$sodienthoai', '$email','$ngay','$diachi')";
		if (mysqli_query($conn, $query)) {
			$idmadonhang = $conn->insert_id;
			echo $idmadonhang;
		} else {
			echo "error";
		}
	} else {
		echo "Bạn hãy kiểm tra lại các dữ liệu";
	}
?>