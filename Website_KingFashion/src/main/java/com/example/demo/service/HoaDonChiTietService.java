package com.example.demo.service;

import com.example.demo.entity.HoaDonChiTiet;

import java.util.List;

public interface HoaDonChiTietService {

    List<HoaDonChiTiet> getALl();

    void add(HoaDonChiTiet hoaDonChiTiet);
}
