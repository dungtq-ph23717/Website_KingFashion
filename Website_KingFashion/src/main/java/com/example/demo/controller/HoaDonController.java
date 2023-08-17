package com.example.demo.controller;

import com.example.demo.entity.*;
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
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {
    @Autowired
    private ConversionService conversionService;

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

    @GetMapping("/hien-thi-page-search")
    public String search(Model model, @ModelAttribute("searchHD") HoaDon hoaDon,
                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "xapXep", defaultValue = "0") Integer xapXep,
                         @RequestParam(value = "size", defaultValue = "5") Integer size,
                         @RequestParam(value = "tuNgay", defaultValue = "", required = false) LocalDateTime tuNgay,
                         @RequestParam(value = "denNgay", defaultValue = "", required = false) LocalDateTime denNgay) {
        Map<Integer, Sort> sortMapping = new HashMap<>();
        sortMapping.put(1, Sort.by("ngayTao").descending());
        sortMapping.put(2, Sort.by("ngayTao").ascending());
        sortMapping.put(3, Sort.by("tongTienSauKhiGiam").descending());
        sortMapping.put(4, Sort.by("tongTienSauKhiGiam").ascending());
        sortMapping.put(5, Sort.by("nguoiNhan").descending());
        sortMapping.put(6, Sort.by("nguoiNhan").ascending());
        sortMapping.put(null, Sort.by("ngayTao").descending());

        Sort sort = sortMapping.getOrDefault(xapXep, Sort.by("ngayTao").descending());

        if (page < 0 || page == null) {
            page = 0; // Đặt giá trị mặc định là 0 nếu số trang nhỏ hơn 0
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<HoaDon> listHD = hoaDonService.searchHD(hoaDon.getMaHoaDon(), hoaDon.getNguoiNhan(), hoaDon.getTongTienSauKhiGiam(),
                hoaDon.getTrangThai(), tuNgay != null ? Date.from(tuNgay.atZone(ZoneId.systemDefault()).toInstant()) : null,
                denNgay != null ? Date.from(denNgay.atZone(ZoneId.systemDefault()).toInstant()) : null,
                hoaDon.getLoaiDon(), pageable);

        model.addAttribute("tuNgay", tuNgay);
        model.addAttribute("denNgay", denNgay);

        model.addAttribute("listHD", listHD);
        return "hoadon/hoadon";
    }

    @PostMapping("/update")
    public String updateHD(@ModelAttribute("hoaDon") HoaDon hoaDon,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {

        hoaDonService.updateHD(hoaDon);
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
            row.createCell(3).setCellValue(hoaDon.getTrangThai() == 0 ? "Đang chờ xác nhận"
                    : (hoaDon.getTrangThai() == 1 ? "Đã xác nhận"
                    : (hoaDon.getTrangThai() == 2 ? "Đã hủy đơn"
                    : (hoaDon.getTrangThai() == 3 ? "Chờ giao hàng"
                    : (hoaDon.getTrangThai() == 4 ? "Đang giao hàng"
                    : (hoaDon.getTrangThai() == 5 ? "Giao hàng thành công"
                    : (hoaDon.getTrangThai() == 6 ? "Giao hàng thất bại"
                    : "Thanh toán thành công")))))));
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

    @GetMapping("/view-hoa-don/{id}")
    public String viewHoaDon(@PathVariable UUID id, Model model) {
        HoaDon hoaDon = hoaDonService.detail(id);
        model.addAttribute("listHD", hoaDon);

        List<LichSuHoaDon> listLSHD = lichSuHoaDonService.findAllLichSuHoaDonById(id);
        model.addAttribute("listLSHD",listLSHD);

        return "hoadon/chi-tiet-hoa-don";
    }

    @PostMapping("/huy-don/{id}")
    public String huyDon(@PathVariable(name = "id") UUID id,@RequestParam("ghiChu") String ghiChu, @ModelAttribute("listLshd") LichSuHoaDon lshd,
                         RedirectAttributes redirectAttributes, Model model) {
        HoaDon hoaDon = hoaDonService.detail(id);
        model.addAttribute("listHD", hoaDon);
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();

        lichSuHoaDon.setTrangThai(2);
        hoaDon.setTrangThai(2);
        Date date = new Date();
        lichSuHoaDon.setNgayTao(date);
        hoaDon.setNgayTao(date);

        lichSuHoaDon.setGhiChu(ghiChu);

        lichSuHoaDon.setHoaDon(hoaDon);

        hoaDonService.updateHD(hoaDon);
        lichSuHoaDonService.createLichSuDonHang(lichSuHoaDon);
        redirectAttributes.addAttribute("id", hoaDon.getId());
        return "redirect:/hoa-don/view-hoa-don/{id}";
    }

    @PostMapping("/xac-nhan-don/{id}")
    public String xacNhanDonHang(@PathVariable(name = "id") UUID id,@RequestParam("ghiChu") String ghiChu, @ModelAttribute("listLshd") LichSuHoaDon lshd,
                                 RedirectAttributes redirectAttributes, Model model) {
        HoaDon hoaDon = hoaDonService.detail(id);
        model.addAttribute("listHD", hoaDon);

        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setTrangThai(1);
        hoaDon.setTrangThai(1);
        Date date = new Date();
        lichSuHoaDon.setNgayTao(date);
        hoaDon.setNgayTao(date);

        lichSuHoaDon.setHoaDon(hoaDon);

        lichSuHoaDon.setGhiChu(ghiChu);

        hoaDonService.updateHD(hoaDon);
        lichSuHoaDonService.createLichSuDonHang(lichSuHoaDon);

        redirectAttributes.addAttribute("id", hoaDon.getId());
        return "redirect:/hoa-don/view-hoa-don/{id}";
    }

    @PostMapping("/xac-nhan-giao-hang/{id}")
    public String xacNhanGiaoHang(@PathVariable(name = "id") UUID id, @RequestParam("ghiChu") String ghiChu,@ModelAttribute("listLshd") LichSuHoaDon lshd,
                                  RedirectAttributes redirectAttributes, Model model) {
        HoaDon hoaDon = hoaDonService.detail(id);
        model.addAttribute("listHD", hoaDon);
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setTrangThai(4);
        hoaDon.setTrangThai(4);
        Date date = new Date();
        lichSuHoaDon.setNgayTao(date);
        hoaDon.setNgayTao(date);

        lichSuHoaDon.setGhiChu(ghiChu);

        lichSuHoaDon.setHoaDon(hoaDon);

        hoaDonService.updateHD(hoaDon);
        lichSuHoaDonService.createLichSuDonHang(lichSuHoaDon);
        redirectAttributes.addAttribute("id", hoaDon.getId());
        return "redirect:/hoa-don/view-hoa-don/{id}";
    }

    @PostMapping("/xac-nhan-thanh-toan/{id}")
    public String xacNhanThanhToan(@PathVariable(name = "id") UUID id,@RequestParam("ghiChu") String ghiChu, @ModelAttribute("listLshd") LichSuHoaDon lshd,
                                   RedirectAttributes redirectAttributes, Model model) {
        HoaDon hoaDon = hoaDonService.detail(id);
        model.addAttribute("listHD", hoaDon);
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setTrangThai(7);
        hoaDon.setTrangThai(7);
        Date date = new Date();
        lichSuHoaDon.setNgayTao(date);
        hoaDon.setNgayTao(date);

        lichSuHoaDon.setGhiChu(ghiChu);

        lichSuHoaDon.setHoaDon(hoaDon);

        hoaDonService.updateHD(hoaDon);
        lichSuHoaDonService.createLichSuDonHang(lichSuHoaDon);

//        redirectAttributes.addAttribute("id", hoaDon.getId());
        return "redirect:/hoa-don/view-hoa-don/" + id;
    }
}
