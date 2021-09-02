package com.mvc.spring.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Games")
public class Game implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(updatable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String platform;
    @Column(nullable = false)
    private Integer year;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private String publisher;
    @Column(name = "eu_sales", nullable = false)
    private Double euSales;

    public Game() {
    }

    public Game(String name, String platform, Integer year, String genre, String publisher, Double euSales) {
        this.name = name;
        this.platform = platform;
        this.year = year;
        this.genre = genre;
        this.publisher = publisher;
        this.euSales = euSales;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Double getEuSales() {
        return euSales;
    }

    public void setEuSales(Double euSales) {
        this.euSales = euSales;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", platform='" + platform + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", publisher='" + publisher + '\'' +
                ", euSales=" + euSales +
                '}';
    }
}