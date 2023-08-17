package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Table(name = "TaiKhoan")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_vt", referencedColumnName = "id")
    private VaiTro vaiTro;

    @NotBlank(message = "Không được để trống")
    @Column(name = "ma")
    private String maTaiKhoan;

    @NotBlank(message = "Không được để trống")
    @Column(name = "ten")
    private String tenTaiKhoan;

    @NotBlank(message = "Không được để trống")
    @Column(name = "sdt")
    private String sdt;

    @NotBlank(message = "Không được để trống")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Không được để trống")
    @Column(name = "dia_chi")
    private String diaChi;


    @Column(name = "ngay_sinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySinh;


    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySua;


    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_sua")
    private String nguoiSua;

    @NotBlank(message = "Không được để trống")
    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "taiKhoan",fetch = FetchType.LAZY)
    private List<HoaDon> hoaDonList;


}
