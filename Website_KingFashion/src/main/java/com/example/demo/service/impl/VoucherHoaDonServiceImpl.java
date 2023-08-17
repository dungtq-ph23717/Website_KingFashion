package com.example.demo.service.impl;

import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.Voucher_HoaDon;
import com.example.demo.repository.VoucherHoaDonRepository;
import com.example.demo.service.VoucherHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherHoaDonServiceImpl implements VoucherHoaDonService {

    @Autowired
    private VoucherHoaDonRepository voucherHoaDonRepository;

    @Override
    public List<Voucher_HoaDon> getVoucher_HoaDonByHoaDonId(UUID id) {
        return voucherHoaDonRepository.getVoucher_HoaDonByHoaDonId(id);
    }

    @Override
    public void addAll(List<Voucher_HoaDon> voucher_hoaDons) {
        voucherHoaDonRepository.saveAll(voucher_hoaDons);
    }

    @Override
    public void add(Voucher_HoaDon voucher_hoaDon) {
        voucherHoaDonRepository.save(voucher_hoaDon);
    }

    @Override
    public Double tongTienMucGiam(UUID id) {
        return voucherHoaDonRepository.tongTienMucGiam(id);
    }


}
