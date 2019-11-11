package com.tnq.ngocquang.datn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.tnq.ngocquang.datn.model.User;

public class InfoActivity extends AppCompatActivity {

    private TextView mDemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info2);
        anhxa();
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");
        mDemo.setText( "_" + user.getGender());
    }

    private void anhxa(){
        mDemo = findViewById(R.id.demo);
    }
}
