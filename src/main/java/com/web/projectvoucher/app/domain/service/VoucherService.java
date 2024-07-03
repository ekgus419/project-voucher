package com.web.projectvoucher.app.domain.service;

import com.web.projectvoucher.common.type.VoucherAmountType;
import com.web.projectvoucher.common.type.VoucherStatusType;
import com.web.projectvoucher.storage.voucher.VoucherEntity;
import com.web.projectvoucher.storage.voucher.VoucherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
    
    // 상품권 발행
    @Transactional
    public String publish(final LocalDate validFrom, final LocalDate validTo, final VoucherAmountType amount) {
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherEntity voucherEntity = new VoucherEntity(code, VoucherStatusType.PUBLISH, validFrom, validTo, amount);
        return voucherRepository.save(voucherEntity).code();
    }

    // 상품권 사용 불가 처리
    @Transactional
    public void disable(String code) {
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권입니다."));
        voucherEntity.disable();
    }

    // 상품권 사용
    @Transactional
    public void use(String code) {
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권입니다."));
        voucherEntity.use();
    }

}
