package com.example.kakao_clone;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FindpassActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CollectionReference FindRef;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpass);

        editTextEmail = (EditText)findViewById(R.id.editTextCurrentEmail);
        editTextName = (EditText)findViewById(R.id.editTextCurrentName);

        firebaseFirestore = FirebaseFirestore.getInstance();
        FindRef = firebaseFirestore.collection("Users");
    }

    private void Findpassward(){
        final String email = editTextEmail.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();

        //email과 password가 비었는지 아닌지를 체크 한다.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseFirestore.collection("Users").whereEqualTo("email", email);

    }
}
