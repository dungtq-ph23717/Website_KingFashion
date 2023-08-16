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

import java.util.UUID;

@Table(name = "GioHangChiTiet")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GioHangChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_gh", referencedColumnName = "id")
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "id_ctsp", referencedColumnName = "id")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "tong_tien")
    private Double tongTien;

}
