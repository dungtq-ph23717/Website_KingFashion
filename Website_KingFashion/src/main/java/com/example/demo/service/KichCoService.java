package com.example.demo.service;

import com.example.demo.entity.KichCo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface KichCoService {
    List<KichCo> getAll();

    Page<KichCo> phanTrang(Integer pageNum, Integer pageNo);

    void add(KichCo kichCo);

    KichCo detail(UUID id);

    void delete(UUID id);

    List<KichCo> getKichCoByChiTietSanPhamId(UUID chiTietSanPhamId);
}
