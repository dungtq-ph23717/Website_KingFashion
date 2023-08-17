package com.example.demo.service.impl;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.LichSuHoaDon;
import com.example.demo.repository.LichSuHoaDonRepository;
import com.example.demo.service.LichSuHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class LichSuHoaDonServiceImpl  implements LichSuHoaDonService {
    @Autowired
    private LichSuHoaDonRepository lichSuHoaDonRepository;

    @Override
    public List<LichSuHoaDon> getAll() {
        return lichSuHoaDonRepository.findAll();
    }

    @Override
    public LichSuHoaDon detail(UUID id) {
        return lichSuHoaDonRepository.findLichSuHoaDonById(id);
    }

    @Override
    public List<LichSuHoaDon> findAllLichSuHoaDonById(UUID id) {
        return lichSuHoaDonRepository.findLichSuHoaDonByHoaDonId(id);
    }

    @Override
    public void createLichSuDonHang(LichSuHoaDon lichSuHoaDon) {
        lichSuHoaDonRepository.save(lichSuHoaDon);
    }

}
