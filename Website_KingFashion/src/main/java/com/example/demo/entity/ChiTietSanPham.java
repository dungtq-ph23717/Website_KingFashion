package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Table(name = "ChiTietSanPham")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChiTietSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "ma")
    private String ma;


    @ManyToOne
    @JoinColumn(name = "id_cl", referencedColumnName = "id")
    private ChatLieu chatLieu;


    @ManyToOne
    @JoinColumn(name = "id_sp", referencedColumnName = "id")
    private SanPham sanPham;


    @ManyToOne
    @JoinColumn(name = "id_ms", referencedColumnName = "id")
    private MauSac mauSac;


    @ManyToOne
    @JoinColumn(name = "id_img", referencedColumnName = "id")
    private Anh anh;


    @ManyToOne
    @JoinColumn(name = "id_loaisp", referencedColumnName = "id")
    private LoaiSanPham loaiSanPham;


    @ManyToOne
    @JoinColumn(name = "id_nsx", referencedColumnName = "id")
    private NhaSanXuat nhaSanXuat;


    @ManyToOne
    @JoinColumn(name = "id_kichco", referencedColumnName = "id")
    private KichCo kickCo;


    @ManyToOne
    @JoinColumn(name = "id_coao", referencedColumnName = "id")
    private CoAo coAo;


    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia_ban")
    private Integer giaBan;

    @Column(name = "ngay_sua")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySua;


    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayTao;

    @Column(name = "nguoi_tao")
    private String nguoiTao;


    @Column(name = "nguoi_sua")
    private String nguoiSua;


    @Column(name = "trang_thai")
    private Integer trangThai;

}