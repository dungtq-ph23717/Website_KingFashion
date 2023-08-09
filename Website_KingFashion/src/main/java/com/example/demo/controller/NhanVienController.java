package com.example.demo.controller;

import com.example.demo.entity.TaiKhoan;
import com.example.demo.service.impl.NhanVienServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller

public class NhanVienController {
    @Autowired
    private NhanVienServiceImpl service;

//    @GetMapping("/hien-thi")
//    public String hienThi(Model model, @RequestParam(name = "page", defaultValue = "0") Integer p) {
//        Page<TaiKhoan> page = service.page(p, 5);
//        model.addAttribute("list", page);
//        model.addAttribute("nhanvien", new NhanVien());
//        return "nhanvien/nhan-vien";
//    }
//
//    @GetMapping("/view-add")
//    public String viewAdd(Model model) {
//        model.addAttribute("nhanvien", new NhanVien());
//        return "nhanvien/add";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable UUID id, Model model) {
//        service.deleteById(id);
//        return "redirect:/nhan-vien/hien-thi";
//    }
//
//    @GetMapping("/view-update/{id}")
//    public String viewUpdate(@PathVariable UUID id, Model model) {
//        NhanVien nhanVien = service.detail(id);
//        model.addAttribute("nhanvien", nhanVien);
//        return "nhanvien/update";
//    }
//
//    @PostMapping("/add")
//    public String add(@Valid @ModelAttribute("nhanvien") NhanVien nhanVien, BindingResult result, Model model) {
////        if(result.hasErrors()){
////            return "nhanvien/add";
////        }
//        nhanVien.setNgayTao(new Date());
//        nhanVien.setNgaySua(new Date());
//        model.addAttribute("nhanvien", nhanVien);
//        service.save(nhanVien);
//        return "redirect:/nhan-vien/hien-thi";
//    }
//
//    @PostMapping("/update")
//    public String update(@Valid @ModelAttribute("nhanvien") NhanVien nhanVien, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
////        if(result.hasErrors()){
////            return "nhanvien/update";
////        }
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date ngayTaoDate;
//        try {
//            ngayTaoDate = dateFormat.parse(ngayTao);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return "redirect:/error";
//        }
//        nhanVien.setNgayTao(ngayTaoDate);
//        nhanVien.setNgaySua(new Date());
//        service.save(nhanVien);
//        model.addAttribute("nhanvien", new NhanVien());
//        return "redirect:/nhan-vien/hien-thi";
//    }
//    @GetMapping("/search")
//    public String search(Model model, @ModelAttribute("nhanvien") NhanVien nhanVien, @RequestParam(name = "page", defaultValue = "0") Integer p){
//        Page<NhanVien> list = service.search(nhanVien.getTenNhanVien(),nhanVien.getSdt(),nhanVien.getEmail(),nhanVien.getDiaChi(),nhanVien.getNgaySinh(),5,p);
//        model.addAttribute("list",list);
//        return "nhanvien/nhan-vien";
//    }
}
