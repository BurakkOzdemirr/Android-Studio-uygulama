package com.example.mobil_veteriner_uygulamasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil_veteriner_uygulamasi.Fragments.AsiDetayFragment;
import com.example.mobil_veteriner_uygulamasi.Models.AsiModelItem;
import com.example.mobil_veteriner_uygulamasi.Models.PetModelItem;
import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.Utils.ChangeFragments;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarneGecmisAdapter extends RecyclerView.Adapter<SanalKarneGecmisAdapter.viewHolder> {

    List<AsiModelItem> list ;
    Context context;

    public SanalKarneGecmisAdapter(List<AsiModelItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanalkarnegecmislayout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.sanalKarneGecmisAsiText.setText(list.get(position).getAsiisim().toString()+" aşısı yapılmıştır.");
        holder.sanalKarneGecmisAsiBilgi.setText(list.get(position).getPetisim().toString()
        +" isimli petinize "+list.get(position).getAsitarih().toString()+" tarihinde "+list.get(position).getAsiisim().toString()+" aşısı yapılmıştır.");

        Picasso.get().load(list.get(position).getPetresim().toString()).into(holder.sanalKarneGecmisAsiImage);




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        TextView sanalKarneGecmisAsiText,sanalKarneGecmisAsiBilgi;
        CircleImageView sanalKarneGecmisAsiImage;


        public viewHolder(View itemView) {
            super(itemView);
            sanalKarneGecmisAsiText=(TextView) itemView.findViewById(R.id.sanalKarneGecmisAsiText);
            sanalKarneGecmisAsiBilgi=(TextView)itemView.findViewById(R.id.sanalKarneGecmisAsiBilgi);


            sanalKarneGecmisAsiImage=(CircleImageView) itemView.findViewById(R.id.sanalKarneGecmisAsiImage);
        }
    }

}
