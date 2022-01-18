package com.example.veterineradmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.veterineradmin.Adapters.VeterinerSoruAdapter;
import com.example.veterineradmin.Models.SoruModelItem;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SorularFragment extends Fragment {
    private View view;
    private RecyclerView soruRecView;
    private List<SoruModelItem> list;
    private VeterinerSoruAdapter veterinerSoruAdapter;
    private ChangeFragments changeFragments;
    private ImageView kampanyaBackImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_sorular, container, false);
        tanimla();
        click();
        isteAt();

        return view;
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

    public void tanimla()
    {
        soruRecView=(RecyclerView)view.findViewById(R.id.soruRecView);
        kampanyaBackImage=(ImageView)view.findViewById(R.id.kampanyaBackImage);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        soruRecView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        changeFragments=new ChangeFragments(getContext());

    }
    public void isteAt()
    {
        Call<List<SoruModelItem>> req= ManagerAll.getInstance().getSoru();
        req.enqueue(new Callback<List<SoruModelItem>>() {
            @Override
            public void onResponse(Call<List<SoruModelItem>> call, Response<List<SoruModelItem>> response) {
                if(response.body().get(0).isTf())
                {
                    list=response.body();
                    veterinerSoruAdapter=new VeterinerSoruAdapter(list,getContext(),getActivity());
                    soruRecView.setAdapter(veterinerSoruAdapter);

                }
                else
                {
                    Toast.makeText(getContext(), "Veteriner hekime hiç soru sorulmamıştır.", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<SoruModelItem>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }
}