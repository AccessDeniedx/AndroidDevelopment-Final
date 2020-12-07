package com.example.androiddevelopmentfinal.Person;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import com.example.androiddevelopmentfinal.R;
import com.leon.lib.settingview.LSettingItem;

public class PersonFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.person_fragment, container, false);
        Log.d("FirstActivity", "Task id is " + getActivity().getTaskId());//singleinstance打印当前返回栈的id

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*//显式intent
        Button explicit_button = (Button) getActivity().findViewById(R.id.explicitIntent_button);
        explicit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                startActivity(intent);
            }
        });*/


        //对一个控件进行点击事件
        LSettingItem one =view.findViewById(R.id.item_one);

        //碎片实践 新闻
        /*Button news_button = (Button) getActivity().findViewById(R.id.news_button);
        news_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), News_activity.class);
                startActivity(intent);
            }
        });*/

    }

}
