package tgs.app.pion.Guru;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import tgs.app.pion.R;
import tgs.app.pion.model.Response;
import tgs.app.pion.retrofit.Api;
import tgs.app.pion.retrofit.ApiInterface;

public class EditGuruActivity extends AppCompatActivity {

    TextInputLayout edit_nama_guru, edit_alasan_tidak_hadir, edit_tugas;
    Spinner spin_kelas, spin_jurusan;
    Button btn_kirim;

    String kelas, jurusan;
    ProgressDialog progressDialog;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_guru);

        bundle = getIntent().getExtras();

        kelas = "";
        jurusan = "";

        progressDialog = new ProgressDialog(this);

        edit_tugas = findViewById(R.id.edit_tugas);
        edit_alasan_tidak_hadir = findViewById(R.id.edit_alasan_tidak_hadir);
        edit_nama_guru = findViewById(R.id.edit_nama_guru);
        spin_kelas = findViewById(R.id.spin_kelas);
        spin_jurusan = findViewById(R.id.spin_jurusan);
        btn_kirim = findViewById(R.id.btn_kirim);

        SpinKelas();
        SpinJurusan();
        AmbilData();

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KirimData();
            }
        });

        getSupportActionBar().setTitle("Edit Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void AmbilData(){
        kelas = bundle.getString("kelas");
        jurusan = bundle.getString("jurusan");
        edit_nama_guru.getEditText().setText(bundle.getString("nama_guru"));
        edit_alasan_tidak_hadir.getEditText().setText(bundle.getString("alasan_tidak_hadir"));
        edit_tugas.getEditText().setText(bundle.getString("tugas"));

        switch (kelas){
            case "Kelas" : spin_kelas.setSelection(0);
                break;
            case "X" : spin_kelas.setSelection(1);
                break;
            case "XI" : spin_kelas.setSelection(2);
                break;
            case "XII" : spin_kelas.setSelection(3);
                break;
        }
        switch (jurusan){
            case "Jurusan" : spin_jurusan.setSelection(0);
                break;
            case "AKL 1" : spin_jurusan.setSelection(1);
                break;
            case "AKL 2" : spin_jurusan.setSelection(2);
                break;
            case "AKL 3" : spin_jurusan.setSelection(3);
                break;
            case "OTKP 1" : spin_jurusan.setSelection(4);
                break;
            case "OTKP 2" : spin_jurusan.setSelection(5);
                break;
            case "BDP 1" : spin_jurusan.setSelection(6);
                break;
            case "BDP 2" : spin_jurusan.setSelection(7);
                break;
            case "BDP 3" : spin_jurusan.setSelection(8);
                break;
            case "RPL 1" : spin_jurusan.setSelection(9);
                break;
            case "RPL 2" : spin_jurusan.setSelection(10);
                break;
            case "TKJ 1" : spin_jurusan.setSelection(11);
                break;
            case "TKJ 2" : spin_jurusan.setSelection(12);
                break;
        }
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
                Toast.makeText(EditGuruActivity.this, "Isi data dengan benar", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditGuruActivity.this, "Isi data dengan benar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void KirimData(){
        String id_tugas = bundle.getString("id_tugas");
        String nama_guru = edit_nama_guru.getEditText().getText().toString();
        String alasan_tidak_hadir = edit_alasan_tidak_hadir.getEditText().getText().toString();
        String tugas = edit_tugas.getEditText().getText().toString();

        if (nama_guru.isEmpty() || alasan_tidak_hadir.isEmpty()){
            Toast.makeText(this, "Isi data dengan benar", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
            Call<Response> call = apiInterface.updateGuru(id_tugas ,nama_guru, alasan_tidak_hadir, tugas, kelas, jurusan);
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.body().getResponse().equals("success")) {
                        Toast.makeText(EditGuruActivity.this, "Data Sudah Disimpan", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        edit_nama_guru.getEditText().setText("");
                        edit_alasan_tidak_hadir.getEditText().setText("");
                        edit_tugas.getEditText().setText("");
                        spin_kelas.setSelection(0);
                        spin_jurusan.setSelection(0);
                        finish();
                    } else {
                        Toast.makeText(EditGuruActivity.this, response.body().getResponse(), Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Toast.makeText(EditGuruActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            });
        }
    }
}
