package com.example.demo.service;

import com.example.demo.entity.GioHang;

import java.util.List;
import java.util.UUID;

public interface GioHangService {

    List<GioHang> getAll();

    GioHang getOne(UUID id);

    void add(GioHang gioHang);

    void delete(UUID id);
}
