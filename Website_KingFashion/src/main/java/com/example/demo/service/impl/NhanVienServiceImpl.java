package com.example.demo.service.impl;

import com.example.demo.entity.NhanVien;
import com.example.demo.repository.NhanVienRepository;
import com.example.demo.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository repository;

    @Override
    public Page<NhanVien> page(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    @Override
    public NhanVien detail(UUID id) {
        return repository.findNhanVienById(id);
    }

    @Override
    public List<NhanVien> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(NhanVien nhanVien) {
        repository.save(nhanVien);
    }

    @Override
    public void update(NhanVien nhanVien) {
        repository.save(nhanVien);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
