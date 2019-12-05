package com.example.vibrateex;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup rGroup;
    RadioButton rdo1, rdo2, rdo3;
    TextView tvResult;
    Button btnOk;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rGroup = (RadioGroup) findViewById(R.id.rGroup);
        rdo1 = (RadioButton) findViewById(R.id.rdo1);
        rdo2 = (RadioButton) findViewById(R.id.rdo2);
        rdo3 = (RadioButton) findViewById(R.id.rdo3);
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnOk = (Button) findViewById(R.id.btnOk);
        mp = MediaPlayer.create(this, R.raw.terminate);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (rGroup.getCheckedRadioButtonId()) {
                    case R.id.rdo1:
                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        // 1초동안 진동
                        //vibrator.vibrate(1000);

                        // 0.5초동안 쉬고, 1초 진동, 0.4초 쉬고, 2초 진동
                        // repeat을 0으로 하면 무한반복 되며, 버튼을 추가로 만들어 진동 멈추게 처리해야함. //vibrate.cancel();
                        vibrator.vibrate(new long[]{500, 1000, 400, 2000}, -1);

                        tvResult.setText("틀렸습니다.");
                        break;
                    case R.id.rdo2:
                        mp.start();
                        tvResult.setText("정답입니다.\n 이유는 별들에게 물어봐");
                        break;
                    case R.id.rdo3:
                        //폰에서 가지고 있는 알람 소리를 출력한다.
                        Uri noti = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), noti);
                        ringtone.play();

                        break;
                    default:
                        Toast.makeText(MainActivity.this, "선택해줘", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
