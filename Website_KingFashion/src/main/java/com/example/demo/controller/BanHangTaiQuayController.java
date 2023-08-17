package com.example.demo.controller;

import com.example.demo.entity.Anh;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.LichSuHoaDon;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.entity.Voucher;
import com.example.demo.entity.Voucher_HoaDon;
import com.example.demo.service.AnhService;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.HoaDonChiTietService;
import com.example.demo.service.HoaDonService;
import com.example.demo.service.LichSuHoaDonService;
import com.example.demo.service.TaiKhoanService;
import com.example.demo.service.VaiTroService;
import com.example.demo.service.VoucherHoaDonService;
import com.example.demo.service.impl.VoucherServiceImpl;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    private VoucherHoaDonService voucherHoaDonService;

    @Autowired
    private LichSuHoaDonService lichSuHoaDonService;

    @GetMapping("hien-thi")
    public String hienThiTable(Model model) {
        model.addAttribute("hdctlist", hoaDonChiTietService.getALl());
        model.addAttribute("hdlist", hoaDonService.getAll());
        model.addAttribute("hdct", new HoaDonChiTiet());
        model.addAttribute("hd", new HoaDon());
        return "BanHangTaiQuay/BanHangTaiQuay";
    }

    @PostMapping("carts")
    public String addDonhang(@ModelAttribute("hdct") HoaDonChiTiet hoaDonChiTiet, RedirectAttributes redirectAttributes, Model model) {

        HoaDon hoaDon = new HoaDon();
        String ma = "HD" + new Random().nextInt(100000);
        hoaDon.setMaHoaDon(ma);
        hoaDon.setNgayTao(new Date());
        hoaDon.setLoaiDon(0);
        hoaDon.setTrangThai(0);
        hoaDonService.add(hoaDon);

        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        String mals = "LS" + new Random().nextInt(100000);
        lichSuHoaDon.setMa(mals);
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setNgayTao(new Date());
        lichSuHoaDon.setTrangThai(0);


        hoaDonChiTiet.setHoaDon(hoaDon);

        hoaDonChiTietService.add(hoaDonChiTiet);
        lichSuHoaDonService.createLichSuDonHang(lichSuHoaDon);
        model.addAttribute("hdct", hoaDonChiTiet);
        redirectAttributes.addAttribute("id", hoaDon.getId());
        return "redirect:/ban-hang-tai-quay/viewcart/{id}";
    }

    @PostMapping("viewcart")
    public String updateOrCreate(@ModelAttribute("hdct") HoaDonChiTiet hoaDonChiTiet,
                                 @RequestParam("idctsp") UUID idctsp,
                                 @RequestParam("id") UUID id,
                                 @RequestParam("soLuong") int soLuong,
                                 RedirectAttributes redirectAttributes, Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(idctsp);
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.getHoaDonChiTietByHoaDonId(id);
        HoaDon hoaDon = hoaDonService.detail(id);

        boolean foundExistingChiTiet = false;

        for (HoaDonChiTiet hdt : hoaDonChiTietList) {
            if (hdt.getChiTietSanPham() == null) {
                // Nếu tìm thấy chi tiết hóa đơn bị null, set thông tin và thoát vòng lặp
                hdt.setChiTietSanPham(chiTietSanPham);
                hdt.setHoaDon(hoaDon);
                hdt.setSoLuong(soLuong);
                hdt.setDonGia(Double.valueOf(chiTietSanPham.getGiaBan()));
                foundExistingChiTiet = true;
                break;
            } else if (hdt.getChiTietSanPham().getId().equals(idctsp)) {
                // Nếu tìm thấy chi tiết hóa đơn với cùng idctsp, cộng dồn số lượng
                hdt.setSoLuong(hdt.getSoLuong() + soLuong);
                hdt.setDonGia(Double.valueOf(chiTietSanPham.getGiaBan()));
                foundExistingChiTiet = true;
                break;
            }
        }

        double totalDiscountAmount = hoaDon.getVoucher_hoaDonList().stream()
                .mapToDouble(vchd -> vchd.getVoucher().getMucGiam())
                .sum();

        double totalAmount = hoaDonChiTietList.stream()
                .mapToDouble(hdt -> hdt.getSoLuong() * hdt.getDonGia())
                .sum();

        if (!foundExistingChiTiet) {
            // Tạo mới một chi tiết hóa đơn và set thông tin
            HoaDonChiTiet newHoaDonChiTiet = new HoaDonChiTiet();
            newHoaDonChiTiet.setChiTietSanPham(chiTietSanPham);
            newHoaDonChiTiet.setHoaDon(hoaDon);
            newHoaDonChiTiet.setSoLuong(soLuong);
            newHoaDonChiTiet.setDonGia(Double.valueOf(chiTietSanPham.getGiaBan()));
            hoaDonChiTietService.add(newHoaDonChiTiet);

            // Cập nhật lại tổng tiền sau khi giảm sau khi thêm mới chi tiết hóa đơn
            totalAmount += newHoaDonChiTiet.getSoLuong() * newHoaDonChiTiet.getDonGia();
        }

        double totalAmountAfterDiscount = totalAmount - totalDiscountAmount;
        hoaDon.setTongTienSauKhiGiam(totalAmountAfterDiscount);
        hoaDon.setTongTien(totalAmount);

        // Cập nhật chi tiết hóa đơn ban đầu hoặc điều hướng tới trang bạn muốn
        hoaDonChiTietService.addAll(hoaDonChiTietList);
        redirectAttributes.addAttribute("id", hoaDon.getId());
        return "redirect:/ban-hang-tai-quay/viewcart/{id}";
    }



    @PostMapping("viewKhachHang")
    public String themKhachHang(@ModelAttribute("hd") HoaDon hoaDon,
                                @RequestParam("idtk") UUID idtk,
                                @RequestParam("id") UUID id,
                                RedirectAttributes redirectAttributes, Model model) {
        TaiKhoan taiKhoan = taiKhoanService.getAllKhachHang(idtk);
        hoaDon = hoaDonService.detail(id);
        hoaDon.setTaiKhoan(taiKhoan);
        hoaDon.setLoaiDon(0);
        hoaDon.setNguoiNhan(taiKhoan.getTenTaiKhoan());
        hoaDonService.add(hoaDon);
        // Cập nhật chi tiết hóa đơn ban đầu hoặc điều hướng tới trang bạn muốn
        redirectAttributes.addAttribute("id", hoaDon.getId());
        return "redirect:/ban-hang-tai-quay/viewcart/{id}";
    }

    @PostMapping("viewVoucher")
    public String themVoucher(@ModelAttribute("voucher") Voucher_HoaDon voucher_hoaDon,
                              @RequestParam("id") UUID id,
                              @RequestParam("idvc") UUID idvc,
                              RedirectAttributes redirectAttributes, Model model) {
        Voucher voucher = voucherService.detail(idvc);
        HoaDon hoaDon = hoaDonService.detail(id);

        List<HoaDonChiTiet> hoaDonChiTietList = hoaDon.getHoaDonChiTietList();
        double totalAmount = 0.0;

        for (HoaDonChiTiet hdct : hoaDonChiTietList) {
            totalAmount += hdct.getDonGia() * hdct.getSoLuong();
        }


        if (totalAmount >= voucher.getTien()) {
            List<Voucher_HoaDon> voucher_hoaDons = voucherHoaDonService.getVoucher_HoaDonByHoaDonId(id);

            boolean foundExistingvc = false;

            for (Voucher_HoaDon vchd : voucher_hoaDons) {
                if (vchd.getVoucher() == null) {
                    vchd.setVoucher(voucher);
                    vchd.setHoaDon(hoaDon);
                    foundExistingvc = true;
                    break;
                }
            }
            if (!foundExistingvc) {
                // Tạo mới một chi tiết hóa đơn và set thông tin
                Voucher_HoaDon newHoavchd = new Voucher_HoaDon();
                newHoavchd.setVoucher(voucher);
                newHoavchd.setHoaDon(hoaDon);
                // Lưu chi tiết hóa đơn mới
                voucherHoaDonService.add(newHoavchd);
            }

            double totalDiscountAmount = 0.0;
            for (Voucher_HoaDon vchd : hoaDon.getVoucher_hoaDonList()) {
                totalDiscountAmount += vchd.getVoucher().getMucGiam();
            }
            // Cập nhật tổng tiền hóa đơn sau khi áp dụng voucher
            double totalAmountAfterDiscount = totalAmount - totalDiscountAmount;
            hoaDon.setTongTienSauKhiGiam(totalAmountAfterDiscount);
            hoaDon.setTongTien(totalAmount);

            // Lưu hoaDon
            hoaDonService.add(hoaDon);

            voucherHoaDonService.addAll(voucher_hoaDons);

            redirectAttributes.addAttribute("id", hoaDon.getId());
            return "redirect:/ban-hang-tai-quay/viewcart/{id}";
        } else {
            redirectAttributes.addAttribute("id", hoaDon.getId());
            return "redirect:/ban-hang-tai-quay/viewcart/{id}";
        }
    }


    @GetMapping("viewcart/delete/{id}")
    public String delete(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietService.findById(id);

        if (hoaDonChiTiet != null) {
            HoaDon hoaDon = hoaDonChiTiet.getHoaDon();
            UUID hoaDonId = hoaDon.getId();

            // Xóa chi tiết hóa đơn
            hoaDonChiTietService.delete(id);

            // Lấy danh sách chi tiết hóa đơn còn lại
            List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.getHoaDonChiTietByHoaDonId(hoaDonId);

            // Tính lại tổng tiền sau khi xóa chi tiết hóa đơn
            double totalDiscountAmount = hoaDon.getVoucher_hoaDonList().stream()
                    .mapToDouble(vchd -> vchd.getVoucher().getMucGiam())
                    .sum();

            double totalAmount = hoaDonChiTietList.stream()
                    .mapToDouble(hdt -> (hdt.getSoLuong() * hdt.getDonGia()))
                    .sum();

            double totalAmountAfterDiscount = totalAmount - totalDiscountAmount;
            hoaDon.setTongTienSauKhiGiam(totalAmountAfterDiscount);
            hoaDon.setTongTien(totalAmount);

            // Cập nhật tổng tiền của hóa đơn sau khi xóa
            hoaDonService.add(hoaDon);

            redirectAttributes.addAttribute("id", hoaDonId);
        }

        return "redirect:/ban-hang-tai-quay/viewcart/{id}";
    }


    @GetMapping("viewcart/{id}")
    public String showSanPham(@PathVariable UUID id, Model model) {
        List<ChiTietSanPham> list = chiTietSanPhamService.getAll();
        List<HoaDonChiTiet> listhdct = hoaDonChiTietService.getALl();
        List<TaiKhoan> list1 = taiKhoanService.getAll();
        List<Voucher> list2 = voucherService.getAll();
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        TaiKhoan taiKhoan = taiKhoanService.findByHoaDonId(id);

        Double tongTien = hoaDonChiTietService.tongTien(id);
        Double tongMucGiam = voucherHoaDonService.tongTienMucGiam(id);
        Double tongTienSauGiam = hoaDonService.tongTienSauGiam(id);


        model.addAttribute("hdct", new HoaDonChiTiet());
        model.addAttribute("hd", new HoaDon());
        model.addAttribute("taiKhoan", taiKhoan);
        model.addAttribute("ctsp", chiTietSanPham);
        model.addAttribute("list1", list1);
        model.addAttribute("list2", list2);
        model.addAttribute("list", list);
        model.addAttribute("listhdct", listhdct);
        model.addAttribute("TongTien", tongTien);
        model.addAttribute("TongTienMucGiam", tongMucGiam);
        model.addAttribute("tongTienSauGiam", tongTienSauGiam);
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

    @GetMapping("view-hoa-don/{id}")
    public String viewHoaDon(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        HoaDon hoaDon = hoaDonService.detail(id);
        model.addAttribute("listHD", hoaDon);
        List<LichSuHoaDon> listLSHD = lichSuHoaDonService.findAllLichSuHoaDonById(id);
        model.addAttribute("listLSHD",listLSHD);

        redirectAttributes.addAttribute("id", hoaDon.getId());
        return "redirect:/hoa-don/view-hoa-don/{id}";
    }

}
