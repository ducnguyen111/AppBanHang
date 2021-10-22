package com.example.project1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project1.R;
import com.example.project1.model.ThanhToan;
import com.example.project1.ultil.CheckConnection;
import com.example.project1.ultil.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ThongTinActivity extends AppCompatActivity {
TextView ten1,sdt1,email1,diachi1;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        anhxa();
        actionbar();
        getdulieu();
    }

    private void getdulieu() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.duongdanthanhtoan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int id = 0;
                            String tenkh = "";
                            int sodienthoai = 0;
                            String email = "";
                            String ngay ="";
                            String diachi="";
                            id = jsonObject.getInt("id");
                            tenkh = jsonObject.getString("ten");
                            sodienthoai = jsonObject.getInt("sodienthoai");
                            email = jsonObject.getString("email");
                            ngay=jsonObject.getString("ngay");
                            diachi=jsonObject.getString("diachi");
                           ten1.setText("HỌ và tên: "+tenkh);
                           sdt1.setText("Số điện thoại: "+sodienthoai);
                           email1.setText("Email: "+email);
                           diachi1.setText("Địa chỉ: "+diachi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
    private void anhxa() {
        ten1=findViewById(R.id.ten_user);
        sdt1=findViewById(R.id.sdt_user);
        email1=findViewById(R.id.email_user);
        diachi1=findViewById(R.id.diachi_user);
        toolbar=findViewById(R.id.toolbar_thongtin);
    }
}