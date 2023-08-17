package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.LichSuHoaDon;
import com.example.demo.service.LichSuHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/lich-su-hoa-don/")
public class LichSuHoaDonController {

    @Autowired
    private LichSuHoaDonService lichSuHoaDonService;



//    @GetMapping("hien-thi")
//    public String hienThiLichSuHoaDon(Model model){
//        List<LichSuHoaDon> listLSHD = lichSuHoaDonService.getAll();
//        model.addAttribute("listLSHD", listLSHD);
//        return "hoadon/hoa-don-chi-tiet";
//    }


}
