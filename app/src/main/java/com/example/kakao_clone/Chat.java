package com.example.kakao_clone;

import android.text.Editable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Chat {

    private String uid;
    private String msg;
    private String timestamp;

    public Chat() {}

    public Chat(String uid, String msg, String timestamp) {
        this.uid = uid;
        this.msg = msg;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
