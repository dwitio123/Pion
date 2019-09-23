package tgs.app.pion.Tugas;

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
import tgs.app.pion.Guru.EditGuruActivity;
import tgs.app.pion.R;
import tgs.app.pion.model.Response;
import tgs.app.pion.model.Tugas;
import tgs.app.pion.retrofit.Api;
import tgs.app.pion.retrofit.ApiInterface;

public class TugasAdapter extends RecyclerView.Adapter<TugasAdapter.ViewHolder> {

    private List<Tugas.TugasGuru> dataTugas;

    public TugasAdapter(List<Tugas.TugasGuru> dataTugas) {
        this.dataTugas = dataTugas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_tugas, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text_id_tugas.setText(dataTugas.get(i).getId_tugas());
        viewHolder.Tanggal.setText(dataTugas.get(i).getTanggal());
        viewHolder.Nama.setText(dataTugas.get(i).getNama_guru());
        viewHolder.Alasan.setText(dataTugas.get(i).getAlasan_tidak_hadir());
        viewHolder.Tugas.setText(dataTugas.get(i).getTugas());
        viewHolder.Kelas.setText(dataTugas.get(i).getKelas());
        viewHolder.Jurusan.setText(dataTugas.get(i).getJurusan());
    }

    @Override
    public int getItemCount() {
        return dataTugas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_id_tugas, Tanggal, Nama, Alasan, Tugas, Kelas, Jurusan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_id_tugas = itemView.findViewById(R.id.text_id_tugas);
            Tanggal = itemView.findViewById(R.id.Tanggal);
            Nama = itemView.findViewById(R.id.Nama);
            Alasan = itemView.findViewById(R.id.Alasan);
            Tugas = itemView.findViewById(R.id.Tugas);
            Kelas = itemView.findViewById(R.id.Kelas);
            Jurusan = itemView.findViewById(R.id.Jurusan);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Pilihan");
                    builder.setItems(R.array.aksi, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0){
                                Intent intent = new Intent(v.getContext(), EditGuruActivity.class);
                                intent.putExtra("id_tugas",             text_id_tugas.getText().toString());
                                intent.putExtra("nama_guru",            Nama.getText().toString());
                                intent.putExtra("alasan_tidak_hadir",   Alasan.getText().toString());
                                intent.putExtra("tugas",                Tugas.getText().toString());
                                intent.putExtra("kelas",                Kelas.getText().toString());
                                intent.putExtra("jurusan",              Jurusan.getText().toString());
                                v.getContext().startActivity(intent);
                            } else if (which == 1){
                                ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
                                Call<Response> call = apiInterface.deleteGuru(text_id_tugas.getText().toString());
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
