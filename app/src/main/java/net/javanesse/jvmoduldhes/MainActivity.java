package net.javanesse.jvmoduldhes;



import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int soundID;


    // Begin Import Timer

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 5000;

    // End Import Timer

    // Begin initialization Wakelock import
//    private PowerManager.WakeLock wakeLock;
//    private boolean isWakeLockAcquired = false;
//    private boolean isWakeLockReleased = false;
    // End initialization Wakelock import


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Begin FUlLSCREEN
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();

        // End FUlLSCREEN




        MaterialButton buttonPlay = findViewById(R.id.jvButton);

        // Inisialisasi sound pool
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
        SoundPool.Builder builder = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(attrBuilder.build());
        soundPool = builder.build();

        // Begin Muat suara ke dalam sound pool
        soundID = soundPool.load(this, R.raw.nyco_dhes, 1);
        // End Muat suara ke dalam sound pool

//        // Begin Setelah suara dimuat, set listener pada tombol Play Sound
//        buttonPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                soundPool.play(soundID, 1, 1, 1, 0, 1);
//            }
//        });
//
//        // Klik menggunakan Mouse
//        buttonPlay.performClick();
        // End Setelah suara dimuat, set listener pada tombol Play Sound


        // Begin Touch Down Trigger Button
        buttonPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Kode yang ingin dijalankan saat tombol ditekan
                        soundPool.play(soundID, 1, 1, 1, 0, 1);
                        Toast.makeText(MainActivity.this, "Down", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        // Kode yang ingin dijalankan saat tombol dilepas
                        break;
                }
                return false;
            }
        });
        // END Touch Down Trigger Button
    }

    // Begin Timer
    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);

                // soundPool.play(soundID, 1, 1, 1, 0, 1);
                Toast.makeText(MainActivity.this, "CheckPoint Releasing Memory",
                        Toast.LENGTH_SHORT).show();
            }
        }, delay);
        super.onResume();
    }

    // End Timer

    // Begin onDestroy

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
        super.onDestroy();
        soundPool.release(); // Jangan lupa untuk membebaskan sound pool setelah selesai menggunakan aplikasi
    }

    // End onDestroy
}