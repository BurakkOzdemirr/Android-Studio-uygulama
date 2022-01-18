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
import com.example.mobil_veteriner_uygulamasi.Models.PetModelItem;
import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.Utils.ChangeFragments;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarnePetAdapter extends RecyclerView.Adapter<SanalKarnePetAdapter.viewHolder> {

    List<PetModelItem> list ;
    Context context;

    public SanalKarnePetAdapter(List<PetModelItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanalkarnepetlayout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.sanalKarnePetText.setText(list.get(position).getPetisim().toString());
        holder.sanalKarneBilgiText.setText(list.get(position).getPetisim().toString()+" isimli " +list.get(position).getPettur().toString()+" türü ve "
                +list.get(position).getPetcins().toString()+ " cinsine ait petinizin geçmiş aşılarını görmek için tıklayınız.");

        Picasso.get().load(list.get(position).getPetresim()).into(holder.sanalKarnePetImage);
        holder.sanalLayoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeFragments changeFragments=new ChangeFragments(context);
                changeFragments.changeWithParameter(new AsiDetayFragment(),list.get(position).getPetid());
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        TextView sanalKarnePetText,sanalKarneBilgiText;
        CircleImageView sanalKarnePetImage;
        CardView sanalLayoutCard;


        public viewHolder(View itemView) {
            super(itemView);
            sanalKarnePetText=(TextView) itemView.findViewById(R.id.sanalKarnePetText);
            sanalKarneBilgiText=(TextView)itemView.findViewById(R.id.sanalKarneBilgiText);
            sanalLayoutCard=(CardView)itemView.findViewById(R.id.sanalLayoutCard);

            sanalKarnePetImage=(CircleImageView) itemView.findViewById(R.id.sanalKarnePetImage);
        }
    }

}
