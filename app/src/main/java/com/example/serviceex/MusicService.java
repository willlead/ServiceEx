package com.example.serviceex;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class MusicService extends Service {
    MediaPlayer mp;

    public MusicService() {
    }

    @Override
    public void onDestroy() {
        // stopservice()가 호출되면 onDestroy()가 호출된다.
        mp.stop();
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp = MediaPlayer.create(this, R.raw.da);
        mp.setLooping(true);
        mp.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        Intent notiIntent = new Intent(this, MainActivity.class);

        //노티 인텐트는 pendingintent 이다.
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notiIntent, 0);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification); //노티 액티비티는 리모트 뷰로 생성한다.
        NotificationCompat.Builder builder;

        if (Build.VERSION.SDK_INT >= 26) {
            String channelID = "MusicServiceChannel";
            NotificationChannel channel = new NotificationChannel(channelID,"음악 채널",
                    NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, channelID);
        } else {
            builder = new NotificationCompat.Builder(this);
        }
        return super.startForegroundService(service);
    }
}
