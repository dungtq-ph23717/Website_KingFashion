package com.example.demo.repository;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.Voucher;
import jakarta.transaction.Transactional;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, UUID> {

    ChiTietSanPham getChiTietSanPhamById(UUID id);

    @Query(value = "select c from ChiTietSanPham c")
    Page<ChiTietSanPham> phanTrang(Pageable pageable);


//    @Query("SELECT a.ten, s.ten, c.giaBan, SUM(k.soLuong) , c.trangThai " +
//            "FROM KichCo k JOIN k.chiTietSanPham c " +
//            "JOIN c.anh a " +
//            "JOIN c.sanPham s " +
//            "GROUP BY a.ten, s.ten, c.giaBan, c.trangThai")
//    Page<ChiTietSanPham> phanTrang(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update ChiTietSanPham c set c.trangThai = 0 where c.id = :id")
    void delete(@Param("id") UUID id);


    @Query("SELECT v FROM ChiTietSanPham v JOIN v.sanPham s " +
            "WHERE (:ten IS NULL OR LOWER(s.ten) LIKE LOWER(CONCAT('%', :ten, '%')))\n" +
            "AND (:minTien IS NULL OR v.giaBan >= :minTien)\n" +
            "AND (:maxTien IS NULL OR v.giaBan <= :maxTien)")
    Page<ChiTietSanPham> search(@Param("ten") String ten,
                                @Param("minTien") Integer minTien,
                                @Param("maxTien") Integer maxTien,
                                Pageable pageable);



}
