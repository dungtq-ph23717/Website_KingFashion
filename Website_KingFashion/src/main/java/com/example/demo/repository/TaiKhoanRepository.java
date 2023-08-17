package com.example.demo.repository;

import com.example.demo.entity.HoaDon;
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

    @Query("SELECT t FROM TaiKhoan t JOIN t.vaiTro v WHERE t.id = ?1 and v.tenVaiTro LIKE lower(CONCAT('%', 'Khách hàng', '%'))")
    TaiKhoan getAllKhachHang(UUID id);

    @Query("SELECT t FROM TaiKhoan t JOIN t.hoaDonList hd WHERE hd.id = :id")
    TaiKhoan findByHoaDonId(@Param("id") UUID id);

    @Query(value = "SELECT tk.id, tk.ma, tk.dia_chi, tk.email, tk.sdt, tk.ten, tk.ngay_sinh, tk.ngay_tao, tk.ngay_sua, tk.nguoi_tao,\n" +
            "       tk.nguoi_sua, tk.mat_khau, tk.trang_thai, tk.id_vt\n" +
            "FROM TaiKhoan tk\n" +
            "JOIN vaiTro vt ON tk.id_vt = vt.id\n" +
            "WHERE (tk.trang_thai = 1 OR tk.trang_thai = 0)\n" +
            "      AND (vt.ten LIKE LOWER(CONCAT('%', 'Nhân viên', '%')))\n" +
            "      AND vt.ten = 'Nhân viên'", nativeQuery = true)
    List<TaiKhoan> findByTrangThai(@Param("trangthai") Integer trangThai);

    @Query(value = "SELECT tk.id, tk.ma, tk.dia_chi, tk.email, tk.sdt, tk.ten, tk.ngay_sinh, tk.ngay_tao, tk.ngay_sua, tk.nguoi_tao,\n" +
            "       tk.nguoi_sua, tk.mat_khau, tk.trang_thai, tk.id_vt\n" +
            "FROM TaiKhoan tk\n" +
            "JOIN VaiTro vt ON tk.id_vt = vt.id\n" +
            "WHERE (tk.ma LIKE CONCAT('%', :keyword, '%') OR tk.dia_chi LIKE CONCAT('%', :keyword, '%')\n" +
            "        OR tk.email LIKE CONCAT('%', :keyword, '%') OR tk.ten LIKE CONCAT('%', :keyword, '%')\n" +
            "        OR tk.sdt LIKE CONCAT('%', :keyword, '%') OR tk.mat_khau LIKE CONCAT('%', :keyword, '%'))\n" +
            "    AND LOWER(vt.ten) LIKE LOWER(CONCAT('%', 'Nhân viên', '%'))\n ", nativeQuery = true)
    List<TaiKhoan> findByKeyWord(@Param("keyword") String keyword);


    @Query("SELECT t FROM TaiKhoan t JOIN t.vaiTro v WHERE v.tenVaiTro LIKE lower(CONCAT('%', 'Nhân viên', '%'))")
    Page<TaiKhoan> getAllNhanvien1(Pageable pageable);
}
