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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @FormUrlEncoded
    @POST("/veterinerservis/kayitol.php")
    Call<RegisterPojo> registerUser(@Field("mailAdres") String mailAdres , @Field("kadi") String kadi , @Field("pass") String pass, @Field("telefon") String telefon);

    @FormUrlEncoded
    @POST("/veterinerservis/girisyap.php")
    Call<LoginModel> loginUser(@Field("mailadres") String mailAdres , @Field("sifre") String pass );

    @FormUrlEncoded
    @POST("/veterinerservis/petlerim.php")
    Call<List<PetModelItem>> getPets(@Field("musid") String mus_id );

    @FormUrlEncoded
    @POST("/veterinerservis/sorusor.php")
    Call<AskQuestionModel> soruSor(@Field("id") String id , @Field("soru") String soru ,@Field("konu") String konu);

    @FormUrlEncoded
    @POST("/veterinerservis/cevaplar.php")
    Call<List<AnswersModelsItem>> getAnswers(@Field("id") String mus_id );

    @FormUrlEncoded
    @POST("/veterinerservis/cevapsil.php")
    Call<DeleteAnswerModel>deleteAnswer(@Field("cevap") String cevapid, @Field("soru") String soruid );


    @GET("/veterinerservis/kampanya.php")
    Call<List<KampanyaModelItem>> getKampanya();

    @FormUrlEncoded
    @POST("/veterinerservis/asitakip.php")
    Call<List<AsiModelItem>> getAsi(@Field("id") String id );

    @FormUrlEncoded
    @POST("/veterinerservis/gecmisasi.php")
    Call<List<AsiModelItem>> getGecmisAsi(@Field("id") String id ,@Field("petid") String pet_id );

}
