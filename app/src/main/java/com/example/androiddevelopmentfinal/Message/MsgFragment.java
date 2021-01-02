package com.example.androiddevelopmentfinal.Message;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddevelopmentfinal.R;
import com.example.androiddevelopmentfinal.login.*;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

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
                    replyMsgs(content);
                    adapter.notifyItemInserted(msgLists.size() - 1);
                    msgRecyclerView.scrollToPosition(msgLists.size() - 1);
                    inputText.setText("");
                }
            }
        });

        return view;
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
