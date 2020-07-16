package com.example.kakao_clone;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;

public class ChattingActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference ChatRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private ChatAdapter adapter;
    private RecyclerView recyclerView;
    private String uid;
    private Date date;

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
                .document("asdfqw@gmail.com").collection("rooms")
                .document("asdf").collection("messages");

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