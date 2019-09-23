package tgs.app.pion.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sekretaris {

    @SerializedName("hasil")
    public List<Siswa> readSiswa;

    public List<Siswa> getReadSiswa() {
        return readSiswa;
    }

    public class Siswa {
        @SerializedName("id_siswa")
        private String id_siswa;
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

        public String getId_siswa() {
            return id_siswa;
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
