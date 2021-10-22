package com.example.project1.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.activity.ChiTietDonHangActivity;
import com.example.project1.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ViewHolder> implements Filterable {
    ArrayList<SanPham> list;
    Context context;
    ArrayList<SanPham> mlist;

    public SanphamAdapter(ArrayList<SanPham> list, Context context) {
        this.list = list;
        this.mlist=list;
        this.context = context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.item_sanpham,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanphamAdapter.ViewHolder holder, int position) {
     SanPham sanPham=list.get(position);
     holder.tensp.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.giasp.setText("Giá:"+decimalFormat.format(sanPham.getGiasanpham())+" Vnđ");
        Picasso.with(context).load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.ic_baseline_cloud_download_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(holder.hinhanhsp);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
       public ImageView hinhanhsp;
       public TextView tensp;
       public TextView giasp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhanhsp=itemView.findViewById(R.id.item_hinhanh);
            tensp=itemView.findViewById(R.id.item_tensp);
            giasp=itemView.findViewById(R.id.item_giasp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ChiTietDonHangActivity.class);
                    intent.putExtra("thongtinsanpham",list.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }
    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int mSpace;

        public SpacesItemDecoration(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect,
                                   View view,
                                   RecyclerView parent, RecyclerView.State state) {

            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) == 0)
                outRect.top = mSpace;
        }
    }
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
              String string=constraint.toString();
               if (string.isEmpty()){
                   list=mlist;
               }
               else {
                   List<SanPham> list1=new ArrayList<>();
                   for (SanPham sanPham:mlist){
                       if (sanPham.getTensanpham().toLowerCase().contains(string.toLowerCase())){
                           list1.add(sanPham);
                       }
                   }
                   list= (ArrayList<SanPham>) list1;
               }
               FilterResults filterResults=new FilterResults();
               filterResults.values=list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               list= (ArrayList<SanPham>) results.values;
               notifyDataSetChanged();
            }
        };
    }

}
