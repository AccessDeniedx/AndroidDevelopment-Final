package com.example.androiddevelopmentfinal.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androiddevelopmentfinal.FragmentTabActivity;
import com.example.androiddevelopmentfinal.R;

public class login extends AppCompatActivity implements View.OnClickListener{

    //用户账户
    String username;
    //用户密码
    String password;
    //登录按钮
    View login_button ;
    //找回密码
    View find_password;
    //手机号登录
    View phone_login ;
    //新用户注册
    View new_regist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText UserNumberedit = findViewById(R.id.UserNumber);
        EditText passwordedit = findViewById(R.id.Password);
        username = UserNumberedit.getText().toString();
        password = passwordedit.getText().toString();
        login_button = findViewById(R.id.login_button);
        find_password = findViewById(R.id.find_password);
        phone_login = findViewById(R.id.phone_login);
        new_regist = findViewById(R.id.new_regist);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //登录按钮
            case R.id.login_button:
                if(login_check(username,password)){
                    Intent intent = new Intent(this, FragmentTabActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(this,"用户名或者密码错误！",Toast.LENGTH_SHORT).show();

            case R.id.find_password:

            case R.id.phone_login:

            case R.id.new_regist:
        }

    }

    private boolean login_check(String username,String password){
        if (username.equals("admin") && password.equals("123456")){
            return true;
        }
        else
            return false;
    }
}
