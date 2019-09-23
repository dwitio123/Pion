package tgs.app.pion.Guru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import tgs.app.pion.R;
import tgs.app.pion.Tugas.TugasActivity;
import tgs.app.pion.model.DataGuru;
import tgs.app.pion.model.Response;
import tgs.app.pion.retrofit.Api;
import tgs.app.pion.retrofit.ApiInterface;

public class GuruActivity extends AppCompatActivity {

    TextInputLayout edit_alasan_tidak_hadir, edit_tugas;
    Spinner spin_kelas, spin_jurusan;
    Button btn_kirim, btn_data;
    AutoCompleteTextView auto_nama_guru;

    String status, jenisk, kelas, jurusan;
    ProgressDialog progressDialog;

    List<DataGuru.Guru> detailGuru;
    ArrayList<String> listGuru = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);

        status = "";
        jenisk = "";
        kelas = "";
        jurusan = "";

        progressDialog = new ProgressDialog(this);

        edit_tugas = findViewById(R.id.edit_tugas);
        edit_alasan_tidak_hadir = findViewById(R.id.edit_alasan_tidak_hadir);
        auto_nama_guru = findViewById(R.id.auto_nama_guru);
        spin_kelas = findViewById(R.id.spin_kelas);
        spin_jurusan = findViewById(R.id.spin_jurusan);
        btn_kirim = findViewById(R.id.btn_kirim);
        btn_data = findViewById(R.id.btn_data);

        AmbilData();
        SpinKelas();
        SpinJurusan();
        AmbilGuru();

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KirimData();
            }
        });
        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruActivity.this, TugasActivity.class));
            }
        });

        getSupportActionBar().setTitle("Guru");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void AmbilData(){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<DataGuru> call = apiInterface.getDataGuruCall();
        call.enqueue(new Callback<DataGuru>() {
            @Override
            public void onResponse(Call<DataGuru> call, retrofit2.Response<DataGuru> response) {
                detailGuru = response.body().getReadDataGuru();
                for (int i=0; i < detailGuru.size(); i++){
                    listGuru.add(detailGuru.get(i).getNama_guru());
                }
            }

            @Override
            public void onFailure(Call<DataGuru> call, Throwable t) {

            }
        });
    }

    private void AmbilGuru(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listGuru);
        auto_nama_guru.setAdapter(adapter);
    }

    private void SpinKelas(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kelas_guru,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_kelas.setAdapter(adapter);
        spin_kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kelas = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(GuruActivity.this, "Isi data dengan benar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SpinJurusan(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jurusan_guru,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_jurusan.setAdapter(adapter);
        spin_jurusan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jurusan = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(GuruActivity.this, "Isi data dengan benar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void KirimData(){
        String nama_guru = auto_nama_guru.getText().toString();
        String alasan_tidak_hadir = edit_alasan_tidak_hadir.getEditText().getText().toString();
        String tugas = edit_tugas.getEditText().getText().toString();

        if (nama_guru.isEmpty() || alasan_tidak_hadir.isEmpty()){
            Toast.makeText(this, "Isi data dengan benar", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
            Call<Response> call = apiInterface.insertGuru(nama_guru, alasan_tidak_hadir, tugas, kelas, jurusan);
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.body().getResponse().equals("success")) {
                        Toast.makeText(GuruActivity.this, "Data Sudah Dimasukkan", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        auto_nama_guru.setText("");
                        edit_alasan_tidak_hadir.getEditText().setText("");
                        edit_tugas.getEditText().setText("");
                        spin_kelas.setSelection(0);
                        spin_jurusan.setSelection(0);
                    } else {
                        Toast.makeText(GuruActivity.this, response.body().getResponse(), Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Toast.makeText(GuruActivity.this, "Data gagal dimasukkan", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
