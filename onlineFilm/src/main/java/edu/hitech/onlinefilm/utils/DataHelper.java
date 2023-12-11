package edu.hitech.onlinefilm.utils;

import edu.hitech.onlinefilm.dao.*;
import edu.hitech.onlinefilm.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class DataHelper {

    @Autowired
    private final  FilmRepository filmRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final TheaterRepository theaterRepository;

    private static final Logger logger = LoggerFactory.getLogger(DataHelper.class);

    public DataHelper(FilmRepository filmRepository, CustomerRepository customerRepository, ScheduleRepository scheduleRepository, OrderRepository orderRepository, TheaterRepository theaterRepository) {
        this.filmRepository = filmRepository;
        this.customerRepository = customerRepository;
        this.scheduleRepository = scheduleRepository;
        this.orderRepository = orderRepository;
        this.theaterRepository = theaterRepository;
    }

//    static final List<Film> films = new ArrayList<Film>();

//    static final Map<Integer, Film> filmMap = new HashMap<Integer, Film>();


//    static final List<Schedule> scheduleList = new ArrayList<Schedule>();

//    static final Map<Integer, Schedule> scheduleMap = new HashMap<Integer, Schedule>();

//    static  List<Order>  orderList = new ArrayList<Order>();


//    /**
//     *  客户ID 生成器
//     */
//    static AtomicInteger  CUSTOMERID_GENERATOR = new AtomicInteger(0);


//    static {
//        initFilm();
//
//        initSchedule();
//
//        initCustomer();
//    }
//
//    private static void initCustomer() {
//        //增加默认管理员
//        Customer customer = new Customer();
//        customer.setId(CUSTOMERID_GENERATOR.incrementAndGet());
//        customer.setAccount("superadmin");
//        customer.setPassword("123456");
//        customer.setGender("m");
//        customer.setEmail("admin@hotmail.com");
//        customer.setRegisterTime(new Date(System.currentTimeMillis()));
//        customer.setAlias("admin");
//
//        customerAccountMap.put(customer.getAccount(),customer);
//
//    }
//
//    private static void initFilm() {
//        Film film = new Film();
//        film.setId(1);
//        film.setName("蜀山传");
//        film.setClassify("玄幻");
//        film.setDirector("徐克");
//        film.setHero("郑伊健");
//        film.setHeroine("张伯芝");
//        film.setProduction(DateUtils.getDateByYMD("1993-04-23"));
//        films.add(film);
//
//        filmMap.put(film.getId(), film);
//
//        Film film2 = new Film();
//        film2.setId(2);
//        film2.setName("英雄");
//        film2.setClassify("武侠");
//        film2.setDirector("张艺谋");
//        film2.setHero("李连杰");
//        film2.setHeroine("章子怡");
//        film2.setProduction(DateUtils.getDateByYMD("2002-12-24"));
//        films.add(film2);
//        filmMap.put(film2.getId(), film2);
//
//        Film film3 = new Film();
//        film3.setId(3);
//        film3.setName("机械师2：复活");
//        film3.setClassify("科幻");
//        film3.setDirector("丹尼斯");
//        film3.setHero("杰森·斯");
//        film3.setHeroine("杰西卡");
//        film3.setProduction(DateUtils.getDateByYMD("2016-08-23"));
//        films.add(film3);
//
//        filmMap.put(film3.getId(), film3);
//
//    }
//
//
//    private static void initSchedule() {
//        Schedule schedule = new Schedule();
//        schedule.setId(100);
//        schedule.setFId(1);
//        schedule.setPrice(100);
//        schedule.setQuota(200);
//        schedule.setTheater("New York");
//        schedule.setShowTime(DateUtils.getDateByYMDHMS("2023-01-10 09:10:00"));
//
//        scheduleList.add(schedule);
//        scheduleMap.put(schedule.getId(), schedule);
//
//
//        Schedule schedule2 = new Schedule();
//        schedule2.setId(120);
//        schedule2.setFId(1);
//        schedule2.setPrice(100);
//        schedule2.setQuota(200);
//        schedule2.setTheater("New York");
//        schedule2.setShowTime(DateUtils.getDateByYMDHMS("2023-01-12 11:10:00"));
//
//        scheduleList.add(schedule2);
//        scheduleMap.put(schedule2.getId(), schedule2);
//
//
//        Schedule schedule3 = new Schedule();
//        schedule3.setId(200);
//        schedule3.setFId(2);
//        schedule3.setPrice(120);
//        schedule3.setQuota(200);
//        schedule3.setTheater("华盛顿");
//        schedule3.setShowTime(DateUtils.getDateByYMDHMS("2023-01-12 09:10:00"));
//
//        scheduleList.add(schedule3);
//        scheduleMap.put(schedule3.getId(), schedule3);
//
//
//        Schedule schedule4 = new Schedule();
//        schedule4.setId(300);
//        schedule4.setFId(3);
//        schedule4.setPrice(300);
//        schedule4.setQuota(150);
//        schedule4.setTheater("Boston");
//        schedule4.setShowTime(DateUtils.getDateByYMDHMS("2023-01-13 11:10:00"));
//
//
//        scheduleList.add(schedule4);
//        scheduleMap.put(schedule4.getId(), schedule4);
//
//    }


    public Film getFilmById(Integer id) {
        return filmRepository.findById(id).orElse(null);
    }

    public Customer getCustomByAccount(String account) {
        Optional<Customer> optionalCustomer = customerRepository.findByAccount(account);
        return optionalCustomer.orElse(null);
    }
    public boolean addCustom(Customer customer) {
        // Check if the customer with the same account already exists
        if (customerRepository.findByAccount(customer.getAccount()).isPresent()) {
            return false; // Customer with the same account already exists
        }

        // Set other properties and save the customer to the database
        customer.setRegisterTime(new Date(System.currentTimeMillis()));
        // Generate salt
        String salt = generateSalt();
        customer.setSalt(salt);

        // Encrypt password with MD5 and salt
        String encryptedPassword = encryptPassword(customer.getPassword(), salt);
        customer.setPassword(encryptedPassword);

        // Save the customer to the database
        try {
            System.out.println("Trying to save customer to database");
            customerRepository.save(customer);
        } catch (Exception e) {
            // Log the exception details
            System.out.println("Failed to save customer to database");
            System.out.println(e.getMessage());
            e.printStackTrace();
            // Handle the exception or rethrow as needed
        }
        return true;
    }

    // md5加密
    private String generateSalt() {
        // Generate a random string for salt
        // For simplicity, let's use a timestamp as a salt
        return String.valueOf(System.currentTimeMillis());
    }

    private String encryptPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((password + salt).getBytes());
            byte[] byteData = md.digest();

            // Convert the byte to hex format
            StringBuilder hexString = new StringBuilder();
            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception or rethrow as needed
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    public Schedule getScheduleById(int id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

//    public static List<Map<String,Object>> getOrderList(Integer customId){
//            List<Map<String,Object>> result = new ArrayList<>(20);
//        for (Order order: orderList
//             ) {
//             if (order.getCustomId().equals(customId)){
//                    Map<String,Object> orderInfo = new HashMap<>();
//                    orderInfo.put("orderNo",order.getId());
//                    Schedule schedule = scheduleMap.get(order.getScheduleId());
//                    orderInfo.put("filmName",filmMap.get(schedule.getId()).getName());
//                    orderInfo.put("showTime",DateUtils.fmtYmdHms(schedule.getShowTime()));
//                    orderInfo.put("buyTime",DateUtils.fmtYmdHms(order.getOrderTime()));
//                    orderInfo.put("quality",order.getQuality());
//                    orderInfo.put("money",order.getPrice()*order.getQuality());
//                    result.add(orderInfo);
//             }
//        }
//        return result;
//    }

    public List<Map<String, Object>> getOrderList(Integer customId) {
        List<Map<String, Object>> result = new ArrayList<>(20);

        List<Order> orderList = orderRepository.findByCustomId(customId);

        for (Order order : orderList) {
            Map<String, Object> orderInfo = new HashMap<>();
            orderInfo.put("orderNo", order.getId());

            Schedule schedule = scheduleRepository.findById(order.getScheduleId()).orElse(null);
            if (schedule != null) {
                Film film = filmRepository.findById(schedule.getFId()).orElse(null);
                orderInfo.put("filmName", film.getName());
                orderInfo.put("showTime", DateUtils.fmtYmdHms(schedule.getShowTime()));
            }

            orderInfo.put("buyTime", DateUtils.fmtYmdHms(order.getOrderTime()));
            orderInfo.put("quality", order.getQuality());
            orderInfo.put("money", order.getPrice() * order.getQuality());
            result.add(orderInfo);
        }
        return result;
    }

//    public  static  List<Map<String,Object>> getSchedule(){
//        List<Map<String,Object>> result = new ArrayList<>(20);
//        for (Schedule schedule: scheduleList
//        ) {
//                Map<String,Object> scheduleInfo = new HashMap<>();
//                scheduleInfo.put("id",schedule.getId());
//                scheduleInfo.put("theater",schedule.getTheater());
//                scheduleInfo.put("filmName",filmMap.get(schedule.getFId()).getName());
//                scheduleInfo.put("showTime",DateUtils.fmtYmdHms(schedule.getShowTime()));
//                scheduleInfo.put("ticketNum",schedule.getQuota());
//                scheduleInfo.put("price",schedule.getPrice());
//                result.add(scheduleInfo);
//        }
//        return result;
//    }



    public List<Map<String, Object>> getPaginatedSchedule(Pageable pageable) {
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);
        List<Map<String, Object>> result = new ArrayList<>(schedulePage.getSize());

        for (Schedule schedule : schedulePage.getContent()) {
            Map<String, Object> scheduleInfo = new HashMap<>();
            scheduleInfo.put("id", schedule.getId());

            Theater theater = theaterRepository.findById(schedule.getTheaterId()).orElse(null);
            scheduleInfo.put("theater", theater != null ? theater.getName() : null);

            Film film = filmRepository.findById(schedule.getFId()).orElse(null);
            scheduleInfo.put("filmName", film != null ? film.getName() : null);

            scheduleInfo.put("showTime", DateUtils.fmtYmdHms(schedule.getShowTime()));
            scheduleInfo.put("ticketNum", schedule.getQuota());
            scheduleInfo.put("price", schedule.getPrice());

            result.add(scheduleInfo);
        }

        return result;
    }


//    public static boolean addOrder(Order order){
//        orderList.add(order);
//        return true;
//    }

    public boolean addOrder(Order order) {
        try {
            orderRepository.save(order);
            return true;
        } catch (Exception e) {
            // Handle the exception, log it, or throw a custom exception if needed
            return false;
        }
    }


//    public static void doStore(){
//            List<Object> allData = new ArrayList<>();
//            allData.add(orderList);
//            allData.add(customerAccountMap);
//            try(
//                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("order.data")));
//            ){
//                out.writeObject(allData);
//                out.flush();
//                logger.info("数据成功保存到磁盘文件中");
//            }catch (Exception e){
//                logger.info("订单保存到数据文件失败");
//                throw new OnlineFilmException(RespCode.PERSIST_BACKUP_FAILUE);
//            }
//    }

//    public static void doStore() {
//        try {
//            List<Order> orderList = orderRepository.findAll();
//            Map<Integer, Customer> customerAccountMap = getCustomerAccountMap();
//
//            List<Object> allData = new ArrayList<>();
//            allData.add(orderList);
//            allData.add(customerAccountMap);
//
//            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("order.data")))) {
//                out.writeObject(allData);
//                out.flush();
//                logger.info("数据成功保存到磁盘文件中");
//            } catch (Exception e) {
//                logger.info("订单保存到数据文件失败");
//                throw new OnlineFilmException(RespCode.PERSIST_BACKUP_FAILUE);
//            }
//        } catch (Exception e) {
//            logger.error("Failed to fetch data from database", e);
//            // Handle exception or rethrow as needed
//        }
//    }
//
//    private static Map<Integer, Customer> getCustomerAccountMap() {
//        List<Customer> customers = customerRepository.findAll();
//        return customers.stream().collect(Collectors.toMap(Customer::getId, Function.identity()));
//    }
//
//
//
//    public static void  doRecover(OrderEvent orderEvent){
//        File dataFile = new File("order.data");
//        if (!dataFile.exists()){
//            logger.error("订单数据文件不存在");
//            throw new OnlineFilmException(RespCode.PERSIST_RECOVER_NO_FILE_FOUND);
//        }
//        try(
//                ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile));
//        ) {
//            List<Object> dataList = (List) in.readObject();
//            if (dataList==null || dataList.size()<1){
//                logger.warn("订单数据为空");
//                throw new OnlineFilmException(RespCode.PERSIST_RECOVER_EMPTY_FILE);
//            }
//            for(Object each:dataList){
//                if (each instanceof List){
//                    //订单数据
//                    orderList = (List<Order>) each;
//                    for(Order order:orderList){
//                        orderEvent.notifyObservers(order);
//                    }
//                }else if (each instanceof  Map){
//                    //用户信息
//                    customerAccountMap = (Map<String,Customer>)each;
//                }
//
//            }
//            DataHelper.orderList = orderList;
//        }catch (Exception e){
//            logger.error("订单数据加载失败",e);
//            throw new OnlineFilmException(RespCode.PERSIST_RECOVER_FAILUE);
//        }
//    }
}
