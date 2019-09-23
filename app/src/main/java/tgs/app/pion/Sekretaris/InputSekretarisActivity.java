package tgs.app.pion.Sekretaris;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import tgs.app.pion.R;
import tgs.app.pion.model.Response;
import tgs.app.pion.model.Sekretaris;
import tgs.app.pion.retrofit.Api;
import tgs.app.pion.retrofit.ApiInterface;

public class InputSekretarisActivity extends AppCompatActivity {

    RadioGroup radio_status;
    RadioButton radio_sakit, radio_izin, radio_alpha;
    Button btn_kirim, btn_data;
    Spinner spin_nis, spin_nama;

    String status, jenisk, kelas, jurusan, nis, nama;
    ProgressDialog progressDialog;

    Bundle bundle;

    List<Sekretaris.Siswa> detailSiswa;
    ArrayList<String> listNis = new ArrayList<>();
    ArrayList<String> listNama = new ArrayList<>();
    ArrayList<String> listJenisKelamin = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sekretaris);

        bundle = getIntent().getExtras();

        status = "";
        kelas = bundle.getString("kelas");
        jurusan = bundle.getString("jurusan");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        spin_nis = findViewById(R.id.spin_nis);
        spin_nama = findViewById(R.id.spin_nama);
        radio_sakit = findViewById(R.id.radio_sakit);
        radio_izin = findViewById(R.id.radio_izin);
        radio_alpha = findViewById(R.id.radio_alpha);
        radio_status = findViewById(R.id.radio_status);
        btn_kirim = findViewById(R.id.btn_kirim);
        btn_data = findViewById(R.id.btn_data);

        AmbilData();
        SpinNIS();
        SpinNama();

        radio_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_sakit : status = "Sakit";
                        break;
                    case R.id.radio_izin : status = "Izin";
                        break;
                    case R.id.radio_alpha : status = "Alpha";
                        break;
                }
            }
        });

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KirimData();
            }
        });

        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputSekretarisActivity.this, DataSekretarisActivity.class);
                intent.putExtra("kelas", kelas);
                intent.putExtra("jurusan", jurusan);
                startActivity(intent);
            }
        });

        listJenisKelamin.add("Jenis kelamin");

        getSupportActionBar().setTitle("Siswa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    private void AmbilData(){
        progressDialog.show();
        ApiInterface apiInterface1 = Api.getUrl().create(ApiInterface.class);
        Call<Sekretaris> call = apiInterface1.getSekretarisCall(bundle.getString("kelas"), bundle.getString("jurusan"));
        call.enqueue(new Callback<Sekretaris>() {
            @Override
            public void onResponse(Call<Sekretaris> call, retrofit2.Response<Sekretaris> response) {
                detailSiswa = response.body().getReadSiswa();
                for (int i = 0; i < detailSiswa.size(); i++){
                    listNis.add(detailSiswa.get(i).getNIS());
                    listNama.add(detailSiswa.get(i).getNama_lengkap());
                    listJenisKelamin.add(detailSiswa.get(i).getJenis_kelamin());
                    Log.e("listnis", detailSiswa.get(i).getNIS());
                    progressDialog.hide();
                }
            }

            @Override
            public void onFailure(Call<Sekretaris> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(InputSekretarisActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SpinNIS(){
        listNis.add("NIS");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listNis);
        spin_nis.setAdapter(adapter);
        spin_nis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                nis = detailSiswa.get(position).getNIS();
                Log.e("nis", listNis.get(position));
                if (position == 0){
                    Toast.makeText(InputSekretarisActivity.this, "NIS harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    nis = listNis.get(position);
                    spin_nama.setSelection(parent.getSelectedItemPosition());
                    jenisk = listJenisKelamin.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SpinNama(){
        listNama.add("Nama");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listNama);
        spin_nama.setAdapter(adapter);
        spin_nama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("nama", listNama.get(position));
                if (position == 0){
                    Toast.makeText(InputSekretarisActivity.this, "Nama harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    nama = listNama.get(position);
                    spin_nis.setSelection(parent.getSelectedItemPosition());
                    jenisk = listJenisKelamin.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void KirimData(){
        progressDialog.show();
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Response> call = apiInterface.insertData(nis, nama, jenisk, kelas, jurusan, status);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body().getResponse().equals("success")) {
                    Toast.makeText(InputSekretarisActivity.this, "Data Sudah Dimasukkan", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                } else {
                    Toast.makeText(InputSekretarisActivity.this, "Data Dirubah", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(InputSekretarisActivity.this, "Data gagal dimasukkan", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
