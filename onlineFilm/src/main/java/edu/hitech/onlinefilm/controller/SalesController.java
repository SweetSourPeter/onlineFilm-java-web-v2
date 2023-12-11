package edu.hitech.onlinefilm.controller;

import edu.hitech.onlinefilm.common.ResultJSONObject;
import edu.hitech.onlinefilm.domain.Film;
import edu.hitech.onlinefilm.domain.Order;
import edu.hitech.onlinefilm.domain.Schedule;
import edu.hitech.onlinefilm.domain.Statistic;
import edu.hitech.onlinefilm.services.SaleStatisticsServices;
import edu.hitech.onlinefilm.utils.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private DataHelper dataHelper;

    @Autowired
    private SaleStatisticsServices saleStatisticsServices;

    @RequestMapping("/summary")
    public ResultJSONObject getSummary() {
        ResultJSONObject result = ResultJSONObject.success();
        List<Map<String, Object>> data = getSalesData();
        result.setData(data);
        return result;
    }

    private List<Map<String, Object>> getSalesData() {
        List<Map<String, Object>> result = new ArrayList<>();

        List<Order> orders = dataHelper.getOrders();

        for (Order order : orders) {
            Map<String, Object> item = new HashMap<>();

            Schedule schedule = dataHelper.getScheduleById(order.getScheduleId());
            Film film = dataHelper.getFilmById(schedule.getFId());

            item.put("scheduleId", schedule.getId());
            item.put("filmName", film.getName());
            item.put("showTime", schedule.getShowTime());
            item.put("quality", order.getQuality());
            item.put("amount", order.getQuality());

            result.add(item);
        }

        return result;
    }
}