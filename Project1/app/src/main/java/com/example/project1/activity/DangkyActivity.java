package com.example.project1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class DangkyActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etReenterPassword;
    private TextView tvStatus;
    private Button btnRegister;
    public String name, email, password, reenterPassword;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etReenterPassword = findViewById(R.id.etReenterPassword);
        tvStatus = findViewById(R.id.tvStatus);
        btnRegister = findViewById(R.id.btnRegister);
        toolbar=findViewById(R.id.toolbar_dk);
        actionbar();
        name = email = password = reenterPassword = "";
        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                reenterPassword = etReenterPassword.getText().toString().trim();
                if(!password.equals(reenterPassword)){
                    Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
                }
                else if(!name.equals("") && !email.equals("") && !password.equals("")){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.duongdandangky, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("ketnoi")) {
                                tvStatus.setText("Successfully registered.");
                                btnRegister.setClickable(false);
                            } else if (response.equals("failure")) {
                                tvStatus.setText("Something went wrong!");                    }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("name", name);
                            data.put("email", email);
                            data.put("pass", password);
                            return data;
                        }
                    };
                    requestQueue.add(stringRequest);
                    CheckConnection.showToast(getApplicationContext(),"Đăng ký thành công");
                    etName.setText("");
                    etEmail.setText("");
                    etPassword.setText("");
                    etReenterPassword.setText("");
                }
            }
        });
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
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

}