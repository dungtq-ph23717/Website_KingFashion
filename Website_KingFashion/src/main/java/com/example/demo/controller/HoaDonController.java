package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.LichSuHoaDon;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.service.HoaDonChiTietService;
import com.example.demo.service.HoaDonService;
import com.example.demo.service.LichSuHoaDonService;
import com.example.demo.service.TaiKhoanService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/hoa-don/")
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private LichSuHoaDonService lichSuHoaDonService;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @GetMapping("hien-thi")
    public String hienThiHoaDon(Model model, @RequestParam(name = "page", defaultValue = "0") Integer pageNum) {
        Page<HoaDon> hoaDonPage = hoaDonService.phanTrangHoaDon(pageNum, 5);
        model.addAttribute("listHD", hoaDonPage);
        List<LichSuHoaDon> lichSuHoaDons = lichSuHoaDonService.getAll();
        model.addAttribute("listLSHD", lichSuHoaDons);
        List<TaiKhoan> taiKhoans = taiKhoanService.getAll();
        model.addAttribute("listTK", taiKhoans);
        return "hoadon/hoadon";
    }

    @GetMapping("view-hoa-don/{id}")
    public String viewHoaDon(@PathVariable UUID id, Model model) {
        HoaDon hoaDon = hoaDonService.detail(id);
        model.addAttribute("hd1", hoaDon);
        List<LichSuHoaDon> lichSuHoaDon = lichSuHoaDonService.detail(hoaDon.getLichSuHoaDon().getId());
        model.addAttribute("lshd1", lichSuHoaDon);
        return "/hoadon/hoa-don-chi-tiet";
    }



    @PostMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();


        XSSFSheet sheet = workbook.createSheet("Danh sách hóa đơn");
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã Hóa Đơn");
        headerRow.createCell(1).setCellValue("Ngày Thanh Toán");
        headerRow.createCell(2).setCellValue("Tổng Tiền Sau Khi Giảm");
        headerRow.createCell(3).setCellValue("Trạng Thái");
        headerRow.createCell(4).setCellValue("Người Nhận");
        headerRow.createCell(5).setCellValue("Ngày nhận dự kiến");
        headerRow.createCell(6).setCellValue("Ngày Ship");


        List<HoaDon> hoaDons = hoaDonService.getExcel();
        int rowNum = 1;
        for (HoaDon hoaDon : hoaDons) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(hoaDon.getMaHoaDon());
            row.createCell(1).setCellValue(hoaDon.getNgayThanhToan());
            row.createCell(2).setCellValue(hoaDon.getTongTienSauKhiGiam());
            row.createCell(3).setCellValue(hoaDon.getTrangThai());
            row.createCell(4).setCellValue(hoaDon.getTenNguoiNhan());
            row.createCell(5).setCellValue(hoaDon.getNgayDuKienNhan());
            row.createCell(6).setCellValue(hoaDon.getNgayShip());
        }


        response.setHeader("Content-Disposition", "attachment; filename=danhsachhoadon.xlsx");
        response.setContentType("application/vnd.ms-excel");

        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
    }

}
