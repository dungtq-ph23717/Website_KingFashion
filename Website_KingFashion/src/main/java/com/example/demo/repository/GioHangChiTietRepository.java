package com.example.demo.repository;

import com.example.demo.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, UUID> {

    @Query(value = "select h.id,ctsp.id,sp.id,a.id, g.id, a.ten, sp.ten, ctsp.gia_ban, g.so_luong, ctsp.gia_ban * g.so_luong as tong_tien\n" +
            "from GioHangChiTiet g\n" +
            "join GioHang h on g.id_gh = h.id\n" +
            "join ChiTietSanPham ctsp on g.id_ctsp = ctsp.id\n" +
            "join SanPham sp on sp.id = ctsp.id_sp\n" +
            "join Anh a on ctsp.id = a.id_ctsp", nativeQuery = true)
    List<GioHangChiTiet> getAll();

    @Query(value = "select SUM(tong_tien) as tong_tien\n" +
            "from GioHangChiTiet", nativeQuery = true)
    Double tongTien();
}
