package edu.hitech.onlinefilm.services;


import edu.hitech.onlinefilm.domain.Order;
import edu.hitech.onlinefilm.domain.Schedule;
import edu.hitech.onlinefilm.utils.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class InventoryServices implements Observer {
    @Autowired
    private DataHelper dataHelper;
    private static final ExecutorService executorService =  Executors.newSingleThreadExecutor();

    @Override
    public void update(Observable o, Object arg) {
        Runnable runnable = () ->{
                Order order = (Order)arg;
                Schedule schedule = dataHelper.getScheduleById(order.getScheduleId());
                schedule.setQuota(schedule.getQuota()-order.getQuality());
        };
        executorService.submit(runnable);
    }




}
