package edu.hitech.onlinefilm.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "t_film")
public class Film  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;

    private String classify;

    private String director;

    private String hero;

    private String heroine;

    private Date production;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getClassify() {
//        return classify;
//    }
//
//    public void setClassify(String classify) {
//        this.classify = classify;
//    }
//
//    public String getDirector() {
//        return director;
//    }
//
//    public void setDirector(String director) {
//        this.director = director;
//    }
//
//    public String getHero() {
//        return hero;
//    }
//
//    public void setHero(String hero) {
//        this.hero = hero;
//    }
//
//    public String getHeroine() {
//        return heroine;
//    }
//
//    public void setHeroine(String heroine) {
//        this.heroine = heroine;
//    }
//
//    public Date getProduction() {
//        return production;
//    }
//
//    public void setProduction(Date production) {
//        this.production = production;
//    }
}
