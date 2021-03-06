package com.example.kakao_clone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.HttpException;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.w3c.dom.Text;

public class ChatAdapter extends FirestoreRecyclerAdapter<Chat, ChatAdapter.ChatHolder> {

    public ChatAdapter(@NonNull FirestoreRecyclerOptions<Chat> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatHolder chatHolder, int i, @NonNull Chat chat) {
        chatHolder.TextChat.setText(chat.getMsg());
        chatHolder.TextName.setText(chat.getUid());
        chatHolder.TextTime.setText(chat.getTimestamp());
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat, parent, false);
        return new ChatHolder(view);
    }

    class ChatHolder extends RecyclerView.ViewHolder {
        TextView TextName;
        TextView TextChat;
        TextView TextTime;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            TextName = itemView.findViewById(R.id.uid);
            TextChat = itemView.findViewById(R.id.msg);
            TextTime = itemView.findViewById(R.id.timestamp);
        }
    }

}
