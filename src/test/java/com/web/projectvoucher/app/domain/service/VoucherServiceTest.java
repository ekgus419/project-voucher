package com.web.projectvoucher.app.domain.service;

import com.web.projectvoucher.common.type.VoucherAmountType;
import com.web.projectvoucher.common.type.VoucherStatusType;
import com.web.projectvoucher.domain.service.VoucherService;
import com.web.projectvoucher.storage.voucher.VoucherEntity;
import com.web.projectvoucher.storage.voucher.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoucherServiceTest {
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    @DisplayName("발행된 상품권은 code 로 조회할 수 있어야 된다.")
    @Test
    public void test1() {
        // given
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);
        final VoucherAmountType amount = VoucherAmountType.KRW_30000;

        String code = voucherService.publish(validFrom, validTo, amount);

        // when
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();

        // then
        assertThat(voucherEntity.code()).isEqualTo(code);
        assertThat(voucherEntity.status()).isEqualTo(VoucherStatusType.PUBLISH);
        assertThat(voucherEntity.validFrom()).isEqualTo(validFrom);
        assertThat(voucherEntity.validTo()).isEqualTo(validTo);
        assertThat(voucherEntity.amount()).isEqualTo(amount);
    }

    @DisplayName("발행된 상품권은 사용 불가 처리 할 수 있다.")
    @Test
    public void test2() {
        // given
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);
        final VoucherAmountType amount = VoucherAmountType.KRW_30000;

        String code = voucherService.publish(validFrom, validTo, amount);

        // when
        voucherService.disable(code);
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();

        // then
        assertThat(voucherEntity.code()).isEqualTo(code);
        assertThat(voucherEntity.status()).isEqualTo(VoucherStatusType.DISABLE);
        assertThat(voucherEntity.validFrom()).isEqualTo(validFrom);
        assertThat(voucherEntity.validTo()).isEqualTo(validTo);
        assertThat(voucherEntity.amount()).isEqualTo(amount);
        assertThat(voucherEntity.updateAt()).isNotEqualTo(voucherEntity.createAt());
    }

    @DisplayName("발행된 상품권은 사용 할 수 있다.")
    @Test
    public void test3() {
        // given
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);
        final VoucherAmountType amount = VoucherAmountType.KRW_30000;

        String code = voucherService.publish(validFrom, validTo, amount);

        // when
        voucherService.use(code);
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();

        // then
        assertThat(voucherEntity.code()).isEqualTo(code);
        assertThat(voucherEntity.status()).isEqualTo(VoucherStatusType.USE);
        assertThat(voucherEntity.validFrom()).isEqualTo(validFrom);
        assertThat(voucherEntity.validTo()).isEqualTo(validTo);
        assertThat(voucherEntity.amount()).isEqualTo(amount);
        assertThat(voucherEntity.updateAt()).isNotEqualTo(voucherEntity.createAt());

        System.out.println("### voucherEntity.createAt() = " + voucherEntity.createAt());
        System.out.println("### voucherEntity.updateAt() = " + voucherEntity.updateAt());
    }

}
