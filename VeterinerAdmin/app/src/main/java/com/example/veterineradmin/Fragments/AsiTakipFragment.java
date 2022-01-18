package com.example.veterineradmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.veterineradmin.Adapters.PetAsiTakipAdapter;
import com.example.veterineradmin.Models.PetAiTakipModel;
import com.example.veterineradmin.Models.PetAiTakipModelItem;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsiTakipFragment extends Fragment {
    private View view;
    private DateFormat format;
    private Date date;
    private String today;
    private ChangeFragments changeFragments;
    private RecyclerView asiTakipRec;
    private List<PetAiTakipModelItem> list;
    private PetAsiTakipAdapter petAsiTakipAdapter;
    private ImageView kampanyaBackImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_asi_takip, container, false);
        tanimla();
        click();
        istekAt(today);
        return view;
    }

    public void tanimla()
    {
        format=new SimpleDateFormat("dd/MM/yyyy");
        date = Calendar.getInstance().getTime();
        today=format.format(date);
        changeFragments=new ChangeFragments(getContext());
        asiTakipRec=(RecyclerView)view.findViewById(R.id.asiTakipRec);
        kampanyaBackImage=(ImageView)view.findViewById(R.id.kampanyaBackImage);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        asiTakipRec.setLayoutManager(layoutManager);
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

    public void istekAt(String tarih)
    {
        Call<List<PetAiTakipModelItem>> req= ManagerAll.getInstance().getPetAsiTakip(tarih);
        req.enqueue(new Callback<List<PetAiTakipModelItem>>() {
            @Override
            public void onResponse(Call<List<PetAiTakipModelItem>> call, Response<List<PetAiTakipModelItem>> response) {
                if(response.body().get(0).isTf())
                {
                    Toast.makeText(getContext(), "Bugün "+response.body().size()+" pete aşı yapılacaktır.", Toast.LENGTH_LONG).show();
                    list= response.body();
                    petAsiTakipAdapter=new PetAsiTakipAdapter(list,getContext(),getActivity());
                    asiTakipRec.setAdapter(petAsiTakipAdapter);
                }
                else
                {
                    Toast.makeText(getContext(), "Bugün aşı yapılacak pet yoktur", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<PetAiTakipModelItem>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }
}