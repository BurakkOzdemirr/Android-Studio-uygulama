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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineradmin.Fragments.KullaniciPetlerFragment;
import com.example.veterineradmin.Models.AsiEkle;
import com.example.veterineradmin.Models.KullaniciSilModel;
import com.example.veterineradmin.Models.KullanicilarModelItem;
import com.example.veterineradmin.Models.PetModelItem;
import com.example.veterineradmin.Models.PetSilModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.viewHolder> {

    List<PetModelItem> list;
    Context context;
    Activity activity;
    ChangeFragments changeFragments;
    String musid;
    String tarih="",formatlıtarih="";


    public PetAdapter(List<PetModelItem> list, Context context, Activity activity, String musid) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.musid = musid;
        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userpetitemlayout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.petNameText.setText(list.get(position).getPetisim().toString());
        holder.petBilgiText.setText("Türü : " + list.get(position).getPettur().toString() + "\nCinsi: " + list.get(position).getPetcins().toString() + "\n" +
                list.get(position).getPetisim().toString() + " isimli pete aşı eklemek için tıklayınız.");
        Picasso.get().load(list.get(position).getPetresim().toString()).resize(200, 200).into(holder.petImage);
        holder.soruLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAsiEkle(list.get(position).getPetid().toString());
            }
        });
        holder.petSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petSil(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView petBilgiText, petNameText;
        LinearLayout soruLayout;
        ImageView petImage;
        Button petSilButon;


        public viewHolder(View itemView) {
            super(itemView);
            petBilgiText = (TextView) itemView.findViewById(R.id.petBilgiText);
            petNameText = (TextView) itemView.findViewById(R.id.petNameText);
            petImage = (ImageView) itemView.findViewById(R.id.petImage);
            soruLayout = (LinearLayout) itemView.findViewById(R.id.soruLayout);
            petSilButon = (Button) itemView.findViewById(R.id.petSilButon);


        }
    }

    public void addAsiEkle(String petid) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.asieklelayout, null);
        CalendarView calendarView=view.findViewById(R.id.asiEkleTakvim);
        final EditText asiEkleName=view.findViewById(R.id.asiEkleName);
        Button  asiEkleButon=view.findViewById(R.id.asiEkleButon);



        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                DateFormat inputFormat=new SimpleDateFormat("dd/MM/yyyy");
                DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                tarih = dayOfMonth+"/"+(month+1)+"/"+year;

                try {
                    Date date=inputFormat.parse(tarih);
                    formatlıtarih = format.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        asiEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!formatlıtarih.equals("")&& !asiEkleName.getText().toString().equals(""))
                {
                    addAsi(musid,petid,asiEkleName.getText().toString(),formatlıtarih,alertDialog);
                }
                else
                {
                    Toast.makeText(context,"Tarih seçiniz veya aşı ismi giriniz",Toast.LENGTH_LONG).show();
                }
            }
        });


        alertDialog.show();

    }

    public void addAsi(String musid,String petid,String asiname,String tarih,final AlertDialog alertDialog)
    {
        Call<AsiEkle> req= ManagerAll.getInstance().asiEkle(musid,petid,asiname,tarih);
        req.enqueue(new Callback<AsiEkle>() {
            @Override
            public void onResponse(Call<AsiEkle> call, Response<AsiEkle> response) {
                alertDialog.cancel();
                Toast.makeText(context,response.body().getText().toString(),Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<AsiEkle> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });

    }
    public void petSil(int position) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.petsillayout, null);
        Button petSilEvet = (Button) view.findViewById(R.id.petSilEvet);
        Button petSilHayır = (Button) view.findViewById(R.id.petSilHayır);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        petSilEvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petiSil(list.get(position).getPetid().toString(), position);
                alertDialog.cancel();
            }
        });

        petSilHayır.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });


        alertDialog.show();
    }

    public void petiSil(String id,int position)
    {
        Call<PetSilModel> req=ManagerAll.getInstance().petSil(id);
        req.enqueue(new Callback<PetSilModel>() {
            @Override
            public void onResponse(Call<PetSilModel> call, Response<PetSilModel> response) {
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
            public void onFailure(Call<PetSilModel> call, Throwable t) {
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
