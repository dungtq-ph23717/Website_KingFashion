package com.example.demo.repository;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, UUID> {
    Optional<HoaDonChiTiet> findByHoaDonAndChiTietSanPham(HoaDon hoaDon, ChiTietSanPham chiTietSanPham);

    List<HoaDonChiTiet> getHoaDonChiTietByHoaDonId(UUID id);

    @Query(value = "select sum(so_luong * don_gia)  as tong_tien from HoaDonChiTiet join HoaDon on HoaDon.id = HoaDonChiTiet.id_hd where id_hd like ?1", nativeQuery = true)
    Double tongTien(UUID id);



}
