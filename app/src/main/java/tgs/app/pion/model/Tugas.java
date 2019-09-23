package tgs.app.pion.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tugas {

    @SerializedName("hasil")
    public List<TugasGuru> readTugas;

    public List<TugasGuru> getReadTugas() {
        return readTugas;
    }

    public class TugasGuru {
        @SerializedName("id_tugas")
        private String id_tugas;
        @SerializedName("tanggal")
        private String tanggal;
        @SerializedName("nama_guru")
        private String nama_guru;
        @SerializedName("alasan_tidak_hadir")
        private String alasan_tidak_hadir;
        @SerializedName("tugas")
        private String tugas;
        @SerializedName("kelas")
        private String kelas;
        @SerializedName("jurusan")
        private String jurusan;

        public String getId_tugas() {
            return id_tugas;
        }

        public String getTanggal() {
            return tanggal;
        }

        public String getNama_guru() {
            return nama_guru;
        }

        public String getAlasan_tidak_hadir() {
            return alasan_tidak_hadir;
        }

        public String getTugas() {
            return tugas;
        }

        public String getKelas() {
            return kelas;
        }

        public String getJurusan(){
            return jurusan;
        }
    }
}
