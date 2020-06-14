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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private ArrayList<Chat> arrayList;
    private Context context;

    public ChatAdapter(ArrayList<Chat> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat, parent, false);
        ChatViewHolder holder = new ChatViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.them_profile);
        holder.UserName.setText(arrayList.get(position).getName());
        holder.chatCon.setText(arrayList.get(position).getChat());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView them_profile;
        TextView chatCon;
        TextView UserName;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            this.them_profile = itemView.findViewById(R.id.them_profile);
            this.chatCon = itemView.findViewById(R.id.chatCon);
            this.UserName = itemView.findViewById(R.id.UserName);
        }

    }

}
