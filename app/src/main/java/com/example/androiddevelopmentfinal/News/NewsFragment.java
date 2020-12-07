package com.example.androiddevelopmentfinal.News;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddevelopment.Message.Msg;
import com.example.androiddevelopment.Message.MsgAdapter;
import com.example.androiddevelopment.R;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private List<Msg> msgLists= new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.msg_fragment, container, false);

        return view;
    }
}
