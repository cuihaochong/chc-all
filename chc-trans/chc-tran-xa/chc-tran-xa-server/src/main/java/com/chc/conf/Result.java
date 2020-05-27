package com.chc.conf;

import java.io.Serializable;

/**
 * Description:通用封装返回类
 *
 * @author CuiHaochong
 * @date 2019/8/29
 */
public class Result<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    /**
     * 成功,默认状态码,返回消息,无返回数据
     *
     * @param <T> 返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> success() {
        return new Result<>();
    }

    /**
     * 成功,默认状态码,返回消息,自定义返回数据
     *
     * @param data 自定义返回数据
     * @param <T>  返回类泛型,不能为String
     * @return 通用返回Result
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * 成功,默认状态码,自定义返回消息,无返回数据
     *
     * @param msg 自定义返回消息
     * @param <T> 返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> success(String msg) {
        return new Result<>(msg);
    }

    /**
     * 成功,默认状态码,自定义返回消息,返回数据
     *
     * @param msg  自定义返回消息
     * @param data 自定义返回数据
     * @param <T>  返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(msg, data);
    }

    /**
     * 失败,默认状态码,返回消息,无返回数据
     *
     * @param <T> 返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> error() {
        return new Result<>(CodeMsg.ERROR);
    }

    /**
     * 失败,默认状态码,自定义返回消息,无返回数据
     *
     * @param <T> 返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(CodeMsg.ERROR.getCode(), msg);
    }

    /**
     * 失败,自定义状态码,返回消息,无返回数据
     *
     * @param code 自定义状态码
     * @param msg  自定义返回消息
     * @param <T>  返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg);
    }

    /**
     * 失败,使用CodeMsg状态码,返回消息,无返回数据
     *
     * @param codeMsg CodeMsg,参数如下:
     *                <p> code 状态码
     *                <p> msg  返回消息
     * @param <T>     返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<>(codeMsg);
    }

    /**
     * 成功构造器,无返回数据
     */
    private Result() {
        this(CodeMsg.SUCCESS);
    }

    /**
     * 成功构造器,自定义返回数据
     *
     * @param data 返回数据
     */
    private Result(T data) {
        this(CodeMsg.SUCCESS, data);
    }

    /**
     * 成功构造器,自定义返回消息,无返回数据
     *
     * @param msg 返回消息
     */
    private Result(String msg) {
        this(CodeMsg.SUCCESS.getCode(), msg);
    }

    /**
     * 成功构造器,自定义返回信息,返回数据
     *
     * @param msg  返回信息
     * @param data 返回数据
     */
    private Result(String msg, T data) {
        this(CodeMsg.SUCCESS.getCode(), msg, data);
    }

    /**
     * 构造器,自定义状态码,返回消息
     *
     * @param code 状态码
     * @param msg  返回消息
     */
    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造器,自定义状态码,返回消息,返回数据
     *
     * @param code 状态码
     * @param msg  返回消息
     * @param data 返回数据
     */
    private Result(int code, String msg, T data) {
        this(code, msg);
        this.data = data;
    }

    /**
     * 构造器,使用CodeMsg状态码与返回信息
     *
     * @param codeMsg CodeMsg,参数如下:
     *                <p> code 状态码
     *                <p> msg  返回消息
     */
    private Result(CodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }

    /**
     * 构造器,使用CodeMsg状态码与返回信息,自定义返回数据
     *
     * @param codeMsg CodeMsg,参数如下:
     *                <p> code 状态码
     *                <p> msg  返回消息
     * @param data    返回数据
     */
    private Result(CodeMsg codeMsg, T data) {
        this(codeMsg);
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{" +
            "code=" + code +
            ", msg='" + msg + '\'' +
            ", data=" + data +
            '}';
    }

    public static class CodeMsg {
        /**
         * 状态码
         */
        private int code;

        /**
         * 状态信息
         */
        private String msg;

        /**
         * 默认成功
         */
        static CodeMsg SUCCESS = new CodeMsg(0, "操作成功");
        /**
         * 默认失败
         */
        static CodeMsg ERROR = new CodeMsg(1, "操作失败");

        private CodeMsg(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        @Override
        public String toString() {
            return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
        }
    }
}