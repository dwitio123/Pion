package tgs.app.pion.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataGuru {
    @SerializedName("hasil")
    public List<Guru> readDataGuru;

    public List<Guru> getReadDataGuru() {
        return readDataGuru;
    }

    public class Guru {
        @SerializedName("id_guru")
        private String id_guru;
        @SerializedName("nama_guru")
        private String nama_guru;
        @SerializedName("jenis_kelamin")
        private String jenis_kelamin;
        @SerializedName("mapel")
        private String mapel;

        public String getId_guru() {
            return id_guru;
        }

        public String getNama_guru() {
            return nama_guru;
        }

        public String getJenis_kelamin() {
            return jenis_kelamin;
        }

        public String getMapel() {
            return mapel;
        }
    }
}
