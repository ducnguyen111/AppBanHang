package com.example.project1.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project1.R;
import com.example.project1.adapter.DienThoaiAdapter;
import com.example.project1.model.SanPham;
import com.example.project1.ultil.CheckConnection;
import com.example.project1.ultil.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    DienThoaiAdapter adapter;
    ArrayList<SanPham> list;
    int iddt = 0;
    int page = 1;
    private View footerLoad;
    boolean isloading = false;
    private HandlerLoad handlerLoad;
    boolean checkdata = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getiddt();
            actionbar();
            getdata(page);
            loadmoredata();
        } else {
            CheckConnection.showToast(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
            finish();
        }
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


    private void loadmoredata() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ChiTietDonHangActivity.class);
                intent.putExtra("thongtinsanpham",list.get(position));
                startActivity(intent);
                return false;
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && isloading == false && checkdata == false) {
                    isloading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void getdata(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = Sever.duongdandt + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String ten = "";
                int gia = 0;
                String hinhanh = "";
                String mota = "";
                int idsp = 0;
                if (response != null && response.length() > 0) {
                    listView.removeFooterView(footerLoad);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            ten = jsonObject.getString("tensp");
                            gia = jsonObject.getInt("giasp");
                            hinhanh = jsonObject.getString("hinhanhsp");
                            mota = jsonObject.getString("motasp");
                            idsp = jsonObject.getInt("idsp");
                            list.add(new SanPham(id, ten, gia, hinhanh, mota, idsp));
                            adapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    checkdata = true;
                    listView.removeFooterView(footerLoad);
                    CheckConnection.showToast(getApplicationContext(), "Hết dữ liệu");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
                stringStringHashMap.put("idsanpham", String.valueOf(iddt));
                return stringStringHashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getiddt() {
        iddt = getIntent().getIntExtra("idloaisanpham", -1);
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbar_dienthoai);
        listView = findViewById(R.id.list_dienthoai);
        list = new ArrayList<>();
        adapter = new DienThoaiAdapter(getApplicationContext(), list);
        listView.setAdapter(adapter);
        handlerLoad = new HandlerLoad();
        footerLoad = LayoutInflater.from(getApplicationContext()).inflate(R.layout.loaing_dienthoai ,null);

    }

    public class HandlerLoad extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listView.addFooterView(footerLoad);
                    break;
                case 1:
                    //page++;
                    getdata(++page);
                    isloading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            handlerLoad.sendEmptyMessage(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Phuoc thuc obtainMessage() lien ket Thread voi Hanler.
            Message message = handlerLoad.obtainMessage(1);
            handlerLoad.sendMessage(message);
            super.run();
        }
    }

}