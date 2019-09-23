package tgs.app.pion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import tgs.app.pion.R;

public class SplashActivity extends AppCompatActivity {

//    TextView text_judul;
    ProgressBar progress_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        text_judul = findViewById(R.id.text_judul);
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Hippo.otf");
//        text_judul.setTypeface(typeface);

        progress_loading = findViewById(R.id.progress_loading);
        progress_loading.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
