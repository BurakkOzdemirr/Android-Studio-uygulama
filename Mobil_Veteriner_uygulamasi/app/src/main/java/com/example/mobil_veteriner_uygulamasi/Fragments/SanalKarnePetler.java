package com.example.mobil_veteriner_uygulamasi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobil_veteriner_uygulamasi.Adapters.PetsAdapter;
import com.example.mobil_veteriner_uygulamasi.Adapters.SanalKarnePetAdapter;
import com.example.mobil_veteriner_uygulamasi.Models.PetModelItem;
import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.RestApi.ManagerAll;
import com.example.mobil_veteriner_uygulamasi.Utils.ChangeFragments;
import com.example.mobil_veteriner_uygulamasi.Utils.GetSharedPreferences;
import com.example.mobil_veteriner_uygulamasi.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SanalKarnePetler extends Fragment {

    private View view;
    private RecyclerView sanalKarnePetlerRecycler;
    private List<PetModelItem> petlist;
    private ChangeFragments changeFragments;
    private String mus_id;
    private GetSharedPreferences getSharedPreferences;
    private SanalKarnePetAdapter sanalKarnePetAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_sanal_karne_petler, container, false);
        tanimla();
        getPets(mus_id);

        return view;
    }
    public void getPets(String mus_id)
    {
        Call<List<PetModelItem>> req= ManagerAll.getInstance().getPets(mus_id);
        req.enqueue(new Callback<List<PetModelItem>>() {
            @Override
            public void onResponse(Call<List<PetModelItem>> call, Response<List<PetModelItem>> response) {
                if(response.body().get(0).isTf())
                {
                    petlist=response.body();
                    sanalKarnePetAdapter=new SanalKarnePetAdapter(petlist,getContext());
                    sanalKarnePetlerRecycler.setAdapter(sanalKarnePetAdapter);


                }
                else
                {
                    Toast.makeText(getContext(), "Sistemde kayıtlı pediniz bulunmamaktadır", Toast.LENGTH_SHORT).show();
                    changeFragments.change(new HomeFragment());
                }

            }

            @Override
            public void onFailure(Call<List<PetModelItem>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }
    public void tanimla()
    {
        petlist =new ArrayList<>();
        sanalKarnePetlerRecycler=view.findViewById(R.id.sanalKarnePetlerRecycler);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(),1);
        sanalKarnePetlerRecycler.setLayoutManager(mng);
        changeFragments=new ChangeFragments(getContext());
        getSharedPreferences =new GetSharedPreferences(getActivity());
        mus_id=getSharedPreferences.getSession().getString("id",null);
    }
}