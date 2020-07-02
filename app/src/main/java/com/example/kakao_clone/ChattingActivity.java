package com.example.kakao_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChattingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    mUser.getIdToken(true)
            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
        public void onComplete(@NonNull Task<GetTokenResult> task) {
            if (task.isSuccessful()) {
                String idToken = task.getResult().getToken();
                // Send token to your backend via HTTPS
                // ...
            } else {
                // Handle error -> task.getException();
            }
        }
    });

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ChatRef = db.collection("messages");

    private ChatAdapter adapter;

    private static final String TAG = "ChattingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

//        final Intent chatIntent = getIntent();

        final EditText sendTxt = findViewById(R.id.sendtxt);
        final ImageButton sendBtn = findViewById(R.id.sendbtn);

//        final String uid = chatIntent.getStringExtra("uid");
        final String Chatting = sendTxt.getText().toString();

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdftime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final String formatDate = sdftime.format(date);

        setUpRecyclerView();

        ChatRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               Log.d(TAG, document.getId() + " => " + document.getData());
                           }
                       } else {
                           Log.w(TAG, "Error getting documents.", task.getException());
                       }
                   }
               });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(sendTxt.getText())) {
                    return;
                }
//                ChatRef.add(new Chat(uid, Chatting, formatDate));
                sendTxt.setText("");
            }
        });
    }

    public void setUpRecyclerView() {
        Query query = ChatRef.orderBy("timestamp", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Chat> options = new FirestoreRecyclerOptions.Builder<Chat>()
                .setQuery(query, Chat.class)
                .build();

        recyclerView = findViewById(R.id.chtscr);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}