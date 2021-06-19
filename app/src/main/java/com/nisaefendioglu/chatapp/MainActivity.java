package com.nisaefendioglu.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<String> list;
    String username;
    RecyclerView userList;
    UserAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getExtras().getString("UserName"); //kullanıcı adını alır.
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        list = new ArrayList<>();
        userList = (RecyclerView)findViewById(R.id.userList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2); //1 satırda 2 kullanıcı
        userList.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(MainActivity.this,list,MainActivity.this, username);
        userList.setAdapter(userAdapter);
        list();
    }

    public void list (){
        reference.child("Users").addChildEventListener(new ChildEventListener() { //tüm verileri gösterir.
            @Override
            public void onChildAdded(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
                //Log.i("Kullanıcı :" , snapshot.getKey()); //kullanıcıları log kısmında görüntüler.
                if(!snapshot.getKey().equals(username)){ //kullanıcı listesinde kendisi olmaması.
                    list.add(snapshot.getKey());
                    userAdapter.notifyDataSetChanged(); //verilerin güncellenmesini sağlar.
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}