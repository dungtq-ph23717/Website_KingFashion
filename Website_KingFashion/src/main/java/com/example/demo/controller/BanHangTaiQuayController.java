package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.service.HoaDonChiTietService;
import com.example.demo.service.HoaDonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ban-hang-tai-quay/")
public class BanHangTaiQuayController {

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("hien-thi")
    public String hienThiTable(Model model){
        model.addAttribute("hdctlist",hoaDonChiTietService.getALl());
        return "BanHangTaiQuay/BanHangTaiQuay";
    }

    @PostMapping("add")
    public String addDonhang(@Valid @ModelAttribute("hd")HoaDon hoaDon, BindingResult result,Model model){

        return "BanHangTaiQuay/TaoDonHang";
    }
}
