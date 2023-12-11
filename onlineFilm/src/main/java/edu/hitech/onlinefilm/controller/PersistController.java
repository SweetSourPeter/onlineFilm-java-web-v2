//package edu.hitech.onlinefilm.controller;
//
//import edu.hitech.onlinefilm.common.RespCode;
//import edu.hitech.onlinefilm.common.ResultJSONObject;
//import edu.hitech.onlinefilm.services.OrderEvent;
//import edu.hitech.onlinefilm.utils.DataHelper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/persist")
//public class PersistController {
//
//        @Autowired
//        private OrderEvent orderEvent;
//
//        private  static final Character PERSIT_LOCK = new Character('0');
//
//        @RequestMapping("/store")
//        public ResultJSONObject  backup(){
//                ResultJSONObject result = new ResultJSONObject(RespCode.SUCCESS);
//                synchronized (PERSIT_LOCK){
//                    DataHelper.doStore();
//                }
//                return result;
//        }
//
//
//
//        @RequestMapping("/recover")
//        public ResultJSONObject  recover(){
//            ResultJSONObject result = new ResultJSONObject(RespCode.SUCCESS);
//            synchronized (PERSIT_LOCK){
//                DataHelper.doRecover(orderEvent);
//            }
//            return result;
//        }
//
//
//}
