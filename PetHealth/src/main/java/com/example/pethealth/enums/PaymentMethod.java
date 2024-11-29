package com.example.pethealth.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum PaymentMethod {
    CASH("thanh toán tiền mặt"),
    CARD("thanh toán bằng thẻ");

    private final String displayName;

    PaymentMethod(String displayName){
        this.displayName = displayName;
    }
}
