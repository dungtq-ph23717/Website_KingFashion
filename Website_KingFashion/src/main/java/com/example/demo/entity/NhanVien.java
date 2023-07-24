package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Table(name = "TaiKhoan")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "ma")
    @NotBlank(message = "Không được để trống mã")
    private String maNhanVien;

    @Column(name = "ten")
    @NotBlank(message = "Không được để trống tên")
    private String tenNhanVien;

    @Column(name = "sdt")
    @NotBlank(message = "Không được để trống số điện thoại")
    private String sdt;

    @Column(name = "email")
    @NotBlank(message = "Không được để trống email")
    private String email;

    @Column(name = "dia_chi")
    @NotBlank(message = "Không được để trống địa chỉ")
    private String diaChi;

    @Column(name = "ngay_sinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Không được để trống ngày sinh")
    private Date ngaySinh;

    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Không được để trống ngày tạo")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Không được để trống ngày sửa")
    private Date ngaySua;

    @Column(name = "nguoi_tao")
    @NotBlank(message = "Không được để trống người tạo")
    private String nguoiTao;

    @Column(name = "nguoi_sua")
    @NotBlank(message = "Không được để trống người sửa")
    private String nguoiSua;

    @Column(name = "mat_khau")
    @NotBlank(message = "Không được để trống mật khẩu")
    private String matKhau;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vt", referencedColumnName = "id")
    private VaiTro vaiTro;
}
