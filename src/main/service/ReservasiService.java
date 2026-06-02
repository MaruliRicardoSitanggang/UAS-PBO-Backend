package com.papikost.api.service;

import com.papikost.api.entity.KamarKost;
import com.papikost.api.model.Reservasi;
import com.papikost.api.model.ReservasiPatungan;
import com.papikost.api.model.ReservasiSolo;
import com.papikost.api.repository.KamarKostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservasiService {

    @Autowired
    private KamarKostRepository kamarKostRepository;

    public List<KamarKost> getAllKamar() {
        return kamarKostRepository.findAll();
    }

    public double hitungTotalDariHarga(double hargaDasar, int durasiBulan, boolean isPatungan, int jumlahOrang) {
        String idReservasi = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Reservasi reservasi;
        if (isPatungan) {
            reservasi = new ReservasiPatungan(idReservasi, hargaDasar, durasiBulan, jumlahOrang);
        } else {
            reservasi = new ReservasiSolo(idReservasi, hargaDasar, durasiBulan);
        }

        return reservasi.hitungTotalTagihan();
    }

    public String hitungTotal(Long kamarId, int durasiBulan, boolean isPatungan, int jumlahOrang) {
        KamarKost kamar = kamarKostRepository.findById(kamarId)
                .orElseThrow(() -> new RuntimeException("Kamar dengan ID " + kamarId + " tidak ditemukan."));

        double total = hitungTotalDariHarga(kamar.getHargaDasar(), durasiBulan, isPatungan, jumlahOrang);
        return String.format("Rp %.0f", total);
    }
}
