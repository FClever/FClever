package cn.hnhczn.app.mvp.model.entity;

import java.io.Serializable;

import cn.hnhczn.app.mvp.model.api.Api;

/**
 * 统一返回数据格式
 *
 * @author yanweiqiang
 * @Title:ResultData
 * @Description: TODO
 * @Company: Hunan Haichuang Intelligent Technology Development Co.Ltd
 * @date 2017年10月19日 下午2:40:12
 */
public class ResultData<T> implements Serializable{
    /**
     * 请求状态码
     * 200	成功
     * 500	异常
     * 304	重定向
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回结果
     */
    private T result;

    public ResultData() {
    }

    public ResultData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultData(Integer code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}