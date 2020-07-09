package com.example.kakao_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChattingActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference ChatRef;
    private FirebaseUser uid;
    private FirebaseAuth firebaseAuth;

    private ChatAdapter adapter;
    private RecyclerView recyclerView;
    private Date date;

    private static final String TAG = "ChattingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        uid = firebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        ChatRef = db.collection("Users")
                .document().collection("rooms")
                .document().collection("messages");

//        final Intent chatIntent = getIntent();

        final EditText sendTxt = findViewById(R.id.sendtxt);
        final ImageButton sendBtn = findViewById(R.id.sendbtn);

//        final String uid = chatIntent.getStringExtra("uid");
        final String Chatting = sendTxt.getText().toString();

        date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdftime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final String formatDate = sdftime.format(date);





        setUpRecyclerView();

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