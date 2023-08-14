package com.example.demo.controller;

import com.example.demo.entity.TaiKhoan;
import com.example.demo.entity.VaiTro;
import com.example.demo.service.TaiKhoanService;
import com.example.demo.service.VaiTroService;
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

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/khach-hang")
public class TaiKhoanController1 {
    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private VaiTroService vaiTroService;

    @GetMapping("/hien-thi")
    public String hienThi(Model model, @RequestParam(name = "page", defaultValue = "0") Integer p) {
        Page<TaiKhoan> page = taiKhoanService.page(p, 5);
        model.addAttribute("list", page);
        model.addAttribute("search", new TaiKhoan());
        return "khach-hang/home";
    }

    @GetMapping("/search")
    public String search(Model model, @ModelAttribute("search") TaiKhoan taiKhoan, @RequestParam(name = "page", defaultValue = "0") Integer p, @RequestParam("maTaiKhoan") String maTaiKhoan) {
        Page<TaiKhoan> list = taiKhoanService.search(taiKhoan.getMaTaiKhoan(), taiKhoan.getTenTaiKhoan(), taiKhoan.getSdt(), taiKhoan.getEmail(), taiKhoan.getDiaChi(), taiKhoan.getNgaySinh(), 5, p);
        model.addAttribute("list", list);
        return "khach-hang/home";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        model.addAttribute("khachhang", new TaiKhoan());
        return "khach-hang/add";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, Model model) {
        taiKhoanService.delete(id);
        return "redirect:/khach-hang/hien-thi";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable UUID id, Model model) {
        TaiKhoan taiKhoan = taiKhoanService.detail(id);
        model.addAttribute("khachhang", taiKhoan);
        List<VaiTro> vaiTroList=vaiTroService.getAll();
        model.addAttribute("vaiTro",vaiTroList);
        return "khach-hang/update";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("khachhang") TaiKhoan taiKhoan, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "khach-hang/add";
        }
        taiKhoanService.add(taiKhoan);
        return "redirect:/khach-hang/hien-thi";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("khachhang") TaiKhoan taiKhoan, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<VaiTro> vaiTros = vaiTroService.getAll();
            model.addAttribute("vaiTro", vaiTros);
            return "khach-hang/update";
        }
        taiKhoanService.update(taiKhoan);
        return "redirect:/khach-hang/hien-thi";
    }
}
