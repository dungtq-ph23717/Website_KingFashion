package com.example.demo.service;

import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Page<Voucher> page(Integer page, Integer size);

    List<Voucher> getAll();

    Page<Voucher> search(String ma, String ten, Integer mucGiam, Double tien, Date ngayBatDau, Date ngayKetThuc, Integer trangThai, Integer size, Integer page);

    Voucher detail(UUID id);

    void add(Voucher voucher);

    void delete(UUID id);

}
