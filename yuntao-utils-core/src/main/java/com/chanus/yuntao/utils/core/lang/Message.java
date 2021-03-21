/*
 * Copyright (c) 2020 Chanus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chanus.yuntao.utils.core.lang;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 消息封装
 *
 * @author Chanus
 * @date 2020-10-26 14:11:47
 * @since 1.4.2
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 操作成功代码
     */
    public final static int SUCCESS = 0;
    /**
     * 操作失败代码
     */
    public final static int FAIL = 1;

    /**
     * 信息代码
     */
    private int code;
    /**
     * 信息内容
     */
    private String msg;
    /**
     * 数据对象
     */
    private Object data;
    /**
     * 数据列表
     */
    private List<?> datas;
    /**
     * Map 集合数据
     */
    private Map<?, ?> map;

    /**
     * 构造方法，初始化 {@code code}
     */
    public Message() {
        super();
        this.code = SUCCESS;
    }

    /**
     * 构造方法，初始化 {@code code} 和 {@code msg}
     *
     * @param code 信息代码
     * @param msg  信息内容
     */
    public Message(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    /**
     * 设置 {@code code} 和 {@code msg}
     *
     * @param code 信息代码
     * @param msg  信息内容
     * @return {@link Message}
     */
    public static Message init(int code, String msg) {
        return new Message(code, msg);
    }

    /**
     * 执行成功
     *
     * @return {@link Message}
     */
    public static Message success() {
        return new Message();
    }

    /**
     * 执行成功
     *
     * @param msg 信息内容
     * @return {@link Message}
     */
    public static Message success(String msg) {
        return new Message(SUCCESS, msg);
    }

    /**
     * 执行失败
     *
     * @return {@link Message}
     */
    public static Message fail() {
        return new Message(FAIL, null);
    }

    /**
     * 执行失败
     *
     * @param msg 信息内容
     * @return {@link Message}
     */
    public static Message fail(String msg) {
        return new Message(FAIL, msg);
    }

    /**
     * 执行失败
     *
     * @param code 信息代码
     * @param msg  信息内容
     * @return {@link Message}
     */
    public static Message fail(int code, String msg) {
        return new Message(code, msg);
    }

    /**
     * 添加成功
     *
     * @return {@link Message}
     */
    public static Message addSuccess() {
        return success("添加成功");
    }

    /**
     * 添加失败
     *
     * @return {@link Message}
     */
    public static Message addFail() {
        return fail("添加失败");
    }

    /**
     * 删除成功
     *
     * @return {@link Message}
     */
    public static Message deleteSuccess() {
        return success("删除成功");
    }

    /**
     * 删除失败
     *
     * @return {@link Message}
     */
    public static Message deleteFail() {
        return fail("删除失败");
    }

    /**
     * 修改成功
     *
     * @return {@link Message}
     */
    public static Message updateSuccess() {
        return success("修改成功");
    }

    /**
     * 修改失败
     *
     * @return {@link Message}
     */
    public static Message updateFail() {
        return fail("修改失败");
    }

    /**
     * 操作成功
     *
     * @return {@link Message}
     */
    public static Message operateSuccess() {
        return success("操作成功");
    }

    /**
     * 操作失败
     *
     * @return {@link Message}
     */
    public static Message operateFail() {
        return fail("操作失败");
    }

    /**
     * 判断是否操作成功
     *
     * @return {@code true} 操作成功；{@code false} 操作失败
     * @since 1.4.3
     */
    public boolean isSuccess() {
        return this.code == SUCCESS;
    }

    /**
     * 判断是否操作失败
     *
     * @return {@code true} 操作失败；{@code false} 操作成功
     * @since 1.4.6
     */
    public boolean isFail() {
        return this.code != SUCCESS;
    }

    /**
     * 执行成功
     *
     * @param msg 信息内容
     * @return {@link Message}
     * @since 1.4.6
     */
    public Message setSuccess(String msg) {
        this.code = SUCCESS;
        this.msg = msg;
        return this;
    }

    /**
     * 执行失败
     *
     * @param msg 信息内容
     * @return {@link Message}
     * @since 1.4.6
     */
    public Message setFail(String msg) {
        this.code = FAIL;
        this.msg = msg;
        return this;
    }

    /**
     * 执行失败
     *
     * @param code 信息代码
     * @param msg  信息内容
     * @return {@link Message}
     * @since 1.4.6
     */
    public Message setFail(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Message setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Message setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Message setData(Object data) {
        this.data = data;
        return this;
    }

    public List<?> getDatas() {
        return datas;
    }

    public Message setDatas(List<?> datas) {
        this.datas = datas;
        return this;
    }

    public Map<?, ?> getMap() {
        return map;
    }

    public Message setMap(Map<?, ?> map) {
        this.map = map;
        return this;
    }

    @Override
    public String toString() {
        return "Message [" +
                "code=" + code +
                ", msg=" + msg +
                ", data=" + data +
                ", datas=" + datas +
                ", map=" + map +
                "]";
    }
}
