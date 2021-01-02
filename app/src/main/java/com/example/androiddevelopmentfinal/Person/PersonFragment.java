package com.example.androiddevelopmentfinal.Person;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.androiddevelopmentfinal.R;
import com.leon.lib.settingview.LSettingItem;

import com.leon.lib.settingview.LSettingItem;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

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
        //Log.d("onActivityCreate:","Here is AccessDenied's test");
        /*//显式intent
        Button explicit_button = (Button) getActivity().findViewById(R.id.explicitIntent_button);
        explicit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                startActivity(intent);
            }
        });*/
        /*强制下线*/
        LSettingItem force_Offline = (LSettingItem) view.findViewById(R.id.force_Offline);
        force_Offline.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                /*Toast.makeText(getActivity(),"You clicked Add",Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent("com.example.androiddevelopmentfinal." +
                        "FORCE_OFFLINE");
                getActivity().sendBroadcast(intent);
            }

        });

        /*读取联系人*/
        LSettingItem read_contact = (LSettingItem) view.findViewById(R.id.read_contacts);
        read_contact.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {

                Intent intent = new Intent(getActivity(), Contacts_Reader.class);
                startActivity(intent);
            }

        });

        /*更换头像*/
        CircleImageView user_image = (CircleImageView) view.findViewById(R.id.user_image);
        user_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });


    }

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);

        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }
}
