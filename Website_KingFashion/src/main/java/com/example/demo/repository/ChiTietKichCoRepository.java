package com.example.demo.repository;

import com.example.demo.entity.ChiTietKichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChiTietKichCoRepository extends JpaRepository<ChiTietKichCo, UUID> {

        List<ChiTietKichCo> findAllByChiTietSanPhamId(UUID ChiTietSanPhamId);

        ChiTietKichCo getByChiTietSanPhamIdAndKichCoId(UUID chiTietSanPhamId, UUID kichCoId);

        @Query(value = "select c from ChiTietKichCo c where c.trangThai = 1")
        List<ChiTietKichCo> findAllByChiTietSanPhamIdHoatDong(UUID ChiTietSanPhamId);

        List<ChiTietKichCo> findByChiTietSanPhamIdAndKichCoIdIn(UUID chiTietSanPhamId, List<UUID> kichCoIds);

}
