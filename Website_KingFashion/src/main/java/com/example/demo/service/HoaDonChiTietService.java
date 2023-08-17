package com.example.demo.service;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;

import java.util.List;
import java.util.UUID;

public interface HoaDonChiTietService {

    List<HoaDonChiTiet> getALl();

    void add(HoaDonChiTiet hoaDonChiTiet);

    void addAll(List<HoaDonChiTiet> hoaDonChiTiet);

    void delete(UUID id);

    HoaDonChiTiet findById(UUID id);

    HoaDonChiTiet findByHoaDonAndChiTietSanPham(HoaDon hoaDon, ChiTietSanPham chiTietSanPham);


    List<HoaDonChiTiet> getHoaDonChiTietByHoaDonId(UUID id);

    Double tongTien(UUID id);


}
