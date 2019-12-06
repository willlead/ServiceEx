package com.example.notificationex;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    final int SDK_VER_OREO = 26;

    Button btnNoti;
    NotificationCompat.Builder mBuilder;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNoti = (Button) findViewById(R.id.btnNoti);

        // 노티를 사용하겠습니다. 정의
        final NotificationManager notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //오레오 이상 버전은 채널을 설정해줘야 한다.
        if (Build.VERSION.SDK_INT >= SDK_VER_OREO) {
            NotificationChannel channel = new NotificationChannel(
                    "noti", "notiChannel", NotificationManager.IMPORTANCE_DEFAULT);
            notiManager.createNotificationChannel(channel);
            channel.setDescription("채널");
            channel.enableLights(true); // 밤이라면 불이 약간 들어온다.
            channel.setLightColor(Color.GREEN); // 불 색을 정의한다.
            channel.enableVibration(true); // 진동을 울린다.
            channel.setVibrationPattern(new long[]{100, 200, 100, 200}); //진동 패턴 정의의
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        }

        //노티에서 부르는 인텐트
        //노출을 호출한 액티비티, 리퀘스트 코드,
        final PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0,
                new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= SDK_VER_OREO) {
                    mBuilder = new NotificationCompat.Builder(MainActivity.this, "noti");
                } else {
                    mBuilder = new NotificationCompat.Builder(MainActivity.this);
                }
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.end);
                mBuilder.setSmallIcon(R.drawable.end) // 스테터스바에 아이콘
                        .setContentTitle("공지사항")
                        .setContentText("내일은 노티활용편입니다.")
                        .setLargeIcon(largeIcon) //
                        .setDefaults(Notification.DEFAULT_VIBRATE) //DEFAULT_SOUND, DEFAULT_VIBRATE, DEFAULT_LIGHTS
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 중요도
                        .setContentIntent(pIntent); //노티를 눌렀을때 실행할 액티비티 *중요*
                notiManager.notify(0, mBuilder.build());

            }
        });
    }
}
