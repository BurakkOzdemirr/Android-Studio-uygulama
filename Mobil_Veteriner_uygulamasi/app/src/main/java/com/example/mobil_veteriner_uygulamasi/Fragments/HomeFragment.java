package com.example.mobil_veteriner_uygulamasi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.Utils.ChangeFragments;
import com.example.mobil_veteriner_uygulamasi.Utils.GetSharedPreferences;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View view;
    private LinearLayout petlerimLayout, sorusorlinearlayout,cevapLayout,kampanyaLinear,asiTakipLayout,sanalKarneLayout;
    private ChangeFragments changeFragments;
    private GetSharedPreferences getSharedPreferences;
    private String id;
    //private AnswersAdapter answersAdapter;
    //private List<AnswersModelsItem> answerList;


    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        view = inflater.inflate(R.layout.fragment_home, container, false);
        tanimla();
        action();

        return view;
    }

    public void tanimla() {
        petlerimLayout = (LinearLayout) view.findViewById(R.id.petlerimLayout);
        //sorusorlinearlayout = (LinearLayout) view.findViewById(R.id.soruorlinearlayout);
        cevapLayout =(LinearLayout)  view.findViewById(R.id.cevapLayout);
        asiTakipLayout=(LinearLayout)  view.findViewById(R.id.asiTakipLayout);
       // answerList= new ArrayList<>();
        kampanyaLinear = (LinearLayout) view.findViewById(R.id.kampanyaLinearlayout);
        sanalKarneLayout= (LinearLayout) view.findViewById(R.id.sanalKarneLayout);
        changeFragments = new ChangeFragments(getContext());
        getSharedPreferences = new GetSharedPreferences(getActivity());
        id = getSharedPreferences.getSession().getString("id", null);
    }

    public void action() {
        petlerimLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new UserPetsFragment());
            }
        });
        /*sorusorlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuestionAlert();
            }
        });*/
        cevapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new SorularFragment());
                //getAnswers(id);

            }
        });
        kampanyaLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new KampanyaFragment());
            }
        });
        asiTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new AsiFragment());
            }
        });
        sanalKarneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new SanalKarnePetler());
            }
        });
    }
   /* public void openQuestionAlert() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sorusoralertlayout, null);

        final EditText sorusoredittext = (EditText) view.findViewById(R.id.sorusoredittext);
        Button sorusorbuton = (Button) view.findViewById(R.id.sorusorButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        sorusorbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soru = sorusoredittext.getText().toString();
                sorusoredittext.setText("");
                alertDialog.cancel();
                askQuestion(id,soru,alertDialog );
            }
        });
        alertDialog.show();

    }

    public void askQuestion(String mus_id, String text,AlertDialog alr)
    {
        Call<AskQuestionModel> req =ManagerAll.getInstance().soruSor(mus_id,text);
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
    public void getAnswers(String mus_id)
    {
        final Call<List<AnswersModelsItem>> req =ManagerAll.getInstance().getAnswers(mus_id);
        req.enqueue(new Callback<List<AnswersModelsItem>>() {
            @Override
            public void onResponse(Call<List<AnswersModelsItem>> call, Response<List<AnswersModelsItem>> response) {
                if(response.body().get(0).isTf())
                {
                    if(response.isSuccessful())
                    {
                        answerList=response.body();
                        answersAdapter=new AnswersAdapter(answerList,getContext());
                        openAnswersAlert();
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
    public void openAnswersAlert() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.cevapalertlayout, null);

        RecyclerView cevapRecyclerView =(RecyclerView)view.findViewById(R.id.cevapRecyclerView);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        cevapRecyclerView.setLayoutManager(layoutManager);
        cevapRecyclerView.setAdapter(answersAdapter);


        alertDialog.show();

    }*/
}