package com.example.Ejercios.Persistence.entity;

import jdk.jfr.DataAmount;


import javax.persistence.*;

@Entity
@Table(name="laptop")
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String mark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Laptop(Long id, String name, String mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;
    }

    public Laptop() {
    }
}
