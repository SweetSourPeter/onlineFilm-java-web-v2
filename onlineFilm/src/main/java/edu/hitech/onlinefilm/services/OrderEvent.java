package edu.hitech.onlinefilm.services;

import edu.hitech.onlinefilm.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Observable;

@Service
public class OrderEvent extends Observable {

        @Autowired
        InventoryServices inventoryServices;

        @Autowired
        SaleStatisticsServices saleStatisticsServices;

        @PostConstruct
        void init()
        {
            addObserver(inventoryServices);
            addObserver(saleStatisticsServices);
        }

        public void notifyObservers(Order order){
            super.setChanged();
            super.notifyObservers(order);
        }

    }