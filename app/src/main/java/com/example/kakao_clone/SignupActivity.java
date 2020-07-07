package com.example.kakao_clone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    //define view objects
    EditText editTextEmail;
    EditText editTextPassword;
    EditText getEditTextPasswordCheck;
    Button buttonSignup;
    TextView textviewSingin;
    TextView textviewMessage;
    ProgressDialog progressDialog;
    //define firebase object
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        getEditTextPasswordCheck = (EditText) findViewById(R.id.editTextPasswordCheck);
        textviewSingin= (TextView) findViewById(R.id.textViewSignin);
        textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);

        //button click event
        buttonSignup.setOnClickListener(this);
        textviewSingin.setOnClickListener(this);
    }

    private boolean isFindAlphaNum(String s){
        char[] chars = s.toCharArray();
        boolean[] result = {false,false};

        result[0] = s.matches(".*[0-9].*");

        for(char c : chars){
            if(Character.isLetter(c)){
                result[1] = true;
                break;
            }
        }

        return result[0] && result[1];
    }


    //Firebse creating a new user
    private void registerUser() {
        //사용자가 입력하는 email, password를 가져온다.
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordCheck = getEditTextPasswordCheck.getText().toString().trim();

        //email과 password가 비었는지 아닌지를 체크 한다.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(passwordCheck)) {
            Toast.makeText(this, "Password 확인을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }

        //email과 password가 제대로 입력되어 있다면 계속 진행된다.
        progressDialog.setMessage("등록중입니다. 기다려 주세요...");
        progressDialog.show();

        //creating a new user
        if (password.length() >= 8 && password.equals(passwordCheck) && isFindAlphaNum(password)) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            } else {
                                //에러발생시
                                textviewMessage.setText("에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러");
                                Toast.makeText(SignupActivity.this, "등록 에러!", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
        else {
            textviewMessage.setText("에러유형\n - 비밀번호를 확인을 하세요. \n - 비밀번호는 영어를 포함한 8자리 입니다.\n - 서버에러");
            progressDialog.dismiss();
        }
    }

    //button click event
    @Override
    public void onClick(View view) {
        if(view == buttonSignup){
            registerUser();
        }

        if(view == textviewSingin) {
            startActivity(new Intent(this, LoginActivity.class)); //추가해 줄 로그인 액티비티
            finish();
        }
    }
    

}