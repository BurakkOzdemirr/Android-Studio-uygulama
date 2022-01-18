package com.example.mobil_veteriner_uygulamasi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mobil_veteriner_uygulamasi.Fragments.HomeFragment;
import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.Utils.ChangeFragments;
import com.example.mobil_veteriner_uygulamasi.Utils.GetSharedPreferences;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences getSharedPreferences;
    private GetSharedPreferences preferences;
    private ImageView anasayfabuton,aramayapbuton,cikisYapButon;
    private ChangeFragments changeFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragment();
        tanimla();
        kontrol();
        action();
    }

    private void getFragment()
    {
        changeFragments =new ChangeFragments(MainActivity.this);
        changeFragments.change(new HomeFragment());
    }

    public void action()
    {
        anasayfabuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new HomeFragment());
            }
        });
        cikisYapButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSharedPreferences getSharedPreferences=new GetSharedPreferences(MainActivity.this);
                getSharedPreferences.deleteToSession();
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        aramayapbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:00000000000"));
                startActivity(intent);
            }
        });
    }

    public void tanimla()
    {
        preferences = new GetSharedPreferences(MainActivity.this);
        getSharedPreferences = preferences.getSession();
        anasayfabuton=(ImageView) findViewById(R.id.anasayfabuton);
        aramayapbuton=(ImageView)findViewById(R.id.aramaYapButon);
        cikisYapButon=(ImageView)findViewById(R.id.cikisYapButon);

    }

    public void kontrol()
    {
        if(getSharedPreferences.getString("id",null)==null && getSharedPreferences.getString("username",null)==null
                && getSharedPreferences.getString("mailadres",null)==null )
        {
            Intent ıntent = new Intent(MainActivity.this,GirisYapActivity.class);
            startActivity(ıntent);
            finish();
        }
    }
}