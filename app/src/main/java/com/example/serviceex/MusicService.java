package com.example.serviceex;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
    MediaPlayer mp;
    public MusicService() {
    }

    @Override
    public void onDestroy() {
        mp.stop();
        super.onDestroy(); // stopservice()가 호출되면 onDestroy()가 호출된다.

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
}
