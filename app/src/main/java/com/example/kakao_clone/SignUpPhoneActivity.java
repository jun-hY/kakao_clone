package com.example.kakao_clone;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpPhoneActivity  extends AppCompatActivity implements View.OnClickListener{

    EditText editTextNumber;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_phone);
        editTextNumber = (EditText)findViewById(R.id.editTextNumber);

        editTextNumber.setOnClickListener(this);
    }
    private void Certification(){
    }
    @Override
    public void onClick(View view){
        final String phoneNumber = editTextNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            Certification();
        }
    }
}