package com.example.demo.service.impl;

import com.example.demo.entity.TaiKhoan;
import com.example.demo.entity.VaiTro;
import com.example.demo.repository.TaiKhoanRepository;
import com.example.demo.repository.VaiTroRepository;
import com.example.demo.service.TaiKhoanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Override
    public List<TaiKhoan> getAll() {
        return taiKhoanRepository.findAll();
    }

    @Override
    public Page<TaiKhoan> page(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return taiKhoanRepository.getAllNhanVien(pageable);
    }

    @Override
    public Page<TaiKhoan> getAllNhanVien(Integer page, Integer size) {
        Pageable pageable=PageRequest.of(page,size);
        return taiKhoanRepository.getAllNhanvien1(pageable);
    }


    @Override
    public Page<TaiKhoan> search(String ma, String ten, String sdt, String email, String diaChi, Date ngaySinh, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        return taiKhoanRepository.search(ma, ten, sdt, diaChi, email, ngaySinh, pageable);
    }

    @Override
    public TaiKhoan detail(UUID id) {
        return taiKhoanRepository.findById(id).get();
    }

    @Override
    public TaiKhoan update(TaiKhoan taiKhoan) {
        return taiKhoanRepository.save(taiKhoan);
    }

    @Override
    public TaiKhoan add(TaiKhoan taiKhoan) {
        return taiKhoanRepository.save(taiKhoan);
    }


    @Override
    public void delete(UUID id) {
        taiKhoanRepository.deleteById(id);
    }

    @Override
    public List<TaiKhoan> getByKeyWord(String keyword) {
        return taiKhoanRepository.findByKeyWord(keyword);
    }

    @Override
    public List<TaiKhoan> getTrangThai(Integer trangthai) {
        return taiKhoanRepository.findByTrangThai(trangthai);
    }

    @Override
    public TaiKhoan getAllKhachHang(UUID id) {
        return taiKhoanRepository.getAllKhachHang(id);
    }

    @Override
    public TaiKhoan findByHoaDonId(UUID id) {
        return taiKhoanRepository.findByHoaDonId(id);
    }
};



