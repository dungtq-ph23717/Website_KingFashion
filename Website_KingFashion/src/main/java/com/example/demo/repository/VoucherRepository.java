package com.example.demo.repository;

import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, UUID> {
    @Query("SELECT v FROM Voucher v " +
            "WHERE (:ma is null OR v.ma LIKE lower(CONCAT('%', :ma, '%')))\n" +
            "  AND (:ten is null OR v.ten LIKE lower(CONCAT('%', :ten, '%')))\n" +
            "  AND (:mucGiam is null OR v.mucGiam <= :mucGiam)\n" +
            "  AND (:tien is null OR v.tien <= :tien)\n" +
            "  AND (:thoiGianBatDau IS NULL OR v.thoiGianBatDau <= :thoiGianBatDau)\n" +
            "  AND (:thoiGianKetThuc IS NULL OR v.thoiGianKetThuc >= :thoiGianKetThuc)\n" +
            "  AND (:trangThai is null OR v.trangThai = :trangThai)")
    Page<Voucher> search(@Param("ma") String ma, @Param("ten") String ten,@Param("mucGiam") Integer mucGiam, @Param("tien") Double tien,
                         @Param("thoiGianBatDau") Date thoiGianBatDau, @Param("thoiGianKetThuc") Date thoiGianKetThuc, @Param("trangThai") Integer trangThai,
                         Pageable pageable);

    List<Voucher> findByThoiGianKetThucAfterAndTrangThaiNot(Date endDate, Integer status);
}
