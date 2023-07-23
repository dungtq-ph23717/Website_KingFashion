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

import java.util.List;
import java.util.UUID;

@Service
public class TaiKhoanServiceImpl  implements TaiKhoanService {

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
        return taiKhoanRepository.findAll(pageable);
    }

    @Override
    public TaiKhoan detail(UUID id) {
        return taiKhoanRepository.findById(id).orElse(null);
    }

    @Override
    public void add(TaiKhoan taiKhoan) {
        taiKhoanRepository.save(taiKhoan);
    }

    @Override
    public void delete(UUID id) {
        taiKhoanRepository.deleteById(id);
    }

}
