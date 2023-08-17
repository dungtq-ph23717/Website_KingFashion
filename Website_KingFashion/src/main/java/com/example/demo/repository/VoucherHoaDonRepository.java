package com.example.demo.repository;

import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.Voucher_HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoucherHoaDonRepository extends JpaRepository<Voucher_HoaDon, UUID> {
    List<Voucher_HoaDon> getVoucher_HoaDonByHoaDonId(UUID id);

    @Query(value = "SELECT SUM(CONVERT(float, KhuyenMai.muc_giam)) as tong_tien_muc_giam\n" +
            "FROM KhuyenMai_HoaDon\n" +
            "JOIN KhuyenMai ON KhuyenMai.id = KhuyenMai_HoaDon.id_km\n" +
            "JOIN HoaDon ON HoaDon.id = KhuyenMai_HoaDon.id_hd\n" +
            "WHERE KhuyenMai_HoaDon.id_hd = ?1", nativeQuery = true)
    Double tongTienMucGiam(UUID id);

}
