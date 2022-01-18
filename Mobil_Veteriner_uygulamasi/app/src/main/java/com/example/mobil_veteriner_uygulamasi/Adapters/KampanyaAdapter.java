package com.example.mobil_veteriner_uygulamasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil_veteriner_uygulamasi.Models.KampanyaModelItem;
import com.example.mobil_veteriner_uygulamasi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KampanyaAdapter extends RecyclerView.Adapter<KampanyaAdapter.viewHolder> {

    List<KampanyaModelItem> list ;
    Context context;

    public KampanyaAdapter(List<KampanyaModelItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kampanyaitemlayout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.kampanyaBaslik.setText(list.get(position).getBaslik().toString());
        holder.kampanyaText.setText(list.get(position).getText().toString());

        Picasso.get().load(list.get(position).getResim()).into(holder.kampanyaImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        TextView kampanyaBaslik,kampanyaText;
        ImageView kampanyaImage;

        public viewHolder(View itemView) {
            super(itemView);
            kampanyaBaslik=(TextView)itemView.findViewById(R.id.kampanyaBaslikText);
            kampanyaText=(TextView)itemView.findViewById(R.id.kampanyaText);
            kampanyaImage=(ImageView)itemView.findViewById(R.id.kampanyaImageview);


        }
    }

}
