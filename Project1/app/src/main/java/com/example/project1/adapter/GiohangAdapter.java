package com.example.project1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.activity.GioHangActivity;
import com.example.project1.activity.MainActivity;
import com.example.project1.model.GioHang;
import com.example.project1.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GioHang> arrayListGioHang;

    public GiohangAdapter(Context context, ArrayList<GioHang> arrayListGioHang) {
        this.context = context;
        this.arrayListGioHang = arrayListGioHang;
    }

    @Override
    public int getCount() {
        return arrayListGioHang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListGioHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_giohang, null);
            viewHolder.txtTengiohang = view.findViewById(R.id.tvTengiohang);
            viewHolder.txtGiamonhang = view.findViewById(R.id.tvGiamonhang);
            viewHolder.imgGiohang = view.findViewById(R.id.imgGiohang);
            viewHolder.btnTru = view.findViewById(R.id.btnTru);
            viewHolder.btnGiatri = view.findViewById(R.id.btnGiatri);
            viewHolder.btnCong = view.findViewById(R.id.btnCong);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        viewHolder.txtTengiohang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiamonhang.setText(decimalFormat.format(gioHang.getGiasp()).replace(",", ".") + "đ");
        Picasso.with(context).load(gioHang.getHinhanh())
                .placeholder(R.drawable.ic_baseline_cloud_download_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(viewHolder.imgGiohang);
        viewHolder.btnGiatri.setText(String.valueOf(gioHang.getSoluong()));
        //Xu ly Button Tang giam
        int sl= Integer.parseInt(viewHolder.btnGiatri.getText().toString());
         viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int slmoinhat = Integer.parseInt(viewHolder.btnGiatri.getText().toString()) + 1;
                 int slhientai =MainActivity.listgiohang.get(i).getSoluong();
                 long giahientai = MainActivity.listgiohang.get(i).getGiasp();
                 MainActivity.listgiohang.get(i).setSoluong(slmoinhat);
                 long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainActivity.listgiohang.get(i).setGiasp(giamoinhat);
                 DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                 viewHolder.txtGiamonhang.setText(decimalFormat.format(giamoinhat).replace(",", ".") + "đ");
                 //set Tong tien
                 GioHangActivity.event();
                 if (slmoinhat > 9) {
                     viewHolder.btnCong.setVisibility(View.INVISIBLE);
                     viewHolder.btnTru.setVisibility(View.VISIBLE);
                     viewHolder.btnGiatri.setText(String.valueOf(slmoinhat));
                 } else {
                     viewHolder.btnCong.setVisibility(View.VISIBLE);
                     viewHolder.btnTru.setVisibility(View.VISIBLE);
                     viewHolder.btnGiatri.setText(String.valueOf(slmoinhat));
                 }

             }
         });
         viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int slmoinhat = Integer.parseInt(viewHolder.btnGiatri.getText().toString()) - 1;
                 int slhientai =MainActivity.listgiohang.get(i).getSoluong();
                 long giahientai = MainActivity.listgiohang.get(i).getGiasp();
                 MainActivity.listgiohang.get(i).setSoluong(slmoinhat);
                 long giamoinhat = (giahientai * slmoinhat) / slhientai;
                 MainActivity.listgiohang.get(i).setGiasp(giamoinhat);
                 DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                 viewHolder.txtGiamonhang.setText(decimalFormat.format(giamoinhat).replace(",", ".") + "đ");
                 //set Tong tien
                 GioHangActivity.event();
                 if (slmoinhat < 2) {
                     viewHolder.btnCong.setVisibility(View.VISIBLE);
                     viewHolder.btnTru.setVisibility(View.INVISIBLE);
                     viewHolder.btnGiatri.setText(String.valueOf(slmoinhat));
                 } else {
                     viewHolder.btnCong.setVisibility(View.VISIBLE);
                     viewHolder.btnTru.setVisibility(View.VISIBLE);
                     viewHolder.btnGiatri.setText(String.valueOf(slmoinhat));
                 }

             }
         });
        return view;
    }

    public class ViewHolder {
        private TextView txtTengiohang, txtGiamonhang;
        private ImageView imgGiohang;
        private Button btnTru, btnGiatri, btnCong;
    }
}
