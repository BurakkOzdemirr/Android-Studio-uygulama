package com.example.veterineradmin.RestApi;



import com.example.veterineradmin.Models.AsiEkle;
import com.example.veterineradmin.Models.AsiOnaylaModel;
import com.example.veterineradmin.Models.CevaplaModel;
import com.example.veterineradmin.Models.KampanyaEkleModel;
import com.example.veterineradmin.Models.KampanyaModelItem;
import com.example.veterineradmin.Models.KampanyaSilModel;
import com.example.veterineradmin.Models.KullaniciSilModel;
import com.example.veterineradmin.Models.KullanicilarModelItem;
import com.example.veterineradmin.Models.PetAiTakipModelItem;
import com.example.veterineradmin.Models.PetEkleModel;
import com.example.veterineradmin.Models.PetModelItem;
import com.example.veterineradmin.Models.PetSilModel;
import com.example.veterineradmin.Models.SoruModelItem;

import java.util.List;

import retrofit2.Call;

public class ManagerAll  extends BaseManager{

    private  static ManagerAll ourInstance = new ManagerAll();

    public  static synchronized ManagerAll getInstance()
    {
        return  ourInstance;
    }

    public Call<List<KampanyaModelItem>>getKampanya()
    {
        Call<List<KampanyaModelItem>> x= getRestApi().getKampanya();
        return  x ;
    }
    public Call<KampanyaEkleModel>addKampanya(String baslik, String icerik, String imageString)
    {
        Call<KampanyaEkleModel>x= getRestApi().addKampanya(baslik,icerik,imageString);
        return  x ;
    }

    public Call<KampanyaSilModel>kampanyaSil(String id)
    {
        Call<KampanyaSilModel>x= getRestApi().kampanyaSil(id);
        return  x ;
    }

    public Call<List<PetAiTakipModelItem>>getPetAsiTakip(String tarih)
    {
        Call<List<PetAiTakipModelItem>> x= getRestApi().getPetAsiTakip(tarih);
        return  x ;
    }

    public Call<AsiOnaylaModel>asiOnayla(String id)
    {
        Call<AsiOnaylaModel>x= getRestApi().asiOnayla(id);
        return  x ;
    }

    public Call<AsiOnaylaModel>asiIptal(String id)
    {
        Call<AsiOnaylaModel>x= getRestApi().asiIptal(id);
        return  x ;
    }

    public Call<List<SoruModelItem>>getSoru()
    {
        Call<List<SoruModelItem>> x= getRestApi().getSoru();
        return  x ;
    }

    public Call<CevaplaModel>cevapla(String musid, String soruid, String text)
    {
        Call<CevaplaModel>x= getRestApi().cevapla(musid,soruid,text);
        return  x ;
    }

    public Call<List<KullanicilarModelItem>>getKullanicilar()
    {
        Call<List<KullanicilarModelItem>> x= getRestApi().getKullanicilar();
        return  x ;
    }

    public Call<List<PetModelItem>>getPet(String id)
    {
        Call<List<PetModelItem>> x= getRestApi().getPet(id);
        return  x ;
    }

    public Call<PetEkleModel>petEkle(String musid, String isim, String tur, String cins, String resim)
    {
        Call<PetEkleModel>x= getRestApi().petEkle(musid,isim,tur,cins,resim);
        return  x ;
    }

    public Call<AsiEkle>asiEkle(String musid, String petid, String name, String tarih)
    {
        Call<AsiEkle>x= getRestApi().asiEkle(musid,petid,name,tarih);
        return  x ;
    }

    public Call<KullaniciSilModel> kadiSil(String id)
    {
        Call<KullaniciSilModel> x= getRestApi().kadiSil(id);
        return  x ;
    }

    public Call<PetSilModel> petSil(String id)
    {
        Call<PetSilModel> x= getRestApi().petSil(id);
        return  x ;
    }




}
