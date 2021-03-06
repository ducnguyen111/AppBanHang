package com.example.project1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> list;

    public DienThoaiAdapter(Context context, ArrayList<SanPham> list) {
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
    public class Viewholder{
        public TextView tendienthoai,gia,mota;
        public ImageView hinhanh;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder=null;
        if (convertView==null){
            viewholder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_dienthoai,null);
            viewholder.tendienthoai=convertView.findViewById(R.id.item_dienthoai);
            viewholder.hinhanh=convertView.findViewById(R.id.image_dienthoai);

            viewholder.gia=convertView.findViewById(R.id.item_giadienthoai);
            viewholder.mota=convertView.findViewById(R.id.tv_motadienthoai);
            convertView.setTag(viewholder);
        }
        else {
            viewholder=(Viewholder) convertView.getTag();
        }
        SanPham sanPham= (SanPham) getItem(position);
        viewholder.tendienthoai.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewholder.gia.setText("Gi??:"+decimalFormat.format(sanPham.getGiasanpham())+" Vn??");
        viewholder.mota.setMaxLines(2);
        viewholder.mota.setEllipsize(TextUtils.TruncateAt.END);
        viewholder.mota.setText(sanPham.getMotasanpham());
        Picasso.with(context).load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.ic_baseline_cloud_download_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(viewholder.hinhanh);
        return convertView;
    }
}
