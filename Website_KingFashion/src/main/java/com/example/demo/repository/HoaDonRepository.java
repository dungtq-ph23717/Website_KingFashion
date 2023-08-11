package com.example.demo.repository;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface HoaDonRepository  extends JpaRepository<HoaDon, UUID> {
//    HoaDon getHoaDonById(UUID id);

    @Query("SELECT h FROM HoaDon h " +
            "WHERE (:maHoaDon is null OR h.maHoaDon LIKE lower(CONCAT('%', :maHoaDon, '%')))\n" +
            "  AND (:nguoiNhan is null OR h.nguoiNhan LIKE lower(CONCAT('%', :nguoiNhan, '%')))\n" +
            "  AND (:trangThai is NULL OR h.trangThai = :trangThai)\n" +
            "  AND (:ngayThanhToan IS NULL OR h.ngayThanhToan <= :ngayThanhToan)\n" +
            "  AND (:tongTienSauKhiGiam is null OR h.tongTienSauKhiGiam = :tongTienSauKhiGiam)\n" +
            "  AND (:ngayShip IS NULL OR h.ngayShip >= :ngayShip)\n" +
            "  AND (:ngayDuKienNhan IS NULL OR h.ngayDuKienNhan <= :ngayDuKienNhan)\n")
    Page<HoaDon> searchHD(@Param("maHoaDon") String maHoaDon, @Param("nguoiNhan") String tenNguoiNhan,
                         @Param("trangThai") Integer trangThai, @Param("ngayThanhToan") Date ngayThanhToan,
                         @Param("tongTienSauKhiGiam") Double tongTienSauKhiGiam,
                         @Param("ngayShip") Date ngayShip,@Param("ngayDuKienNhan") Date ngayDuKienNhan,
                          Pageable pageable);
}
