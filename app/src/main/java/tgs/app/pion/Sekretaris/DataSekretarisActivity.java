package tgs.app.pion.Sekretaris;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tgs.app.pion.AbsenSiswaAdapter;
import tgs.app.pion.MainActivity;
import tgs.app.pion.R;
import tgs.app.pion.URLPrint;
import tgs.app.pion.model.AbsenSiswa;
import tgs.app.pion.retrofit.Api;
import tgs.app.pion.retrofit.ApiInterface;

public class DataSekretarisActivity extends AppCompatActivity {

    SwipeRefreshLayout swipe_refresh;
    RecyclerView recyclerView;
    TextView text_empty;

    List<AbsenSiswa.Siswa> detailSiswa;

    int i;

    Bundle bundle;
    String kelas, jurusan;

    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sekretaris);

        myCalendar = Calendar.getInstance();

        bundle = getIntent().getExtras();

        kelas = bundle.getString("kelas");
        jurusan = bundle.getString("jurusan");

        text_empty = findViewById(R.id.text_empty);
        swipe_refresh = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.rc_data);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        detailSiswa = new ArrayList<>();
        recyclerView.setAdapter(new AbsenSiswaAdapter(detailSiswa));
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("siswa", detailSiswa.get(i).getId_absen_siswa());
            }
        });

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh.setRefreshing(true);
                DataKelasJurusan();
            }
        });

        getSupportActionBar().setTitle("Data Absen Siswa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        DataKelasJurusan();
        super.onResume();
    }

    private void DataKelasJurusan(){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<AbsenSiswa> call = apiInterface.getAbsenSiswa(kelas, jurusan);
        call.enqueue(new Callback<AbsenSiswa>() {
            @Override
            public void onResponse(Call<AbsenSiswa> call, Response<AbsenSiswa> response) {
                swipe_refresh.setRefreshing(false);
                detailSiswa = response.body().getReadAbsenSiswa();
                recyclerView.setAdapter(new AbsenSiswaAdapter(detailSiswa));
                if (detailSiswa.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    text_empty.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    text_empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AbsenSiswa> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.optionmenu, menu);
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_filter){
            DatePickerDialog datePickerDialog = new DatePickerDialog(DataSekretarisActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    swipe_refresh.setRefreshing(true);
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    String formatTanggal = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                    String tgl = sdf.format(myCalendar.getTime());
                    ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
                    Call<AbsenSiswa> call = apiInterface.getFilterSiswa(kelas, jurusan, tgl);
                    call.enqueue(new Callback<AbsenSiswa>() {
                        @Override
                        public void onResponse(Call<AbsenSiswa> call, Response<AbsenSiswa> response) {
                            swipe_refresh.setRefreshing(false);
                            detailSiswa = response.body().getReadAbsenSiswa();
                            Log.e("siswa", String.valueOf(detailSiswa.size()));
                            recyclerView.setAdapter(new AbsenSiswaAdapter(detailSiswa));
                            if (detailSiswa.isEmpty()){
                                recyclerView.setVisibility(View.GONE);
                                text_empty.setVisibility(View.VISIBLE);
                            } else {
                                recyclerView.setVisibility(View.VISIBLE);
                                text_empty.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<AbsenSiswa> call, Throwable t) {

                        }
                    });
                }
            }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        } else if (item.getItemId() == android.R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.menu_print){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(URLPrint.URL_PRINT));
            startActivity(intent);
        }
        return true;
    }
}
