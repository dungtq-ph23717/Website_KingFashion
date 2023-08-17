package com.example.demo.repository;

import com.example.demo.entity.LichSuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LichSuHoaDonRepository extends JpaRepository<LichSuHoaDon, UUID> {
    LichSuHoaDon findLichSuHoaDonById(UUID id);

//    @Query(value = "SELECT LS FROM HoaDon HD JOIN LichSuHoaDon LS " +
//            "ON LS.id_hd = HD.id WHERE HD.id LIKE ?1",nativeQuery = true)
    List<LichSuHoaDon> findLichSuHoaDonByHoaDonId(UUID id);
}
