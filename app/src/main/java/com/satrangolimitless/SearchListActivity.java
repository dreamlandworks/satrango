package com.satrangolimitless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.satrangolimitless.Booknow.BooknowSingleMoveActivity;

public class SearchListActivity extends AppCompatActivity {
TextView AG_BOOKNOW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        AG_BOOKNOW=findViewById(R.id.AG_BOOKNOW);

        findViewById(R.id.AG_BOOKNOW).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchListActivity.this, BooknowSingleMoveActivity.class);
                startActivity(intent);
            }
        });

    }
}