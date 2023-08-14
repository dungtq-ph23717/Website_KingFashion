package com.example.demo.service.impl;

import com.example.demo.entity.HoaDon;
import com.example.demo.repository.HoaDonRepository;
import com.example.demo.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HoaDonServiceImpl implements HoaDonService {

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
    public Page<HoaDon> searchHD(String maHoaDon, String nguoiNhan, Double tongTienSauKhiGiam,
                                 Integer trangThai, Date ngayTao,
                                 Integer loaiDon, Integer page, Integer size,
                                 Integer xapXep) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "ngayTao");

        return hoaDonRepository.searchHD(maHoaDon, nguoiNhan, tongTienSauKhiGiam,
                trangThai, ngayTao, loaiDon, pageable);
    }
}
