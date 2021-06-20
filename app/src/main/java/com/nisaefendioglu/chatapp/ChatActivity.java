package com.nisaefendioglu.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    String userName,otherName;
    TextView chatUserName;
    ImageView backImage , sendImage;
    EditText chatEditText;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView chatRecyclerView;

    MessageAdapter messageAdapter;
    List<MessageModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



        userName = getIntent().getExtras().getString("username");
        otherName = getIntent().getExtras().getString("othername");

        chatUserName = (TextView)findViewById(R.id.chatUserName);
        backImage = (ImageView)findViewById(R.id.backImage);
        sendImage = (ImageView)findViewById(R.id.sendImage);
        chatEditText = (EditText)findViewById(R.id.chatEditText);
        chatUserName.setText(otherName); //mesajlaşılan user'ın name'i

        list = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                intent.putExtra("UserName", userName);
                startActivity(intent);
            }
        });

        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatEditText.getText().toString();
                chatEditText.setText("");
                sendMessage(message);
            }
        });
            loadMessage();

        chatRecyclerView = (RecyclerView)findViewById(R.id.chatRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChatActivity.this, 1);
        chatRecyclerView.setLayoutManager(layoutManager);
        messageAdapter = new MessageAdapter(ChatActivity.this, list, ChatActivity.this,userName);
        chatRecyclerView.setAdapter(messageAdapter);

    }

    public void sendMessage(String text){
        final String key = reference.child("Messages").child(userName).child(otherName).push().getKey();
        final Map messageMap = new HashMap();
        messageMap.put("text" , text);
        messageMap.put("from" , userName);
        reference.child("Messages").child(userName).child(otherName).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    reference.child("Messages").child(otherName).child(userName).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }
        });

    }

    public void loadMessage(){
        reference.child("Messages").child(userName).child(otherName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
                MessageModel messageModel = snapshot.getValue(MessageModel.class);
                list.add(messageModel); //gelen mesajları listele
                messageAdapter.notifyDataSetChanged(); //güncelleme
                chatRecyclerView.scrollToPosition(list.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}