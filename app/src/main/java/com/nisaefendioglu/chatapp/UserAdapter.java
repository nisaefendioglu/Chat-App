package com.nisaefendioglu.chatapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    List<String> list;
    Activity activity;
    String userName;

    public UserAdapter(Context context, List<String> list, Activity activity, String userName) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.userName = userName;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, final int position) { //atama işlemleri, görüntüleme
        holder.textView.setText(list.get(position).toString());
        holder.userID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ChatActivity.class);
                intent.putExtra("username", userName); //kullanıcı al
                intent.putExtra("othername", list.get(position).toString()); //kullanıcının adını al
                activity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() { //listedekilerin uzunluğu, sayısı
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        LinearLayout userID;


        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.userName);
            userID = itemView.findViewById(R.id.userID);
        }
    }
}
