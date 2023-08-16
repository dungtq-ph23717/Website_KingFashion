package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name = "GioHang")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String ma;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_thanh_toan")
    private Date ngayThanhToan;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ten_nguoi_nhan")
    private String tenNguoiNhan;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "dia_chi")
    private String diaChi;

    @ManyToOne
    @JoinColumn(name = "id_tk", referencedColumnName = "id")
    private TaiKhoan taiKhoan;

}
