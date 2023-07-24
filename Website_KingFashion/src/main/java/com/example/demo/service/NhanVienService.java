package com.example.demo.service;

import com.example.demo.entity.NhanVien;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface NhanVienService {
    Page<NhanVien> page(Integer page, Integer size);

    NhanVien detail(UUID id);

    List<NhanVien> getAll();

    void save(NhanVien nhanVien);

    void update(NhanVien nhanVien);

    void deleteById(UUID id);
}
