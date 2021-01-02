package com.example.androiddevelopmentfinal.Message;

import android.provider.ContactsContract;

import org.litepal.crud.DataSupport;

public class Msg extends DataSupport {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private int type;
    private String account;
    public Msg(String content,int type){
        this.content = content;
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContent(){
        return content;
    }

    public int getType(){
        return type;
    }

    public static int getTypeReceived() {
        return TYPE_RECEIVED;
    }

    public static int getTypeSent() {
        return TYPE_SENT;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }
}
