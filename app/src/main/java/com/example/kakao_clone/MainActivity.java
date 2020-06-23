package com.example.kakao_clone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonChat;
    Button buttonLogin;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        buttonChat = (Button)findViewById(R.id.button2);
        buttonLogin = (Button)findViewById(R.id.button3);


        buttonChat.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonChat){

            startActivity(new Intent(getApplicationContext(), ChattingActivity.class));

        }
        if(view == buttonLogin){

            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        }

    }
}