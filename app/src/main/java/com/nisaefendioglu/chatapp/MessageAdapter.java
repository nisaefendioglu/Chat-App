package com.nisaefendioglu.chatapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context context;
    List<MessageModel> list;
    Activity activity;
    String userName;
    Boolean state;
    int send =0, received=1;

    public MessageAdapter(Context context, List<MessageModel> list, Activity activity, String userName) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.userName = userName;
        state = false;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == send){
            view= LayoutInflater.from(context).inflate(R.layout.send_layout, parent, false);
            return new ViewHolder(view);
        }
        else {
            view= LayoutInflater.from(context).inflate(R.layout.received_layout, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, final int position) { //atama işlemleri, görüntüleme
        holder.textView.setText(list.get(position).getText().toString());

    }

    @Override
    public int getItemCount() { //listedekilerin uzunluğu, sayısı
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;


        public ViewHolder(View itemView) {
            super(itemView);
            if(state == true){
                textView = itemView.findViewById(R.id.sendView);
            }
            else {
                textView = itemView.findViewById(R.id.receivedView);
            }


        }
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getFrom().equals(userName)){
            state = true;
            return send;
        }
        else {
            state = false;
            return received;
        }
    }
}
