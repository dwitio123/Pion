package tgs.app.pion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import tgs.app.pion.Sekretaris.EditSekretarisActivity;
import tgs.app.pion.model.AbsenSiswa;
import tgs.app.pion.model.Response;
import tgs.app.pion.retrofit.Api;
import tgs.app.pion.retrofit.ApiInterface;

public class AbsenSiswaAdapter extends RecyclerView.Adapter<AbsenSiswaAdapter.ViewHolder> {

    private List<AbsenSiswa.Siswa> dataSiswa;

    public AbsenSiswaAdapter(List<AbsenSiswa.Siswa> dataSiswa) {
        this.dataSiswa = dataSiswa;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_sekretaris, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.text_no.setText(dataSiswa.get(i).getId_absen_siswa());
        viewHolder.Tanggal.setText(dataSiswa.get(i).getTanggal());
        viewHolder.Nis.setText(dataSiswa.get(i).getNIS());
        viewHolder.Nama.setText(dataSiswa.get(i).getNama_lengkap());
        viewHolder.JenisKel.setText(dataSiswa.get(i).getJenis_kelamin());
        viewHolder.Kelas.setText(dataSiswa.get(i).getKelas());
        viewHolder.Jurusan.setText(dataSiswa.get(i).getJurusan());
        viewHolder.Status.setText(dataSiswa.get(i).getStatus());
    }

    @Override
    public int getItemCount() {
        return dataSiswa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_no;
        TextView Tanggal, Nis, Nama, JenisKel, Kelas, Jurusan, Status;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            text_no = itemView.findViewById(R.id.text_no);
            Tanggal = itemView.findViewById(R.id.Tanggal);
            Nis = itemView.findViewById(R.id.Nis);
            Nama = itemView.findViewById(R.id.Nama);
            JenisKel = itemView.findViewById(R.id.JenisKel);
            Kelas = itemView.findViewById(R.id.Kelas);
            Jurusan = itemView.findViewById(R.id.Jurusan);
            Status = itemView.findViewById(R.id.Status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Pilihan");
                    builder.setItems(R.array.aksi, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0){
                                Intent intent = new Intent(v.getContext(), EditSekretarisActivity.class);
                                intent.putExtra("id_absen_siswa",text_no.getText().toString());
                                intent.putExtra("NIS",           Nis.getText().toString());
                                intent.putExtra("nama_siswa",    Nama.getText().toString());
                                intent.putExtra("jenis_kelamin", JenisKel.getText().toString());
                                intent.putExtra("kelas",         Kelas.getText().toString());
                                intent.putExtra("jurusan",       Jurusan.getText().toString());
                                intent.putExtra("status",        Status.getText().toString());
                                v.getContext().startActivity(intent);
                            } else if (which == 1){
                                ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
                                Call<Response> call = apiInterface.deleteData(text_no.getText().toString());
                                call.enqueue(new Callback<Response>() {
                                    @Override
                                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                        if (response.body().getResponse().equals("success")) {
                                            Toast.makeText(v.getContext(), "Data sudah dihapus", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(v.getContext(), response.body().getResponse(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Response> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    });
                    builder.create().show();
                }
            });
        }
    }
}
