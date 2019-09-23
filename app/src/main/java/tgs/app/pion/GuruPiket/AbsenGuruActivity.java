package tgs.app.pion.GuruPiket;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tgs.app.pion.Tugas.TugasAdapter;
import tgs.app.pion.MainActivity;
import tgs.app.pion.R;
import tgs.app.pion.URLPrint;
import tgs.app.pion.model.Tugas;
import tgs.app.pion.retrofit.Api;
import tgs.app.pion.retrofit.ApiInterface;

public class AbsenGuruActivity extends AppCompatActivity {

    SwipeRefreshLayout swipe_refresh_tugas;
    RecyclerView rc_tugas;

    List<Tugas.TugasGuru> tugasGuru;

    Spinner spin_kelas, spin_jurusan;
    String kelas, jurusan;
    String tgl;

    Calendar myCalendar;
    TextView text_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_guru);

        kelas = "";
        jurusan = "";
        tgl = "";

        myCalendar = Calendar.getInstance();
        String formatTanggal = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
        tgl = sdf.format(myCalendar.getTime());

        rc_tugas = findViewById(R.id.rc_tugas);
        swipe_refresh_tugas = findViewById(R.id.swipe_refresh_tugas);
        text_empty = findViewById(R.id.text_empty);
        spin_kelas = findViewById(R.id.spin_kelas);
        spin_jurusan = findViewById(R.id.spin_jurusan);
        rc_tugas.setLayoutManager(new LinearLayoutManager(this));

        SpinKelas();
        SpinJurusan();

        tugasGuru = new ArrayList<>();
        rc_tugas.setAdapter(new TugasAdapter(tugasGuru));

        swipe_refresh_tugas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_tugas.setRefreshing(true);
                DataTugas();
                DataKelasJurusan();
                DataKelasSiswa();
                DataJurusanSiswa();
                spin_kelas.setSelection(0);
                spin_jurusan.setSelection(0);
            }
        });

        getSupportActionBar().setTitle("Absen Guru");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        DataTugas();
        DataKelasSiswa();
        DataJurusanSiswa();
        DataKelasJurusan();
        super.onResume();
    }

    private void SpinKelas(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kelas, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_kelas.setAdapter(adapter);
        spin_kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    DataTugas();
                    kelas = null;
                } else {
                    kelas = parent.getItemAtPosition(position).toString();
                    DataKelasSiswa();
                    DataKelasJurusan();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SpinJurusan(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jurusan, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_jurusan.setAdapter(adapter);
        spin_jurusan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    DataTugas();
                    jurusan = null;
                } else {
                    jurusan = parent.getItemAtPosition(position).toString();
                    DataJurusanSiswa();
                    DataKelasJurusan();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void DataKelasSiswa(){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Tugas> call = apiInterface.getTugasKelasSiswa(kelas);
        call.enqueue(new Callback<Tugas>() {
            @Override
            public void onResponse(Call<Tugas> call, Response<Tugas> response) {
                swipe_refresh_tugas.setRefreshing(false);
                tugasGuru = response.body().getReadTugas();
                rc_tugas.setAdapter(new TugasAdapter(tugasGuru));
                if (tugasGuru.isEmpty()){
                    rc_tugas.setVisibility(View.GONE);
                    text_empty.setVisibility(View.VISIBLE);
                } else {
                    rc_tugas.setVisibility(View.VISIBLE);
                    text_empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Tugas> call, Throwable t) {

            }
        });
    }

    private void DataJurusanSiswa(){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Tugas> call = apiInterface.getTugasJurusanSiswa(jurusan);
        call.enqueue(new Callback<Tugas>() {
            @Override
            public void onResponse(Call<Tugas> call, Response<Tugas> response) {
                swipe_refresh_tugas.setRefreshing(false);
                tugasGuru = response.body().getReadTugas();
                rc_tugas.setAdapter(new TugasAdapter(tugasGuru));
                if (tugasGuru.isEmpty()){
                    rc_tugas.setVisibility(View.GONE);
                    text_empty.setVisibility(View.VISIBLE);
                } else {
                    rc_tugas.setVisibility(View.VISIBLE);
                    text_empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Tugas> call, Throwable t) {

            }
        });
    }

    private void DataKelasJurusan(){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Tugas> call = apiInterface.getKelasJurusanTugas(kelas, jurusan);
        call.enqueue(new Callback<Tugas>() {
            @Override
            public void onResponse(Call<Tugas> call, Response<Tugas> response) {
                swipe_refresh_tugas.setRefreshing(false);
                tugasGuru = response.body().getReadTugas();
                rc_tugas.setAdapter(new TugasAdapter(tugasGuru));
                if (tugasGuru.isEmpty()){
                    rc_tugas.setVisibility(View.GONE);
                    text_empty.setVisibility(View.VISIBLE);
                } else {
                    rc_tugas.setVisibility(View.VISIBLE);
                    text_empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Tugas> call, Throwable t) {

            }
        });
    }

    private void DataTugas(){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Tugas> call = apiInterface.getAllTugas();
        call.enqueue(new Callback<Tugas>() {
            @Override
            public void onResponse(Call<Tugas> call, Response<Tugas> response) {
                swipe_refresh_tugas.setRefreshing(false);
                tugasGuru = response.body().getReadTugas();
                rc_tugas.setAdapter(new TugasAdapter(tugasGuru));
                if (tugasGuru.isEmpty()){
                    rc_tugas.setVisibility(View.GONE);
                    text_empty.setVisibility(View.VISIBLE);
                } else {
                    rc_tugas.setVisibility(View.VISIBLE);
                    text_empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Tugas> call, Throwable t) {
                swipe_refresh_tugas.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_filter){
            DatePickerDialog datePickerDialog = new DatePickerDialog(AbsenGuruActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    swipe_refresh_tugas.setRefreshing(true);
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    String formatTanggal = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                    tgl = sdf.format(myCalendar.getTime());
                    ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
                    Call<Tugas> call = apiInterface.getFilterTugas(kelas, jurusan, tgl);
                    call.enqueue(new Callback<Tugas>() {
                        @Override
                        public void onResponse(Call<Tugas> call, Response<Tugas> response) {
                            swipe_refresh_tugas.setRefreshing(false);
                            tugasGuru = response.body().getReadTugas();
                            rc_tugas.setAdapter(new TugasAdapter(tugasGuru));
                            if (tugasGuru.isEmpty()){
                                rc_tugas.setVisibility(View.GONE);
                                text_empty.setVisibility(View.VISIBLE);
                            } else {
                                rc_tugas.setVisibility(View.VISIBLE);
                                text_empty.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Tugas> call, Throwable t) {

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
