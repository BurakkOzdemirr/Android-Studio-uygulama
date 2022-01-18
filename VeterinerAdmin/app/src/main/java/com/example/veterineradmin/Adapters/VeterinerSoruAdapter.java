package com.example.veterineradmin.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineradmin.Models.AsiOnaylaModel;
import com.example.veterineradmin.Models.CevaplaModel;
import com.example.veterineradmin.Models.PetAiTakipModelItem;
import com.example.veterineradmin.Models.SoruModelItem;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VeterinerSoruAdapter extends RecyclerView.Adapter<VeterinerSoruAdapter.viewHolder> {

    List<SoruModelItem> list ;
    Context context;
    Activity activity;

    public VeterinerSoruAdapter(List<SoruModelItem> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity=activity;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sorularitemlayout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.soruKullanıcıText.setText("İsim: "+list.get(position).getKadi().toString());
        holder.soruSoruText.setText("Konu: "+list.get(position).getKonu().toString());
        holder.soruArama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ara(list.get(position).getTelefon().toString());

            }
        });
        holder.soruCevapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cevaplaAlert(list.get(position).getMusid().toString(),list.get(position).getSoruid(),position,list.get(position).getSoru().toString());

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        TextView soruKullanıcıText,soruSoruText;
        ImageView soruCevapla,soruArama;

        public viewHolder(View itemView) {
            super(itemView);
            soruKullanıcıText=(TextView)itemView.findViewById(R.id.soruKullanıcıText);
            soruSoruText=(TextView)itemView.findViewById(R.id.soruSoruText);

            soruCevapla=(ImageView)itemView.findViewById(R.id.soruCevapla);
            soruArama=(ImageView)itemView.findViewById(R.id.soruArama);

        }
    }

    public void deleteToList(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void ara(String num)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+num));
        activity.startActivity(intent);
    }
    public void cevaplaAlert(final String musid,final String soruid,final int position,String soru) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.cevaplaalertlayout, null);

        final EditText cevaplaEditText = (EditText) view.findViewById(R.id.cevaplaEditText);
        Button cevaplaButon = (Button) view.findViewById(R.id.cevaplaButon);
        TextView cevplanacakSoruText=(TextView)view.findViewById(R.id.cevplanacakSoruText);
        cevplanacakSoruText.setText(soru);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        cevaplaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cevap = cevaplaEditText.getText().toString();
                cevaplaEditText.setText("");
                alertDialog.cancel();
                cevapla(musid,soruid,cevap,alertDialog,position);
            }
        });
        alertDialog.show();

    }
    public void cevapla(String musid,String soruid,String text,AlertDialog alertDialog,int position)
    {
        Call<CevaplaModel> req=ManagerAll.getInstance().cevapla(musid,soruid,text);
        req.enqueue(new Callback<CevaplaModel>() {
            @Override
            public void onResponse(Call<CevaplaModel> call, Response<CevaplaModel> response) {
                if(response.body().isTf())
                {
                    Toast.makeText(context,response.body().getText2().toString(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                    deleteToList(position);
                }
                else
                {
                    Toast.makeText(context,response.body().getText2().toString(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel();

                }
            }

            @Override
            public void onFailure(Call<CevaplaModel> call, Throwable t) {
                Toast.makeText(context,Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });

    }







}
