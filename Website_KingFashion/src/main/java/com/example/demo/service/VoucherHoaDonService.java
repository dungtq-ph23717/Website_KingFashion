package com.example.demo.service;

import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.Voucher_HoaDon;

import java.util.List;
import java.util.UUID;

public interface VoucherHoaDonService {
    List<Voucher_HoaDon> getVoucher_HoaDonByHoaDonId(UUID id);

    void addAll(List<Voucher_HoaDon> voucher_hoaDons);

    void add(Voucher_HoaDon voucher_hoaDon);

    Double tongTienMucGiam(UUID id);
}
