package com.example.demo.controller;

import com.example.demo.entity.Anh;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.entity.Voucher;
import com.example.demo.service.AnhService;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.HoaDonChiTietService;
import com.example.demo.service.HoaDonService;
import com.example.demo.service.TaiKhoanService;
import com.example.demo.service.VaiTroService;
import com.example.demo.service.impl.VoucherServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/ban-hang-tai-quay/")
public class BanHangTaiQuayController {

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private HoaDonService hoaDonService;


    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private AnhService anhService;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private VaiTroService vaiTroService;

    @Autowired
    private VoucherServiceImpl voucherService;

    @GetMapping("hien-thi")
    public String hienThiTable(Model model){
        model.addAttribute("hdctlist",hoaDonChiTietService.getALl());
        model.addAttribute("hdct",new HoaDonChiTiet());
        return "BanHangTaiQuay/BanHangTaiQuay";
    }

    @PostMapping("carts")
    public String addDonhang(@ModelAttribute("hdct") HoaDonChiTiet hoaDonChiTiet, RedirectAttributes redirectAttributes, Model model){

        HoaDon hoaDon = new HoaDon();
        String ma = "HD" + new Random().nextInt(100000);
        hoaDon.setMaHoaDon(ma);
        hoaDon.setNgayTao(new Date());
        hoaDon.setTrangThai(0);
        hoaDonService.add(hoaDon);

        hoaDonChiTiet.setHoaDon(hoaDon);

        hoaDonChiTietService.add(hoaDonChiTiet);
        model.addAttribute("hdct",hoaDonChiTiet);
        redirectAttributes.addAttribute("id", hoaDonChiTiet.getId());
        return "redirect:/ban-hang-tai-quay/viewcart/{id}";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("hdct") HoaDonChiTiet hoaDonChiTiet,
                         @RequestParam("id")UUID id,
                         RedirectAttributes redirectAttributes, Model model){
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);

        hoaDonChiTiet.setChiTietSanPham(chiTietSanPham);
        hoaDonChiTietService.add(hoaDonChiTiet);

        model.addAttribute("ctsp",chiTietSanPham);
        model.addAttribute("hdct",hoaDonChiTiet);

        redirectAttributes.addAttribute("idd", hoaDonChiTiet.getId());
        return "redirect:/ban-hang-tai-quay/viewcart/{idd}";
    }

    @GetMapping("viewcart/{id}")
    public String showSanPham(@PathVariable UUID id, Model model){
        List<ChiTietSanPham> list = chiTietSanPhamService.getAll();
        List<TaiKhoan> list1 = taiKhoanService.getAll();
        List<Voucher> list2 = voucherService.getAll();
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        model.addAttribute("hdct",new HoaDonChiTiet());
        model.addAttribute("ctsp",chiTietSanPham);
        model.addAttribute("list1", list1);
        model.addAttribute("list2", list2);
        model.addAttribute("list",list);
        return "BanHangTaiQuay/TaoDonHang";
    }

    @GetMapping("display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") UUID id) throws IOException, SQLException {
        Anh image = anhService.viewById(id);
        byte[] imageBytes = image.getTen().getBytes(1, (int) image.getTen().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @GetMapping("display1")
    public ResponseEntity<byte[]> displayImage1(@RequestParam("id") UUID id) throws IOException, SQLException {
        Anh anh = anhService.getAnhById(id);

        // Kiểm tra nếu trường image của đối tượng Anh là null thì xử lý hoặc thông báo lỗi tùy ý
        if (anh.getTen() != null) {
            // Convert Blob to byte[]
            byte[] imageData = convertBlobToBytes(anh.getTen());

            // Thiết lập các header cho response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Hoặc MediaType.IMAGE_PNG tùy loại ảnh

            // Trả về response chứa dữ liệu của ảnh
            return ResponseEntity.ok().headers(headers).body(imageData);
        } else {
            // Xử lý trường hợp image là null, ví dụ:
            String errorResponse = "Ảnh không khả dụng"; // Hoặc bạn có thể đưa ra thông báo lỗi khác tùy ý

            // Trả về response chứa thông báo lỗi
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return ResponseEntity.badRequest().headers(headers).body(errorResponse.getBytes());
        }
    }

    private byte[] convertBlobToBytes(Blob blob) throws IOException, SQLException {
        try (InputStream inputStream = blob.getBinaryStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }

}
