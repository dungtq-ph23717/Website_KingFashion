package com.example.demo.service;

import com.example.demo.entity.GioHang;
import com.example.demo.entity.GioHangChiTiet;

import java.util.List;
import java.util.UUID;

public interface GioHangChiTietService {

    List<GioHangChiTiet> getAll();

    GioHangChiTiet getOne(UUID id);

    void add(GioHangChiTiet gioHang);

    void delete(UUID id);

    Double tongTien();
}
