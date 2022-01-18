package com.example.mobil_veteriner_uygulamasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil_veteriner_uygulamasi.Models.PetModelItem;
import com.example.mobil_veteriner_uygulamasi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.viewHolder> {

    List<PetModelItem> list ;
    Context context;

    public PetsAdapter(List<PetModelItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.petlistitemlayout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.petlayoutcins.setText("Pet Cinsi : "+list.get(position).getPetcins().toString());
        holder.petlayoutname.setText("Pet İsmi : "+list.get(position).getPetisim().toString());
        holder.petlayouttur.setText("Pet Türü : "+list.get(position).getPettur().toString());

        Picasso.get().load(list.get(position).getPetresim()).into(holder.petimage);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        TextView petlayoutname,petlayoutcins,petlayouttur;
        CircleImageView petimage;


        public viewHolder(View itemView) {
            super(itemView);
            petlayoutname=(TextView) itemView.findViewById(R.id.petLayoutpetname);
            petlayoutcins=(TextView)itemView.findViewById(R.id.petLayoutcinsname);
            petlayouttur=(TextView)itemView.findViewById(R.id.petLayoutturname);
            petimage=(CircleImageView) itemView.findViewById(R.id.petLayoutpetimage);
        }
    }

}
