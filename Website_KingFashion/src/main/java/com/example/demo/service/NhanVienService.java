package com.example.demo.service;

import com.example.demo.entity.NhanVien;
import com.example.demo.entity.TaiKhoan;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface NhanVienService {
    Page<TaiKhoan> page(Integer page, Integer size);

    NhanVien detail(UUID id);

    List<NhanVien> getAll();

    void save(NhanVien nhanVien);

    void update(NhanVien nhanVien);

    void deleteById(UUID id);

    Page<NhanVien> search(String ten, String sdt, String email, String diaChi, Date ngaySinh, Integer size, Integer page);
}