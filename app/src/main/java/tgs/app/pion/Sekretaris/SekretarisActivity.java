package tgs.app.pion.Sekretaris;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import tgs.app.pion.Guru.LoginGuruActivity;
import tgs.app.pion.GuruPiket.GuruPiketActivity;
import tgs.app.pion.R;
import tgs.app.pion.Tugas.LoginTugasActivity;

public class SekretarisActivity extends AppCompatActivity {

    TextView text_sekre;
    LinearLayout linear_guru, linear_siswa, linear_tugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_atau_siswa);

        linear_guru = findViewById(R.id.linear_guru);
        linear_siswa = findViewById(R.id.linear_siswa);
        linear_tugas = findViewById(R.id.linear_tugas);
        text_sekre = findViewById(R.id.text_sekre);

        Typeface customfont = Typeface.createFromAsset(getAssets(), "font/Hippo.otf");
        text_sekre.setTypeface(customfont);

        linear_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SekretarisActivity.this, LoginGuruActivity.class));
            }
        });

        linear_siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SekretarisActivity.this, LoginSekretarisActivity.class));
            }
        });
        linear_tugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SekretarisActivity.this, LoginTugasActivity.class));
            }
        });

        getSupportActionBar().setTitle("Sekretaris");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
