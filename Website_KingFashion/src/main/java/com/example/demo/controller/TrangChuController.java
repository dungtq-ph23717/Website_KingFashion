package com.example.demo.controller;

import com.example.demo.entity.Anh;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.SanPham;
import com.example.demo.repository.AnhRepository;
import com.example.demo.service.AnhService;
import com.example.demo.service.ChatLieuService;
import com.example.demo.service.ChiTietKichCoService;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.CoAoService;
import com.example.demo.service.KichCoService;
import com.example.demo.service.LoaiSanPhamService;
import com.example.demo.service.MauSacService;
import com.example.demo.service.NhaSanXuatService;
import com.example.demo.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class TrangChuController {

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private AnhService anhService;

    @Autowired
    private AnhRepository anhRepository;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private KichCoService kichCoService;

    @Autowired
    private CoAoService coAoService;

    @Autowired
    private LoaiSanPhamService loaiSanPhamService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private NhaSanXuatService nhaSanXuatService;

    @Autowired
    private ChatLieuService chatLieuService;

    @Autowired
    private ChiTietKichCoService chiTietKichCoService;

    @GetMapping("/trang-chu")
    public String trangChu() {
        return "trang-chu/trang-chu";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable UUID id,Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        model.addAttribute("ctsp", chiTietSanPham);
        List<ChiTietSanPham> list = chiTietSanPhamService.getAll();
        model.addAttribute("list",list);
        List<Anh> listAnh = anhService.getAllByChiTietSanPhamId(id);
        List<Anh> filteredAnhList = new ArrayList<>();
        for (Anh anh : listAnh) {
            if (anh.getChiTietSanPham().getId().equals(id)) {
                filteredAnhList.add(anh);
            }
        }
        model.addAttribute("listAnh", filteredAnhList);
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listKichCo", kichCoService.getAll());
        return "trang-chu/detail";
    }

    @GetMapping("/san-pham")
    public String sanPham(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model, HttpSession session) {
        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        List<SanPham> listSanPham = sanPhamService.getAll();
        Page<ChiTietSanPham> page = chiTietSanPhamService.PhanTrang(pageNum, 5);
        model.addAttribute("listSanPham", listSanPham);
        model.addAttribute("list", page);
        model.addAttribute("listCoAo", coAoService.getAll());
        model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listHang", mauSacService.getAll());
        model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
        model.addAttribute("listChatLieu", chatLieuService.getAll());
        model.addAttribute("att", new ChiTietSanPham());
        return "trang-chu/san-pham";
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
