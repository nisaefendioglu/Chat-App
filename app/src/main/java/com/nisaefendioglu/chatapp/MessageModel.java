package com.nisaefendioglu.chatapp;

public class MessageModel {
    private String from, text;

    public MessageModel(){

    }
    public MessageModel(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "from='" + from + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
