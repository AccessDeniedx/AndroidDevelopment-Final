package com.example.androiddevelopmentfinal.Media;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.androiddevelopmentfinal.R;

import java.io.File;

public class MediaFragment extends Fragment implements View.OnClickListener{
    View view;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private VideoView videoView;
    ImageView music_play,music_pause,music_stop;
    ImageView video_play,video_pause,video_replay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.media_fragment, container, false);
        /*getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏*/
        getViews();
        music_play.setOnClickListener(this);
        music_stop.setOnClickListener(this);
        music_pause.setOnClickListener(this);
        video_replay.setOnClickListener(this);
        video_play.setOnClickListener(this);
        video_pause.setOnClickListener(this);

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.
                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            initMediaPlayer();
            initVideoPath();
        }
        return view;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.music_play:
                if(! mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                break;
            case R.id.music_pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.music_stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer();
                }
                break;
            case R.id.video_play:
                if(!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.video_pause:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.video_replay:
                if(videoView.isPlaying()){
                    videoView.resume();
                }
                break;
            default:
                break;
        }

    }

    private void initVideoPath(){
        File file = new File(Environment.getExternalStorageDirectory(),"movie.mp4");
        videoView.setVideoPath(file.getPath());
    }

    private void initMediaPlayer(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
            Log.d("Mediatest","test4");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initMediaPlayer();
                }else {
                    Toast.makeText(getActivity(),"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                break;
            default:
        }
    }

    private void getViews(){
        music_pause = (ImageView) view.findViewById(R.id.music_pause);
        music_stop = (ImageView) view.findViewById(R.id.music_stop);
        music_play = (ImageView) view.findViewById(R.id.music_play);
        video_pause = (ImageView) view.findViewById(R.id.video_pause);
        video_play = (ImageView) view.findViewById(R.id.video_play);
        video_replay = (ImageView) view.findViewById(R.id.video_replay);
        videoView = (VideoView) view.findViewById(R.id.video_view);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if(videoView != null){
            videoView.suspend();
        }
    }
}
