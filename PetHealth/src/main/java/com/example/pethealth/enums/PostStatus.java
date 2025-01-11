package com.example.pethealth.enums;

import lombok.Getter;
import lombok.Setter;

@Getter

public enum PostStatus {
    ACTIVE("Được duyệt"),
    PENDING("Chưa được duyệt"),
    DISABLE("Từ chối");
    private final String displayStatus;

    PostStatus(String displayStatus){
        this.displayStatus = displayStatus;
    }
}
