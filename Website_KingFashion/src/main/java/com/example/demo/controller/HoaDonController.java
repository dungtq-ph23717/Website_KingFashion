package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.LichSuHoaDon;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.entity.Voucher;
import com.example.demo.service.HoaDonChiTietService;
import com.example.demo.service.HoaDonService;
import com.example.demo.service.LichSuHoaDonService;
import com.example.demo.service.TaiKhoanService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private LichSuHoaDonService lichSuHoaDonService;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @GetMapping("/hien-thi")
    public String hienThiHoaDon(Model model, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Page<HoaDon> hoaDonPage = hoaDonService.phanTrangHoaDon(page, 5);
        model.addAttribute("listHD", hoaDonPage);
        List<LichSuHoaDon> listLSHD = lichSuHoaDonService.getAll();
        model.addAttribute("listLSHD", listLSHD);
        List<TaiKhoan> listTK = taiKhoanService.getAll();
        model.addAttribute("listTK", listTK);
        model.addAttribute("searchHD", new HoaDon());
        return "hoadon/hoadon";
    }

    @GetMapping("/view-hoa-don/{id}")
    public String viewHoaDon(@PathVariable UUID id, Model model) {
        HoaDon hoaDon = hoaDonService.detail(id);
        model.addAttribute("listHD", hoaDon);
        List<LichSuHoaDon> lichSuHoaDon = lichSuHoaDonService.detail(hoaDon.getLichSuHoaDon().getId());
        model.addAttribute("listLshd", lichSuHoaDon);
        return "hoadon/chi-tiet-hoa-don";
    }

    @GetMapping("/search")
    public String search(Model model, @ModelAttribute("searchHD") HoaDon hoaDon,
                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "xapXep", defaultValue = "0") Integer xapXep) {
        Map<Integer, Sort> sortMapping = new HashMap<>();
        sortMapping.put(1, Sort.by("ngayTao").descending());
        sortMapping.put(2, Sort.by("ngayTao").ascending());
        sortMapping.put(3, Sort.by("tongTienKhiGiam").descending());
        sortMapping.put(4, Sort.by("tongTienKhiGiam").ascending());
        sortMapping.put(5, Sort.by("nguoiNhan").descending());
        sortMapping.put(6, Sort.by("nguoiNhan").ascending());
        sortMapping.put(0, Sort.by("ngayTao").descending());

        Sort sort = sortMapping.getOrDefault(xapXep, Sort.by("ngayTao").descending());

        Integer sortHD = sort.hashCode();

        Page<HoaDon> listHD = hoaDonService.searchHD(hoaDon.getMaHoaDon(), hoaDon.getNguoiNhan(), hoaDon.getTongTienSauKhiGiam(),
                hoaDon.getTrangThai(), hoaDon.getNgayTao(), hoaDon.getLoaiDon(), page, 5, sortHD);

        model.addAttribute("listHD", listHD);
        return "hoadon/hoadon";
    }

    @PostMapping("/update")
    public String updateHD(@ModelAttribute HoaDon hoaDon,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        Date date = new Date();
        hoaDon.setNgaySua(date);
        hoaDonService.add(hoaDon);
        redirectAttributes.addAttribute("id", hoaDon.getId());
        session.setAttribute("successMessage", "Cập nhật thành công !");
        return "redirect:/hoa-don/view-hoa-don/{id}";
    }

    @PostMapping("/export-excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();


        XSSFSheet sheet = workbook.createSheet("Danh sách hóa đơn");
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã Hóa Đơn");
        headerRow.createCell(1).setCellValue("Ngày Thanh Toán");
        headerRow.createCell(2).setCellValue("Tổng Tiền Sau Khi Giảm");
        headerRow.createCell(3).setCellValue("Trạng Thái");
        headerRow.createCell(4).setCellValue("Người Nhận");
        headerRow.createCell(5).setCellValue("Ngày Giao Hàng");
        headerRow.createCell(6).setCellValue("Ngày Nhận Dự Kiến");


        // Tạo CellStyle cho định dạng ngày
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }

        List<HoaDon> hoaDons = hoaDonService.getExcel();
        int rowNum = 1;
        for (HoaDon hoaDon : hoaDons) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(hoaDon.getMaHoaDon());
            Cell ngayThanhToanCell = row.createCell(1);
            ngayThanhToanCell.setCellValue(hoaDon.getNgayThanhToan());
            ngayThanhToanCell.setCellStyle(dateCellStyle);
            row.createCell(2).setCellValue(hoaDon.getTongTienSauKhiGiam() + " VND");
            row.createCell(3).setCellValue(hoaDon.getTrangThai() == 1 ? "Đang chờ xác nhận" : (hoaDon.getTrangThai() == 2 ? "Đã hủy" : "Đã hoàn thành"));
            row.createCell(4).setCellValue(hoaDon.getNguoiNhan());
            Cell ngayShipCell = row.createCell(5);
            ngayShipCell.setCellValue(hoaDon.getNgayShip());
            ngayShipCell.setCellStyle(dateCellStyle);

            Cell ngayDuKienNhanCell = row.createCell(6);
            ngayDuKienNhanCell.setCellValue(hoaDon.getNgayDuKienNhan());
            ngayDuKienNhanCell.setCellStyle(dateCellStyle);
        }


        response.setHeader("Content-Disposition", "attachment; filename=danhsachhoadon.xlsx");
        response.setContentType("application/vnd.ms-excel");

        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
    }

}
