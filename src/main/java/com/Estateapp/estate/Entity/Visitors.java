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
public class Visitors {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long visitorId;

    private String visitor_name;
    private String visitor_email;
    private String visitor_phone;
    private int visitor_duration;
    private String location;
    private String visitor_code;
    private String whomToSee;

}
