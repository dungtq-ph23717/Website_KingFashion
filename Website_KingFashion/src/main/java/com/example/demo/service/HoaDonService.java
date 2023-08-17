package com.example.demo.service;

import com.example.demo.entity.HoaDon;

import com.example.demo.entity.LichSuHoaDon;
import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface HoaDonService {

    List<HoaDon> getAll();

    Page<HoaDon> phanTrangHoaDon(Integer page, Integer size);

    List<HoaDon> getExcel();

    HoaDon detail(UUID id);

    void add(HoaDon hoaDon);

    void updateHD(HoaDon hoaDon);

    Page<HoaDon> searchHD(String maHoaDon, String nguoiNhan, Double tongTienSauKhiGiam, Integer trangThai, Date tuNgay, Date denNgay, Integer loaiDon, Pageable pageable);

    HoaDon getHoaDonByHoaDonChiTietId(UUID id);

    HoaDon findByHoaDonChiTietId(UUID id);

    Double tongTienSauGiam(UUID id);
}
