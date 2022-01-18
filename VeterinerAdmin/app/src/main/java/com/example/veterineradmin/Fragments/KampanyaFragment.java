package com.example.veterineradmin.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.veterineradmin.Adapters.KampanyaAdapter;
import com.example.veterineradmin.Models.KampanyaEkleModel;
import com.example.veterineradmin.Models.KampanyaModelItem;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class KampanyaFragment extends Fragment {

    private View view;
    private RecyclerView kampanyaRecView;
    private List<KampanyaModelItem> kampanyaList;
    private KampanyaAdapter kampanyaAdapter;
    private ChangeFragments changeFragments;
    private Button kampanyaEkleButon;
    private ImageView kampanyaEkleImageview,kampanyaBackImage;
    private Bitmap bitmap;
    private String imageString;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_kampanya, container, false);
        tanimla();
        getKampanyaa();
        click();
        return view;
    }

    public void tanimla() {
        kampanyaRecView = (RecyclerView) view.findViewById(R.id.kampanyaRecView);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(), 1);
        kampanyaRecView.setLayoutManager(mng);
        kampanyaList = new ArrayList<>();
        changeFragments = new ChangeFragments(getContext());
        kampanyaEkleButon = (Button) view.findViewById(R.id.kampanyaEkleButon);
        kampanyaBackImage=(ImageView) view.findViewById(R.id.kampanyaBackImage);
        bitmap=null;
        imageString="";
    }

    public void click() {
        kampanyaEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKampanya();
            }
        });
        kampanyaBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void getKampanyaa() {
        Call<List<KampanyaModelItem>> req = ManagerAll.getInstance().getKampanya();
        req.enqueue(new Callback<List<KampanyaModelItem>>() {
            @Override
            public void onResponse(Call<List<KampanyaModelItem>> call, Response<List<KampanyaModelItem>> response) {

                if (response.body().get(0).isTf()) {
                    kampanyaList = response.body();
                    kampanyaAdapter = new KampanyaAdapter(kampanyaList, getContext(),getActivity());
                    kampanyaRecView.setAdapter(kampanyaAdapter);

                } else {
                    Toast.makeText(getContext(), "Herhangi bir kampanya bulunmamaktadır", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<KampanyaModelItem>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }

    public void addKampanya() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.kampanyaeklelayout, null);

        final EditText kampanyaBaslikEit = view.findViewById(R.id.kampanyaBaslikEit);
        final EditText kampanyaTextEdit = view.findViewById(R.id.kampanyaTextEdit);
        kampanyaEkleImageview = view.findViewById(R.id.kampanyaEkleImageview);

        Button kampanyaEkleButon = view.findViewById(R.id.kampanyaEkleButon);
        Button kampanyaImageEkle = view.findViewById(R.id.kampanyaImageEkle);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        kampanyaImageEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeriAc();
            }
        });
        kampanyaEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageToString().equals("") && !kampanyaBaslikEit.getText().toString().equals("") &&
                        !kampanyaTextEdit.getText().toString().equals("")) {

                    kampanyaEkle(kampanyaBaslikEit.getText().toString(), kampanyaTextEdit.getText().toString(), imageToString(), alertDialog);
                    kampanyaBaslikEit.setText("");
                    kampanyaTextEdit.setText("");

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 && data != null)
        {
            Uri path = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                kampanyaEkleImageview.setImageBitmap(bitmap);
                kampanyaEkleImageview.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

    public void kampanyaEkle(String baslik, String icerik, String imageString, AlertDialog alertDialog) {
        Call<KampanyaEkleModel> req = ManagerAll.getInstance().addKampanya(baslik, icerik, imageString);
        req.enqueue(new Callback<KampanyaEkleModel>() {
            @Override
            public void onResponse(Call<KampanyaEkleModel> call, Response<KampanyaEkleModel> response) {
                if (response.body().isTf()) {
                    Toast.makeText(getContext(), response.body().getSonuc(), Toast.LENGTH_LONG).show();
                    getKampanyaa();
                    alertDialog.cancel();

                } else {
                    Toast.makeText(getContext(), response.body().getSonuc(), Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<KampanyaEkleModel> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }

}
