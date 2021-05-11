package com.satrangolimitless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.satrangolimitless.Booknow.BooknowSingleMoveActivity;

public class JobListActivity extends AppCompatActivity {
    TextView JV_BOOKNOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        JV_BOOKNOW=findViewById(R.id.JV_BOOKNOW);

        findViewById(R.id.JV_BOOKNOW).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobListActivity.this, BooknowSingleMoveActivity.class);
                startActivity(intent);
            }
        });
    }
}