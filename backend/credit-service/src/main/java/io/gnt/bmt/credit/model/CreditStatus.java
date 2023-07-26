package io.gnt.bmt.credit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CreditStatus {
    FAILED,
    PENDING,
    APPROVED,
    DECLINED,
}