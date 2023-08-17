package com.example.demo.service.impl;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.LichSuHoaDon;
import com.example.demo.repository.HoaDonRepository;
import com.example.demo.service.HoaDonService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public List<HoaDon> getAll() {
        return hoaDonRepository.findAll();
    }

    @Override
    public Page<HoaDon> phanTrangHoaDon(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return hoaDonRepository.findAll(pageable);
    }

    @Override
    public List<HoaDon> getExcel() {
        return hoaDonRepository.findAll();
    }

    @Override
    public HoaDon detail(UUID id) {
        return hoaDonRepository.getHoaDonById(id);
    }

    @Override
    public void add(HoaDon hoaDon) {
        hoaDonRepository.save(hoaDon);
    }

    @Override
    public void updateHD(HoaDon hoaDon) {
        Date date = new Date();
        hoaDon.setNgaySua(date);
        hoaDonRepository.save(hoaDon);
    }

    @Override
    public Page<HoaDon> searchHD(String maHoaDon, String tenNguoiNhan, Double tongTienSauKhiGiam, Integer trangThai,
                                 Date tuNgay,Date denNgay, Integer loaiDon, Pageable pageable) {
        Page<HoaDon> result = hoaDonRepository.searchHD(maHoaDon, tenNguoiNhan, tongTienSauKhiGiam, trangThai, tuNgay,denNgay, loaiDon, pageable);

        System.out.println(result);
        return result;
    }

    @Override
    public HoaDon getHoaDonByHoaDonChiTietId(UUID id) {
        return hoaDonRepository.getHoaDonByHoaDonChiTietId(id);
    }

    @Override
    public HoaDon findByHoaDonChiTietId(UUID id) {
        return hoaDonRepository.findByHoaDonChiTietId(id);
    }

    @Override
    public Double tongTienSauGiam(UUID id) {
        return hoaDonRepository.tongTienSauGiam(id);
    }


}
