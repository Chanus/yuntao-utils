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

import com.chanus.yuntao.utils.core.map.CustomMap;

import java.util.List;

/**
 * 分页数据封装
 *
 * @param <T> 分页数据实体类型
 * @author Chanus
 * @since 1.4.2
 */
public class Page<T> {
    /**
     * 默认每页数据条数
     */
    public static final int PAGE_SIZE = 20;

    /**
     * 信息代码
     */
    private int code;

    /**
     * 信息内容
     */
    private String msg;

    /**
     * 记录条数
     */
    private int count;

    /**
     * 记录对象
     */
    private Object object;

    /**
     * 记录列表
     */
    private List<T> data;

    /**
     * 记录统计行
     */
    private T totalRow;

    /**
     * 构造方法，初始化 {@code code} 和 {@code count}
     */
    public Page() {
        super();
        this.code = 0;
        this.count = 0;
    }

    /**
     * 构造方法，初始化 {@code code}、{@code count} 和 {@code data}
     *
     * @param count 记录条数
     * @param data  记录列表
     */
    public Page(int count, List<T> data) {
        super();
        this.code = 0;
        this.count = count;
        this.data = data;
    }

    /**
     * 构造方法，初始化 {@code code}、{@code count}、{@code data} 和 {@code totalRow}
     *
     * @param count    记录条数
     * @param data     记录列表
     * @param totalRow 记录统计行
     */
    public Page(Integer count, List<T> data, T totalRow) {
        super();
        this.code = 0;
        this.count = count;
        this.data = data;
        this.totalRow = totalRow;
    }

    /**
     * 初始化分页查询所需参数
     *
     * @param params 分页查询请求参数
     * @return {@link CustomMap}
     */
    public static CustomMap initPageParams(CustomMap params) {
        int page = params.get("page") == null ? 1 : Integer.parseInt(String.valueOf(params.get("page")));
        int limit = params.get("limit") == null ? PAGE_SIZE : Integer.parseInt(String.valueOf(params.get("limit")));
        return params.putNext("start", (page - 1) * limit).putNext("limit", limit).putNext("pagination", true);
    }

    /**
     * 分页
     *
     * @param count 记录条数
     * @param data  记录列表
     * @param <T>   泛型
     * @return {@link Page}
     */
    public static <T> Page<T> pagination(int count, List<T> data) {
        return new Page<>(count, data);
    }

    /**
     * 分页，带统计行
     *
     * @param count    记录条数
     * @param data     记录列表
     * @param totalRow 记录统计行
     * @return {@link Page}
     */
    public static <T> Page<T> pagination(int count, List<T> data, T totalRow) {
        return new Page<>(count, data, totalRow);
    }

    public int getCode() {
        return code;
    }

    public Page<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Page<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Page<T> setCount(int count) {
        this.count = count;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public Page<T> setObject(Object object) {
        this.object = object;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public Page<T> setData(List<T> data) {
        this.data = data;
        return this;
    }

    public T getTotalRow() {
        return totalRow;
    }

    public Page<T> setTotalRow(T totalRow) {
        this.totalRow = totalRow;
        return this;
    }

    @Override
    public String toString() {
        return "Page [" +
                "code=" + code +
                ", msg=" + msg +
                ", count=" + count +
                ", object=" + object +
                ", data=" + data +
                ", totalRow=" + totalRow +
                "]";
    }
}
