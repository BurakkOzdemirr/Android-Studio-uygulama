package com.example.veterineradmin.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.veterineradmin.Models.KampanyaModelItem;
import com.example.veterineradmin.Models.KampanyaSilModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KampanyaAdapter extends RecyclerView.Adapter<KampanyaAdapter.viewHolder> {

    List<KampanyaModelItem> list ;
    Context context;
    Activity activity;

    public KampanyaAdapter(List<KampanyaModelItem> list, Context context,Activity activity) {
        this.list = list;
        this.context = context;
        this.activity=activity;
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

        Picasso.get().load(list.get(position).getResim()).resize(200,200).into(holder.kampanyaImage);

        holder.kampanyaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kampanyaSil(position);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        TextView kampanyaBaslik,kampanyaText;
        ImageView kampanyaImage;
        CardView kampanyaCardView;

        public viewHolder(View itemView) {
            super(itemView);
            kampanyaBaslik=(TextView)itemView.findViewById(R.id.kampanyaBaslikText);
            kampanyaText=(TextView)itemView.findViewById(R.id.kampanyaText);
            kampanyaImage=(ImageView)itemView.findViewById(R.id.kampanyaImageview);
            kampanyaCardView=(CardView)itemView.findViewById(R.id.kampanyaCardView);


        }
    }

    public void kampanyaSil (int position) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.kampanyasillayout, null);
        Button kampanyaSilEvet= (Button)view.findViewById(R.id.kampanyaSilEvet);
        Button kampanyaSilHayır=(Button) view.findViewById(R.id.kampanyaSilHayır);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        kampanyaSilEvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kampanyayıSil(list.get(position).getId().toString(),position);
                alertDialog.cancel();
            }
        });

        kampanyaSilHayır.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });


        alertDialog.show();

    }

    public void kampanyayıSil(String id,final int position)
    {
        Call<KampanyaSilModel> req= ManagerAll.getInstance().kampanyaSil(id);
        req.enqueue(new Callback<KampanyaSilModel>() {
            @Override
            public void onResponse(Call<KampanyaSilModel> call, Response<KampanyaSilModel> response) {
                if(response.body().isTf())
                {
                    Toast.makeText(context,response.body().getText(),Toast.LENGTH_LONG).show();
                    deleteToList(position);

                }
                else
                {
                    Toast.makeText(context,response.body().getText(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<KampanyaSilModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });
    }

    public void deleteToList(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }



}
