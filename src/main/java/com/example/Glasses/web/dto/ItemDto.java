package com.example.Glasses.web.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private String itemName;

    private boolean available = true;

    private double price;

    private double correction;

    private String url;
}
