package com.satrangolimitless.User_UI.POSTAJOB;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

//user_jobpost_multimove_three
public class Activity_PostJob_Multiplelocation_Third extends AppCompatActivity {

    Button btnnextt;

    Session session;
    ImageView back;
    CardView cdhour,cdday,cdjob, cdestday,cdestdays,cdbidoneday,cdbidthreday,cdbidsevenday;
    String estimate_time="",bid_per="",accept_bid_for="";
    EditText edtno;
    TextView txtmaxbid,txtminbid;
    String time,date,description,location,bid_max_range, bid_min_range,weight,endlocation,tolocation;
    CrystalRangeSeekbar rangeSeekbar;
    ArrayList<String> Imagelist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_jobpost_multimove_three);
        session = new Session(getApplicationContext());
        btnnextt= findViewById(R.id.btnnextt);
        back= findViewById(R.id.back);
        txtmaxbid= findViewById(R.id.txtmaxbid);
        txtminbid= findViewById(R.id.txtminbid);
        edtno= findViewById(R.id.edtno);
        cdhour= findViewById(R.id.cdhour);
        cdday= findViewById(R.id.cdday);
        cdjob= findViewById(R.id.cdjob);
        cdestday= findViewById(R.id.cdestday);
        cdestdays= findViewById(R.id.cdestdays);
        cdbidoneday= findViewById(R.id.cdbidoneday);
        cdbidthreday= findViewById(R.id.cdbidthreday);
        cdbidsevenday= findViewById(R.id.cdbidsevenday);
        rangeSeekbar =    findViewById(R.id.rangeSeekbar);

        if (getIntent()!=null){
            location = getIntent().getStringExtra("location");
            description = getIntent().getStringExtra("description");


            tolocation = getIntent().getStringExtra("tolocation");
            endlocation = getIntent().getStringExtra("endlocation");
            weight = getIntent().getStringExtra("weight");
            System.out.println("third page date time ** "+date+" ** "+time);
        }


        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {


                bid_min_range = (String.valueOf(minValue));
                bid_max_range = (String.valueOf(maxValue));
                System.out.println("  bid_max_rangecxcx bid_min_range  " + (String.valueOf(maxValue)));
                txtminbid.setText(bid_min_range);
                txtmaxbid.setText(bid_max_range);
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
                estimate_time="Days";
                cdestday.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdestdays.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        cdestdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estimate_time="Hours";
                cdestdays.setCardBackgroundColor(Color.parseColor("#fdc400"));
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
                accept_bid_for="3 Day";
                cdbidthreday.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdbidoneday.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbidsevenday.setCardBackgroundColor(Color.parseColor("#ffffff"));

            }
        });
        cdbidsevenday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept_bid_for="7 Day";
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
                if (Estimatedtime.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select estimated time", Toast.LENGTH_SHORT).show();

                }else if(bid_per.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please select Bid per", Toast.LENGTH_SHORT).show();

                }

                else if(accept_bid_for.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please select accept bid for", Toast.LENGTH_SHORT).show();

                }


                else {

                    Intent intent = new Intent(Activity_PostJob_Multiplelocation_Third.this, Activity_PostJob_Multiplelocation_Fourth.class);
                    intent.putExtra("Estimatedtime", numberofday);
                    intent.putExtra("bid_per", bid_per);
                    intent.putExtra("bid_max_range", bid_max_range);
                    intent.putExtra("bid_min_range", bid_min_range);
                    intent.putExtra("accept_bid_for", accept_bid_for);
//firstpage data---------------------------------------------------------
                    intent.putExtra("tolocation", tolocation);
                    intent.putExtra("endlocation", endlocation);
                    intent.putExtra("location", location);
                    intent.putExtra("description", description);
                    intent.putExtra("date", date);
                    intent.putExtra("time", time);
                    intent.putExtra("weight", weight);
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

