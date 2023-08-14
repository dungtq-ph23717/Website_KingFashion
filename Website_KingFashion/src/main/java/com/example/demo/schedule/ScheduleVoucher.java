package com.example.demo.schedule;

import com.example.demo.entity.Voucher;
import com.example.demo.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduleVoucher {

    @Autowired
    private VoucherRepository voucherRepository;

    @Scheduled(fixedRate = 6000000)
    public void updateVoucherStatus() {
        Date currentDateTime = new Date();
        List<Voucher> expiredVouchers = voucherRepository.findByThoiGianKetThucAfterAndTrangThaiNot(currentDateTime, 0);
        for (Voucher voucher : expiredVouchers) {
            Date voucherEndTime = voucher.getThoiGianKetThuc();
            if (voucherEndTime.after(currentDateTime) || voucherEndTime.equals(currentDateTime)) { // Kiểm tra nếu thời gian kết thúc >= thời gian hiện tại hoặc bằng thời gian hiện tại
                voucher.setTrangThai(0);
                voucherRepository.save(voucher);
            }
        }
    }

}
