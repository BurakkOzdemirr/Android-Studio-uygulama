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

import com.example.mobil_veteriner_uygulamasi.Adapters.PetsAdapter;
import com.example.mobil_veteriner_uygulamasi.Models.PetModel;
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


public class UserPetsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    View view;
    private RecyclerView petlistrecyclerview;
    private PetsAdapter petsAdapter;
    private List<PetModelItem> petlist;
    private ChangeFragments changeFragments;
    private String mus_id;
    private GetSharedPreferences getSharedPreferences;


    public UserPetsFragment() {

    }


    public static UserPetsFragment newInstance(String param1, String param2) {
        UserPetsFragment fragment = new UserPetsFragment();
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

        view= inflater.inflate(R.layout.fragment_user_pets, container, false);
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
                    petsAdapter=new PetsAdapter(petlist,getContext());
                    petlistrecyclerview.setAdapter(petsAdapter);
                    Toast.makeText(getContext(), "Sistemde kayıtlı "+petlist.size()+" petiniz bulunmaktadır", Toast.LENGTH_SHORT).show();

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
        petlistrecyclerview=view.findViewById(R.id.petlistrecyclerview);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(),1);
        petlistrecyclerview.setLayoutManager(mng);
        changeFragments=new ChangeFragments(getContext());
        getSharedPreferences =new GetSharedPreferences(getActivity());
        mus_id=getSharedPreferences.getSession().getString("id",null);
    }
}