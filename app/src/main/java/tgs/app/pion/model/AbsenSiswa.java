package tgs.app.pion.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AbsenSiswa {

    @SerializedName("hasil")
    public List<Siswa> readAbsenSiswa;

    public List<Siswa> getReadAbsenSiswa() {
        return readAbsenSiswa;
    }

    public class Siswa {
        @SerializedName("id_absen_siswa")
        private String id_absen_siswa;
        @SerializedName("tanggal")
        private String tanggal;
        @SerializedName("NIS")
        private String NIS;
        @SerializedName("nama_lengkap")
        private String nama_lengkap;
        @SerializedName("jenis_kelamin")
        private String jenis_kelamin;
        @SerializedName("kelas")
        private String kelas;
        @SerializedName("jurusan")
        private String jurusan;
        @SerializedName("status")
        private String status;

        public String getId_absen_siswa() {
            return id_absen_siswa;
        }

        public String getTanggal(){
            return tanggal;
        }

        public String getNIS() {
            return NIS;
        }

        public String getNama_lengkap() {
            return nama_lengkap;
        }

        public String getJenis_kelamin() {
            return jenis_kelamin;
        }

        public String getKelas() {
            return kelas;
        }

        public String getJurusan() {
            return jurusan;
        }

        public String getStatus() {
            return status;
        }
    }
}
