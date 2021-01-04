package com.example.androiddevelopmentfinal.Message;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddevelopmentfinal.FragmentTabActivity;
import com.example.androiddevelopmentfinal.Http.WebActivity;
import com.example.androiddevelopmentfinal.Person.PersonFragment;
import com.example.androiddevelopmentfinal.R;
import com.example.androiddevelopmentfinal.login.*;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MsgFragment extends Fragment {
    private List<Msg> msgLists= new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    String account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.msg_fragment, container, false);

        account = getArguments().getString("account");
        initMsgs();
        inputText = (EditText) view.findViewById(R.id.input_text);
        send = (Button) view.findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) view.findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgLists);
        msgRecyclerView.setAdapter(adapter);
        //发送
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String content = inputText.getText().toString();
                /*Log.d("Msgdatabase", "account:"+account);
                Log.d("Msgdatabase", "content:"+content);*/
                if(!"".equals(content)){
                    Msg msg = new Msg(content,Msg.TYPE_SENT);
                    msg.setAccount(account);
                    msg.setContent(content);
                    msg.setType(1);
                    msg.save();
                    msgLists.add(msg);
                    WebActivity webActivity=new WebActivity(msg.getContent());
                    replyMsgs(content);
                    adapter.notifyItemInserted(msgLists.size() - 1);
                    msgRecyclerView.scrollToPosition(msgLists.size() - 1);
                    inputText.setText("");
                    showNotification(account,content);
                }
            }
        });

        return view;
    }

    private void showNotification(String account,String content){
        Intent intent = new Intent(getActivity(), FragmentTabActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getActivity(),0,intent,0);
        NotificationManager manager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= 26)
        {
            //当sdk版本大于26
            String id = "channel_1";
            String description = "143";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(id, description, importance);
            manager.createNotificationChannel(channel);
            PersonFragment personFragment = new PersonFragment();
            Notification notification = new Notification.Builder(getActivity(), id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setContentTitle(account)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    /*.setStyle(new Notification.BigPictureStyle().bigPicture(
                            BitmapFactory.decodeResource(getResources(),R.drawable.apple_pic)))*/
                    .setStyle(new Notification.BigPictureStyle().bigPicture(personFragment.getPicture()))
                    .build();
            manager.notify(1, notification);
        }
        else
        {
            //当sdk版本小于26
            Notification notification = new NotificationCompat.Builder(getActivity())
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(
                            BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background)))
                    .build();
            manager.notify(1,notification);
        }
    }

    private void initMsgs(){
        List<Msg> msgs = DataSupport.findAll(Msg.class);
        for(Msg msg: msgs){
            if(msg.getAccount().equals(account)){
                Msg msg3 = new Msg(msg.getContent(), msg.getType());
                msgLists.add(msg3);
            }
        }
    }
    private void replyMsgs(String content){
        String reply;
        if(content.contains("你好")||content.contains("hello")){
            reply = "你好呀！很高兴认识你！";
        }else if(content.contains("名字")||content.contains("name")){
            reply = "我叫小羊，是不是很可爱呢？";
        }else{
            reply = "我读书少，这么深奥的问题我还不懂欸。。";
        }
        Msg msg = new Msg(reply,Msg.TYPE_RECEIVED);
        msg.setAccount(account);
        msg.setContent(reply);
        msg.setType(0);
        msg.save();
        msgLists.add(msg);
    }
}
