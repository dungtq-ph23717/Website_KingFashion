package com.example.demo.service.impl;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.repository.HoaDonChiTietRepository;
import com.example.demo.repository.HoaDonRepository;
import com.example.demo.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public void addAll(List<HoaDonChiTiet> hoaDonChiTiet) {
        hoaDonChiTietRepository.saveAll(hoaDonChiTiet);
    }

    @Override
    public void delete(UUID id) {
         hoaDonChiTietRepository.deleteById(id);
    }

    @Override
    public HoaDonChiTiet findById(UUID id) {
        return hoaDonChiTietRepository.findById(id).orElse(null);
    }

    @Override
    public HoaDonChiTiet findByHoaDonAndChiTietSanPham(HoaDon hoaDon, ChiTietSanPham chiTietSanPham) {
        Optional<HoaDonChiTiet> hoaDonChiTietOptional = hoaDonChiTietRepository.findByHoaDonAndChiTietSanPham(hoaDon, chiTietSanPham);
        return hoaDonChiTietOptional.orElse(null);
    }


    @Override
    public List<HoaDonChiTiet> getHoaDonChiTietByHoaDonId(UUID id) {
        return hoaDonChiTietRepository.getHoaDonChiTietByHoaDonId(id);
    }

    @Override
    public Double tongTien(UUID id) {
        return hoaDonChiTietRepository.tongTien(id);
    }

}
