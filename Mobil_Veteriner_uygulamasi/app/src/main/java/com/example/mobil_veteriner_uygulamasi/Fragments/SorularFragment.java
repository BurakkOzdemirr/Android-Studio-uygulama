package com.example.mobil_veteriner_uygulamasi.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mobil_veteriner_uygulamasi.Adapters.AnswersAdapter;
import com.example.mobil_veteriner_uygulamasi.Models.AnswersModelsItem;
import com.example.mobil_veteriner_uygulamasi.Models.AskQuestionModel;
import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.RestApi.ManagerAll;
import com.example.mobil_veteriner_uygulamasi.Utils.GetSharedPreferences;
import com.example.mobil_veteriner_uygulamasi.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SorularFragment extends Fragment {

    View view;
    private RecyclerView sorulistrecyclerview;
    private AnswersAdapter answersAdapter;
    private List<AnswersModelsItem> answerList;
    private GetSharedPreferences getSharedPreferences;
    private String mus_id;
    private ImageView soruEkleImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_sorular, container, false);
        tanimla();
        soruEkleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuestionAlert();
            }
        });
        getSorular(mus_id);
        return view;
    }
    public void tanimla()
    {
        soruEkleImage=(ImageView)view.findViewById(R.id.soruEkleImage);
        answerList= new ArrayList<>();
        sorulistrecyclerview=view.findViewById(R.id.sorulistrecyclerview);
        RecyclerView.LayoutManager mng=new GridLayoutManager(getContext(),1);
        sorulistrecyclerview.setLayoutManager(mng);
        getSharedPreferences=new GetSharedPreferences(getActivity());
        mus_id=getSharedPreferences.getSession().getString("id",null);

    }

    public void getSorular(String mus_id)
    {

        final Call<List<AnswersModelsItem>> req = ManagerAll.getInstance().getAnswers(mus_id);
        req.enqueue(new Callback<List<AnswersModelsItem>>() {
            @Override
            public void onResponse(Call<List<AnswersModelsItem>> call, Response<List<AnswersModelsItem>> response) {
               if(response.body().get(0).isTf())
                {
                    if(response.isSuccessful())
                    {
                        answerList=response.body();
                        answersAdapter=new AnswersAdapter(answerList,getContext(),getActivity());
                        sorulistrecyclerview.setAdapter(answersAdapter);

                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Herhangi bir cevap yok.",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<AnswersModelsItem>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });
    }

    public void openQuestionAlert() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sorusoralertlayout, null);

        final EditText sorusoredittext = (EditText) view.findViewById(R.id.sorusoredittext);
        final EditText konuSoruSorEdittext = (EditText) view.findViewById(R.id.konuSoruSorEdittext);
        Button sorusorbuton = (Button) view.findViewById(R.id.sorusorButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        sorusorbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soru = sorusoredittext.getText().toString();
                String konu=konuSoruSorEdittext.getText().toString();
                sorusoredittext.setText("");
                konuSoruSorEdittext.setText("");
                alertDialog.cancel();
                askQuestion(mus_id,soru,konu,alertDialog );
            }
        });
        alertDialog.show();

    }

    public void askQuestion(String mus_id, String text,String konu,AlertDialog alr)
    {
        Call<AskQuestionModel> req =ManagerAll.getInstance().soruSor(mus_id,text,konu);
        req.enqueue(new Callback<AskQuestionModel>() {
            @Override
            public void onResponse(Call<AskQuestionModel> call, Response<AskQuestionModel> response) {
                if(response.body().isTf())
                {
                    Toast.makeText(getContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    alr.cancel();
                }
                else
                {
                    Toast.makeText(getContext(),response.body().getText(),Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<AskQuestionModel> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });

    }
}