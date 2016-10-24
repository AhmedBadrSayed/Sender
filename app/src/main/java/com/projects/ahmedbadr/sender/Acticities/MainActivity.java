package com.projects.ahmedbadr.sender.Acticities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.projects.ahmedbadr.sender.Service.MyService;
import com.projects.ahmedbadr.sender.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getBaseContext(),MyService.class));
                startActivity(new Intent(getApplicationContext(),MapsActivity.class));
            }
        });
    }
}
