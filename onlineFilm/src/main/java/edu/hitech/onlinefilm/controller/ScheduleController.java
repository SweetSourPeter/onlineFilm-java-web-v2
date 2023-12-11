package edu.hitech.onlinefilm.controller;

import edu.hitech.onlinefilm.common.ResultJSONObject;
import edu.hitech.onlinefilm.domain.Schedule;
import edu.hitech.onlinefilm.utils.CacheUtils;
import edu.hitech.onlinefilm.utils.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/schedule")
@RestController
public class ScheduleController {
        @Autowired
        private DataHelper dataHelper;

        @RequestMapping("/list")
        public ResultJSONObject getSchedule(Pageable pageable) {
                ResultJSONObject result = ResultJSONObject.success();
                List<Map<String, Object>> schedulePage = dataHelper.getPaginatedSchedule(pageable);

                // Assuming that the DataHelper has a method getPaginatedSchedule that returns Page<Schedule>
                result.setData(schedulePage);
                return result;
        }
}
