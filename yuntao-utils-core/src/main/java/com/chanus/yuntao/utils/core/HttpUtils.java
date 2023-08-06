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
package com.chanus.yuntao.utils.core;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Http 请求工具类
 *
 * @author Chanus
 * @since 1.0.0
 */
public class HttpUtils {
    /**
     * 请求超时时间60s
     */
    private static final int TIMEOUT_IN_MILLIONS = 60000;
    /**
     * 文件上传分割线必须多两道线
     */
    private static final String TWO_HYPHENS = "--";
    /**
     * 文件上传边界
     */
    private static final String BOUNDARY = "*****";
    /**
     * 文件上传结尾
     */
    private static final String END = StringUtils.CRLF;

    /**
     * 表单类型 Content-Type（key/value 数据格式）
     */
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded; charset=UTF-8";
    /**
     * JSON 类型 Content-Type
     */
    public static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
    /**
     * 流类型 Content-Type
     */
    public static final String CONTENT_TYPE_STREAM = "application/octet-stream; charset=UTF-8";
    /**
     * XML 类型 Content-Type
     */
    public static final String CONTENT_TYPE_XML = "application/xml; charset=UTF-8";
    /**
     * 文本类型 Content-Type
     */
    public static final String CONTENT_TYPE_TEXT = "application/text; charset=UTF-8";
    /**
     * 需要在表单中进行文件上传时的媒体类型
     */
    public static final String CONTENT_TYPE_UPLOAD = "multipart/form-data; boundary=" + BOUNDARY;

    private HttpUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 异步请求回调接口
     */
    public interface CallBack {
        /**
         * 请求完成后的处理方法
         *
         * @param result 请求结果
         */
        void onRequestComplete(String result);
    }

