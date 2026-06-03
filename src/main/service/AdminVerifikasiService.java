package com.papikost.api.service;

import com.papikost.api.entity.Akun;
import com.papikost.api.entity.Biodata;
import com.papikost.api.entity.PengajuanOwner;
import com.papikost.api.repository.AkunRepository;
import com.papikost.api.repository.BiodataRepository;
import com.papikost.api.repository.PengajuanOwnerRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminVerifikasiService {

    private final BiodataRepository biodataRepository;
    private final PengajuanOwnerRepository pengajuanOwnerRepository;
    private final AkunRepository akunRepository;

    public AdminVerifikasiService(BiodataRepository biodataRepository,
                                  PengajuanOwnerRepository pengajuanOwnerRepository,
                                  AkunRepository akunRepository) {
        this.biodataRepository = biodataRepository;
        this.pengajuanOwnerRepository = pengajuanOwnerRepository;
        this.akunRepository = akunRepository;
    }

    // ── Verifikasi Data Diri ─────────────────────────────────────────────────

    public List<Biodata> getAllVerifikasiDataDiri() {
        return biodataRepository.findAll();
    }

    public Biodata updateStatusDataDiri(@NonNull Long userId, @NonNull String status) {
        Biodata biodata = biodataRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Biodata dengan ID " + userId + " tidak ditemukan."));

        biodata.setVerifikasiStatus(status.toUpperCase());
        biodata.setIsVerified("DISETUJUI".equalsIgnoreCase(status));
        return biodataRepository.save(biodata);
    }

    // ── Pengajuan Owner ──────────────────────────────────────────────────────

    public PengajuanOwner buatPengajuanOwner(@NonNull PengajuanOwner pengajuan) {
        return pengajuanOwnerRepository.save(pengajuan);
    }

    public List<PengajuanOwner> getAllPengajuanOwner() {
        return pengajuanOwnerRepository.findAllByOrderByCreatedAtDesc();
    }

    public PengajuanOwner updateStatusPengajuanOwner(@NonNull Long id, @NonNull String status) {
        PengajuanOwner pengajuan = pengajuanOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pengajuan Owner tidak ditemukan."));

        pengajuan.setStatus(status);

        // Jika disetujui, ubah role akun menjadi pemilik
        if ("DISETUJUI".equalsIgnoreCase(status)) {
            Long userId = pengajuan.getUserId();
            if (userId != null) {
                Optional<Akun> akunOpt = akunRepository.findById(userId);
                akunOpt.ifPresent(akun -> {
                    akun.setRole("pemilik");
                    akunRepository.save(akun);
                });
            }
        }

        return pengajuanOwnerRepository.save(pengajuan);
    }
}
