package tgs.app.pion.Tugas;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tgs.app.pion.R;
import tgs.app.pion.model.Tugas;

public class TugasAdapter2 extends RecyclerView.Adapter<TugasAdapter2.ViewHolder> {

    private List<Tugas.TugasGuru> dataTugas;

    public TugasAdapter2(List<Tugas.TugasGuru> dataTugas) {
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
        }
    }
}
