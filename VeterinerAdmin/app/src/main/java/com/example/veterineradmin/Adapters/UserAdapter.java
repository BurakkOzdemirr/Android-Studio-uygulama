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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineradmin.Fragments.KullaniciPetlerFragment;
import com.example.veterineradmin.Models.KampanyaModelItem;
import com.example.veterineradmin.Models.KampanyaSilModel;
import com.example.veterineradmin.Models.KullaniciSilModel;
import com.example.veterineradmin.Models.KullanicilarModel;
import com.example.veterineradmin.Models.KullanicilarModelItem;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {

    List<KullanicilarModelItem> list;
    Context context;
    Activity activity;
    ChangeFragments changeFragments;

    public UserAdapter(List<KullanicilarModelItem> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kullaniciitemlayout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.kullaniciNameText.setText(list.get(position).getKadi().toString());
        holder.userAramaYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ara(list.get(position).getTelefon().toString());
            }
        });
        holder.userPetler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.changeWithParameter(new KullaniciPetlerFragment(), list.get(position).getId().toString());
            }
        });
        holder.kullaniciSilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullaniciSil(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView kullaniciNameText;
        Button userPetler, userAramaYap;
        CardView userCardView;
        LinearLayout kullaniciSilLayout;

        public viewHolder(View itemView) {
            super(itemView);
            kullaniciNameText = (TextView) itemView.findViewById(R.id.kullaniciNameText);

            userPetler = (Button) itemView.findViewById(R.id.userPetler);
            userAramaYap = (Button) itemView.findViewById(R.id.userAramaYap);

            userCardView = (CardView) itemView.findViewById(R.id.userCardView);

            kullaniciSilLayout = (LinearLayout) itemView.findViewById(R.id.kullaniciSilLayout);


        }
    }

    public void ara(String num) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:" + num));
        activity.startActivity(intent);
    }

    public void kullaniciSil(int position) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.kullanicisillayout, null);
        Button kullaniciSilButon = (Button) view.findViewById(R.id.kullaniciSilButon);
        Button kullaniciSiliptal = (Button) view.findViewById(R.id.kullaniciSiliptal);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        kullaniciSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullaniciyiSil(list.get(position).getId().toString(), position);
                alertDialog.cancel();
            }
        });

        kullaniciSiliptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });


        alertDialog.show();
    }

    public void kullaniciyiSil(String id,int position)
    {
        Call<KullaniciSilModel> req=ManagerAll.getInstance().kadiSil(id);
        req.enqueue(new Callback<KullaniciSilModel>() {
            @Override
            public void onResponse(Call<KullaniciSilModel> call, Response<KullaniciSilModel> response) {
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
            public void onFailure(Call<KullaniciSilModel> call, Throwable t) {
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
