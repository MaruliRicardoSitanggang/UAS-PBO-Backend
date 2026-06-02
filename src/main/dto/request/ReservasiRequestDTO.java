package com.papikost.api.dto.request;

public class ReservasiRequestDTO {
    private Long idPenyewa;
    private Long idKamar;
    private int durasiBulan;
    private boolean isPatungan;
    private int jumlahOrang; // Diisi jika isPatungan = true (minimal 2)

    public Long getIdPenyewa() { return idPenyewa; }
    public void setIdPenyewa(Long idPenyewa) { this.idPenyewa = idPenyewa; }

    public Long getIdKamar() { return idKamar; }
    public void setIdKamar(Long idKamar) { this.idKamar = idKamar; }

    public int getDurasiBulan() { return durasiBulan; }
    public void setDurasiBulan(int durasiBulan) { this.durasiBulan = durasiBulan; }

    public boolean isPatungan() { return isPatungan; }
    public void setPatungan(boolean patungan) { isPatungan = patungan; }

    public int getJumlahOrang() { return jumlahOrang; }
    public void setJumlahOrang(int jumlahOrang) { this.jumlahOrang = jumlahOrang; }
}
