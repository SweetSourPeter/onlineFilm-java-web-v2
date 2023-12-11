package edu.hitech.onlinefilm.exception;

import edu.hitech.onlinefilm.common.RespCode;

/*
 * @author      chengYu
 * @date     	2020/9/2
 * @describe    在线电影自定义异常类,所有接口层业务中的异常捕获均需要抛出该异常类
 */
public class OnlineFilmException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	private final int errorCode;
	
	/**
	 * 异常原因
	 */
	private final String reason;
	
	/**
	 * 请求上下文
	 */
	private  String ctx="";
	
	/**
	 * 运维开始时间
	 */
	private String  beginTime="";
	
	
	/**
	 * 运维结束时间
	 */
	private String  endTime="";
	
	/**
	 * 请求的方法
	 */
	private String method;

	/**
	 * @param message
	 * @param cause
	 */
	public OnlineFilmException(int errorCode, String message, Throwable cause, String ctx) {
		super(message, cause);
		this.errorCode = errorCode;
		this.reason = cause.getMessage();
		this.ctx=ctx;
	}
	
	public OnlineFilmException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.reason = cause.getMessage();
		this.ctx="";
	}

	/**
	 * 
	 * @param errorCode
	 * @param message
	 */
	public OnlineFilmException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.reason = message;
	}
	
	public OnlineFilmException(int errorCode, String message, String ctx) {
		super(message);
		this.errorCode = errorCode;
		this.reason = message;
		this.ctx = ctx;
	}

	/**
	 * @param message
	 * @param message
	 */
	public OnlineFilmException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = 1;
		this.reason = cause.getMessage();
	}
	
	/**
	 * @param message
	 */
	public OnlineFilmException(String message) {
		super(message);
		this.errorCode = 1;
		this.reason = message;
	}

	public OnlineFilmException(RespCode respCode, Object... params) {
		super(respCode.getMessage());
		this.errorCode = respCode.getCode();
		this.reason = respCode.toString(params);
	}



	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	
	
	public void setCtx(String ctx) {
		 this.ctx=ctx;
	}
	
	public String getCtx() {
		return ctx;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
}