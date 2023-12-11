package edu.hitech.onlinefilm.domain;

import lombok.Data;
import org.slf4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "t_schedule")
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "f_id")
    private Integer fId;

    @Column(name = "show_time")
    private Date showTime;

    private Integer quota;

    private Integer price;

    @Column(name = "theater_id")
    private Integer theaterId;

    private Integer version;


//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getTheater() {
//        return theater;
//    }
//
//    public void setTheater(String theater) {
//        this.theater = theater;
//    }
//
//    public Integer getfId() {
//        return fId;
//    }
//
//    public void setfId(Integer fId) {
//        this.fId = fId;
//    }
//
//    public Date getShowTime() {
//        return showTime;
//    }
//
//    public void setShowTime(Date showTime) {
//        this.showTime = showTime;
//    }
//
//    public Integer getQuota() {
//        return quota;
//    }
//
//    public void setQuota(Integer quota) {
//        this.quota = quota;
//    }
//
//    public Integer getPrice() {
//        return price;
//    }
//
//    public void setPrice(Integer price) {
//        this.price = price;
//    }
}
