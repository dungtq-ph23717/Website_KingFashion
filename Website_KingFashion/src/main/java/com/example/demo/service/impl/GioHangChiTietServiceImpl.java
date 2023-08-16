package com.example.demo.service.impl;

import com.example.demo.entity.GioHang;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.repository.GioHangChiTietRepository;
import com.example.demo.repository.GioHangRepository;
import com.example.demo.service.GioHangChiTietService;
import com.example.demo.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {

    @Autowired
    private GioHangChiTietRepository repository;

    @Override
    public List<GioHangChiTiet> getAll() {
        return repository.findAll();
    }

    @Override
    public GioHangChiTiet getOne(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void add(GioHangChiTiet gioHang) {
        repository.save(gioHang);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Double tongTien() {
        return repository.tongTien();
    }
}
