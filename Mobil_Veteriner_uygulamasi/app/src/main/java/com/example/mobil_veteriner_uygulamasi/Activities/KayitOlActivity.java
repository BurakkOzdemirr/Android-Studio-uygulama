package com.example.mobil_veteriner_uygulamasi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobil_veteriner_uygulamasi.Models.RegisterPojo;
import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.RestApi.ManagerAll;
import com.example.mobil_veteriner_uygulamasi.Utils.Warnings;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitOlActivity extends AppCompatActivity {

    private Button kayitOlButon;
    private EditText registerUserName,registerPassword,registerMailAdress,registertel;
    private TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        tanimla();
        registerToUser();
        changeActivity();

    }


    public void tanimla()
    {
        registertel=(EditText)findViewById(R.id.registertel);
        kayitOlButon =(Button)findViewById(R.id.kayitOlButon);
        registerPassword=(EditText)findViewById(R.id.registerPassword);
        registerMailAdress=(EditText)findViewById(R.id.registerMailAdress);
        registerUserName=(EditText)findViewById(R.id.registerUserName);
        registerText=(TextView)findViewById(R.id.registerText);
    }
    public void changeActivity()
    {
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent= new Intent (KayitOlActivity.this,GirisYapActivity.class);
                startActivity(ıntent);
                finish();
            }
        });
    }

    public void registerToUser()
    {
        kayitOlButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail =registerMailAdress.getText().toString();
                String userN=registerUserName.getText().toString();
                String pass = registerPassword.getText().toString();
                String tel=registertel.getText().toString();
                register(mail,userN,pass,tel);


            }
        });
    }
    public void delete()
    {
        registerPassword.setText("");
        registerMailAdress.setText("");
        registerUserName.setText("");
    }

    public void register(String userMailAdress,String userName, String userPass,String tel)
    {
        Call<RegisterPojo>  req  = ManagerAll.getInstance().kayitOl(userMailAdress,userName,userPass,tel);
        req.enqueue(new Callback<RegisterPojo>() {
            @Override
                public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                    if(response.body().isTf())
                    {
                        Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                        Intent  ıntent=new Intent(KayitOlActivity.this,GirisYapActivity.class);
                        startActivity(ıntent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<RegisterPojo> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();


                }
            });
    }
}