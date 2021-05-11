package com.satrangolimitless.User_UI.POSTAJOB;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.Adapter.Adapter_attachments_images;
import com.satrangolimitless.R;
import com.satrangolimitless.model.SubjectData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Activity_PostJob_Multiplelocation_Second extends AppCompatActivity {
    private final int SELECT_FILE = 1;
    private final List<String> FilenamList = new ArrayList<>();
    private final ArrayList<Uri> arrayList = new ArrayList<>();
    String endlocation, tolocation, location, time, Date;
    EditText edtdecsription;
    CardView cdheavy, cdmed, cdlight;
    ImageView img_add;
    RecyclerView list;
    Button btnnextt;
    Uri pickedImage;
    String userChoosenTask;
    String filenew1;
    File destination;
    ArrayList<SubjectData> imagearrayList = new ArrayList<SubjectData>();
    Adapter_attachments_images adapter_popular_categoriesActivity;
    String weight = "", description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_jobpost_multimove_second);
        btnnextt = findViewById(R.id.btnnextt);
        edtdecsription = findViewById(R.id.edtdecsription);
        cdheavy = findViewById(R.id.cdheavy);
        cdmed = findViewById(R.id.cdmed);
        cdlight = findViewById(R.id.cdlight);
        img_add = findViewById(R.id.img_add);
        list = findViewById(R.id.list);


        if (getIntent() != null) {


            location = getIntent().getStringExtra("location");
            tolocation = getIntent().getStringExtra("tolocation");
            endlocation = getIntent().getStringExtra("endlocation");


            System.out.println("first data--**-  " + "  " + location + "  " + tolocation + "  " + endlocation);

        }


        cdlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdlight.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdmed.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdheavy.setCardBackgroundColor(Color.parseColor("#ffffff"));

                weight = "light";
            }
        });

        cdmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdmed.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdlight.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdheavy.setCardBackgroundColor(Color.parseColor("#ffffff"));
                weight = "medium";
            }
        });


        cdheavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdheavy.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdlight.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdmed.setCardBackgroundColor(Color.parseColor("#ffffff"));

                weight = "heavy";
            }
        });


        btnnextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                description = edtdecsription.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select description", Toast.LENGTH_SHORT).show();

                }
                else if(weight.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Please select weight", Toast.LENGTH_SHORT).show();

                }
                else {
                    Intent intent = new Intent(Activity_PostJob_Multiplelocation_Second.this, Activity_PostJob_Multiplelocation_Third.class);

                    intent.putExtra("location", location);
                    intent.putExtra("tolocation", tolocation);
                    intent.putExtra("endlocation", endlocation);
                    intent.putExtra("weight", weight);
                    intent.putExtra("description", description);
                    startActivity(intent);
                }



            }
        });


    }


}
