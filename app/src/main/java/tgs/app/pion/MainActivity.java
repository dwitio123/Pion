package tgs.app.pion;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import tgs.app.pion.GuruPiket.GuruPiketActivity;
import tgs.app.pion.Sekretaris.SekretarisActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout linear_sekretaris;
    LinearLayout linear_guru_piket;
    LinearLayout linear_absen_guru;

    TextView text_absen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linear_absen_guru = findViewById(R.id.linear_absen_guru);
        linear_sekretaris = findViewById(R.id.linear_sekretaris);
        linear_guru_piket = findViewById(R.id.linear_guru_piket);
        text_absen = findViewById(R.id.text_absen);
        Typeface customfont = Typeface.createFromAsset(getAssets(), "font/Hippo.otf");
        text_absen.setTypeface(customfont);

        linear_guru_piket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GuruPiketActivity.class));
            }
        });
        linear_absen_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginKepalaSekolahActivity.class));
            }
        });

        linear_sekretaris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SekretarisActivity.class));
            }
        });
    }
}
