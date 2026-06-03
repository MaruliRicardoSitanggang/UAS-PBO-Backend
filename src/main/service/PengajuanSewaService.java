package com.papikost.api.service;

import com.papikost.api.dto.request.PengajuanSewaRequestDTO;
import com.papikost.api.entity.PengajuanSewa;
import com.papikost.api.repository.PengajuanSewaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PengajuanSewaService {

    private final PengajuanSewaRepository pengajuanSewaRepository;

    public PengajuanSewaService(PengajuanSewaRepository repository) {
        this.pengajuanSewaRepository = repository;
    }

    public PengajuanSewa prosesPengajuan(@NonNull PengajuanSewaRequestDTO request) {
        PengajuanSewa pengajuan = new PengajuanSewa();
        pengajuan.setUserId(request.getUserId());
        pengajuan.setKamarId(request.getKamarId());
        pengajuan.setTipeSewa(request.getTipeSewa());
        pengajuan.setDurasiBulan(request.getDurasiBulan());
        pengajuan.setTotalTagihan(request.getTotalTagihan());
        return pengajuanSewaRepository.save(pengajuan);
    }

    public List<PengajuanSewa> ambilRiwayatSewa(@NonNull Long userId) {
        return pengajuanSewaRepository.findByUserIdOrderByTanggalPengajuanDesc(userId);
    }

    public List<PengajuanSewa> getAllPengajuanMasuk() {
        return pengajuanSewaRepository.findAll();
    }

    public PengajuanSewa updateStatusPengajuan(@NonNull Long id, @NonNull String status) {
        PengajuanSewa pengajuan = pengajuanSewaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Data pengajuan sewa tidak ditemukan dengan ID: " + id));
        pengajuan.setStatus(status);
        return pengajuanSewaRepository.save(pengajuan);
    }
}
