package com.study.system.exception;


import com.study.tool.model.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther  吕伟林
 * @Des 接口异常拦截通用处理
 * @Date 2021/6/25 12:01
 */
@ControllerAdvice
@Slf4j
public class ControllerException {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public HttpResult errorHandler(Exception ex) {
        if (ex instanceof MsgException) {
            return HttpResult.error(100, null, ex.getMessage());
        } else {
            log.error(ex.getMessage() + getStackMsg(ex));
            return HttpResult.error(ex.getMessage());
        }
    }

    private String getStackMsg(Exception e) {

        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackArray = e.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            sb.append(element.toString() + "\n");

        }
        return sb.toString();
    }

}
