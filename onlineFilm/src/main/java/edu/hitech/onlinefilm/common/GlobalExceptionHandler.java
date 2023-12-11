package edu.hitech.onlinefilm.common;

import edu.hitech.onlinefilm.exception.OnlineFilmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    public String defaultExceptionHandler(HttpServletRequest request,
                                                    Exception exception) throws Exception{
        logger.warn(exception.getMessage(),exception);
        return "error";
    }


    @ExceptionHandler(value = {NoHandlerFoundException.class} )
    public ResultJSONObject noHandlerException(HttpServletRequest request, NoHandlerFoundException exception) throws Exception{
        logger.warn(exception.getMessage(),exception);
        return ResultJSONObject.noHandlerError(exception.getMessage());
    }

    @ExceptionHandler(value = { BindException.class } )
    @ResponseBody
    public ResultJSONObject  validateException(HttpServletRequest request, BindException exception) throws Exception{
        logger.warn(exception.getMessage(),exception);
        return ResultJSONObject.validFailure(extractBindInfo(request,exception));
    }

    @ExceptionHandler(value = { MissingServletRequestParameterException.class} )
    public ResultJSONObject noHandlerException(HttpServletRequest request,     MissingServletRequestParameterException exception) throws Exception{
        logger.warn(exception.getMessage(),exception);
        ResultJSONObject result = new ResultJSONObject(RespCode.PARAM_ERROR);
        result.setReason(exception.getMessage());
        return result;
    }

    @ExceptionHandler(value = { ConstraintViolationException.class} )
    public ResultJSONObject noHandlerException(HttpServletRequest request,     ConstraintViolationException exception) throws Exception{
        logger.warn(exception.getMessage(),exception);
        ResultJSONObject result = new ResultJSONObject(RespCode.PARAM_ERROR);
        result.setMessage(exception.getMessage());
        result.setReason(exception.getMessage());
        return result;
    }

    private Map  extractBindInfo(HttpServletRequest request,BindException bindException){
            Map<String,Object> message = new HashMap<String, Object>();
            bindException.getFieldErrors().forEach( e -> message.put(e.getField(),e.getDefaultMessage()));
            return message;

    }

    //捕获可预知的异常，超时，权限等
    @ExceptionHandler(value= {OnlineFilmException.class})
    public ResultJSONObject allExceptionHandler(HttpServletRequest request, OnlineFilmException exception) throws Exception
    {
        ResultJSONObject result=new ResultJSONObject(RespCode.FAIL);
        result.setResultCode(exception.getErrorCode());
        result.setMessage(exception.getMessage());
        result.setReason(exception.getReason());
        return result;
    }


}
