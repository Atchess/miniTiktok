package com.example.minitiktok.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.L;
import com.example.minitiktok.R;
import com.example.minitiktok.base.Data;
import com.example.minitiktok.dataBase.UserDao;
import com.example.minitiktok.dataBase.UserDataBase;
import com.example.minitiktok.dataBase.UserEntity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    Button login, register;
    EditText email;
    EditText password;
    Data app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        app = (Data)getApplication();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable address = email.getText();
                Editable passwd = password.getText();
                app.setA(address.toString());
                new Thread() {
                    @Override
                    public void run() {
                        UserDao dao = UserDataBase.inst(LoginActivity.this).userDao();
                        String rP = dao.select(address.toString());
                        if (rP != null &&rP.equals(passwd.toString())){
                            Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                            intent.putExtra("login","login");
                            startActivity(intent);
                            finish();
                        }
                    }
                }.start();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable address = email.getText();
                Editable passwd = password.getText();
                app.setA(address.toString());
                new Thread() {
                    @Override
                    public void run() {
                        UserDao dao = UserDataBase.inst(LoginActivity.this).userDao();
                        dao.addTodo(new UserEntity("newUser",address.toString(),passwd.toString()));
                        Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                        intent.putExtra("login","login");
                        startActivity(intent);
                        finish();
                    }
                }.start();
            }
        });
    }
}