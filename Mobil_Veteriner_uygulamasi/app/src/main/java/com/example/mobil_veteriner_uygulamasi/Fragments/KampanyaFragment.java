package com.example.mobil_veteriner_uygulamasi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobil_veteriner_uygulamasi.Adapters.KampanyaAdapter;
import com.example.mobil_veteriner_uygulamasi.Models.KampanyaModelItem;
import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.RestApi.ManagerAll;
import com.example.mobil_veteriner_uygulamasi.Utils.ChangeFragments;
import com.example.mobil_veteriner_uygulamasi.Utils.Warnings;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KampanyaFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private View view;
    private RecyclerView kampanyaRecycler;
    private ChangeFragments changeFragments;
    private KampanyaAdapter kampanyaAdapter;
    private List<KampanyaModelItem> kampanyaList;

    public KampanyaFragment() {

    }



    public static KampanyaFragment newInstance(String param1, String param2) {
        KampanyaFragment fragment = new KampanyaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_kampanya, container, false);
        tanimla();
        getKampanyaa();
        return view;

    }
    public void tanimla()
    {
        kampanyaRecycler=(RecyclerView)view.findViewById(R.id.kampanyaRecyclerview);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        kampanyaRecycler.setLayoutManager(layoutManager);
        changeFragments =new ChangeFragments(getContext());
        kampanyaList = new ArrayList<>();
    }
    public void getKampanyaa()
    {
        Call<List<KampanyaModelItem>> req = ManagerAll.getInstance().getKampanya();
        req.enqueue(new Callback<List<KampanyaModelItem>>() {
            @Override
            public void onResponse(Call<List<KampanyaModelItem>> call, Response<List<KampanyaModelItem>> response) {

                if(response.body().get(0).isTf())
                {
                    kampanyaList=response.body();
                    kampanyaAdapter=new KampanyaAdapter(kampanyaList,getContext());
                    kampanyaRecycler.setAdapter(kampanyaAdapter);

                }
                else
                {
                    Toast.makeText(getContext(), "Herhangi bir kampanya bulunmamaktadır",Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<KampanyaModelItem>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });
    }
}