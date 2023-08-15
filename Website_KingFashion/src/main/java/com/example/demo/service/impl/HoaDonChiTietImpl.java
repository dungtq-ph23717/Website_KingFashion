package com.example.demo.service.impl;

import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.repository.HoaDonChiTietRepository;
import com.example.demo.repository.HoaDonRepository;
import com.example.demo.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonChiTietImpl implements HoaDonChiTietService {

    @Autowired

    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Override
    public List<HoaDonChiTiet> getALl() {
        return hoaDonChiTietRepository.findAll();
    }

    @Override
    public void add(HoaDonChiTiet hoaDonChiTiet) {
        hoaDonChiTietRepository.save(hoaDonChiTiet);
    }
}
