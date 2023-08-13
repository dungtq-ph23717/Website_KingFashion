package com.example.demo.service;

import com.example.demo.entity.HoaDon;

import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface HoaDonService {

    List<HoaDon> getAll();

    Page<HoaDon> phanTrangHoaDon(Integer page, Integer size);

    List<HoaDon> getExcel();

    HoaDon detail(UUID id);

    void add(HoaDon hoaDon);

    Page<HoaDon> searchHD(String ma, String tenNguoiNhan, Integer trangThai, Date ngayThanhToan, Double tongTienSauKhiGiam, Date ngayShip, Date ngayDuKienNhan, Integer page, Integer size);
}
