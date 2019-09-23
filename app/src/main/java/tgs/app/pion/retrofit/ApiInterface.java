package tgs.app.pion.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import tgs.app.pion.model.AbsenSiswa;
import tgs.app.pion.model.DataGuru;
import tgs.app.pion.model.Response;
import tgs.app.pion.model.Sekretaris;
import tgs.app.pion.model.Tugas;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("create.php")
    Call<Response> insertData(@Field("NIS") String nis,
                              @Field("nama") String nama,
                              @Field("jenis_kelamin") String jenis_kelamin,
                              @Field("kelas") String kelas,
                              @Field("jurusan") String jurusan,
                              @Field("status") String status);

    @GET("read.php")
    Call<Sekretaris> getSekretarisCall(@Query("kelas") String kelas,
                                       @Query("jurusan") String jurusan);

    @GET("readkelassiswa.php")
    Call<AbsenSiswa> getAbsenKelasSiswa(@Query("kelas") String kelas);

    @GET("readjurusansiswa.php")
    Call<AbsenSiswa> getAbsenJurusanSiswa(@Query("jurusan") String jurusan);

    @GET("readabsensiswa.php")
    Call<AbsenSiswa> getAbsenSiswa(@Query("kelas") String kelas,
                                   @Query("jurusan") String jurusan);

    @GET("filtersiswa.php")
    Call<AbsenSiswa> getFilterSiswa(@Query("kelas") String kelas,
                                    @Query("jurusan") String jurusan,
                                    @Query("tgl") String tgl);

    @GET("filtertugas.php")
    Call<Tugas> getFilterTugas(@Query("kelas") String kelas,
                               @Query("jurusan") String jurusan,
                               @Query("tgl") String tgl);

    @GET("filtertugasguru.php")
    Call<Tugas> getFilterTugasGuru(@Query("tgl") String tgl);

    @GET("readkelastugas.php")
    Call<Tugas> getTugasKelasSiswa(@Query("kelas") String kelas);

    @GET("readjurusantugas.php")
    Call<Tugas> getTugasJurusanSiswa(@Query("jurusan") String jurusan);

    @GET("readkelasjurusantugas.php")
    Call<Tugas> getKelasJurusanTugas(@Query("kelas") String kelas,
                                     @Query("jurusan") String jurusan);

    @GET("readalltugas.php")
    Call<Tugas> getAllTugas();

    @GET("readallabsensiswa.php")
    Call<AbsenSiswa> getAllAbsenSiswa();

    @FormUrlEncoded
    @POST("update.php")
    Call<Response> updateData(@Field("id_absen_siswa") String id_absen_siswa,
                              @Field("NIS") String nis,
                              @Field("nama_lengkap") String nama_lengkap,
                              @Field("status") String status);

    @FormUrlEncoded
    @POST("delete.php")
    Call<Response> deleteData(@Field("id_absen_siswa") String id_absen_siswa);

    @FormUrlEncoded
    @POST("createguru.php")
    Call<Response> insertGuru(@Field("nama_guru") String nama_guru,
                              @Field("alasan_tidak_hadir") String alasan_tidak_hadir,
                              @Field("tugas") String tugas,
                              @Field("kelas") String kelas,
                              @Field("jurusan") String jurusan);

    @GET("readguru.php")
    Call<Tugas> getTugasCall();

    @GET("readtugas.php")
    Call<Tugas> getReadTugas(@Query("kelas") String kelas,
                             @Query("jurusan") String jurusan);

    @GET("dataguru.php")
    Call<DataGuru> getDataGuruCall();

    @FormUrlEncoded
    @POST("updateguru.php")
    Call<Response> updateGuru(@Field("id_tugas") String id_tugas,
                              @Field("nama_guru") String nama_guru,
                              @Field("alasan_tidak_hadir") String alasan_tidak_hadir,
                              @Field("tugas") String tugas,
                              @Field("kelas") String kelas,
                              @Field("jurusan") String jurusan);

    @FormUrlEncoded
    @POST("deleteguru.php")
    Call<Response> deleteGuru(@Field("id_tugas") String id_tugas);

    @FormUrlEncoded
    @POST("login.php")
    Call<Response> loginData(@Field("username") String username,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("loginguru.php")
    Call<Response> loginGuru(@Field("username") String username,
                             @Field("password") String password);
}
