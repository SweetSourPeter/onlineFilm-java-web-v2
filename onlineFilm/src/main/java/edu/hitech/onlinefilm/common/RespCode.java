package edu.hitech.onlinefilm.common;

import java.util.IllegalFormatException;

public enum RespCode {

    SUCCESS(0, "成功", ""),

    FAIL(1, "失败", ""),

    ERROR(-1, "错误", ""),


    USER_NO_LOGIN(3, "未登录或登录已超时，请重新登录", ""),

    INVALID_FORMATE(-10,"请求格式不符合要求",""),

    //数据库相关部分 -100 ~ -190           应该对最终用户屏蔽
    DB_ERROR(-100, "数据更新失败", ""),


    UN_UNIQUE_ERROR(-110, "违反唯一约束", "违反唯一约束:%s必须唯一！"),


    //缓存相关部分  -200 ~-290              应该对最终用户屏蔽
    REDIS_ERROR(-200, "缓存更新失败", "缓存更新失败"),

    REDISK_LOCK_ERROR(-210, "锁获取失败，业务不允许并发", "%s"),


    //消息中间件部分 ~300 ~ 390              应该对最终用户屏蔽
    MQ_ERROR(-300, "MQ失败", "MQ失败:%s"),



    //通用部分  100 ~900
    INVALID_DATA_PRIVILEGE(110, "非法的数据访问", "非法的数据访问:%s"),

    PARAM_ERROR(120, "参数不符合要求", "参数不符合要求:%s属性为空或属性值不合法！"),



    ACCESS_TOO_FASTER(130, "请求过于频繁", "请求过于频繁"),

    PATH_ERROR(150,"请求的路径不存在",""),

    //用户相关  1000 ~1900
    USER_ACCOUNT_ERROR(1010, "登录名或密码不正确", ""),    // 即用户不存在

    USER_ACCOUNT_DUPLICATE(1011, "账号重复", ""),



    USER_BLACKS_ERROR(1021, "您的账号存在异常，请与系统管理员联系", ""),


    //场次相关 1800 ~ 1900
    SCHEDULE_ID_NOT_EXISTS(2300,"场次id不存在",""),

    //订单相关，2000~2900
    ORDER_QUOTA_LACK(2100, "订单添加失败", "余票不足"),

    ORDER_ADD_FAILUE(2200, "订单添加失败！", ""),


    //备份恢复相关，2300~2500
    PERSIST_BACKUP_FAILUE(2300, "数据备份失败", "数据备份失败"),

    PERSIST_RECOVER_FAILUE(2400, "数据恢复失败！", "数据恢复失败"),

    PERSIST_RECOVER_NO_FILE_FOUND(2410, "数据恢复失败！", "文件不存在"),

    PERSIST_RECOVER_EMPTY_FILE(2420, "数据恢复失败！", "备份数据为空")
            ;

    /**
     * 代码
     */
    private final int code;
    /**
     * 信息
     */
    private final String message;

    /**
     * 补充信息，失败原因
     */
    private final Object reason;

    /**
     * @param code    错误码
     * @param message 错误信息
     * @param reason  详细描述
     */
    RespCode(int code, String message, Object reason) {
        this.code = code;
        this.message = message;
        this.reason = reason;
    }

    /**
     * 获得错误详细描述，并将指定参数进行格式化
     * <p>
     * <p/>
     *
     * @param params
     * @return
     * @throws IllegalFormatException
     * @author Holin Ding
     * @date 2017年12月2日 下午4:52:07
    */
    public String toString(Object... params) throws IllegalFormatException {
        if (params == null || params.length == 0) {
            return reason.toString();
        }
        return String.format(reason.toString(), params);
    }



    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the reason
     */
    public Object getReason() {
        return reason;
    }


}
