package com.papikost.api.controller;

import com.papikost.api.dto.request.ReservasiRequestDTO;
import com.papikost.api.entity.KamarKost;
import com.papikost.api.service.ReservasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservasi")
public class ReservasiController {

    @Autowired
    private ReservasiService reservasiService;

    @GetMapping("/kamar")
    public ResponseEntity<List<KamarKost>> getAllKamar() {
        List<KamarKost> kamarList = reservasiService.getAllKamar();
        return ResponseEntity.ok(kamarList);
    }

    @GetMapping("/hitung")
    public ResponseEntity<?> hitungReservasiGet(
            @RequestParam(defaultValue = "solo") String tipe,
            @RequestParam double hargaDasar,
            @RequestParam int durasi,
            @RequestParam(defaultValue = "1") int jumlahOrang) {
        try {
            boolean isPatungan = tipe.equalsIgnoreCase("patungan");
            double total = reservasiService.hitungTotalDariHarga(hargaDasar, durasi, isPatungan, jumlahOrang);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("tipe", tipe);
            response.put("hargaDasar", hargaDasar);
            response.put("durasiBulan", durasi);
            response.put("jumlahOrang", jumlahOrang);
            response.put("totalHarga", String.format("Rp %.0f", total));
            response.put("totalHargaAngka", total);
            response.put("isEmulatedFromJava", false);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/hitung")
    public ResponseEntity<?> hitungReservasiPost(@RequestBody ReservasiRequestDTO request) {
        try {
            String hasilHitung = reservasiService.hitungTotal(
                request.getIdKamar(),
                request.getDurasiBulan(),
                request.isPatungan(),
                request.getJumlahOrang()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("totalHarga", hasilHitung);
            response.put("isEmulatedFromJava", false);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
