package edu.hitech.onlinefilm.controller;

import cn.hutool.core.util.RandomUtil;
import edu.hitech.onlinefilm.common.RespCode;
import edu.hitech.onlinefilm.common.ResultJSONObject;
import edu.hitech.onlinefilm.dao.OrderRepository;
import edu.hitech.onlinefilm.domain.Order;
import edu.hitech.onlinefilm.domain.Schedule;
import edu.hitech.onlinefilm.exception.OnlineFilmException;
import edu.hitech.onlinefilm.services.OrderEvent;
import edu.hitech.onlinefilm.utils.CacheUtils;
import edu.hitech.onlinefilm.utils.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/order")
@RestController
@Validated
public class OrderController {

    @Autowired
    private DataHelper dataHelper;
    private static final String SEED="0123456789";

    @Autowired
    OrderEvent orderEvent;

    @Autowired
    OrderRepository orderRepository;


    @RequestMapping("/list")
    public ResultJSONObject getOrder(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ResultJSONObject result = ResultJSONObject.success();

        // Assuming CacheUtils.getCurrentUser().getId() returns the user id
        Integer userId = CacheUtils.getCurrentUser().getId();

        // Fetch paginated order data using the existing method
        Page<Order> orderPage = orderRepository.findByCustomIdOrderByOrderTimeDesc(userId, pageable);

        // Extract necessary information from the paginated result
        List<Order> orderList = orderPage.getContent();
        result.setData(orderList);

        // Add pagination information to the result if needed
//        result.setPageInfo(orderPage);

        return result;
    }

    @RequestMapping("/placeOrder")
    public ResultJSONObject  placeOrder(@NotNull  Integer scheduleId,@NotNull @Min(1) Integer ticketNum){
        ResultJSONObject result = ResultJSONObject.success();

        checkQuota(scheduleId,ticketNum);

        Order order = saveOrder(scheduleId, ticketNum);

        if  (order!=null) {
            //通知统计和库存更新
            orderEvent.notifyObservers(order);
        }else{
            result = new ResultJSONObject(RespCode.ORDER_ADD_FAILUE);
        }

        return result;
    }



    private Order saveOrder(Integer scheduleId, Integer ticketNum) {
        Order order = new Order();
//        order.setId(Long.valueOf(RandomUtil.randomString(SEED,8)));
        order.setCustomId(CacheUtils.getCurrentUser().getId());
        order.setQuality(ticketNum);
        order.setPrice(dataHelper.getScheduleById(scheduleId).getPrice());
        order.setScheduleId(scheduleId);
        order.setStatus("new");
        order.setOrderTime(new Date(System.currentTimeMillis()));
        order.setTicketNo(RandomUtil.randomString(SEED,8));

        return dataHelper.addOrder(order)?order:null;
    }

    private void checkQuota(Integer scheduleId, Integer ticketNum) {
        Schedule schedule = dataHelper.getScheduleById(scheduleId);
        if (schedule == null){
            throw new OnlineFilmException(RespCode.SCHEDULE_ID_NOT_EXISTS);
        }
        if (schedule.getQuota()< ticketNum){
            throw new OnlineFilmException(RespCode.ORDER_QUOTA_LACK);
        }

    }
}
