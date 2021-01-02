package com.example.androiddevelopmentfinal.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddevelopmentfinal.FragmentTabActivity;
import com.example.androiddevelopmentfinal.Person.BaseActivity;
import com.example.androiddevelopmentfinal.R;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class login extends BaseActivity implements View.OnClickListener {

    private EditText accountEdit;
    private EditText passwordEdit;

    String account;//用户账户
    String password; //用户密码
    View login_button;//登录按钮
    View find_password;//找回密码
    View phone_login;//手机号登录
    View new_regist;//新用户注册
    private CheckBox rememberPass;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private ImageButton imgbtn;
    private EditText UserNum;
    private List<String> names;
    private DropdownAdapter adapter;
    private PopupWindow pop;
    private View namelayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViews();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password",false);

        if(isRemember){
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }

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

        /*数据库操作*/
        Connector.getDatabase();

        adapter = new DropdownAdapter(login.this, getData());
        listView = new ListView(login.this);
        listView.setAdapter(adapter);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                click_phone_imgbn();
            }
        });
    }

    private void click_phone_imgbn(){
        if (null == pop) {
            //创建一个在输入框下方的popup
            pop = new PopupWindow(listView, namelayout.getWidth(),
                    names.size() * namelayout.getHeight());
            pop.showAsDropDown(namelayout);
        }
        else {
            if (pop.isShowing()) {
                pop.dismiss();
            }
            else {
                pop.showAsDropDown(UserNum);
            }
        }
    }

    private void click_Login_button () {
        accountEdit = (EditText) findViewById(R.id.UserNumber);
        passwordEdit = (EditText) findViewById(R.id.Password);
        account = accountEdit.getText().toString();
        password = passwordEdit.getText().toString();

        if (login_check(account, password)) {
            Toast.makeText(login.this, "用户名："+account+
                    "\n密码："+password+"!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(login.this, FragmentTabActivity.class);
            intent.putExtra("account",account);
            startActivity(intent);
            finish();
        } else
            /*Toast.makeText(login.this, "用户名或者密码错误！", Toast.LENGTH_SHORT).show();*/
            Toast.makeText(login.this, "用户名："+account+
                    "\n密码："+password+"!", Toast.LENGTH_SHORT).show();
    }

    private boolean login_check (String account, String password) {
        List<UserInfo> users = DataSupport.findAll(UserInfo.class);
        boolean flag = false;
        for (UserInfo user : users) {
            if (user.getAccount().equals(account) && user.getPassword().equals(password)) {
                flag = true;
                break;
            }
        }

        if(flag){
            editor = pref.edit();
            if (rememberPass.isChecked()) {
                editor.putBoolean("remember_password", true);
                editor.putString("account", account);
                editor.putString("password", password);
            } else {
                editor.clear();
            }
            editor.apply();
            return true;
        }else
            return false;
    }

    private void click_Find_password() {

    }

    private void click_phone_login(){

    }

    private void click_new_regist(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.user_regist, null);
        final EditText edit_account = (EditText) textEntryView.findViewById(R.id.regist_account);
        final EditText edit_pass = (EditText)textEntryView.findViewById(R.id.regist_pass);
        AlertDialog.Builder ad1 = new AlertDialog.Builder(login.this);
        ad1.setTitle("请输入您的账号和密码：");
        ad1.setView(textEntryView);
        ad1.setPositiveButton("注册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                String account = "";
                String pass = "";
                account = edit_account.getText().toString();
                pass = edit_pass.getText().toString();
                if(!(account.equals("")&&pass.equals(""))  ){
                    UserInfo user = new UserInfo();
                    user.setAccount(account);
                    user.setPassword(pass);
                    user.save();
                    Toast.makeText(login.this, "您已注册成功！",
                            Toast.LENGTH_SHORT).show();
                }else{Toast.makeText(login.this, "您输入的信息为空哦！",
                        Toast.LENGTH_SHORT).show();}
            }
        });
        ad1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        ad1.show();// 显示对话框
    }

    @Override
    public void onClick(View v) {
        /*Log.d(TAG, "onClick: "+v.getTag().toString());*/
    }

    /**用于显示popupWindow内容的适配器*/
    class DropdownAdapter extends BaseAdapter {

        public DropdownAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ClickableViewAccessibility")
        public View getView(final int position, View convertView, ViewGroup parent) {
            layoutInflater  = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            close = (ImageButton)convertView.findViewById(R.id.close_row);
            content = (TextView)convertView.findViewById(R.id.text_row);
            final String editContent = list.get(position);
            content.setText(list.get(position).toString());
            content.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    UserNum.setText(editContent);
                    passwordEdit.setText("");
                    pop.dismiss();
                    return false;
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    names.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        private Context context;
        private LayoutInflater layoutInflater;
        private List<String> list;
        private TextView content;
        private ImageButton close;
    }

    public List<String> getData() {
        List<UserInfo> users = DataSupport.findAll(UserInfo.class);
        names = new ArrayList<String>();
        for(UserInfo user : users){
            names.add(user.getAccount());
        }

        return names;
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*pop.dismiss();*/
    }

    public void getViews() {
        login_button = findViewById(R.id.login_button);
        find_password = findViewById(R.id.find_password);
        phone_login = findViewById(R.id.phone_login);
        new_regist = findViewById(R.id.new_regist);
        accountEdit = (EditText) findViewById(R.id.UserNumber);
        passwordEdit = (EditText) findViewById(R.id.Password);
        namelayout = findViewById(R.id.namelayout);
        imgbtn = (ImageButton)findViewById(R.id.imgbtn);
        UserNum = (EditText)findViewById(R.id.UserNumber);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
    }

}