package com.example.mobil_veteriner_uygulamasi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mobil_veteriner_uygulamasi.Adapters.SanalKarneGecmisAdapter;
import com.example.mobil_veteriner_uygulamasi.Models.AsiModelItem;
import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.RestApi.ManagerAll;
import com.example.mobil_veteriner_uygulamasi.Utils.ChangeFragments;
import com.example.mobil_veteriner_uygulamasi.Utils.GetSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AsiDetayFragment extends Fragment {

    private View view;
    private String musid;
    private String petId;
    private GetSharedPreferences getSharedPreferences;
    private RecyclerView asiDetayRecView;
    private SanalKarneGecmisAdapter adapter;
    private List<AsiModelItem> list;
    private ImageView asiDetayBackImage;
    private ChangeFragments changeFragments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_asi_detay, container, false);
        tanimla();
        click();
        getGecmisAsi();
        return view;
    }
    public void tanimla()
    {

        petId=getArguments().getString("petid").toString();
        getSharedPreferences=new GetSharedPreferences(getActivity());
        musid=getSharedPreferences.getSession().getString("id",null);
        asiDetayRecView=(RecyclerView)view.findViewById(R.id.asiDetayRecView);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        asiDetayRecView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        asiDetayBackImage=(ImageView)view.findViewById(R.id.asiDetayBackImage);

    }
    public void click()
    {
        asiDetayBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments=new ChangeFragments(getContext());
                changeFragments.change(new SanalKarnePetler());
            }
        });
    }
    public void getGecmisAsi()
    {
        Call<List<AsiModelItem>> req = ManagerAll.getInstance().getGecmisAsi(musid,petId);
        req.enqueue(new Callback<List<AsiModelItem>>() {
            @Override
            public void onResponse(Call<List<AsiModelItem>> call, Response<List<AsiModelItem>> response) {
                if(response.body().get(0).isTf())
                {
                    list =response.body();
                    adapter=new SanalKarneGecmisAdapter(list,getContext());
                    asiDetayRecView.setAdapter(adapter);
                    Toast.makeText(getContext(),"Petinize ait "+ list.size()+" adet geçmişte yapılan aşı bulunmaktadır",Toast.LENGTH_LONG).show();

                }
                else
                {
                    ChangeFragments changeFragments=new ChangeFragments(getContext());
                    changeFragments.change(new SanalKarnePetler());
                    Toast.makeText(getContext(),"Petinize ait geçmişte yapılan herhangi bir aşı bulunmamaktadır.",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<AsiModelItem>> call, Throwable t) {

            }
        });
    }
}