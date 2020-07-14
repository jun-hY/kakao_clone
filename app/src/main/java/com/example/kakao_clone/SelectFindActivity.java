package com.example.kakao_clone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectFindActivity extends AppCompatActivity implements View.OnClickListener{

    Button ButtonId;
    Button ButtonPassward;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectfind);

        ButtonId = (Button)findViewById(R.id.buttonFindID);
        ButtonPassward = (Button)findViewById(R.id.buttonFindPassward);

        ButtonId.setOnClickListener(this);
        ButtonPassward.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == ButtonId){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else if(view == ButtonPassward){
            startActivity(new Intent(this, FindpassActivity.class));
            finish();
        }
    }
}
