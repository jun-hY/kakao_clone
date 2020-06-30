package com.example.kakao_clone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChattingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private FirebaseFirestore db;
    private CollectionReference ChatRef;

    private ChatAdapter adapter;

    private static final String TAG = "ChattingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        final Intent chatIntent = getIntent();

        final EditText sendTxt = findViewById(R.id.sendtxt);
        final ImageButton sendBtn = findViewById(R.id.sendbtn);

        final String uid = chatIntent.getStringExtra("uid");
        final String Chatting = sendTxt.getText().toString();

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdftime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final String formatDate = sdftime.format(date);

        db = FirebaseFirestore.getInstance();
        ChatRef = db.collection("messages");

        setUpRecyclerView();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(sendTxt.getText())) {
                    return;
                }
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