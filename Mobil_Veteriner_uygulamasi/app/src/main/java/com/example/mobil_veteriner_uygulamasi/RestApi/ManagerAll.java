package com.example.mobil_veteriner_uygulamasi.RestApi;

import com.example.mobil_veteriner_uygulamasi.Models.AnswersModelsItem;
import com.example.mobil_veteriner_uygulamasi.Models.AsiModelItem;
import com.example.mobil_veteriner_uygulamasi.Models.AskQuestionModel;
import com.example.mobil_veteriner_uygulamasi.Models.DeleteAnswerModel;
import com.example.mobil_veteriner_uygulamasi.Models.KampanyaModelItem;
import com.example.mobil_veteriner_uygulamasi.Models.LoginModel;
import com.example.mobil_veteriner_uygulamasi.Models.PetModelItem;
import com.example.mobil_veteriner_uygulamasi.Models.RegisterPojo;

import java.util.List;

import retrofit2.Call;

public class ManagerAll  extends BaseManager{

    private  static ManagerAll ourInstance = new ManagerAll();

    public  static synchronized ManagerAll getInstance()
    {
        return  ourInstance;
    }

    public Call<RegisterPojo>kayitOl(String mail , String kadi, String parola,String telefon)
    {
        Call<RegisterPojo>x= getRestApi().registerUser(mail,kadi,parola,telefon);
        return  x ;
    }
    public Call<LoginModel>girisYap(String mail , String parola)
    {
        Call<LoginModel>x= getRestApi().loginUser(mail,parola);
        return  x ;
    }

    public Call<List<PetModelItem>> getPets(String id)
    {
        Call<List<PetModelItem>>x= getRestApi().getPets(id);
        return  x ;
    }

    public Call<AskQuestionModel>soruSor(String id , String soru,String konu)
    {
        Call<AskQuestionModel>x= getRestApi().soruSor(id,soru,konu);
        return  x ;
    }

    public Call<List<AnswersModelsItem>> getAnswers(String id)
    {
        Call<List<AnswersModelsItem>>x= getRestApi().getAnswers(id);
        return  x ;
    }

    public Call<DeleteAnswerModel>deleteAnswer(String cevap , String soru)
    {
        Call<DeleteAnswerModel> x= getRestApi().deleteAnswer(cevap,soru);
        return  x ;
    }

    public Call<List<KampanyaModelItem>>getKampanya()
    {
        Call<List<KampanyaModelItem>> x= getRestApi().getKampanya();
        return  x ;
    }

    public Call<List<AsiModelItem>> getAsi(String id)
    {
        Call<List<AsiModelItem>>x= getRestApi().getAsi(id);
        return  x ;
    }

    public Call<List<AsiModelItem>> getGecmisAsi(String id,String pet_id)
    {
        Call<List<AsiModelItem>>x= getRestApi().getGecmisAsi(id,pet_id);
        return  x ;
    }
}
