<?php

	include "connect.php";
	/*$json = */
	$json =$_POST['json'];
	$data = json_decode($json, true);
	foreach ($data as $value) {
		$makhachhang = $value['makhachhang'];
		$masanpham = $value['masanpham'];
		$tensanpham = $value['tensanpham'];
		$giasanpham = $value['giasanpham'];
		$soluongsanpham = $value['soluongsanpham'];
		$query = "INSERT INTO chitietdonhang (id, makhachhang,masanpham,tensanpham,giasanpham,soluongsanpham)
			VALUES (null, '$makhachhang', '$masanpham', '$tensanpham', '$giasanpham', '$soluongsanpham')";
		$Dta = mysqli_query($conn, $query);
	}
	if ($Dta) {
		echo "success";
	} else {
		echo "error";
	}
?>