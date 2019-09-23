package tgs.app.pion.Sekretaris;

import android.app.ProgressDialog;
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
import tgs.app.pion.model.AbsenSiswa;
import tgs.app.pion.model.Response;
import tgs.app.pion.retrofit.Api;
import tgs.app.pion.retrofit.ApiInterface;

public class EditSekretarisActivity extends AppCompatActivity {

    RadioGroup radio_status;
    RadioButton radio_sakit, radio_izin, radio_alpha;
    Button btn_kirim, btn_data;
    Spinner spin_nis, spin_nama;

    String status, nis, nama, id_absen_siswa;
    ProgressDialog progressDialog;

    Bundle bundle;

    List<AbsenSiswa.Siswa> detailSiswa;
    ArrayList<String> listNis = new ArrayList<>();
    ArrayList<String> listNama = new ArrayList<>();
    ArrayList<String> listJenisKelamin = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sekretaris);

        bundle = getIntent().getExtras();

        status = bundle.getString("status");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

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

        getSupportActionBar().setTitle("Edit Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void AmbilData(){
        nis = bundle.getString("NIS");
        nama = bundle.getString("nama_siswa");
        id_absen_siswa = bundle.getString("id_absen_siswa");
        listNis.add(nis);
        listNama.add(nama);
        switch (status){
            case "Sakit" : radio_sakit.setChecked(true);
                break;
            case "Izin" : radio_izin.setChecked(true);
                break;
            case "Alpha" : radio_alpha.setChecked(true);
                break;
        }

//        ApiInterface apiInterface1 = Api.getUrl().create(ApiInterface.class);
//        Call<AbsenSiswa> call = apiInterface1.getAbsenSiswa(kelas, jurusan);
//        call.enqueue(new Callback<AbsenSiswa>() {
//            @Override
//            public void onResponse(Call<AbsenSiswa> call, retrofit2.Response<AbsenSiswa> response) {
//                detailSiswa = response.body().getReadAbsenSiswa();
//                listNis.add(nis);
//                for (int i = 0; i < detailSiswa.size(); i++){
////                    listNis.add(detailSiswa.get(i).getNIS());
//                    listNama.add(detailSiswa.get(i).getNama_lengkap());
//                    listJenisKelamin.add(detailSiswa.get(i).getJenis_kelamin());
//                    Log.e("listnis", detailSiswa.get(i).getNIS());
//                    progressDialog.hide();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AbsenSiswa> call, Throwable t) {
//                progressDialog.hide();
//                Toast.makeText(EditSekretarisActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
//            }
//        });
//        switch (jenisk){
//            case "Jenis Kelamin" : spin_jeniskelamin.setSelection(0);
//                break;
//            case "Laki-laki" : spin_jeniskelamin.setSelection(1);
//                break;
//            case "Perempuan" : spin_jeniskelamin.setSelection(2);
//                break;
//        }
//        switch (kelas){
//            case "Kelas" : spin_kelas.setSelection(0);
//                break;
//            case "X" : spin_kelas.setSelection(1);
//                break;
//            case "XI" : spin_kelas.setSelection(2);
//                break;
//            case "XII" : spin_kelas.setSelection(3);
//                break;
//        }
//        switch (jurusan){
//            case "Jurusan" : spin_jurusan.setSelection(0);
//                break;
//            case "AKL 1" : spin_jurusan.setSelection(1);
//                break;
//            case "AKL 2" : spin_jurusan.setSelection(2);
//                break;
//            case "AKL 3" : spin_jurusan.setSelection(3);
//                break;
//            case "OTKP 1" : spin_jurusan.setSelection(4);
//                break;
//            case "OTKP 2" : spin_jurusan.setSelection(5);
//                break;
//            case "BDP 1" : spin_jurusan.setSelection(6);
//                break;
//            case "BDP 2" : spin_jurusan.setSelection(7);
//                break;
//            case "BDP 3" : spin_jurusan.setSelection(8);
//                break;
//            case "RPL 1" : spin_jurusan.setSelection(9);
//                break;
//            case "RPL 2" : spin_jurusan.setSelection(10);
//                break;
//            case "TKJ 1" : spin_jurusan.setSelection(11);
//                break;
//            case "TKJ 2" : spin_jurusan.setSelection(12);
//                break;
//        }
    }

    private void SpinNIS(){

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listNis);
        spin_nis.setAdapter(adapter);
        spin_nis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                nis = detailSiswa.get(position).getNIS();
                Log.e("nis", listNis.get(position));
                if (position == 0){
//                    Toast.makeText(EditSekretarisActivity.this, "NIS harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    nis = listNis.get(position);
                    spin_nama.setSelection(parent.getSelectedItemPosition());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SpinNama(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listNama);
        spin_nama.setAdapter(adapter);
        spin_nama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("nama", listNama.get(position));
                if (position == 0){
//                    Toast.makeText(EditSekretarisActivity.this, "Nama harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    nama = listNama.get(position);
                    spin_nis.setSelection(parent.getSelectedItemPosition());
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
        Call<Response> call = apiInterface.updateData(id_absen_siswa, nis, nama, status);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body().getResponse().equals("success")) {
                    Toast.makeText(EditSekretarisActivity.this, "Data Sudah Disimpan", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    finish();
                } else {
                    Toast.makeText(EditSekretarisActivity.this, response.body().getResponse(), Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(EditSekretarisActivity.this, "Data gagal dimasukkan", Toast.LENGTH_SHORT).show();
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
