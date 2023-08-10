package com.example.demo.repository;

import com.example.demo.entity.Anh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnhRepository extends JpaRepository<Anh, UUID> {

    Anh getAnhById(UUID id);

    @Query(value = "SELECT a FROM Anh a JOIN a.chiTietSanPham c WHERE c.id = ?1")
    Anh findAllAnhByCTSP(UUID id);

    Anh findFirstByChiTietSanPhamId(UUID id);

    @Query("SELECT a FROM Anh a WHERE a.chiTietSanPham.id = :chiTietSanPhamId")
    List<Anh> findAllByChiTietSanPhamId(@Param("chiTietSanPhamId") UUID chiTietSanPhamId);

    List<Anh> findAllByIdIn(List<UUID> ids);

}
