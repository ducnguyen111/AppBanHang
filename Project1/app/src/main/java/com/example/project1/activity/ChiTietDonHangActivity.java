package com.example.project1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.model.GioHang;
import com.example.project1.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietDonHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView hinhanh;
    TextView ten, gia, mota;
    Spinner spinner;
    Button button;
    int id = 0, idsp = 0, giact = 0;
    String tenct = "", motact = "", hinhanhct = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        anhxa();
        actionbar();
        getdatasss();
        catchspiner();
        btnevent();
    }

    private void btnevent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.listgiohang.size() > 0) {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exsits = false;
                    for (int i = 0; i < MainActivity.listgiohang.size(); i++) {
                        if (MainActivity.listgiohang.get(i).getIdsp() == id) {
                           MainActivity.listgiohang.get(i).setSoluong(MainActivity.listgiohang.get(i).getSoluong() + sl);
                            if (MainActivity.listgiohang.get(i).getSoluong() >= 10) {
                               MainActivity.listgiohang.get(i).setSoluong(10);
                            }
                            MainActivity.listgiohang.get(i).setGiasp(giact * MainActivity.listgiohang.get(i).getSoluong());
                            exsits = true;
                        }
                    }
                    if (exsits == false) {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi = soluong *giact;
                       MainActivity.listgiohang.add(new GioHang(id, tenct, giamoi, hinhanhct, soluong));
                    }
                } else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi = soluong * giact;
                    MainActivity.listgiohang.add(new GioHang(id, tenct, giamoi, hinhanhct, soluong));
                }
                Intent intent = new Intent(ChiTietDonHangActivity.this,GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void catchspiner() {
        Integer[] sl = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, sl);
        spinner.setAdapter(arrayAdapter);
    }

    private void getdatasss() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getId();
        tenct = sanPham.getTensanpham();
        giact = sanPham.getGiasanpham();
        hinhanhct = sanPham.getHinhanhsanpham();
        motact = sanPham.getMotasanpham();
        idsp = sanPham.getIdsanpham();
        ten.setText(tenct);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        gia.setText(decimalFormat.format(giact) + "VNĐ");
        mota.setText(motact);
        Picasso.with(getApplicationContext()).load(hinhanhct)
                .placeholder(R.drawable.ic_baseline_cloud_download_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(hinhanh);
    }

    public void anhxa() {
        toolbar = findViewById(R.id.toolbar_ct);
        hinhanh = findViewById(R.id.ct_hinhanh);
        ten = findViewById(R.id.ct_ten);
        gia = findViewById(R.id.ct_gia);
        mota = findViewById(R.id.ct_mota);
        spinner = findViewById(R.id.ct_sl);
        button = findViewById(R.id.ct_giohang);
    }

    public void actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_giohang:
                Intent intent=new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

}