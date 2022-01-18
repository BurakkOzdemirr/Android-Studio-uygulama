package com.example.veterineradmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.veterineradmin.Adapters.KampanyaAdapter;
import com.example.veterineradmin.Adapters.UserAdapter;
import com.example.veterineradmin.Models.KullanicilarModelItem;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KullanicilarFragment extends Fragment {

    private View view;
    private ChangeFragments changeFragments;
    private RecyclerView kullaniciRecView;
    private List<KullanicilarModelItem> list;
    private UserAdapter userAdapter;
    private ImageView kampanyaBackImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_kullanicilar, container, false);
        tanimla();
        click();
        getKullanicilar();
        return view ;
    }

    public void tanimla()
    {
        kampanyaBackImage=(ImageView)view.findViewById(R.id.kampanyaBackImage);
        changeFragments=new ChangeFragments(getContext());
        kullaniciRecView=(RecyclerView)view.findViewById(R.id.kullaniciRecView);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        kullaniciRecView.setLayoutManager(layoutManager);
        list=new ArrayList<>();

    }
    public void click()
    {
        kampanyaBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void getKullanicilar()
    {
        Call<List<KullanicilarModelItem>> req= ManagerAll.getInstance().getKullanicilar();
        req.enqueue(new Callback<List<KullanicilarModelItem>>() {
            @Override
            public void onResponse(Call<List<KullanicilarModelItem>> call, Response<List<KullanicilarModelItem>> response) {
                if(response.body().get(0).isTf())
                {
                    list=response.body();
                    userAdapter=new UserAdapter(list,getContext(),getActivity());
                    kullaniciRecView.setAdapter(userAdapter);
                    Log.i("kullanicilar",response.body().toString());
                }
                else
                {
                    Toast.makeText(getContext(), "Sisteme kayıtlı kullanıcı yoktur.", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<KullanicilarModelItem>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }
}