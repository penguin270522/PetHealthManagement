package com.example.pethealth.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleResponese <T>{
    public List<T> results;
    public int limit;
    public int page;
    public int totalItem;
    public int totalPage;
    public int totalPending;
    public int totalActive;
    public int totalDisable;
}
