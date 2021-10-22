package com.example.project1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project1.R;
import com.example.project1.adapter.ThanhToanAdapter;
import com.example.project1.model.ThanhToan;
import com.example.project1.ultil.CheckConnection;
import com.example.project1.ultil.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThanhToanActivity extends AppCompatActivity {
    TextView ten, sdt, gmail;
    Toolbar toolbar;
    ArrayList<ThanhToan> list;
    ThanhToanAdapter adapter;
    ListView recyclerView;
    AppCompatButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            anhxa();
            actionbar();
            laydulieu();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ThanhToanActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            CheckConnection.showToast(getApplicationContext(), "bạn hãy kiểm tra lại kết quả");
            finish();
        }
    }

    private void laydulieu() {
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
                          list.add(new ThanhToan(id,tenkh,sodienthoai,email,ngay,diachi));
                            adapter.notifyDataSetChanged();
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
        recyclerView=findViewById(R.id.list_thanhtoan);
        toolbar = findViewById(R.id.toolbar_thanhtoan);
        ten = findViewById(R.id.thanhtoan_ten);
        sdt = findViewById(R.id.thanhtoan_sdt);
        gmail = findViewById(R.id.thanhtoan_gmail);
        button=findViewById(R.id.ii);
        list=new ArrayList<>();
        adapter=new ThanhToanAdapter(ThanhToanActivity.this,list);
        recyclerView.setAdapter(adapter);
    }

}