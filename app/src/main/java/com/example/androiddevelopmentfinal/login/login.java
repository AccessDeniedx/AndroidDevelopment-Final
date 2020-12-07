package com.example.androiddevelopmentfinal.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androiddevelopmentfinal.FragmentTabActivity;
import com.example.androiddevelopmentfinal.R;

public class login extends AppCompatActivity implements View.OnClickListener {

    EditText UserNumberedit;
    EditText passwordedit;
    //用户账户
    String username;
    //用户密码
    String password;
    //登录按钮
    View login_button;
    //找回密码
    View find_password;
    //手机号登录
    View phone_login;
    //新用户注册
    View new_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserNumberedit = findViewById(R.id.UserNumber);
        passwordedit = findViewById(R.id.Password);

        login_button = findViewById(R.id.login_button);
        find_password = findViewById(R.id.find_password);
        phone_login = findViewById(R.id.phone_login);
        new_regist = findViewById(R.id.new_regist);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_Login_button();
            }
        });
        find_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_Find_password();
            }
        });
        phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_phone_login();
            }
        });
        new_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_new_regist();
            }
        });
    }

    private void click_Login_button () {
        if (login_check(username, password)) {
            Intent intent = new Intent(login.this, FragmentTabActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(login.this, "用户名或者密码错误！", Toast.LENGTH_SHORT).show();
    }
    private boolean login_check (String username, String password){
        username = UserNumberedit.getText().toString();
        password = passwordedit.getText().toString();
        if (username.equals("admin") && password.equals("123456")) {
            return true;
        } else
            return false;
    }

    private void click_Find_password() {

    }

    private void click_phone_login(){

    }

    private void click_new_regist(){

    }

    @Override
    public void onClick(View v) {
        /*Log.d(TAG, "onClick: "+v.getTag().toString());*/
    }
}