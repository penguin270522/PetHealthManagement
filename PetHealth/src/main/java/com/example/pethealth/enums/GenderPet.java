package com.example.pethealth.enums;

import lombok.Getter;

@Getter
public enum GenderPet {
    DUC("đực"),
    CAI("cái");

    private final String displayName;

    GenderPet(String displayName) {
        this.displayName = displayName;
    }

}
