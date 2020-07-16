package com.example.kakao_clone;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FindpassActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextName;
    EditText editTextEmail;
    TextView textViewMassage;
    Button buttonCheck;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CollectionReference FindRef;

    final boolean[] checkArray = {false,false};
    final boolean[] checkGetQuery = {false};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpass);

        editTextEmail = (EditText)findViewById(R.id.editTextCurrentEmail);
        editTextName = (EditText)findViewById(R.id.editTextCurrentName);
        textViewMassage = (TextView)findViewById(R.id.textviewMessage);
        buttonCheck = (Button)findViewById(R.id.buttonCheck);

        firebaseFirestore = FirebaseFirestore.getInstance();
        FindRef = firebaseFirestore.collection("Users");

        buttonCheck.setOnClickListener(this);
    }

    private void FindPassward(){
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
        firebaseFirestore.collection("Users").whereEqualTo("userid", email).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        checkArray[0] = true;
                        Log.d("오 이메일 ", String.format("이게되네 %b", checkArray[0]));
                    }
                });
        firebaseFirestore.collection("Users").whereEqualTo("username", name).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        checkArray[1] = true;
                        Log.d("오 이름 ", String.format("이게되네 %b", checkArray[1]));
                    }
                });

    }

    @Override
    public void onClick(View view){
        if(view == buttonCheck){
            FindPassward();
        }
    }
}
