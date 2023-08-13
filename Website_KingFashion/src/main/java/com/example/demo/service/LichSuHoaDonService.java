package com.example.demo.service;

import com.example.demo.entity.LichSuHoaDon;

import java.util.List;
import java.util.UUID;

public interface LichSuHoaDonService {


    List<LichSuHoaDon> getAll();

    List<LichSuHoaDon> detail(UUID id);

    void add(LichSuHoaDon lichSuHoaDon);
}
