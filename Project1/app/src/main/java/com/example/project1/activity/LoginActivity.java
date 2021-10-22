package com.example.project1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project1.R;
import com.example.project1.ultil.CheckConnection;
import com.example.project1.ultil.Sever;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private String email, password;
    Toolbar t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = password = "";
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        t=findViewById(R.id.toobar_dn);
        actionbar();



    }
    private void actionbar() {
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void login(View view) {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if(!email.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.duongdanlogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("res", response);
                    if (response.equals("success")) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else if (response.equals("failure")) {
                        Toast.makeText(LoginActivity.this, "Invalid Login Id/Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", email);
                    data.put("pass", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
            CheckConnection.showToast(getApplicationContext(),"Đăng nhập thành công");
        }else{
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
    }
    public void register(View view) {
        Intent intent = new Intent(this, DangkyActivity.class);
        startActivity(intent);
        finish();
    }

}