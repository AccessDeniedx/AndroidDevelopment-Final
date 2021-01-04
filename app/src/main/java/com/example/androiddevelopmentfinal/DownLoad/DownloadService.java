package com.example.androiddevelopmentfinal.DownLoad;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.androiddevelopmentfinal.R;

import java.io.File;

public class DownloadService extends Service {
    private DownloadTask downloadTask;
    private String downloadUrl;
    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,
                    getNotification("Downloading...", progress)); }
        @Override
        public void onSuccess() {
            downloadTask = null;
            //下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1,
                    getNotification("Download Success", -1));
            Toast.makeText(DownloadService.this,
                    "Download Success", Toast.LENGTH_SHORT).show(); }
        @Override
        public void onFailed() {
            downloadTask = null;
            //下载失败时将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1,
                    getNotification("Download Failed", -1));
            Toast.makeText(DownloadService.this,
                    "Download Failed", Toast.LENGTH_SHORT).show(); }
        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(DownloadService.this,
                    "Paused", Toast.LENGTH_SHORT).show(); }
        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }};
    private DownloadBinder mBinder = new DownloadBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public class DownloadBinder extends Binder {
        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadUrl = url;downloadTask = new DownloadTask(listener);
                downloadTask.execute(downloadUrl);
                startForeground(1, getNotification("Downloading...", 0));
                Toast.makeText(DownloadService.this, "Downloading...", Toast.
                        LENGTH_SHORT).show();
            } }
        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            } }
        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            } else { if (downloadUrl != null) {
                // 取消下载时需将文件删除，并将通知关闭
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory + fileName);
                if (file.exists()) {
                    file.delete();
                }getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this, "Canceled",
                        Toast.LENGTH_SHORT).show();
            } } }}
    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE); }
    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, DownloadService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, 0);
        String channelId = createNotificationChannel("my_channel_ID",
                "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder notification = new NotificationCompat.
                Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent).setContentTitle(title).setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        if (progress > 0) { notification.setContentText(progress + "%");
            notification.setProgress(100, progress, false); }
        return notification.build(); }
    private String createNotificationChannel(String channelID, String channelNAME, int level) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
            getNotificationManager().createNotificationChannel(channel);
            return channelID;
        } else {
            return null; } }
}
