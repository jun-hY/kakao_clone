package com.example.kakao_clone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonChat;
    Button buttonLogout;
    TextView textViewID;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        buttonChat = (Button)findViewById(R.id.button2);
        buttonLogout = (Button)findViewById(R.id.button3);
        textViewID = (TextView) findViewById(R.id.textID);
        firebaseUser = firebaseAuth.getCurrentUser();
        buttonChat.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        if(firebaseUser == null) {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        else{
            textViewID.setText(firebaseUser.getEmail());
        }
    }

    @Override
    public void onClick(View view) {
        if(view == buttonChat){

            startActivity(new Intent(getApplicationContext(), ChattingActivity.class));

        }
        if(view == buttonLogout){
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        }

    }
}