    /**
     * 向指定 URL 发送 GET 方法的请求
     *
     * @param url            发送请求的 URL
     * @param connectTimeout 建立连接的超时时间，单位是毫秒
     * @param readTimeout    传递数据的超时时间，单位是毫秒
     * @param headers        请求头信息
     * @param queries        请求参数
     * @return 远程资源的响应结果
     * @since 1.4.5
     */
    public static String get(String url, int connectTimeout, int readTimeout, Map<String, String> headers, Map<String, Object> queries) {
        // URL 连接
        HttpURLConnection connection = null;
        try {
            connection = getGetConnection(url, connectTimeout, readTimeout, headers, queries);

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("response code is " + connection.getResponseCode());
            }

            // 读取响应
            return StreamUtils.read2String(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * 向指定 URL 发送 GET 方法的请求
     *
     * @param url     发送请求的 URL
     * @param headers 请求头信息
     * @param queries 请求参数
     * @return 远程资源的响应结果
     * @since 1.4.5
     */
    public static String get(String url, Map<String, String> headers, Map<String, Object> queries) {
        return get(url, TIMEOUT_IN_MILLIONS, TIMEOUT_IN_MILLIONS, headers, queries);
    }

    /**
     * 向指定 URL 发送 GET 方法的请求
     *
     * @param url     发送请求的 URL
     * @param queries 请求参数
     * @return 远程资源的响应结果
     */
    public static String get(String url, Map<String, Object> queries) {
        return get(url, null, queries);
    }

    /**
     * 向指定 URL 发送 GET 方法的请求
     *
     * @param url 发送请求的 URL，包含请求参数
     * @return 远程资源的响应结果
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 向指定 URL 发送 GET 方法的请求
     *
     * @param url     发送请求的 URL
     * @param headers 请求头信息
     * @return 远程资源的响应结果
     * @since 1.4.5
     */
    public static String getWithHeaders(String url, Map<String, String> headers) {
        return get(url, headers, null);
    }

    /**
     * 向指定 URL 发送异步 GET 方法的请求
     *
     * @param url            发送请求的 URL
     * @param connectTimeout 建立连接的超时时间，单位是毫秒
     * @param readTimeout    传递数据的超时时间，单位是毫秒
     * @param headers        请求头信息
     * @param queries        请求参数
     * @param callBack       回调方法
     * @since 1.4.5
     */
    public static void getAsync(String url, int connectTimeout, int readTimeout, Map<String, String> headers,
                                Map<String, Object> queries, CallBack callBack) {
        CompletableFuture.supplyAsync(() -> get(url, connectTimeout, readTimeout, headers, queries))
                .thenAcceptAsync(result -> {
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                });
    }

    /**
     * 向指定 URL 发送异步 GET 方法的请求
     *
     * @param url      发送请求的 URL
     * @param headers  请求头信息
     * @param queries  请求参数
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void getAsync(String url, Map<String, String> headers, Map<String, Object> queries, CallBack callBack) {
        getAsync(url, TIMEOUT_IN_MILLIONS, TIMEOUT_IN_MILLIONS, headers, queries, callBack);
    }

    /**
     * 向指定 URL 发送异步 GET 方法的请求
     *
     * @param url      发送请求的 URL
     * @param queries  请求参数
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void getAsync(String url, Map<String, Object> queries, CallBack callBack) {
        getAsync(url, null, queries, callBack);
    }

    /**
     * 向指定 URL 发送异步 GET 方法的请求
     *
     * @param url      发送请求的 URL，包含请求参数
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void getAsync(String url, CallBack callBack) {
        getAsync(url, null, callBack);
    }

    /**
     * 向指定 URL 发送异步 GET 方法的请求
     *
     * @param url      发送请求的 URL
     * @param headers  请求头信息
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void getWithHeadersAsync(String url, Map<String, String> headers, CallBack callBack) {
        getAsync(url, headers, null, callBack);
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url            发送请求的 URL，可以包含请求参数
     * @param connectTimeout 建立连接的超时时间，单位是毫秒
     * @param readTimeout    传递数据的超时时间，单位是毫秒
     * @param headers        请求头信息
     * @param queries        请求参数
     * @param body           请求 body 参数
     * @return 远程资源的响应结果
     * @since 1.4.5
     */
    public static String post(String url, int connectTimeout, int readTimeout, Map<String, String> headers,
                              Map<String, Object> queries, String body) {
        // URL 连接
        HttpURLConnection connection = null;
        try {
            connection = getPostConnection(url, connectTimeout, readTimeout, headers, queries, body);

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("response code is " + connection.getResponseCode());
            }

            // 读取响应
            return StreamUtils.read2String(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred.", e);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url     发送请求的 URL，可以包含请求参数
     * @param headers 请求头信息
     * @param queries 请求参数
     * @param body    请求 body 参数
     * @return 远程资源的响应结果
     * @since 1.4.5
     */
    public static String post(String url, Map<String, String> headers, Map<String, Object> queries, String body) {
        return post(url, TIMEOUT_IN_MILLIONS, TIMEOUT_IN_MILLIONS, headers, queries, body);
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url     发送请求的 URL，可以包含请求参数
     * @param headers 请求头信息
     * @param queries 请求参数
     * @return 远程资源的响应结果
     * @since 1.4.5
     */
    public static String post(String url, Map<String, String> headers, Map<String, Object> queries) {
        return post(url, TIMEOUT_IN_MILLIONS, TIMEOUT_IN_MILLIONS, headers, queries, null);
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url     发送请求的 URL，可以包含请求参数
     * @param headers 请求头信息
     * @param body    请求 body 参数
     * @return 远程资源的响应结果
     * @since 1.4.5
     */
    public static String post(String url, Map<String, String> headers, String body) {
        return post(url, TIMEOUT_IN_MILLIONS, TIMEOUT_IN_MILLIONS, headers, null, body);
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url     发送请求的 URL
     * @param queries 请求参数
     * @return 远程资源的响应结果
     */
    public static String post(String url, Map<String, Object> queries) {
        return post(url, null, queries);
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url  发送请求的 URL，可以包含请求参数
     * @param body 请求 body 参数
     * @return 远程资源的响应结果
     */
    public static String post(String url, String body) {
        return post(url, null, body);
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url 发送请求的 URL，可以包含请求参数
     * @return 远程资源的响应结果
     */
    public static String post(String url) {
        return post(url, TIMEOUT_IN_MILLIONS, TIMEOUT_IN_MILLIONS, null, null, null);
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url     发送请求的 URL，可以包含请求参数
     * @param headers 请求头信息
     * @return 远程资源的响应结果
     * @since 1.4.5
     */
    public static String postWithHeaders(String url, Map<String, String> headers) {
        return post(url, TIMEOUT_IN_MILLIONS, TIMEOUT_IN_MILLIONS, headers, null, null);
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url  发送请求的 URL，可以包含请求参数
     * @param json json 格式的请求参数
     * @return 远程资源的响应结果
     * @since 1.2.4
     */
    public static String postJson(String url, String json) {
        return post(url, initialBasicHeader(CONTENT_TYPE_JSON), json);
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url            发送请求的 URL，可以包含请求参数
     * @param connectTimeout 建立连接的超时时间，单位是毫秒
     * @param readTimeout    传递数据的超时时间，单位是毫秒
     * @param headers        请求头信息
     * @param queries        请求参数
     * @param body           请求 body 参数
     * @param callBack       回调方法
     * @since 1.4.5
     */
    public static void postAsync(String url, int connectTimeout, int readTimeout, Map<String, String> headers,
                                 Map<String, Object> queries, String body, CallBack callBack) {
        CompletableFuture.supplyAsync(() -> post(url, connectTimeout, readTimeout, headers, queries, body))
                .thenAcceptAsync(result -> {
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                });
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url      发送请求的 URL，可以包含请求参数
     * @param headers  请求头信息
     * @param queries  请求参数
     * @param body     请求 body 参数
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void postAsync(String url, Map<String, String> headers, Map<String, Object> queries, String body, CallBack callBack) {
        postAsync(url, TIMEOUT_IN_MILLIONS, TIMEOUT_IN_MILLIONS, headers, queries, body, callBack);
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url      发送请求的 URL，可以包含请求参数
     * @param headers  请求头信息
     * @param queries  请求参数
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void postAsync(String url, Map<String, String> headers, Map<String, Object> queries, CallBack callBack) {
        postAsync(url, headers, queries, null, callBack);
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url      发送请求的 URL，可以包含请求参数
     * @param headers  请求头信息
     * @param body     请求 body 参数
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void postAsync(String url, Map<String, String> headers, String body, CallBack callBack) {
        postAsync(url, headers, null, body, callBack);
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url      发送请求的 URL，可以包含请求参数
     * @param queries  请求参数
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void postAsync(String url, Map<String, Object> queries, CallBack callBack) {
        postAsync(url, null, queries, callBack);
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url      发送请求的 URL，可以包含请求参数
     * @param body     请求 body 参数
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void postAsync(String url, String body, CallBack callBack) {
        postAsync(url, null, body, callBack);
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url      发送请求的 URL，可以包含请求参数
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void postAsync(String url, CallBack callBack) {
        postAsync(url, null, null, null, callBack);
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url      发送请求的 URL，可以包含请求参数
     * @param headers  请求头信息
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void postWithHeadersAsync(String url, Map<String, String> headers, CallBack callBack) {
        postAsync(url, headers, null, null, callBack);
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url      发送请求的 URL，可以包含请求参数
     * @param json     json 格式的请求参数
     * @param callBack 回调方法
     * @since 1.4.5
     */
    public static void postJsonAsync(String url, String json, CallBack callBack) {
        postAsync(url, initialBasicHeader(CONTENT_TYPE_JSON), json, callBack);
    }

    /**
     * 文件上传
     *
     * @param url      发送请求的 URL
     * @param file     需要上传的文件
     * @param formName 表单名称，若为空则默认为 file
     * @return 远程资源的响应结果
     */
    public static String upload(String url, File file, String formName) {
        if (StringUtils.isBlank(formName)) {
            formName = "file";
        }

        // 输出流
        DataOutputStream dataOutputStream = null;
        // URL 连接
        HttpURLConnection connection = null;
        try {
            connection = getPostConnection(url, CONTENT_TYPE_UPLOAD);

            // 获得输出流
            dataOutputStream = new DataOutputStream(connection.getOutputStream());
            // 输出表头
            String head = TWO_HYPHENS + BOUNDARY + END +
                    // 上传文件表单
                    "Content-Disposition: form-data;name=\"" + formName + "\";filename=\"" + file.getName() + "\"" + END +
                    // 获取文件类型设置成请求头
                    "Content-Type: application/octet-stream" + END + END;
            dataOutputStream.write(head.getBytes(StandardCharsets.UTF_8));
            // 把文件以流文件的方式推入到 url 中
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, length);
            }
            dataInputStream.close();

            // 定义数据分隔线
            String foot = END + TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + END;
            dataOutputStream.write(foot.getBytes(StandardCharsets.UTF_8));
            dataOutputStream.flush();

            // 读取响应
            return StreamUtils.read2String(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        } finally {
            closeConnection(connection);
            IOUtils.close(dataOutputStream);
        }
    }

    /**
     * 文件上传
     *
     * @param url  发送请求的 URL
     * @param file 需要上传的文件
     * @return 远程资源的响应结果
     */
    public static String upload(String url, File file) {
        return upload(url, file, null);
    }

    /**
     * 文件下载，请求方式为 POST
     *
     * @param url      发送请求的 URL
     * @param body     请求 body 参数
     * @param savePath 下载文件保存路径
     * @param fileName 下载文件名称，若不带扩展名，则默认为下载文件的类型
     * @return 下载的文件
     */
    public static File downloadPost(String url, String body, String savePath, String fileName) {
        // URL 连接
        HttpURLConnection connection = null;
        try {
            connection = getPostFormConnection(url, body);
            // 获得输入流
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());

            if (!fileName.contains(StringUtils.DOT)) {
                String fileExtension = FileUtils.getFileExtension(bufferedInputStream);
                if (StringUtils.isNotBlank(fileExtension)) {
                    fileName = fileName + StringUtils.DOT + fileExtension;
                }
            }

            return FileUtils.writeFromStream(savePath + File.separatorChar + fileName, bufferedInputStream, false);
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * 文件下载，请求方式为 POST
     *
     * @param url      发送请求的 URL
     * @param savePath 下载文件保存路径
     * @param fileName 下载文件名称，若不带扩展名，则默认为下载文件的类型
     * @return 下载的文件
     */
    public static File downloadPost(String url, String savePath, String fileName) {
        return downloadPost(url, null, savePath, fileName);
    }

    /**
     * 文件下载，请求方式为 POST
     *
     * @param url  发送请求的 URL
     * @param body 请求 body 参数
     * @return {@code BufferedInputStream} 输入流
     */
    public static BufferedInputStream downloadPost(String url, String body) {
        try {
            // URL 连接
            HttpURLConnection connection = getPostFormConnection(url, body);
            // 获得输入流
            return new BufferedInputStream(connection.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        }
    }

    /**
     * 文件下载，请求方式为 POST
     *
     * @param url 发送请求的 URL
     * @return {@code BufferedInputStream} 输入流
     */
    public static BufferedInputStream downloadPost(String url) {
        return downloadPost(url, null);
    }

    /**
     * 文件下载，请求方式为 GET
     *
     * @param url      发送请求的 URL
     * @param savePath 下载文件保存路径
     * @param fileName 下载文件名称，若不带扩展名，则默认为下载文件的类型
     * @return 下载的文件
     */
    public static File downloadGet(String url, String savePath, String fileName) {
        BufferedInputStream bufferedInputStream = downloadGet(url);
        if (!fileName.contains(StringUtils.DOT)) {
            String fileExtension = FileUtils.getFileExtension(bufferedInputStream);
            if (StringUtils.isNotBlank(fileExtension)) {
                fileName = fileName + StringUtils.DOT + fileExtension;
            }
        }

        return FileUtils.writeFromStream(savePath + File.separatorChar + fileName, bufferedInputStream, false);
    }

    /**
     * 文件下载，请求方式为 GET
     *
     * @param url      发送请求的 URL
     * @param savePath 下载文件保存路径
     * @return 下载的文件
     */
    public static File downloadGet(String url, String savePath) {
        // URL 连接
        HttpURLConnection connection = null;
        try {
            connection = getGetConnection(url);
            // 获得输入流
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());

            // 文件路径
            String filePath = connection.getURL().getFile().replace(File.separatorChar, CharUtils.SLASH);
            // 文件名
            String fileFullName = FileUtils.getValidFileName(filePath.substring(filePath.lastIndexOf(CharUtils.SLASH) + 1));

            savePath = savePath.replace(File.separatorChar, CharUtils.SLASH) + CharUtils.SLASH + fileFullName;
            // 获取文件类型
            String fileExtension = FileUtils.getFileExtension(bufferedInputStream);
            if (StringUtils.isNotBlank(fileExtension)) {
                savePath = savePath + StringUtils.DOT + fileExtension;
            }

            return FileUtils.writeFromStream(savePath, bufferedInputStream, false);
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * 文件下载，请求方式为 GET
     *
     * @param url 发送请求的 URL
     * @return {@code BufferedInputStream} 输入流
     */
    public static BufferedInputStream downloadGet(String url) {
        try {
            // URL 连接
            HttpURLConnection connection = getGetConnection(url);

            // 获得输入流
            return new BufferedInputStream(connection.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        }
    }

    /**
     * 初始化 http 请求基础 Header
     *
     * @return Header 参数
     * @since 1.4.5
     */
    public static Map<String, String> initialBasicHeader() {
        Map<String, String> headers = new HashMap<>(8);
        headers.put("Accept", "*/*");
        headers.put("Connection", "Keep-Alive");
        headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");

        return headers;
    }

    /**
     * 初始化 POST 请求基础 Header
     *
     * @param contentType 媒体类型
     * @return Header 参数
     * @since 1.4.5
     */
    public static Map<String, String> initialBasicHeader(String contentType) {
        Map<String, String> headers = initialBasicHeader();

        switch (contentType) {
            case CONTENT_TYPE_FORM:
                headers.put("Content-Type", CONTENT_TYPE_FORM);
                break;
            case CONTENT_TYPE_JSON:
                headers.put("Content-Type", CONTENT_TYPE_JSON);
                break;
            case CONTENT_TYPE_STREAM:
                headers.put("Content-Type", CONTENT_TYPE_STREAM);
                break;
            case CONTENT_TYPE_XML:
                headers.put("Content-Type", CONTENT_TYPE_XML);
                break;
            case CONTENT_TYPE_TEXT:
                headers.put("Content-Type", CONTENT_TYPE_TEXT);
                break;
            default:
                headers.put("Content-Type", contentType);
        }

        return headers;
    }

    /**
     * 获取 GET 请求连接
     *
     * @param url            发送请求的 URL
     * @param connectTimeout 建立连接的超时时间，单位是毫秒
     * @param readTimeout    传递数据的超时时间，单位是毫秒
     * @param headers        请求头信息
     * @param queries        请求参数
     * @return {@link HttpURLConnection}
     * @throws IOException IO 异常
     * @since 1.4.5
     */
    public static HttpURLConnection getGetConnection(String url, int connectTimeout, int readTimeout, Map<String, String> headers,
                                                     Map<String, Object> queries) throws IOException {
        // 将请求参数追加到 url 中
        if (MapUtils.isNotEmpty(queries)) {
            url = UrlUtils.setParam(url, queries);
        }
        // 创建 URL 对象
        URL connUrl = new URL(url);
        // URL 连接
        HttpURLConnection connection = (HttpURLConnection) connUrl.openConnection();
        // 设置请求头信息
        if (MapUtils.isEmpty(headers)) {
            headers = initialBasicHeader();
        }
        for (Map.Entry<String, String> e : headers.entrySet()) {
            connection.setRequestProperty(e.getKey(), e.getValue());
        }
        // 设置通用属性
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        // 建立实际的连接
        connection.connect();

        return connection;
    }

    /**
     * 获取 GET 请求连接
     *
     * @param url     发送请求的 URL
     * @param headers 请求头信息
     * @param queries 请求参数
     * @return {@link HttpURLConnection}
     * @throws IOException IO 异常
     * @since 1.4.5
     */
    public static HttpURLConnection getGetConnection(String url, Map<String, String> headers, Map<String, Object> queries) throws IOException {
        return getGetConnection(url, TIMEOUT_IN_MILLIONS, TIMEOUT_IN_MILLIONS, headers, queries);
    }

    /**
     * 获取 GET 请求连接
     *
     * @param url 发送请求的 URL
     * @return {@link HttpURLConnection}
     * @throws IOException IO 异常
     */
    public static HttpURLConnection getGetConnection(String url) throws IOException {
        return getGetConnection(url, TIMEOUT_IN_MILLIONS, TIMEOUT_IN_MILLIONS, null, null);
    }

    /**
     * 获取 POST 请求连接
     *
     * @param url            发送请求的 URL，可以包含请求参数
     * @param connectTimeout 建立连接的超时时间，单位是毫秒
     * @param readTimeout    传递数据的超时时间，单位是毫秒
     * @param headers        请求头信息
     * @param queries        请求参数
     * @param body           请求 body 参数
     * @return {@link HttpURLConnection}
     * @throws IOException IO 异常
     * @since 1.4.5
     */
    public static HttpURLConnection getPostConnection(String url, int connectTimeout, int readTimeout, Map<String, String> headers,
                                                      Map<String, Object> queries, String body) throws IOException {
        // 创建 URL 对象
        URL connUrl = new URL(url);
        // URL 连接
        HttpURLConnection connection = (HttpURLConnection) connUrl.openConnection();
        // 设置请求头信息
        if (MapUtils.isEmpty(headers)) {
            headers = initialBasicHeader(CONTENT_TYPE_FORM);
        }
        for (Map.Entry<String, String> e : headers.entrySet()) {
            connection.setRequestProperty(e.getKey(), e.getValue());
        }
        // 设定请求的方法，默认是 GET
        connection.setRequestMethod("POST");
        // 设置是否向 connection 输出
        connection.setDoOutput(true);
        // 设置是否从 connection 读入，默认情况下是 true
        connection.setDoInput(true);
        // POST 请求不能使用缓存
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        // 建立实际的连接
        connection.connect();

        // 设置 queries 参数
        if (MapUtils.isNotEmpty(queries)) {
            String uri = UrlUtils.getParamsUri(queries);
            if (uri != null) {
                // 写入参数输出流
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
                bufferedWriter.write(uri);
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        }

        // 设置 body 内的参数
        if (StringUtils.isNotBlank(body)) {
            // 写入参数输出流
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
            bufferedWriter.write(body);
            bufferedWriter.flush();
            bufferedWriter.close();
        }

        return connection;
    }

    /**
     * 获取 POST 请求连接
     *
     * @param url     发送请求的 URL，可以包含请求参数
     * @param headers 请求头信息
     * @param queries 请求参数
     * @param body    请求 body 参数
     * @return {@link HttpURLConnection}
     * @throws IOException IO 异常
     * @since 1.4.5
     */
    public static HttpURLConnection getPostConnection(String url, Map<String, String> headers, Map<String, Object> queries, String body) throws IOException {
        return getPostConnection(url, TIMEOUT_IN_MILLIONS, TIMEOUT_IN_MILLIONS, headers, queries, body);
    }

    /**
     * 获取 POST 请求连接
     *
     * @param url     发送请求的 URL，可以包含请求参数
     * @param headers 请求头信息
     * @return {@link HttpURLConnection}
     * @throws IOException IO 异常
     * @since 1.4.5
     */
    public static HttpURLConnection getPostConnection(String url, Map<String, String> headers) throws IOException {
        return getPostConnection(url, headers, null, null);
    }

    /**
     * 获取 POST 请求连接
     *
     * @param url         发送请求的 URL
     * @param contentType 媒体类型
     * @return {@link HttpURLConnection}
     * @throws IOException IO 异常
     */
    public static HttpURLConnection getPostConnection(String url, String contentType) throws IOException {
        return getPostConnection(url, initialBasicHeader(contentType));
    }

    /**
     * 获取 POST 表单请求连接
     *
     * @param url  发送请求的 URL
     * @param body 请求 body 参数
     * @return {@link HttpURLConnection}
     * @throws IOException IO 异常
     * @since 1.4.5
     */
    public static HttpURLConnection getPostFormConnection(String url, String body) throws IOException {
        return getPostConnection(url, null, null, body);
    }

    /**
     * 关闭链接
     *
     * @param connection {@link HttpURLConnection}
     * @since 1.4.5
     */
    public static void closeConnection(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }
}
