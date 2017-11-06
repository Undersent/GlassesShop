package com.example.Glasses.persistence.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_item")
public class UserItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_item_id")
    private int userItemId;

    @JoinColumn(name = "cart")
    private int cartId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="item")
    private Item item;
}
