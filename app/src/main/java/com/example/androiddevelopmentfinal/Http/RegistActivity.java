package com.example.androiddevelopmentfinal.Http;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.androiddevelopmentfinal.R;
import com.example.androiddevelopmentfinal.login.UserInfo;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegistActivity extends AppCompatActivity {
    String registerAddress="http://10.0.2.2:8080/Androidservice/RegisterServlet";
    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name,et_psw,et_psw_again,school;
    //用户名，密码，再次输入的密码的控件的获取值
    private String userName,psw,pswAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }


    private void init() {

        //从activity_register.xml 页面中获取对应的UI控件
        Button btn_register = (Button) findViewById(R.id.btn_register);
        et_user_name= (EditText) findViewById(R.id.et_user_name);
        et_psw= (EditText) findViewById(R.id.et_psw);
        et_psw_again= (EditText) findViewById(R.id.et_psw_again);
        //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //获取输入在相应控件中的字符串
                //getEditString();
                //判断输入框内容
                userName=et_user_name.getText().toString();
                // Log.d("123",userName);
                psw=et_psw.getText().toString();
                Log.d("123","456");
                pswAgain=et_psw_again.getText().toString();

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegistActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegistActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(pswAgain)) {
                    Toast.makeText(RegistActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                } else if(!psw.equals(pswAgain)){
                    Toast.makeText(RegistActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                    /**
                     *从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
                     */
                }else if(isExistUserName(userName,pswAgain)){
                    Toast.makeText(RegistActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                    UserInfo loginPeople=new UserInfo();
                    loginPeople.setAccount(userName);
                    loginPeople.setPassword(pswAgain);
                    loginPeople.save();
                    RegistActivity.this.finish();
                }
            }
        });
    }


    private boolean isExistUserName(String userName,String psw){
        boolean has_userName=false;
        List<UserInfo> loginActivities= DataSupport.where("name = ?",userName).find(UserInfo.class);
        if(loginActivities != null && loginActivities.size() > 0)
        {
            has_userName=true;
        }

        registerWithOkHttp(registerAddress,userName,psw);

        return has_userName;
    }

    public void registerWithOkHttp(String address,String account,String password){
        HttpUtil.registerWithOkHttp(address, account, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseData.equals("true")){
                            Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegistActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
