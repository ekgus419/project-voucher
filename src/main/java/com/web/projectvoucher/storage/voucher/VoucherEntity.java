package com.web.projectvoucher.storage.voucher;

import com.web.projectvoucher.common.type.VoucherAmountType;
import com.web.projectvoucher.common.type.VoucherStatusType;
import com.web.projectvoucher.storage.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "voucher")
@Entity
public class VoucherEntity extends BaseEntity {
    private String code;
    private VoucherStatusType status;
    private LocalDate validFrom; // 유효기간
    private LocalDate validTo; // 유효기간

    @Enumerated(EnumType.STRING)
    private VoucherAmountType amount;

    public VoucherEntity() {
    }

    public VoucherEntity(String code, VoucherStatusType status, LocalDate validFrom, LocalDate validTo, VoucherAmountType amount) {
        this.code = code;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.amount = amount;
    }

    public String code() {
        return code;
    }

    public VoucherStatusType status() {
        return status;
    }

    public LocalDate validFrom() {
        return validFrom;
    }

    public LocalDate validTo() {
        return validTo;
    }

    public VoucherAmountType amount() {
        return amount;
    }

    public void disable() {
        if (!this.status.equals(VoucherStatusType.PUBLISH)) {
            throw new IllegalStateException("사용 불가 처리할 수 없는 상태의 상품권 입니다.");
        }
        this.status = VoucherStatusType.DISABLE;
    }

    public void use() {
        if (!this.status.equals(VoucherStatusType.PUBLISH)) {
            throw new IllegalStateException("사용할 수 없는 상태의 상품권입니다.");
        }
        this.status = VoucherStatusType.USE;
    }
}
