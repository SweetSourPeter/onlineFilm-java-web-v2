package edu.hitech.onlinefilm.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 分场统计
 */
@Data
@Entity
@Table(name = "t_statistics")
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer amount;

    private Integer quality;

    @Column(name = "schedule_id")
    private Integer scheduleId;
}
