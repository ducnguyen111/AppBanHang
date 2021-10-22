package com.example.project1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project1.R;
import com.example.project1.adapter.GiohangAdapter;
import com.example.project1.adapter.LoaispAdapter;
import com.example.project1.adapter.SanphamAdapter;
import com.example.project1.model.GioHang;
import com.example.project1.model.Loaisp;
import com.example.project1.model.SanPham;
import com.example.project1.ultil.CheckConnection;
import com.example.project1.ultil.Sever;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView;
    private ListView listView;
    private NavigationView navigationView;
    private DrawerLayout layout;
    public static ArrayList<GioHang> listgiohang;
    private ArrayList<Loaisp> loaispArrayList;
    private LoaispAdapter loaispAdapter;
    int id = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";
    int idspm = 0;
    String tensp = "";
    Integer giasp = 0;
    String hinhanhsanpham = "";
    String motasp = "";
    int idsp = 0;
    ArrayList<SanPham> lissanpham;
    SanphamAdapter sanphamAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            Quangcao();
            getdulieuloaisp();
            getdulieusanphammoi();
            onclicklistview();
        } else {
            CheckConnection.showToast(getApplicationContext(), "bạn hãy kiểm tra lại kết quả");
            finish();
        }


    }

    private void onclicklistview() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
                        }
                        layout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham", loaispArrayList.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
                        }
                        layout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idloaisanpham", loaispArrayList.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
                        }
                        layout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
                        }
                        layout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
                        }
                        layout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            CheckConnection.showToast(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
                        }
                        layout.closeDrawer(GravityCompat.START);
                        break;
                }
                return false;
            }
        });
    }

    private void getdulieusanphammoi() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.duongdanspmoi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idspm = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensp");
                            giasp = jsonObject.getInt("giasp");
                            hinhanhsanpham = jsonObject.getString("hinhanhsp");
                            motasp = jsonObject.getString("motasp");
                            idsp = jsonObject.getInt("idsp");
                            lissanpham.add(new SanPham(idspm, tensp, giasp, hinhanhsanpham, motasp, idsp));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getdulieuloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            loaispArrayList.add(new Loaisp(id, tenloaisp, hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    loaispArrayList.add(3, new Loaisp(0, "Liên Hệ", "https://cdn.icon-icons.com/icons2/1381/PNG/512/contacteditor_93970.png"));
                    loaispArrayList.add(4, new Loaisp(0, "Thông tin", "https://cdn.icon-icons.com/icons2/1161/PNG/512/1487716857-user_81635.png"));
                    loaispArrayList.add(5, new Loaisp(0, "Đăng xuất", "https://cdn.icon-icons.com/icons2/1151/PNG/512/1486505366-exit-export-out-send-sending-archive-outside_81436.png"));

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Quangcao() {
        ArrayList<String> listqc = new ArrayList<>();
        listqc.add("https://images.fpt.shop/unsafe/filters:quality(90)/fptshop.com.vn/uploads/images/2015/Tin-Tuc/ThuVTK/Banner-laptop_KMT9.jpg");
        listqc.add("https://fpt.vn/storage/upload/images/promotions/khuyen_mai/banner_cam_2048x758.jpg");
        listqc.add("https://fptshop.com.vn/uploads/originals/2021/7/31/637633703456131299_vuotmuadich-thumb.jpg");
        listqc.add("https://photo-cms-tinnhanhchungkhoan.zadn.vn/w660/Uploaded/2021/cqjwqcqdh/2020_08_29/cd292020-frt-8015.png");
        for (int i = 0; i < listqc.size(); i++) {
            ImageView img = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(listqc.get(i)).into(img);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(img);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sile_quangcao);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sile_quangcao1);
        viewFlipper.setInAnimation(animation);
        viewFlipper.setOutAnimation(animation1);
    }


    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView= (SearchView) menu.findItem(R.id.timkiem_sp).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               sanphamAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               sanphamAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_giohang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            case R.id.timkiem_sp:

        }
        return super.onOptionsItemSelected(item);
    }

    public void anhxa() {
        toolbar = findViewById(R.id.home1);
        viewFlipper = findViewById(R.id.quangcao);
        recyclerView = findViewById(R.id.listsanpham);
        listView = findViewById(R.id.listhome);
        navigationView = findViewById(R.id.navi);
        layout = findViewById(R.id.drawer1);
        loaispArrayList = new ArrayList<>();
        loaispArrayList.add(0, new Loaisp(0, "Home", "https://cdn.icon-icons.com/icons2/370/PNG/512/Home3_37171.png"));
        loaispAdapter = new LoaispAdapter(loaispArrayList, getApplicationContext());
        listView.setAdapter(loaispAdapter);

        lissanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(lissanpham, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.addItemDecoration(new SanphamAdapter.SpacesItemDecoration(30));
        recyclerView.setAdapter(sanphamAdapter);
        if (listgiohang != null) {

        } else {
            listgiohang = new ArrayList<>();
        }
    }


}