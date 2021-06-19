package com.nisaefendioglu.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText userNameEditText;
    Button login;

    //kullanıcı kayıt işlemleri
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameEditText = (EditText)findViewById(R.id.userNameEditText);
        login = (Button)findViewById(R.id.login);

        firebaseDatabase = FirebaseDatabase.getInstance(); //veritabanı ile proje arasındaki referans sağlanır.
        reference = firebaseDatabase.getReference(); //veritabanı içerisindeki write, read kısmıyla ilgili işlemleri sağlar.

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString();
                userAdd(userName);
            }
        });

    }

    public void userAdd(final String userName){
        //kullanici kayit ve giris islemleri
        reference.child("Users").child(userName).child("UserName").setValue(userName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "Kayıt işleminiz tamamlandı." , Toast.LENGTH_LONG).show(); //kayıt işleminin başarıyla tamamlanması durumunda ekrana verilecek yazı.
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class); //main sayfasına geçiş
                    intent.putExtra("UserName", userName); //kullanıcı adını main sayfasına gönderme işlemi
                    startActivity(intent); //geçişi başlatmak için.

                }

            }
        });
    }
}