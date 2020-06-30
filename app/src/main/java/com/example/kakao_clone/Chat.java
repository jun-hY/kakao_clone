package com.example.kakao_clone;

import android.text.Editable;

import java.util.Date;

public class Chat {

    private String profile;
    private String Name;
    private String chat;
    private Date Date;

    public Chat(String uid, String text, String formatDate) {}

    public Chat(String Name, String chat, Date Date) {
        this.Name = Name;
        this.chat = chat;
        this.Date = Date;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
