package com.example.project1.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
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
import com.example.project1.adapter.GiohangAdapter;
import com.example.project1.model.GioHang;
import com.example.project1.model.ThanhToan;
import com.example.project1.ultil.CheckConnection;
import com.example.project1.ultil.Sever;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class GioHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    static TextView tongtien1;
    TextView thongbao;
    int mYear, mMonth, mDay;
    Button thanhtoan, muahang;
    ArrayList<GioHang> list;
    GiohangAdapter adapter;
    TextInputEditText tenkh, gamil, sdt, ngay, diachi;
    AppCompatButton xacnhan, trove;
    ArrayList<ThanhToan> thanhToanArrayList;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhxa();
        actionbar();
        CheckData();
        event();
        xoa();
        onclickbutton();
    }

    private void onclickbutton() {
        muahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.listgiohang.size() > 0) {
                    Dialog dialog = new Dialog(GioHangActivity.this);
                    dialog.setContentView(R.layout.dialog_thongtinkhachhang);
                    dialog.show();
                    tenkh = dialog.findViewById(R.id.donhang_ten);
                    sdt = dialog.findViewById(R.id.donhang_sdt);
                    gamil = dialog.findViewById(R.id.donhang_gmail);
                    xacnhan = dialog.findViewById(R.id.donhang_xacnhan);
                    trove = dialog.findViewById(R.id.donhang_thoat);
                    ngay = dialog.findViewById(R.id.donhang_ngay);
                    diachi = dialog.findViewById(R.id.donhang_diachi);
                    trove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    ngay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            mYear = c.get(Calendar.YEAR);
                            mMonth = c.get(Calendar.MONTH);
                            mDay = c.get(Calendar.DAY_OF_MONTH);
                            DatePickerDialog d = new DatePickerDialog(GioHangActivity.this, 0, ss, mYear, mMonth, mDay);
                            d.show();
                        }
                    });

                    xacnhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String ten1 = tenkh.getText().toString().trim();
                            String sdt1 = sdt.getText().toString().trim();
                            String email1 = gamil.getText().toString().trim();
                            String ngay1=ngay.getText().toString().trim();
                            String diachi1=diachi.getText().toString().trim();
                            if (ten1.length() > 0 && sdt1.length() > 0 && email1.length() > 0&&ngay1.length() > 0&&diachi1.length()>0) {
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.duongdankhachhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(final String makhachhang) {
                                        if (Integer.parseInt(makhachhang) > 0) {
                                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                            StringRequest request = new StringRequest(Request.Method.POST, Sever.duongdanchitietdonhang, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    if (response.equals("success")) {
                                                        MainActivity.listgiohang.clear();
                                                        CheckConnection.showToast(getApplicationContext(), "bạn đã thêm dữ liệu thành công");
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
                                                    JSONArray jsonArray = new JSONArray();
                                                    for (int i = 0; i < MainActivity.listgiohang.size(); i++) {
                                                        JSONObject jsonObject = new JSONObject();
                                                        try {
                                                            jsonObject.put("makhachhang", makhachhang);
                                                            jsonObject.put("masanpham", MainActivity.listgiohang.get(i).getIdsp());
                                                            jsonObject.put("tensanpham", MainActivity.listgiohang.get(i).getTensp());
                                                            jsonObject.put("giasanpham", MainActivity.listgiohang.get(i).getGiasp());
                                                            jsonObject.put("soluongsanpham", MainActivity.listgiohang.get(i).getSoluong());
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        jsonArray.put(jsonObject);
                                                    }
                                                    HashMap<String, String> hashMap = new HashMap<>();
                                                    hashMap.put("json", jsonArray.toString());
                                                    return hashMap;
                                                }
                                            };
                                            queue.add(request);
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
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("tenkhachhang", ten1);
                                        hashMap.put("sodienthoai", sdt1);
                                        hashMap.put("email", email1);
                                        hashMap.put("ngay",ngay1);
                                        hashMap.put("diachi",diachi1);
                                        return hashMap;
                                    }
                                };
                                requestQueue.add(stringRequest);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                                tongtien1.setText("Tổng:" + "0 đ");
                                Intent intent = new Intent(GioHangActivity.this, ThanhToanActivity.class);
                                startActivity(intent);
                            } else {
                                CheckConnection.showToast(getApplicationContext(), "Hãy kiểm tra lại dữ liệu");
                            }

                        }
                    });

                } else {
                    CheckConnection.showToast(getApplicationContext(), "Giỏ hàng của bạn chưa có sản phẩm");
                }

            }
        });
    }

    private void xoa() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Bạn có chắc xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.listgiohang.size() < 0) {
                            Toast.makeText(GioHangActivity.this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                        } else {
                            MainActivity.listgiohang.remove(position);
                            adapter.notifyDataSetChanged();
                            event();
                            if (MainActivity.listgiohang.size() < 0) {
                                Toast.makeText(GioHangActivity.this, "Giỏ hàng đã trống", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetInvalidated();
                        event();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void event() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.listgiohang.size(); i++) {
            tongtien += MainActivity.listgiohang.get(i).getGiasp();

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien1.setText("Tổng:" + decimalFormat.format(tongtien) + "đ");
    }

    private void CheckData() {
        if (MainActivity.listgiohang.size() <= 0) {
            adapter.notifyDataSetChanged();


        } else {
            adapter.notifyDataSetChanged();

        }
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

    public void anhxa() {
        toolbar = findViewById(R.id.toolbar_giohang);
        listView = findViewById(R.id.list_giohang);
        tongtien1 = findViewById(R.id.giohang_tien);
        thanhtoan = findViewById(R.id.btn_thanhtoan);
        muahang = findViewById(R.id.btn_tieptuc);
        thanhToanArrayList = new ArrayList<>();
        list = new ArrayList<>();
        adapter = new GiohangAdapter(getApplicationContext(), MainActivity.listgiohang);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_giohang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    DatePickerDialog.OnDateSetListener ss = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            ngay.setText(sdf.format(c.getTime()));
        }
    };
}