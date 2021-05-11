package com.satrangolimitless.User_UI.POSTAJOB;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session;

public class Activity_PostJobBlueCollar_Second extends AppCompatActivity {

    Button btnnextt;

    Session session;
      ImageView back;
    CardView cdhour,cdday,cdjob, cdestday,cdhours,cdbidoneday,cdbidthreday,cdbidsevenday;
    String estimate_time="",bid_per="",accept_bid_for="";
    EditText edtno;
    TextView txtmaxrange,txtminrange;
    String time,date,description,location,bid_max_range, bid_min_range;
    CrystalRangeSeekbar rangeSeekbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_jobpost_bluecollar_second);
        session = new Session(getApplicationContext());
        btnnextt= findViewById(R.id.btnnextt);
        back= findViewById(R.id.back);
        edtno= findViewById(R.id.edtno);
        cdhour= findViewById(R.id.cdhour);
        cdday= findViewById(R.id.cdday);
        cdjob= findViewById(R.id.cdjob);
        cdestday= findViewById(R.id.cdestday);
        cdhours= findViewById(R.id.cdhours);
        cdbidoneday= findViewById(R.id.cdbidoneday);
        cdbidthreday= findViewById(R.id.cdbidthreday);
        cdbidsevenday= findViewById(R.id.cdbidsevenday);
        rangeSeekbar =    findViewById(R.id.rangeSeekbar);
        txtminrange =    findViewById(R.id.txtminrange);
        txtmaxrange =    findViewById(R.id.txtmaxrange);

        if (getIntent()!=null){
//            location = getIntent().getStringExtra("location");
            description = getIntent().getStringExtra("description");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");


        }


        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {


                bid_min_range = (String.valueOf(minValue));
                bid_max_range = (String.valueOf(maxValue));
                txtminrange.setText(String.valueOf(minValue));
                txtmaxrange.setText("- "+String.valueOf(maxValue));

                System.out.println("  bid_max_rangecxcx bid_min_range  " + (String.valueOf(maxValue)));
            }
        });

        cdhour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bid_per="Hour";
                cdhour.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdday.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdjob.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        cdday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bid_per="Day";
                cdhour.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdday.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdjob.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });


        cdjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bid_per="Job";
                cdhour.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdjob.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdday.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });


        cdestday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estimate_time="days";
                cdestday.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdhours.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        cdhours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estimate_time="hours";
                cdhours.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdestday.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        cdbidoneday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept_bid_for="1 Day";
                cdbidthreday.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbidoneday.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdbidsevenday.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        cdbidthreday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept_bid_for="3 Days";
                cdbidthreday.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdbidoneday.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbidsevenday.setCardBackgroundColor(Color.parseColor("#ffffff"));

            }
        });
        cdbidsevenday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept_bid_for="7 Days";
                cdbidthreday.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbidoneday.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbidsevenday.setCardBackgroundColor(Color.parseColor("#fdc400"));

            }
        });
        btnnextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberofday=edtno.getText().toString();
                String Estimatedtime=numberofday+" "+estimate_time;
              if(bid_per.isEmpty()){
                  Toast.makeText(Activity_PostJobBlueCollar_Second.this, "Please select bid per", Toast.LENGTH_SHORT).show();
              }
               else if(bid_max_range.isEmpty()){
                  Toast.makeText(Activity_PostJobBlueCollar_Second.this, "Please max range", Toast.LENGTH_SHORT).show();

              }
              else if(bid_min_range.isEmpty()){
                  Toast.makeText(Activity_PostJobBlueCollar_Second.this, "Please min range", Toast.LENGTH_SHORT).show();

              }
              else if(accept_bid_for.isEmpty()){
                  Toast.makeText(Activity_PostJobBlueCollar_Second.this, "Please enter accept bid for", Toast.LENGTH_SHORT).show();

              }
               else if(Estimatedtime.isEmpty()){
                  Toast.makeText(Activity_PostJobBlueCollar_Second.this, "Please enter estimated time", Toast.LENGTH_SHORT).show();

              }else{

                  Intent intent = new Intent(Activity_PostJobBlueCollar_Second.this, Activity_PostJob_BluecollarThird.class);
                  intent.putExtra("Estimatedtime", numberofday);
                  intent.putExtra("bid_per", bid_per);
                  intent.putExtra("bid_max_range", bid_max_range);
                  intent.putExtra("bid_min_range", bid_min_range);
                  intent.putExtra("accept_bid_for", accept_bid_for);
//firstpage data---------------------------------------------------------
//                  intent.putExtra("location", location);
                  intent.putExtra("description", description);
                  intent.putExtra("date", date);
                  intent.putExtra("time", time);

                  startActivity(intent);

              }



            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
