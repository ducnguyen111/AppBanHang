package com.example.project1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> list;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> list, Context context) {
        this.list = list;
        this.context = context;
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
        return list.get(position).getId();
    }

    public class Viewholder {
        TextView tenloaisp;
        ImageView imgloaisp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder = null;
        if (convertView == null) {
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_loaisp, null);
            viewholder.tenloaisp = convertView.findViewById(R.id.tv_loaisp);
            viewholder.imgloaisp = convertView.findViewById(R.id.image_loaisp);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();

        }
        Loaisp loaisp = (Loaisp) getItem(position);
        viewholder.tenloaisp.setText(loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhanhloaisp())
                .placeholder(R.drawable.ic_baseline_cloud_download_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(viewholder.imgloaisp);

        return convertView;
    }
}
