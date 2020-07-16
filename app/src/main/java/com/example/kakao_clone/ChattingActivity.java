package com.example.kakao_clone;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.LogWriter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ChattingActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference ChatRef;

    private DocumentReference userRef;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private ChatAdapter adapter;
    private RecyclerView recyclerView;
    private String uid;
    private Date date;

    private Collection userInfo;

    private static final String TAG = "ChattingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseUser.getEmail();

        db = FirebaseFirestore.getInstance();
        ChatRef = db.collection("Users")
                .document(uid).collection("rooms")
                .document("asdf").collection("messages");
        userRef = db.collection("Users")
                .document(uid).collection("rooms")
                .document("asdf");

        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Log.d(TAG, "onComplete: " + documentSnapshot.getData());
                        userInfo = documentSnapshot.getData().values();
                    } else {
                        Log.d(TAG, "Not Such Data");
                    }
                } else {
                    Log.d(TAG, "Get Failed with"  + task.getException());
                }
            }
        }).;



        final EditText sendTxt = findViewById(R.id.sendtxt);
        final ImageButton sendBtn = findViewById(R.id.sendbtn);

        setUpRecyclerView();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date = new Date(System.currentTimeMillis());
                SimpleDateFormat sdftime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String formatDate = sdftime.format(date);

                String Chatting = sendTxt.getText().toString();

                if (TextUtils.isEmpty(Chatting)) {
                    return;
                }
//                Log.d(TAG, "Chatting is " + Chatting);
                ChatRef.add(new Chat(uid, Chatting, formatDate));
                sendTxt.setText("");
            }
        });
    }

    public void setUpRecyclerView() {
        Query query = ChatRef.orderBy("timestamp", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Chat> options = new FirestoreRecyclerOptions.Builder<Chat>()
                .setQuery(query, Chat.class)
                .build();

        adapter = new ChatAdapter(options);

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