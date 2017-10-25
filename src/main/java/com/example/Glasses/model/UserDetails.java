package com.example.Glasses.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_details")
public class UserDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_details_id")
    private int userDetailsId;

    @Column(name = "username")
    private String Username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name ="last_name")
    private String lastName;

}
