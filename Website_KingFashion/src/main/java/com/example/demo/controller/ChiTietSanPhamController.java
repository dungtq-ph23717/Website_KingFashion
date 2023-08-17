package com.example.demo.controller;

import com.example.demo.entity.Anh;
import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.ChiTietKichCo;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.CoAo;
import com.example.demo.entity.KichCo;
import com.example.demo.entity.LoaiSanPham;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.NhaSanXuat;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.Voucher;
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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/chi-tiet-san-pham/")
public class ChiTietSanPhamController {

    private static int currentNumber = 1;

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

    private List<ChiTietKichCo> listCTKC = new ArrayList<>();
    private List<KichCo> listkc = new ArrayList<>();

    @GetMapping("hien-thi")
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model, HttpSession session) {
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
        return "chitietsanpham/chi-tiet-san-pham";
    }


    @GetMapping("search")
    public String search(@RequestParam(value = "ten", required = false) String ten,
                         @RequestParam(value = "minTien", required = false) Integer minTien,
                         @RequestParam(value = "maxTien", required = false) Integer maxTien,
                         @RequestParam(defaultValue = "0", name = "page") Integer pageNum,
                         Model model, HttpSession session) {

        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }

        Page<ChiTietSanPham> ketQuaTimKiem = chiTietSanPhamService.search(ten, minTien, maxTien, pageNum, 5);

        model.addAttribute("list", ketQuaTimKiem);
        model.addAttribute("att", new ChiTietSanPham()); // Add this line to set the "att" attribute in the model

        // Add other model attributes if required

        return "chitietsanpham/chi-tiet-san-pham";
    }

    @GetMapping("view-add")
    public String viewAdd(Model model) {
        model.addAttribute("listCoAo", coAoService.getAll());
        model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
        model.addAttribute("listChatLieu", chatLieuService.getAll());
        model.addAttribute("att", new ChiTietSanPham());
        model.addAttribute("lsp1", new LoaiSanPham());
        model.addAttribute("ms1", new MauSac());
        model.addAttribute("nsx1", new NhaSanXuat());
        model.addAttribute("ca1", new CoAo());
        model.addAttribute("cl1", new ChatLieu());
        return "chitietsanpham/add-chi-tiet-san-pham";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("att") ChiTietSanPham chiTietSanPham,
                      BindingResult result,
                      RedirectAttributes redirectAttributes,
                      Model model, HttpSession session) {

        if (result.hasErrors()) {
            model.addAttribute("listCoAo", coAoService.getAll());
            model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
            model.addAttribute("listMauSac", mauSacService.getAll());
            model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
            model.addAttribute("listChatLieu", chatLieuService.getAll());
            model.addAttribute("lsp1", new LoaiSanPham());
            model.addAttribute("ms1", new MauSac());
            model.addAttribute("nsx1", new NhaSanXuat());
            model.addAttribute("ca1", new CoAo());
            model.addAttribute("cl1", new ChatLieu());
            return "chitietsanpham/add-chi-tiet-san-pham";
        }

        String tenSanPham = chiTietSanPham.getSanPham().getTen();
        String moTaSanPham = chiTietSanPham.getSanPham().getMoTa();

        String maSanPham = "SP" + currentNumber;
        String maChiTietSanPham = "CTSP" + currentNumber;

        currentNumber++;

        SanPham sanPham = new SanPham();
        sanPham.setMa(maSanPham);
        sanPham.setMoTa(moTaSanPham);
        sanPham.setTen(tenSanPham);
        sanPham.setNgayTao(new Date());
        sanPham.setTrangThai(1);
        sanPhamService.add(sanPham);


        chiTietSanPham.setMa(maChiTietSanPham);
        chiTietSanPham.setNgayTao(new Date());
        chiTietSanPham.setTrangThai(1);

        chiTietSanPham.setSanPham(sanPham);

        model.addAttribute("att", sanPham);

        model.addAttribute("att", chiTietSanPham);
        session.setAttribute("successMessage", "Thêm thành công!");
        chiTietSanPhamService.add(chiTietSanPham);
        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable UUID id, Model model, HttpSession session) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        session.setAttribute("successMessage", "Xóa thành công!");
        if (chiTietSanPham.getTrangThai() == 1) {
            chiTietSanPham.setTrangThai(0);
        } else {
            chiTietSanPham.setTrangThai(1);
        }
        chiTietSanPhamService.add(chiTietSanPham);
        return "redirect:/chi-tiet-san-pham/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String updateCTSP(@PathVariable UUID id, Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);

        listCTKC = chiTietKichCoService.getAllByIdCTSP(id);

        listkc = kichCoService.getAll();

        model.addAttribute("att", chiTietSanPham);
        model.addAttribute("ctkc", new ChiTietKichCo());
        model.addAttribute("listctkc", listCTKC);
        model.addAttribute("listkc", listkc);
        model.addAttribute("listCoAo", coAoService.getAll());
        model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
        model.addAttribute("listChatLieu", chatLieuService.getAll());
        model.addAttribute("kc1", new KichCo());
        model.addAttribute("a1", new Anh());
        model.addAttribute("lsp1", new LoaiSanPham());
        model.addAttribute("ms1", new MauSac());
        model.addAttribute("nsx1", new NhaSanXuat());
        model.addAttribute("ca1", new CoAo());
        model.addAttribute("cl1", new ChatLieu());

        List<Anh> listAnh = anhService.getAllByChiTietSanPhamId(id);
        List<Anh> filteredAnhList = new ArrayList<>();
        // Thay ... bằng giá trị UUID thực tế từ URL

        for (Anh anh : listAnh) {
            if (anh.getChiTietSanPham().getId().equals(id)) {
                filteredAnhList.add(anh);
            }
        }
        model.addAttribute("listAnh", filteredAnhList);

        return "chitietsanpham/add-kich-co-anh-chi-tiet-san-pham";
    }


    @PostMapping("update")
    public String update(@Valid @ModelAttribute("att") ChiTietSanPham chiTietSanPham, BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            return "chitietsanpham/update-chi-tiet-san-pham";
        }
        chiTietSanPham.setNgaySua(new Date());
        model.addAttribute("att", chiTietSanPham);

        List<ChiTietKichCo> ListCTKCS = chiTietKichCoService.findAllByChiTietSanPhamIdHoatDong(chiTietSanPham.getId());

        // Tính tổng số lượng kích cỡ
        int totalKichCoQuantity = ListCTKCS.stream().mapToInt(ChiTietKichCo::getSoLuong).sum();

        // Set tổng số lượng kích cỡ cho sản phẩm chi tiết
        chiTietSanPham.setSoLuong(totalKichCoQuantity);
        chiTietSanPhamService.add(chiTietSanPham);
        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }

    @PostMapping("addLoaiSanPham")
    public String addLoaiSanPham(@Valid @ModelAttribute("lsp1") LoaiSanPham loaiSanPham, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        String ma = "LSP" + new Random().nextInt(100000);
        loaiSanPham.setMa(ma);
        loaiSanPham.setTrangThai(1);
        loaiSanPham.setNgayTao(new Date());
        model.addAttribute("lsp1", loaiSanPham);
        loaiSanPhamService.add(loaiSanPham);
        return viewAdd(model);
    }

    @PostMapping("addMauSac")
    public String addMauSac(@Valid @ModelAttribute("ms1") MauSac mauSac, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        String ma = "MS" + new Random().nextInt(100000);
        mauSac.setMaMauSac(ma);
        mauSac.setNgayTao(new Date());
        mauSac.setTrangThai(1);
        model.addAttribute("ms1", mauSac);
        mauSacService.add(mauSac);
        return viewAdd(model);
    }

    @PostMapping("addNhaSanXuat")
    public String addMauSac(@Valid @ModelAttribute("nsx1") NhaSanXuat nhaSanXuat, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        String ma = "NSX" + new Random().nextInt(100000);
        nhaSanXuat.setMaNhaSanXuat(ma);
        nhaSanXuat.setTrangThai(1);
        nhaSanXuat.setNgayTao(new Date());
        model.addAttribute("ms1", nhaSanXuat);
        nhaSanXuatService.add(nhaSanXuat);
        return viewAdd(model);
    }

    @PostMapping("addCoAo")
    public String addMauSac(@Valid @ModelAttribute("ca1") CoAo coAo, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        String ma = "CAO" + new Random().nextInt(100000);
        coAo.setMa(ma);
        coAo.setTrangThai(1);
        coAo.setNgayTao(new Date());
        model.addAttribute("ms1", coAo);
        coAoService.add(coAo);
        return viewAdd(model);
    }

    @PostMapping("addChatLieu")
    public String addMauSac(@Valid @ModelAttribute("cl1") ChatLieu chatLieu, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        String ma = "CL" + new Random().nextInt(100000);
        chatLieu.setMa(ma);
        chatLieu.setTrangThai(1);
        chatLieu.setNgayTao(new Date());
        model.addAttribute("ms1", chatLieu);
        chatLieuService.add(chatLieu);
        return viewAdd(model);
    }

    @ModelAttribute("selectedChiTietKichCoList")
    public List<ChiTietKichCo> selectedChiTietKichCoList() {
        return new ArrayList<>();
    }

    @PostMapping("add-kich-co")
    public String addkc(@Valid @ModelAttribute("kc1") KichCo kichCo, BindingResult result,
                        @RequestParam("id") UUID id,
                        RedirectAttributes redirectAttributes,
                        Model model) {

        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);

        kichCo.setNgayTao(new Date());
        kichCo.setMa("KC01");
        kichCo.setTrangThai(1);
        kichCoService.add(kichCo);

        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }

    @PostMapping("addCTKC")
    public String addCTKC(@Valid @ModelAttribute("ctkc") ChiTietKichCo chiTietKichCo, BindingResult result,
                          @RequestParam("id") UUID id,
                          @RequestParam("kichCoIds") String[] kichCoIds,
                          @RequestParam("soLuong") int soLuong,
                          RedirectAttributes redirectAttributes,
                          Model model) {

        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);

        for (String kichCoId : kichCoIds) {
            UUID kichCoUUID = UUID.fromString(kichCoId);

            // Kiểm tra xem ChiTietKichCo đã tồn tại trong cơ sở dữ liệu chưa
            ChiTietKichCo existingChiTietKichCo = chiTietKichCoService.getByChiTietSanPhamIdAndKichCoId(id, kichCoUUID);

            if (existingChiTietKichCo != null) {
                // Nếu ChiTietKichCo đã tồn tại, cộng dồn số lượng
                existingChiTietKichCo.setSoLuong(existingChiTietKichCo.getSoLuong() + soLuong);
                chiTietKichCoService.add(existingChiTietKichCo);

            } else {
                // Nếu ChiTietKichCo chưa tồn tại, thêm mới ChiTietKichCo
                KichCo kichCo = new KichCo();
                kichCo.setId(kichCoUUID);

                ChiTietKichCo newChiTietKichCo = new ChiTietKichCo();
                newChiTietKichCo.setChiTietSanPham(chiTietSanPham);
                newChiTietKichCo.setKichCo(kichCo);
                newChiTietKichCo.setTrangThai(1);
                newChiTietKichCo.setNgayTao(new Date());
                newChiTietKichCo.setSoLuong(soLuong);
                chiTietKichCoService.add(newChiTietKichCo);

            }
        }
        List<ChiTietKichCo> ListCTKCS = chiTietKichCoService.findAllByChiTietSanPhamIdHoatDong(chiTietSanPham.getId());

        // Tính tổng số lượng kích cỡ
        int totalKichCoQuantity = ListCTKCS.stream().mapToInt(ChiTietKichCo::getSoLuong).sum();

        // Set tổng số lượng kích cỡ cho sản phẩm chi tiết
        chiTietSanPham.setSoLuong(totalKichCoQuantity);
        chiTietSanPhamService.add(chiTietSanPham);

        //... Tiếp tục các xử lý khác sau khi lưu thành công ...

        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }


    @PostMapping("updateCTKC")
    public String updateCTKC(@RequestParam("ctkcIds") List<UUID> ctkcIds,
                             @RequestParam("soLuongs") List<Integer> soLuongs,
                             @RequestParam("id") UUID id,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        for (int i = 0; i < ctkcIds.size(); i++) {
            UUID ctkcId = ctkcIds.get(i);
            int newQuantity = soLuongs.get(i);

            ChiTietKichCo chiTietKichCo = chiTietKichCoService.getCTKCById(ctkcId);
            chiTietKichCo.setSoLuong(newQuantity);

            // Update the ChiTietKichCo object in the database
            chiTietKichCoService.add(chiTietKichCo);
        }
        List<ChiTietKichCo> ListCTKCS = chiTietKichCoService.findAllByChiTietSanPhamIdHoatDong(chiTietSanPham.getId());

        // Tính tổng số lượng kích cỡ
        int totalKichCoQuantity = ListCTKCS.stream().mapToInt(ChiTietKichCo::getSoLuong).sum();

        // Set tổng số lượng kích cỡ cho sản phẩm chi tiết
        chiTietSanPham.setSoLuong(totalKichCoQuantity);
        chiTietSanPhamService.add(chiTietSanPham);

        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }




    @GetMapping("delete1/{id}")
    public String deleteKc(@ModelAttribute("att") ChiTietSanPham chiTietSanPham, @PathVariable UUID id, Model model,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        ChiTietKichCo chiTietKichCo = chiTietKichCoService.getCTKCById(id);
        session.setAttribute("successMessage", "Xóa thành công!");
        if (chiTietKichCo.getTrangThai() == 1) {
            chiTietKichCo.setTrangThai(0);
        } else {
            chiTietKichCo.setTrangThai(1);
        }
        chiTietKichCoService.add(chiTietKichCo);
        UUID chiTietSanPhamId = chiTietKichCo.getChiTietSanPham().getId();
        return "redirect:/chi-tiet-san-pham/view-update/" + chiTietSanPhamId;
    }

    @GetMapping("delete2/{id}")
    public String deleteAnh(@PathVariable UUID id, RedirectAttributes redirectAttributes, HttpSession session) {
        // Get the image by id
        Anh anh = anhService.getAnhById(id);

        // Delete the image
        anhService.delete(id);

        // Get the id of the related ChiTietSanPham
        UUID chiTietSanPhamId = anh.getChiTietSanPham().getId();

        // Add a success message to the session
        session.setAttribute("successMessage", "Xóa thành công!");

        // Redirect back to the "view-update" page
        return "redirect:/chi-tiet-san-pham/view-update/" + chiTietSanPhamId;
    }


    @GetMapping("anh-anh")
    public ModelAndView addImage() {
        return new ModelAndView("addimage");
    }

    @PostMapping("add-anh")
    public String addImagePost(@ModelAttribute("a1") Anh anh,
                               @RequestParam("image") MultipartFile[] files,
                               @RequestParam("id") UUID id,
                               RedirectAttributes redirectAttributes,
                               Model model
    ) throws IOException, SQLException {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        anh.setChiTietSanPham(chiTietSanPham);
        model.addAttribute("a1", anh);
        model.addAttribute("id", id);
        redirectAttributes.addAttribute("id", anh.getId());
        anhService.add(anh, files);
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
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
