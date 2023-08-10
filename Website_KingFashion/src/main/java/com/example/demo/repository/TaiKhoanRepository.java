package com.example.demo.repository;

import com.example.demo.entity.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, UUID> {
    @Query("SELECT v FROM TaiKhoan v join v.vaiTro t " +
            "WHERE (:maTaiKhoan is null or v.maTaiKhoan LIKE lower(CONCAT('%', :maTaiKhoan, '%')))\n" +
            "AND (:tenTaiKhoan is null or v.tenTaiKhoan LIKE lower(CONCAT('%', :tenTaiKhoan, '%')))\n" +
            "AND (:sdt is null or v.sdt LIKE lower(CONCAT('%', :sdt, '%')))\n" +
            "AND (:email is null or v.email LIKE lower(CONCAT('%', :email, '%')))" +
            "AND (:diaChi is null or v.diaChi LIKE lower(CONCAT('%', :diaChi, '%')))" +
            "AND (:ngaySinh IS NULL OR v.ngaySinh = :ngaySinh)" +
            "AND t.tenVaiTro LIKE lower(CONCAT('%', 'Khách hàng', '%'))")
    Page<TaiKhoan> search(@Param("maTaiKhoan") String maTaiKhoan, @Param("tenTaiKhoan") String tenTaiKhoan, @Param("sdt") String sdt, @Param("email") String email,
                          @Param("diaChi") String diaChi, @Param("ngaySinh") Date ngaySinh, Pageable pageable);

    @Query("SELECT t FROM TaiKhoan t JOIN t.vaiTro v WHERE v.tenVaiTro LIKE lower(CONCAT('%', 'Khách hàng', '%'))")
    Page<TaiKhoan> getAllNhanVien(Pageable pageable);

    @Query(value = "select * from TaiKhoan tk where tk.ma like %:keyword% or tk.ten like %:keyword% or tk.sdt like %:keyword% or tk.dia_chi like %:keyword% or tk.email like %:keyword%", nativeQuery = true)
    List<TaiKhoan> findByKeyWord(@Param("keyword") String keyword);

    List<TaiKhoan> findByTrangThai(Integer trangThai);
    @Query("SELECT t FROM TaiKhoan t JOIN t.vaiTro v WHERE v.tenVaiTro LIKE lower(CONCAT('%', 'Nhân viên', '%'))")
    Page<TaiKhoan> getAllNhanvien1(Pageable pageable);
}
