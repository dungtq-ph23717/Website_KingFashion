package com.example.demo.service.impl;

import com.example.demo.entity.GioHang;
import com.example.demo.repository.GioHangRepository;
import com.example.demo.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private GioHangRepository rp;

    @Override
    public List<GioHang> getAll() {
        return rp.findAll();
    }

    @Override
    public GioHang getOne(UUID id) {
        return rp.findById(id).orElse(null);
    }

    @Override
    public void add(GioHang gioHang) {
        rp.save(gioHang);
    }

    @Override
    public void delete(UUID id) {
        rp.deleteById(id);
    }
}
