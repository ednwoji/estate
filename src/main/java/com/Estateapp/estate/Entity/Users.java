package com.Estateapp.estate.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;
    private String phone_number;
    private String house_address;
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;

}
