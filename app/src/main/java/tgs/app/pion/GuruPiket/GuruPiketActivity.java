package tgs.app.pion.GuruPiket;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import tgs.app.pion.Guru.LoginGuruActivity;
import tgs.app.pion.LoginKepalaSekolahActivity;
import tgs.app.pion.R;

public class GuruPiketActivity extends AppCompatActivity {

    TextView text_guru_piket;
    LinearLayout linear_absen_guru, linear_absen_siswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_guru_siswa);

        text_guru_piket = findViewById(R.id.text_guru_piket);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Hippo.otf");
        text_guru_piket.setTypeface(typeface);
        linear_absen_guru = findViewById(R.id.linear_absen_guru);
        linear_absen_siswa = findViewById(R.id.linear_absen_siswa);

        linear_absen_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruPiketActivity.this, LoginAbsenGuruActivity.class));
            }
        });
        linear_absen_siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruPiketActivity.this, LoginAbsenSiswaActivity.class));
            }
        });

        getSupportActionBar().setTitle("Guru Piket");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
