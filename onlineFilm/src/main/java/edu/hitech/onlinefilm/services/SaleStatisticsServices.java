package edu.hitech.onlinefilm.services;

import edu.hitech.onlinefilm.dao.FilmRepository;
import edu.hitech.onlinefilm.dao.ScheduleRepository;
import edu.hitech.onlinefilm.dao.StatisticsRepository;
import edu.hitech.onlinefilm.dao.TheaterRepository;
import edu.hitech.onlinefilm.domain.*;
import edu.hitech.onlinefilm.domain.Statistic;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 销售统计
 */
@Service
public class SaleStatisticsServices implements Observer {
    private static final ExecutorService executorService =  Executors.newSingleThreadExecutor();

    @Autowired
    private StatisticsRepository statisticRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private FilmRepository filmRepository;

    private Map<Integer, Statistic> salesData = new ConcurrentHashMap<>();

    @Override
    public void update(Observable o, Object args) {
        Runnable runnable = ()->{
            Order order  = (Order) args;
            Integer scheduleId = order.getScheduleId();

            // Fetch relevant data
            Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
            Optional<Statistic> statisticOptional = statisticRepository.findByScheduleId(scheduleId);

            if (scheduleOptional.isPresent() && statisticOptional.isPresent()) {
                Schedule schedule = scheduleOptional.get();
                Statistic statistic = statisticOptional.get();

                // Fetch movie and theater information
                Optional<Film> filmOptional = filmRepository.findById(schedule.getFId());
                Optional<Theater> theaterOptional = theaterRepository.findById(schedule.getTheaterId());

                if (filmOptional.isPresent() && theaterOptional.isPresent()) {
                    Film film = filmOptional.get();
                    Theater theater = theaterOptional.get();

                    // Update salesData map
                    salesData.put(schedule.getId(), statistic);

                    // Display sales statistics data
                    System.out.println("Movie Sales Statistics:");
                    System.out.println("Schedule ID: " + schedule.getId());
                    System.out.println("Movie Name: " + film.getName());
                    System.out.println("Theater: " + theater.getName());
                    System.out.println("Booking Quantity: " + statistic.getQuality());
                    System.out.println("Total Amount: " + statistic.getAmount());
                }
            }
        };
        executorService.submit(runnable);
    }
    public Map<Integer, Statistic> getSalesData() {
        return salesData;
    }
}
