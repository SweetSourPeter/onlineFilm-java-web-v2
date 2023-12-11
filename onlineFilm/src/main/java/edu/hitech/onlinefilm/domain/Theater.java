package edu.hitech.onlinefilm.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_theater")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String address;

    private String phone;

    private Integer capacity;

    // Constructors, getters, and setters
}