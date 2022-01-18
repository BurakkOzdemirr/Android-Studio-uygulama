package com.example.mobil_veteriner_uygulamasi.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobil_veteriner_uygulamasi.Fragments.SorularFragment;
import com.example.mobil_veteriner_uygulamasi.Utils.GetSharedPreferences;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mobil_veteriner_uygulamasi.Models.AnswersModelsItem;
import com.example.mobil_veteriner_uygulamasi.Models.DeleteAnswerModel;
import com.example.mobil_veteriner_uygulamasi.Models.PetModelItem;
import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.RestApi.ManagerAll;
import com.example.mobil_veteriner_uygulamasi.Utils.GetSharedPreferences;
import com.example.mobil_veteriner_uygulamasi.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.viewHolder> {
    private GetSharedPreferences getSharedPreferences;
    private String mus_id;

    private AnswersAdapter answersAdapter;
    private List<AnswersModelsItem> list ;
    Context context;
    Activity activity;

    public AnswersAdapter(List<AnswersModelsItem> list, Context context,Activity activity) {
        this.list = list;
        this.context = context;
        this.activity=activity;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cevapitemlayout,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        getSharedPreferences=new GetSharedPreferences(this.activity);
        mus_id=getSharedPreferences.getSession().getString("id",null);
        holder.konuSoruTextview.setText(list.get(position).getKonu().toString());
        holder.soruSilImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soruSil(position);
            }
        });
        holder.silLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswers(mus_id,position);
                //delete(list.get(position).getCevapid().toString(),list.get(position).getSoruid().toString(),position);
            }
        });

    }
    public void getAnswers(String mus_id,int position)
    {
        Log.d("öenmli",""+mus_id+"");
        final Call<List<AnswersModelsItem>> req =ManagerAll.getInstance().getAnswers(mus_id);
        req.enqueue(new Callback<List<AnswersModelsItem>>() {
            @Override
            public void onResponse(Call<List<AnswersModelsItem>> call, Response<List<AnswersModelsItem>> response) {
                if(response.body().get(0).isTf())
                {
                    if(response.isSuccessful())
                    {
                        list=response.body();
                        answersAdapter=new AnswersAdapter(list,context,activity);
                        openAnswersAlert(position);
                    }
                }
                else
                {
                    Toast.makeText(context,"Herhangi bir cevap yok.",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<AnswersModelsItem>> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });
    }
    public void openAnswersAlert(int position) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.cevapalertlayout, null);
        TextView SoruTextview =(TextView)view.findViewById(R.id.SoruTextview);
        TextView cevapTextview =(TextView)view.findViewById(R.id.cevapTextview);


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        cevapTextview.setText("Cevap: "+list.get(position).getCevap().toString());
        SoruTextview.setText("Soru: "+list.get(position).getSoru().toString());
        /*AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        cevapRecyclerView.setLayoutManager(layoutManager);
        cevapRecyclerView.setAdapter(answersAdapter);*/
        alertDialog.show();

    }
    public void soruSil (int position) {

        LayoutInflater layoutInflater=activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sorusillayout, null);
        Button kampanyaSilEvet= (Button)view.findViewById(R.id.kampanyaSilEvet);
        Button kampanyaSilHayır=(Button) view.findViewById(R.id.kampanyaSilHayır);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        kampanyaSilEvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(list.get(position).getCevapid().toString(),list.get(position).getSoruid().toString(),position);
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

    public void deleteToList(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void delete(String cevapid,String soruid,final int pos)
    {
        Call<DeleteAnswerModel> req = ManagerAll.getInstance().deleteAnswer(cevapid,soruid);
        req.enqueue(new Callback<DeleteAnswerModel>() {
            @Override
            public void onResponse(Call<DeleteAnswerModel> call, Response<DeleteAnswerModel> response) {
                if(response.body().isTf())
                {
                    if(response.isSuccessful())
                    {
                        deleteToList(pos);
                        Toast.makeText(context,response.body().getText(),Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(context,response.body().getText(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteAnswerModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout silLinear;
        TextView konuSoruTextview;
        ImageView soruSilImage;



        public viewHolder(View itemView) {
            super(itemView);
            konuSoruTextview=(TextView) itemView.findViewById(R.id.konuSoruTextview);
            soruSilImage=(ImageView)itemView.findViewById(R.id.soruSilImage);
            silLinear=(LinearLayout)itemView.findViewById(R.id.silLinear);


        }
    }

}
