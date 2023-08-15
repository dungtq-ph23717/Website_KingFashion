package com.example.demo.service.impl;

import com.example.demo.entity.Voucher;
import com.example.demo.repository.VoucherRepository;
import com.example.demo.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository repository;

    @Override
    public Page<Voucher> page(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    @Override
    public List<Voucher> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<Voucher> search(String ma, String ten, Integer mucGiam, Double tien, Date ngayBatDau, Date ngayKetThuc, Integer trangThai, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.search(ma, ten, mucGiam, tien, ngayBatDau, ngayKetThuc, trangThai, pageable);
    }

    @Override
    public Voucher detail(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void add(Voucher voucher) {
        repository.save(voucher);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
