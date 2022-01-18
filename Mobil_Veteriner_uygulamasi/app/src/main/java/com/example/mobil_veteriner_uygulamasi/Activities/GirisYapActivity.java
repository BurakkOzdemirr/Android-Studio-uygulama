package com.example.mobil_veteriner_uygulamasi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobil_veteriner_uygulamasi.Models.LoginModel;
import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.RestApi.ManagerAll;
import com.example.mobil_veteriner_uygulamasi.Utils.GetSharedPreferences;
import com.example.mobil_veteriner_uygulamasi.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GirisYapActivity extends AppCompatActivity {
    private EditText loginmailadres,loginparola;
    private TextView logintext;
    private Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);
        tanimla();
        click();

    }

    public void tanimla()
    {
        loginmailadres=(EditText)findViewById(R.id.loginMail);
        loginparola=(EditText)findViewById(R.id.loginPass);
        logintext=(TextView)findViewById(R.id.loginText);
        loginbutton=(Button)findViewById(R.id.loginButton);
    }

    public void click()
    {
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=loginmailadres.getText().toString();
                String pass=loginparola.getText().toString();
                login(mail,pass);

            }
        });
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  ıntent=new Intent(GirisYapActivity.this,KayitOlActivity.class);
                startActivity(ıntent);
                finish();
            }
        });
    }
    public void login(String mailAdres,String parola)
    {
       Call<LoginModel> req= ManagerAll.getInstance().girisYap(mailAdres,parola);
       req.enqueue(new Callback<LoginModel>() {
           @Override
           public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
               if(response.body().isTf())
               {
                   Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                   Intent ıntent=new Intent(GirisYapActivity.this,MainActivity.class);
                   GetSharedPreferences getSharedPreferences=new GetSharedPreferences(GirisYapActivity.this);
                   getSharedPreferences.setSession(response.body().getId().toString(),response.body().getUsername().toString(),response.body().getMailadres().toString());
                   startActivity(ıntent);
                   finish();
               }
               else
               {
                   Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
               }

           }

           @Override
           public void onFailure(Call<LoginModel> call, Throwable t) {

               Toast.makeText(getApplicationContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();

           }
       });
    }
}