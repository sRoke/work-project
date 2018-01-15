package net.kingsilk.qh.oauth.controller;

import io.swagger.annotations.*;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.validation.*;

import java.util.*;

/**
 * 统一格式的JSON响应。
 *
 * 大多数内容参考了 BasicErrorController 中的设置。
 *
 * @see BasicErrorController
 * @see DefaultErrorAttributes
 */
@ApiModel
@Deprecated // 请使用 qh-oauth-api 中 UniResp
public class UniResp<T> {


    @ApiModelProperty(value = "状态码。小于 10000 的，状态码的含义与HTTP协议中的状态一直。大于等于 10000 的，为自定义错误的状态码。成功时为 2xx。", example = "200", required = true)
    private Integer status = 200;

    @ApiModelProperty(value = "业务数据。如果出错时，该字段可能为空。")
    private T data;

    @ApiModelProperty(value = "时间戳。响应生成的时间，或异常发生的服务器端系统时间。。")
    private Date timestamp;

    @ApiModelProperty(value = "错误消息。可选，出错时才可能有值。")
    private String error;

    @ApiModelProperty(value = "异常的名称。可选，出错时才可能有值。")
    private String exception;

    @ApiModelProperty(value = "异常的错误消息。可选，出错时才可能有值。")
    private String message;

    /**
     * BindingResult 异常中的错误。
     *
     * 可选，出错时才可能有值。
     *
     * @see BindingResult
     * @see ObjectError
     */
    @ApiModelProperty(value = "字段绑定出错时的错误信息。可选，出错时才可能有值。")
    private List<Object> errors;

    @ApiModelProperty(value = "异常发生时的堆栈信息。可选，出错时才可能有值。")
    private String trace;

    public static boolean isSuccess(UniResp<?> resp) {
        return isSuccess(resp.status);
    }

    public static boolean isSuccess(Integer status) {
        if (status != null && 200 <= status && status < 300) {
            return true;
        }

        return false;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

}
