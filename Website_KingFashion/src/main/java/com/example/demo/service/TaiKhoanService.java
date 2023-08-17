package com.example.demo.service;

import com.example.demo.entity.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TaiKhoanService {

    List<TaiKhoan> getAll();

    Page<TaiKhoan> page(Integer page, Integer size);

    Page<TaiKhoan> getAllNhanVien(Integer page, Integer size);

    Page<TaiKhoan> search(String ma, String ten, String sdt, String email, String diaChi, Date ngaySinh, Integer size, Integer page);

    TaiKhoan detail(UUID id);

    TaiKhoan update(TaiKhoan taiKhoan);

    TaiKhoan add(TaiKhoan taiKhoan);

    void delete(UUID id);

    List<TaiKhoan> getByKeyWord(String keyword);

    List<TaiKhoan> getTrangThai(Integer trangthai);

    TaiKhoan getAllKhachHang(UUID id);

    TaiKhoan findByHoaDonId(UUID id);

}
