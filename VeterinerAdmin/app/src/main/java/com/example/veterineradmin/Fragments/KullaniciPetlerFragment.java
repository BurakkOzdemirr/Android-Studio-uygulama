package com.example.veterineradmin.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veterineradmin.Adapters.PetAdapter;
import com.example.veterineradmin.Models.PetEkleModel;
import com.example.veterineradmin.Models.PetModelItem;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KullaniciPetlerFragment extends Fragment {
    private View view;
    private String musid;
    private ChangeFragments changeFragments;
    private RecyclerView userPetListRecView;
    private ImageView petEkleResimYok,petEkleImageView,kampanyaBackImage;
    private TextView petEkleUyarıText;
    private Button userPetEkle;
    private List<PetModelItem> list;
    private PetAdapter petAdapter;
    private Bitmap bitmap;
    private String imageString="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view= inflater.inflate(R.layout.fragment_kullanicipetler, container, false);
        tanimla();
        getPets(musid);
        click();
        return view;
    }

    public void tanimla()
    {
        userPetListRecView=(RecyclerView)view.findViewById(R.id.userPetListRecView);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        userPetListRecView.setLayoutManager(layoutManager);
        petEkleResimYok=(ImageView)view.findViewById(R.id.petEkleResimYok);
        kampanyaBackImage=(ImageView)view.findViewById(R.id.kampanyaBackImage);
        petEkleUyarıText=(TextView)view.findViewById(R.id.petEkleUyarıText);
        userPetEkle=(Button)view.findViewById(R.id.userPetEkle);

        musid=getArguments().get("petid").toString();
        changeFragments=new ChangeFragments(getContext());
        list=new ArrayList<>();
        bitmap=null;

    }

    public void click()
    {
        userPetEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petEkleAlert();
            }
        });
        kampanyaBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new KullanicilarFragment());
            }
        });


    }

    public void getPets(String id)
    {
        Call<List<PetModelItem>> req= ManagerAll.getInstance().getPet(id);
        req.enqueue(new Callback<List<PetModelItem>>() {
            @Override
            public void onResponse(Call<List<PetModelItem>> call, Response<List<PetModelItem>> response) {
                if(response.body().get(0).isTf())
                {
                    Toast.makeText(getContext(),"Kullanıcıya ait "+response.body().size() +" tane pet bulunmuştur.",Toast.LENGTH_LONG).show();
                    userPetListRecView.setVisibility(View.VISIBLE);
                    list=response.body();
                    petAdapter=new PetAdapter(list,getContext(),getActivity(),musid);
                    userPetListRecView.setAdapter(petAdapter);
                    petEkleResimYok.setVisibility(View.GONE);
                    petEkleUyarıText.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(getContext(),"Kullanıcıya ait pet bulunamamıştır.",Toast.LENGTH_LONG).show();
                    petEkleResimYok.setVisibility(View.VISIBLE);
                    petEkleUyarıText.setVisibility(View.VISIBLE);
                    userPetListRecView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PetModelItem>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void petEkleAlert() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.petklelayout, null);

        final EditText petEkleNameEdit = view.findViewById(R.id.petEkleNameEdit);
        final EditText petEkleTürEdit = view.findViewById(R.id.petEkleTürEdit);
        final EditText petEkleCinsEdit = view.findViewById(R.id.petEkleCinsEdit);
        petEkleImageView = view.findViewById(R.id.petEkleImageView);

        Button petEkleEkleButon = view.findViewById(R.id.petEkleEkleButon);
        Button petEkleResimSeçButon = view.findViewById(R.id.petEkleResimSeçButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        petEkleResimSeçButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeriAc();
            }
        });
        petEkleEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageToString().equals("") && !petEkleNameEdit.getText().toString().equals("") &&
                        !petEkleTürEdit.getText().toString().equals("")&& !petEkleCinsEdit.getText().toString().equals("")) {

                    petEkle(musid,petEkleNameEdit.getText().toString(),petEkleTürEdit.getText().toString(),petEkleCinsEdit.getText().toString(),imageToString(),alertDialog);
                    petEkleNameEdit.setText("");
                    petEkleTürEdit.setText("");
                    petEkleCinsEdit.setText("");

                } else {
                    Toast.makeText(getContext(), "Tüm alanların doldurulması ve resmin seçilmesi zorunludur.", Toast.LENGTH_LONG).show();
                }
            }
        });

        alertDialog.show();

    }

    void galeriAc() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 777);
    }

    public String imageToString() {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byt = byteArrayOutputStream.toByteArray();
            imageString = Base64.encodeToString(byt, Base64.DEFAULT);
            return imageString;
        }
        else
        {
            return  imageString;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 && data != null)
        {
            Uri path = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                petEkleImageView.setImageBitmap(bitmap);
                petEkleImageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void petEkle(String musid,String petismi,String pettur,String petcins,String imageString,AlertDialog alertDialog)
    {
        Call<PetEkleModel> req=ManagerAll.getInstance().petEkle(musid,petismi,pettur,petcins,imageString);
        req.enqueue(new Callback<PetEkleModel>() {
            @Override
            public void onResponse(Call<PetEkleModel> call, Response<PetEkleModel> response) {
                if(response.body().isTf())
                {
                    getPets(musid);
                    Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
                else
                {
                    Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<PetEkleModel> call, Throwable t) {
                Toast.makeText(getContext(),Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });
    }
}