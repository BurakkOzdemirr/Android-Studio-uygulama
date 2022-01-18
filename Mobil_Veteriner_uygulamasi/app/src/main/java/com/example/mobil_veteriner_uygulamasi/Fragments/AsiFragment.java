package com.example.mobil_veteriner_uygulamasi.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobil_veteriner_uygulamasi.Models.AsiModelItem;
import com.example.mobil_veteriner_uygulamasi.R;
import com.example.mobil_veteriner_uygulamasi.RestApi.ManagerAll;
import com.example.mobil_veteriner_uygulamasi.Utils.ChangeFragments;
import com.example.mobil_veteriner_uygulamasi.Utils.GetSharedPreferences;
import com.squareup.picasso.Picasso;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AsiFragment extends Fragment {


    private View view;
    private CalendarPickerView calendarPickerView;
    private DateFormat format;
    private Calendar nextyear;
    private Date today;
    private List<AsiModelItem> asiList;
    private List<Date> dateList;
    private String id;
    private GetSharedPreferences getSharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_asi, container, false);
        tanimla();
        getAsi();
        clickToCalender();
        return view;
    }

    public void tanimla()
    {
        calendarPickerView=view.findViewById(R.id.calenderPickerView);
        format=new SimpleDateFormat("dd/MM/yyyy");
        nextyear = Calendar.getInstance();
        nextyear.add(Calendar.YEAR,1);
        today=new Date();


        calendarPickerView.init(today,nextyear.getTime());
        asiList=new ArrayList<>();
        dateList=new ArrayList<>();
        getSharedPreferences =new GetSharedPreferences(getActivity());
        id=getSharedPreferences.getSession().getString("id",null);


    }
    public void getAsi()
    {
        Call<List<AsiModelItem>> req= ManagerAll.getInstance().getAsi(id);
        req.enqueue(new Callback<List<AsiModelItem>>() {
            @Override
            public void onResponse(Call<List<AsiModelItem>> call, Response<List<AsiModelItem>> response) {
                if(response.isSuccessful())
                {
                    if(response.body().get(0).isTf())
                    {

                    asiList=response.body();
                    for(int i=0;i<asiList.size();i++)
                    {
                        String dateString=response.body().get(i).getAsitarih().toString();
                        try {

                            Date date = format.parse(dateString);
                            dateList.add(date);
                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    calendarPickerView.init(today,nextyear.getTime())
                            .withHighlightedDates(dateList);
                    }

                }
                else
                {
                    ChangeFragments changeFragments=new ChangeFragments(getContext());
                    changeFragments.change(new HomeFragment());
                    Toast.makeText(getContext(),"Petinize ait gelecek tarihte aşı bulunamamıştır.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AsiModelItem>> call, Throwable t) {

            }
        });
    }

    public void clickToCalender()
    {
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                for(int i=0;i<dateList.size();i++)
                {
                    if(date.toString().equals(dateList.get(i).toString()))
                    {
                        openQuestionAlert(asiList.get(i).getPetisim().toString(),asiList.get(i).getAsitarih().toString(),
                                asiList.get(i).getAsiism().toString(),asiList.get(i).getPetresim().toString());
                    }
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    public void openQuestionAlert(String petİsmi,String tarih,String asiİsmi,String resimUrl) {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.asitakiplayout, null);
        TextView petİsimText = (TextView) view.findViewById(R.id.petİsimText);
        TextView petAsiTakipBilgi = (TextView) view.findViewById(R.id.petAsiTakipBilgi);
        CircleImageView asiTakipİmage = (CircleImageView) view.findViewById(R.id.asiTakipİmage);

        petİsimText.setText(petİsmi);
        petAsiTakipBilgi.setText(petİsmi +" isimli petinizin "+ tarih +" tarihinde "+ asiİsmi +" aşısı yapılacaktır.");
        Picasso.get().load(resimUrl).into(asiTakipİmage);


        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        alertDialog.show();
    }
}