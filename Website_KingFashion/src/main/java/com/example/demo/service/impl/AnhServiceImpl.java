package com.example.demo.service.impl;

import com.example.demo.entity.Anh;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.repository.AnhRepository;
import com.example.demo.repository.ChiTietSanPhamRepository;
import com.example.demo.service.AnhService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnhServiceImpl implements AnhService {

    @Autowired
    private AnhRepository anhRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Override
    public List<Anh> viewAll() {
        return anhRepository.findAll();
    }

    @Override
    public Anh viewById(UUID id) {
        return anhRepository.findFirstByChiTietSanPhamId(id);
    }

    @Override
    public Anh viewAllById(UUID id) {
        return anhRepository.findAllAnhByCTSP(id);
    }

    @Override
    public Page<Anh> phanTrang(Integer pageNum, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNum,pageNo);
        return anhRepository.findAll(pageable);
    }

    @Override
    public void add(Anh anh, MultipartFile[] files) throws SQLException, IOException {
        for (MultipartFile file : files) {
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            anh.setMa("abc");
            anh.setTen(blob);
            anh.setTrangThai(1);
            anhRepository.save(anh);
        }
    }



    @Override
    public Anh detail(UUID id) {
        return anhRepository.getAnhById(id);
    }

    @Override
    public void delete(UUID id) {
        anhRepository.deleteById(id);
    }

    @Override
    public List<Anh> getAllByChiTietSanPhamId(UUID chiTietSanPhamId) {
        return anhRepository.findAllByChiTietSanPhamId(chiTietSanPhamId);
    }

    @Override
    public Anh getAnhById(UUID id) {
        return anhRepository.findById(id).orElse(null);
    }


}
