package com.example.demo.repository;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.LichSuHoaDon;
import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonRepository  extends JpaRepository<HoaDon, UUID> {
    HoaDon getHoaDonById(UUID id);

    @Query("SELECT h FROM HoaDon h " +
            "WHERE (:maHoaDon IS NULL OR h.maHoaDon LIKE CONCAT('%', :maHoaDon, '%')) " +
            "AND (:nguoiNhan IS NULL OR h.nguoiNhan LIKE CONCAT('%', :nguoiNhan, '%')) " +
            "AND (:tongTienSauKhiGiam IS NULL OR h.tongTienSauKhiGiam = :tongTienSauKhiGiam) " +
            "AND (:trangThai IS NULL OR h.trangThai = :trangThai) " +
            "AND (:tuNgay IS NULL OR h.ngayTao >= :tuNgay) " +
            "AND (:denNgay IS NULL OR h.ngayTao <= :denNgay) " +
            "AND (:loaiDon IS NULL OR h.loaiDon = :loaiDon)")
    Page<HoaDon> searchHD(@Param("maHoaDon") String maHoaDon, @Param("nguoiNhan") String tenNguoiNhan,
                          @Param("tongTienSauKhiGiam") Double tongTienSauKhiGiam,
                          @Param("trangThai") Integer trangThai, @Param("tuNgay") Date tuNgay,
                          @Param("denNgay") Date denNgay, @Param("loaiDon") Integer loaiDon,
                          Pageable pageable);
    @Query(value = "select HoaDon.id from HoaDon join HoaDonChiTiet on HoaDon.id = HoaDonChiTiet.id_hd where HoaDonChiTiet.id like ?1", nativeQuery = true)
    HoaDon getHoaDonByHoaDonChiTietId(UUID id);

    @Query("SELECT hd FROM HoaDon hd JOIN hd.hoaDonChiTietList hdt WHERE hdt.id = :id")
    HoaDon findByHoaDonChiTietId(@Param("id") UUID id);

    @Query(value = "select hd.tongTienSauKhiGiam from HoaDon hd where hd.id = ?1")
    Double tongTienSauGiam(UUID id);

}
