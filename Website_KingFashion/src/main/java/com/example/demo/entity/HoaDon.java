package com.example.demo.entity;


import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Table(name = "HoaDon")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_tk", referencedColumnName = "id")
    private TaiKhoan taiKhoan;

    @Column(name = "ma")
    private String maHoaDon;

    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    @Column(name = "ngay_thanh_toan")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayThanhToan;

    @Column(name = "tong_tien")
    private Double tongTien;


    @Column(name = "tong_tien_sau_khi_giam")
    private Double tongTienSauKhiGiam;

    @Column(name = "trang_thai")
    private Integer trangThai;

//    @Column(name = "ten_nguoi_nhan")
//    private String tenNguoiNhan;

    @Column(name = "dia_chi")
    private String diaChi;


    @Column(name = "sdt")
    private String soDienThoai;

    @Column(name = "ngay_ship")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayShip;

    @Column(name = "ngay_du_kien_nhan")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayDuKienNhan;

    @Column(name = "tien_ship")
    private Double tienShip;

    @Column(name = "ngay_sua")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date ngaySua;

    @Column(name = "nguoi_tao")
    private String nguoiTaoHoaDon;

    @Column(name = "nguoi_nhan")
    private String nguoiNhan;

    @Column(name = "ngay_nhan")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date ngayNhan;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "loai_don")
    private Integer loaiDon;

    @OneToMany(mappedBy = "hoaDon",fetch = FetchType.LAZY)
    private List<HoaDonChiTiet> hoaDonChiTietList;

    @OneToMany(mappedBy = "hoaDon",fetch = FetchType.LAZY)
    private List<Voucher_HoaDon> voucher_hoaDonList;
}
