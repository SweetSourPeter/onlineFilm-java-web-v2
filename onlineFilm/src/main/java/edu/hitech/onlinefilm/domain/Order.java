package edu.hitech.onlinefilm.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "t_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "schedule_id")
    private Integer scheduleId;

    private Integer price;

    private Integer  quality;

    @Column(name = "customer_id")
    private  Integer  customId;

    private  String status;

    @Column(name = "order_time")
    private  Date orderTime;

    @Column(name = "pay_time")
    private Date payTime;

    @Column(name = "ticket_no")
    private  String ticketNo;
}
