package com.example.kakao_clone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChatSelfAdapter extends RecyclerView.Adapter<ChatSelfAdapter.ChatViewHolder> {

    private ArrayList<Chat> arrayList;
    private Context context;

    public ChatSelfAdapter(ArrayList<Chat> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_selfchat, parent, false);
        ChatViewHolder holder = new ChatViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.sf_Con.setText(arrayList.get(position).getChat());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView sf_Con;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            this.sf_Con = itemView.findViewById(R.id.sf_con);
        }

    }

}
