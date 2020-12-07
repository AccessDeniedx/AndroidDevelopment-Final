package com.example.androiddevelopmentfinal.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androiddevelopmentfinal.R;

public class login extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText UserNumberedit = findViewById(R.id.UserNumber);
        EditText passwordedit = findViewById(R.id.Password);
        //用户账户
        String usernumber = UserNumberedit.getText().toString();
        //用户密码
        String password = passwordedit.getText().toString();
        //登录按钮
        View login_button = findViewById(R.id.login_button);
        //忘记密码
        View find_password = findViewById(R.id.find_password);
        //手机号登录
        View phone_login = findViewById(R.id.phone_login);
        //新用户注册
        View new_regist = findViewById(R.id.new_regist);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:

        }

    }
}
