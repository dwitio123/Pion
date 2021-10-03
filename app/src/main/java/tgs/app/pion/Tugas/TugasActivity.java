package tgs.app.pion.Tugas;

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
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tgs.app.pion.MainActivity;
import tgs.app.pion.R;
import tgs.app.pion.URLPrint;
import tgs.app.pion.model.Tugas;
import tgs.app.pion.retrofit.Api;
import tgs.app.pion.retrofit.ApiInterface;

public class TugasActivity extends AppCompatActivity {

    SwipeRefreshLayout swipe_refresh_tugas;
    RecyclerView rc_tugas;

    List<Tugas.TugasGuru> tugasGuru;

    TextView text_empty;

    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tugas);

        myCalendar = Calendar.getInstance();

        rc_tugas = findViewById(R.id.rc_tugas);
        text_empty = findViewById(R.id.text_empty);
        swipe_refresh_tugas = findViewById(R.id.swipe_refresh_tugas);
        rc_tugas.setLayoutManager(new LinearLayoutManager(this));

        tugasGuru = new ArrayList<>();
        rc_tugas.setAdapter(new TugasAdapter(tugasGuru));

        swipe_refresh_tugas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataTugas();
            }
        });

        getSupportActionBar().setTitle("Tugas");
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
        super.onResume();
    }

    private void DataTugas(){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Tugas> call = apiInterface.getTugasCall();
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
            DatePickerDialog datePickerDialog = new DatePickerDialog(TugasActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    swipe_refresh_tugas.setRefreshing(true);
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    String formatTanggal = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                    String tgl = sdf.format(myCalendar.getTime());
                    ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
                    Call<Tugas> call = apiInterface.getFilterTugasGuru(tgl);
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
            intent.setData(Uri.parse(URLPrint.PRESENSI_ONLINE_SISWA));
            startActivity(intent);
        }
        return true;
    }
}
