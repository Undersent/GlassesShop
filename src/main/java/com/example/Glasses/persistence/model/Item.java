package com.example.Glasses.persistence.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private int itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "is_available")
    @Builder.Default
    private boolean available = true;

    @Column(name = "price")
    private double price;

    @Column(name = "correction")
    private double correction;

    @Column(name = "url")
    private String url;
}
