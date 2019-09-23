package tgs.app.pion.GuruPiket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import tgs.app.pion.R;
import tgs.app.pion.model.Response;
import tgs.app.pion.retrofit.Api;
import tgs.app.pion.retrofit.ApiInterface;

public class LoginAbsenSiswaActivity extends AppCompatActivity {

    TextView text_login;
    TextInputLayout edit_user, edit_pass;
    Button btn_login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_absen_siswa);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        text_login = findViewById(R.id.text_login);
        edit_user = findViewById(R.id.edit_user);
        edit_pass = findViewById(R.id.edit_pass);
        btn_login = findViewById(R.id.btn_login);

        Typeface customfont = Typeface.createFromAsset(getAssets(), "font/Hippo.otf");
        text_login.setTypeface(customfont);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                GetData();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Absensi Siswa");
    }

    private void GetData(){
        final String user = edit_user.getEditText().getText().toString();
        String pass = edit_pass.getEditText().getText().toString();

        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Response> call = apiInterface.loginGuru(user, pass);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body().getResponse().equals("success")){
                    Intent intent = new Intent(LoginAbsenSiswaActivity.this, AbsenSiswaActivity.class);
                    startActivity(intent);
                    progressDialog.hide();
                    edit_user.getEditText().setText("");
                    edit_pass.getEditText().setText("");
                } else {
                    Toast.makeText(LoginAbsenSiswaActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
