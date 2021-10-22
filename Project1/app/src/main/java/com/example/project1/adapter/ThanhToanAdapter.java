package com.example.project1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.model.SanPham;
import com.example.project1.model.ThanhToan;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThanhToanAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThanhToan> list;

    public ThanhToanAdapter(Context context, ArrayList<ThanhToan> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Viewholder {
        public TextView ten, sdt, email, ngay, diachi;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder = null;
        if (convertView == null) {
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_thanhtoan, null);
            viewholder.ten = convertView.findViewById(R.id.thanhtoan_ten);
            viewholder.sdt = convertView.findViewById(R.id.thanhtoan_sdt);
            viewholder.email = convertView.findViewById(R.id.thanhtoan_gmail);
            viewholder.diachi=convertView.findViewById(R.id.item_diachi);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        ThanhToan thanhToan = (ThanhToan) getItem(position);
        viewholder.ten.setText(thanhToan.getTen());
        viewholder.sdt.setText(thanhToan.getSdt() + "");
        viewholder.email.setText(thanhToan.getGmail());
        viewholder.diachi.setText(thanhToan.getDiachi());


        return convertView;
    }
}
