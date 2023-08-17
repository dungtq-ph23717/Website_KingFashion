package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Table(name = "HoaDonChiTiet")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HoaDonChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;


    @ManyToOne
    @JoinColumn(name ="id_hd", referencedColumnName = "id")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name= "id_ctsp", referencedColumnName = "id")
    private ChiTietSanPham chiTietSanPham;


    @Column(name = "don_gia")
    private Double donGia;


    @Column(name = "so_luong")
    private Integer soLuong;

}
