package com.example.demo.controller;

import com.example.demo.entity.TaiKhoan;
import com.example.demo.entity.VaiTro;
import com.example.demo.service.TaiKhoanService;
import com.example.demo.service.VaiTroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/nhan-vien")
public class TaiKhoanController {
    @Autowired
    private TaiKhoanService taiKhoanService;
    @Autowired
    private VaiTroService vaiTroService;

    @GetMapping("/hien-thi")
    public String hienthi(HttpSession session, Model model, @RequestParam(value = "page", defaultValue = "0") Integer number,String keyword) {
        if (session.getAttribute("successMessage") != null) {
            String successAtribute = (String) session.getAttribute(("successMessage"));
            model.addAttribute("successMessage", successAtribute);
            session.removeAttribute("successMessage");
        }
        model.addAttribute("listVaiTro", vaiTroService.getAll());
        Page<TaiKhoan> page = taiKhoanService.getAllNhanVien(number, 5);
        List<TaiKhoan> listserach = taiKhoanService.getByKeyWord(keyword);
        model.addAttribute("listtaikhoan", listserach);
        model.addAttribute("listtaikhoan", page);
        return "nhanvien/nhan-vien";
    }

//    @GetMapping("/search")
//    public String search(Model model, @ModelAttribute("search") TaiKhoan taiKhoan, @RequestParam(name = "page", defaultValue = "0") Integer p, @RequestParam("maTaiKhoan") String maTaiKhoan) {
//        Page<TaiKhoan> list = taiKhoanService.search(taiKhoan.getMaTaiKhoan(), taiKhoan.getTenTaiKhoan(), taiKhoan.getSdt(), taiKhoan.getEmail(), taiKhoan.getDiaChi(), taiKhoan.getNgaySinh(), 5, p);
//        model.addAttribute("list", list);
//        return "khach-hang/home";
//    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        model.addAttribute("nhanvien", new TaiKhoan());
        return "/nhanvien/add";
    }

    @GetMapping("/deletenhanvien/{id}")
    public String delete(@PathVariable UUID id, Model model) {
        taiKhoanService.delete(id);
        return "redirect:/nhan-vien/hien-thi";
    }

    @GetMapping("/view1-update/{id}")
    public String viewUpdate(@PathVariable UUID id, Model model) {
        TaiKhoan taiKhoan = taiKhoanService.detail(id);
        model.addAttribute("nhanvien", taiKhoan);
        List<VaiTro> listVaiTro=vaiTroService.getAll();
        model.addAttribute("listVaiTro",listVaiTro);
        return "/nhanvien/update";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("nhanvien") TaiKhoan taiKhoan, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/nhanvien/add";
        }
        Date date = new Date();
        taiKhoan.setNgayTao(date);
        taiKhoan.setNgaySua(date);
        taiKhoanService.add(taiKhoan);
        return "redirect:/nhan-vien/hien-thi";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("nhanvien") TaiKhoan taiKhoan, BindingResult result,Model model) {
        if (result.hasErrors()) {
            List<VaiTro> listVaiTro=vaiTroService.getAll();
            model.addAttribute("listVaiTro",listVaiTro);
            return "/nhanvien/update";
        }
        taiKhoanService.update(taiKhoan);
        return "redirect:/nhan-vien/hien-thi";
    }


    @GetMapping("/serach")
    public String Serach(TaiKhoan taiKhoan, String keyword, Model model) {
        if (keyword != null) {
            List<TaiKhoan> listserach = taiKhoanService.getByKeyWord(keyword);
            model.addAttribute("listtaikhoan", listserach);
            return "/nhanvien/nhan-vien";

        }
        return "redirect:/nhan-vien/hien-thi";
    }

    @GetMapping("/fiter-trangthai")
    public String getFilteredtrangThai(@RequestParam(name = "trangThai") Integer trangthai, Model model) {
        List<TaiKhoan> taiKhoans = taiKhoanService.getTrangThai(trangthai);
        model.addAttribute("listtaikhoan", taiKhoans);
        return "/nhanvien/nhan-vien";
    }

}
