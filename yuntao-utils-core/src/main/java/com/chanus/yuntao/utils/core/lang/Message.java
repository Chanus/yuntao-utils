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

import com.chanus.yuntao.utils.core.ObjectUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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

    /**
     * 获取 String 类型的 data
     *
     * @return String 类型的 data
     * @since 1.6.0
     */
    public String getStringData() {
        return data == null ? null : data.toString();
    }

    /**
     * 获取指定类型的 data
     *
     * @param <T> 数据类型
     * @return 指定类型的 data
     * @since 1.6.0
     */
    @SuppressWarnings("unchecked")
    public <T> T getObjectData() {
        return data == null ? null : (T) data;
    }

    /**
     * 获取 Byte 类型的 data
     *
     * @return Byte 类型的 data
     * @since 1.6.0
     */
    public Byte getByteData() {
        return data != null && data instanceof Byte ? (Byte) data : null;
    }

    /**
     * 获取 byte 类型的 data
     *
     * @return byte 类型的 data
     * @since 1.6.0
     */
    public byte getByteValueData() {
        return ObjectUtils.defaultIfNull(getByteData(), (byte) 0);
    }

    /**
     * 获取 Short 类型的 data
     *
     * @return Short 类型的 data
     * @since 1.6.0
     */
    public Short getShortData() {
        return data != null && data instanceof Short ? (Short) data : null;
    }

    /**
     * 获取 short 类型的 data
     *
     * @return short 类型的 data
     * @since 1.6.0
     */
    public short getShortValueData() {
        return ObjectUtils.defaultIfNull(getShortData(), (short) 0);
    }

    /**
     * 获取 Integer 类型的 data
     *
     * @return Integer 类型的 data
     * @since 1.6.0
     */
    public Integer getIntegerData() {
        return data != null && data instanceof Integer ? (Integer) data : null;
    }

    /**
     * 获取 int 类型的 data
     *
     * @return int 类型的 data
     * @since 1.6.0
     */
    public int getIntValueData() {
        return ObjectUtils.defaultIfNull(getIntegerData(), 0);
    }

    /**
     * 获取 Long 类型的 data
     *
     * @return Long 类型的 data
     * @since 1.6.0
     */
    public Long getLongData() {
        return data != null && data instanceof Long ? (Long) data : null;
    }

    /**
     * 获取 long 类型的 data
     *
     * @return long 类型的 data
     * @since 1.6.0
     */
    public long getLongValueData() {
        return ObjectUtils.defaultIfNull(getLongData(), 0L);
    }

    /**
     * 获取 Float 类型的 data
     *
     * @return Float 类型的 data
     * @since 1.6.0
     */
    public Float getFloatData() {
        return data != null && data instanceof Float ? (Float) data : null;
    }

    /**
     * 获取 float 类型的 data
     *
     * @return float 类型的 data
     * @since 1.6.0
     */
    public float getFloatValueData() {
        return ObjectUtils.defaultIfNull(getFloatData(), 0.0f);
    }

    /**
     * 获取 Double 类型的 data
     *
     * @return Double 类型的 data
     * @since 1.6.0
     */
    public Double getDoubleData() {
        return data != null && data instanceof Double ? (Double) data : null;
    }

    /**
     * 获取 double 类型的 data
     *
     * @return double 类型的 data
     * @since 1.6.0
     */
    public double getDoubleValueData() {
        return ObjectUtils.defaultIfNull(getDoubleData(), 0.0d);
    }

    /**
     * 获取 Character 类型的 data
     *
     * @return Character 类型的 data
     * @since 1.6.0
     */
    public Character getCharacterData() {
        return data != null && data instanceof Character ? (Character) data : null;
    }

    /**
     * 获取 char 类型的 data
     *
     * @return char 类型的 data
     * @since 1.6.0
     */
    public char getCharValueData() {
        return ObjectUtils.defaultIfNull(getCharacterData(), '\u0000');
    }

    /**
     * 获取 Boolean 类型的 data
     *
     * @return Boolean 类型的 data
     * @since 1.6.0
     */
    public Boolean getBooleanData() {
        return data != null && data instanceof Boolean ? (Boolean) data : null;
    }

    /**
     * 获取 boolean 类型的 data
     *
     * @return boolean 类型的 data
     * @since 1.6.0
     */
    public boolean getBooleanValueData() {
        return ObjectUtils.defaultIfNull(getBooleanData(), false);
    }

    /**
     * 获取 Date 类型的 data
     *
     * @return Date 类型的 data
     * @since 1.6.0
     */
    public Date getDateData() {
        return data != null && data instanceof Date ? (Date) data : null;
    }

    /**
     * 获取 LocalDateTime 类型的 data
     *
     * @return LocalDateTime 类型的 data
     * @since 1.6.0
     */
    public LocalDateTime getLocalDateTimeData() {
        return data != null && data instanceof LocalDateTime ? (LocalDateTime) data : null;
    }

    /**
     * 获取 LocalDate 类型的 data
     *
     * @return LocalDate 类型的 data
     * @since 1.6.0
     */
    public LocalDate getLocalDateData() {
        return data != null && data instanceof LocalDate ? (LocalDate) data : null;
    }

    /**
     * 获取 LocalTime 类型的 data
     *
     * @return LocalTime 类型的 data
     * @since 1.6.0
     */
    public LocalTime getLocalTimeData() {
        return data != null && data instanceof LocalTime ? (LocalTime) data : null;
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
