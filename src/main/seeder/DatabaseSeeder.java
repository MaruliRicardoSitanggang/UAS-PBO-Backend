package com.papikost.api.seeder;

import com.papikost.api.entity.Penyewa;
import com.papikost.api.entity.KamarKost;
import com.papikost.api.repository.AkunRepository;
import com.papikost.api.repository.PenyewaRepository;
import com.papikost.api.repository.KamarKostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final AkunRepository akunRepository;
    private final PenyewaRepository penyewaRepository;
    private final KamarKostRepository kamarKostRepository;

    public DatabaseSeeder(AkunRepository akunRepository,
                          PenyewaRepository penyewaRepository,
                          KamarKostRepository kamarKostRepository) {
        this.akunRepository    = akunRepository;
        this.penyewaRepository = penyewaRepository;
        this.kamarKostRepository = kamarKostRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // ── Seed Akun Admin ──────────────────────────────────────────────────
        if (akunRepository.findByUsername("admin").isEmpty()) {
            Penyewa admin = new Penyewa(
                "admin", "admin@papikost.com", "admin123",
                "Administrator", "admin", "-", "-"
            );
            akunRepository.save(admin);
            System.out.println("[Seeder] Akun admin berhasil dibuat!");
        }

        // ── Seed Akun Owner ──────────────────────────────────────────────────
        if (akunRepository.findByUsername("owner").isEmpty()) {
            Penyewa owner = new Penyewa(
                "owner", "owner@papikost.com", "owner123",
                "Budi Santoso", "pemilik", "Pengusaha", "Medan"
            );
            akunRepository.save(owner);
            System.out.println("[Seeder] Akun owner berhasil dibuat!");
        }

        // ── Seed Akun Penyewa ────────────────────────────────────────────────
        if (penyewaRepository.count() == 0) {
            penyewaRepository.save(new Penyewa(
                "andreas", "andreas@email.com", "pass123",
                "Andreas Pegri Damanik", "pencari", "Karyawan", "Medan"
            ));
            penyewaRepository.save(new Penyewa(
                "maruli", "maruli@email.com", "pass123",
                "Maruli Ricardo", "pencari", "Karyawan", "Medan"
            ));
            System.out.println("[Seeder] Data Penyewa berhasil di-seed!");
        }

        // ── Seed Daftar Kamar Kost ───────────────────────────────────────────
        if (kamarKostRepository.count() == 0) {
            kamarKostRepository.save(new KamarKost(
                "Kost Putra Padang Bulan", "Padang Bulan", 1500000.0,
                "Tersedia 2 Kamar", 4.8,
                "https://images.unsplash.com/photo-1555854877-bab0e564b8d5?auto=format&fit=crop&q=80&w=600",
                true, true, false, 2,
                "Kost premium minimalis dengan pencahayaan alami melimpah, dirancang khusus untuk kenyamanan belajar mahasiswa USU."
            ));
            kamarKostRepository.save(new KamarKost(
                "Kost Eksklusif Setia Budi", "Setia Budi", 2200000.0,
                "Tersedia", 4.9,
                "https://images.unsplash.com/photo-1505691938895-1758d7feb511?auto=format&fit=crop&q=80&w=600",
                true, true, true, 5,
                "Kost mewah berfasilitas lengkap dekat pusat kuliner Setia Budi Medan. AC, kamar mandi dalam, smart lock, parkir luas."
            ));
            kamarKostRepository.save(new KamarKost(
                "Kost Putri Dr. Mansyur", "Dr. Mansyur", 1300000.0,
                "Sisa 1 Kamar", 4.7,
                "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?auto=format&fit=crop&q=80&w=600",
                true, false, false, 1,
                "Kost putri asri dan kondusif di Jalan Dr. Mansyur Medan. Lingkungan aman dengan penjagaan sekuriti 24 jam."
            ));
            kamarKostRepository.save(new KamarKost(
                "Kost Sejahtera Helvetia", "Helvetia", 900000.0,
                "Penuh", 4.3,
                "https://images.unsplash.com/photo-1540518614846-7eded433c457?auto=format&fit=crop&q=80&w=600",
                false, false, false, 0,
                "Kost sederhana di kawasan Helvetia, cocok untuk pekerja dengan budget terbatas."
            ));
            System.out.println("[Seeder] Data Kamar Kost berhasil di-seed!");
        }
    }
}
