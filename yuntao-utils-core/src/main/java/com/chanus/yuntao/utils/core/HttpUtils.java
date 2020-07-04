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
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Http 请求工具类
 *
 * @author Chanus
 * @date 2020-06-24 11:08:02
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
    private static final String END = "\r\n";

    /**
     * 异步请求回调接口
     */
    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * 向指定 URL 发送 GET 方法的请求
     *
     * @param url 发送请求的 URL，包含请求参数
     * @return 远程资源的响应结果
     */
    public static String get(final String url) {
        StringBuilder result = new StringBuilder();// 返回的结果
        BufferedReader bufferedReader = null;
        try {
            // 打开 URL 连接
            HttpURLConnection connection = getGetConnection(url);
            // 响应头部获取
            // Map<String, List<String>> headers = httpConn.getHeaderFields();

            if (connection.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

                String line;
                // 读取返回的内容
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new RuntimeException("response code is not 200 ... ");
            }
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        } finally {
            IOUtils.close(bufferedReader);
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送 GET 方法的请求
     *
     * @param url        发送请求的 URL
     * @param parameters 请求参数
     * @return 远程资源的响应结果
     */
    public static String get(String url, final Map<String, Object> parameters) {
        url = UrlUtils.setParam(url, parameters);
        return get(url);
    }

    /**
     * 向指定 URL 发送异步 GET 方法的请求
     *
     * @param url      发送请求的 URL，包含请求参数
     * @param callBack 回调方法
     */
    public static void getAsyn(final String url, final CallBack callBack) {
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(() -> {
            try {
                String result = get(url);
                if (callBack != null) {
                    callBack.onRequestComplete(result);
                }
            } catch (Exception e) {
                throw new RuntimeException("Asynchronous request exception", e);
            }
        });

        pool.shutdown();
        try {
            if (!pool.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }

    /**
     * 向指定 URL 发送异步 GET 方法的请求
     *
     * @param url        发送请求的 URL
     * @param parameters 请求参数
     * @param callBack   回调方法
     */
    public static void getAsyn(final String url, final Map<String, Object> parameters, final CallBack callBack) {
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(() -> {
            try {
                String result = get(url, parameters);
                if (callBack != null) {
                    callBack.onRequestComplete(result);
                }
            } catch (Exception e) {
                throw new RuntimeException("Asynchronous request exception", e);
            }
        });

        pool.shutdown();
        try {
            if (!pool.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url    发送请求的 URL，可以包含请求参数
     * @param params 请求参数
     * @return 远程资源的响应结果
     */
    public static String post(final String url, final String params) {
        StringBuilder result = new StringBuilder();// 返回的结果
        BufferedReader bufferedReader = null;// 读取响应输入流
        BufferedWriter bufferedWriter = null;// 写入参数输出流
        try {
            // 打开 URL 连接
            HttpURLConnection connection = getPostConnection(url, false);

            // POST 请求参数
            if (StringUtils.isNotBlank(params)) {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
                bufferedWriter.write(params);
                bufferedWriter.flush();
            }

            // 读取响应
            // 定义 BufferedReader 输入流来读取URL的响应，并设置编码方式
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            // 读取返回的内容
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        } finally {
            IOUtils.close(bufferedWriter);
            IOUtils.close(bufferedReader);
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url 发送请求的 URL，可以包含请求参数
     * @return 远程资源的响应结果
     */
    public static String post(final String url) {
        return post(url, (String) null);
    }

    /**
     * 向指定 URL 发送 POST 方法的请求
     *
     * @param url        发送请求的 URL
     * @param parameters 请求参数
     * @return 远程资源的响应结果
     */
    public static String post(final String url, final Map<String, Object> parameters) {
        String uri = UrlUtils.getParamsUri(parameters);
        return post(url, uri);
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url      发送请求的 URL，包含请求参数
     * @param params   请求参数
     * @param callBack 回调方法
     */
    public static void postAsyn(final String url, final String params, final CallBack callBack) {
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(() -> {
            try {
                String result = post(url, params);
                if (callBack != null) {
                    callBack.onRequestComplete(result);
                }
            } catch (Exception e) {
                throw new RuntimeException("Asynchronous request exception", e);
            }
        });

        pool.shutdown();
        try {
            if (!pool.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url      发送请求的 URL，包含请求参数
     * @param callBack 回调方法
     */
    public static void postAsyn(final String url, final CallBack callBack) {
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(() -> {
            try {
                String result = post(url);
                if (callBack != null) {
                    callBack.onRequestComplete(result);
                }
            } catch (Exception e) {
                throw new RuntimeException("Asynchronous request exception", e);
            }
        });

        pool.shutdown();
        try {
            if (!pool.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }

    /**
     * 向指定 URL 发送异步 POST 方法的请求
     *
     * @param url        发送请求的 URL
     * @param parameters 请求参数
     * @param callBack   回调方法
     */
    public static void postAsyn(final String url, final Map<String, Object> parameters, final CallBack callBack) {
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(() -> {
            try {
                String result = post(url, parameters);
                if (callBack != null) {
                    callBack.onRequestComplete(result);
                }
            } catch (Exception e) {
                throw new RuntimeException("Asynchronous request exception", e);
            }
        });

        pool.shutdown();
        try {
            if (!pool.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }

    /**
     * 文件上传
     *
     * @param url      发送请求的 URL
     * @param file     需要上传的文件
     * @param formName 表单名称，若为空则默认为 file
     * @return 远程资源的响应结果
     */
    public static String upload(final String url, File file, String formName) {
        if (StringUtils.isBlank(formName))
            formName = "file";
        StringBuilder result = new StringBuilder();// 返回的结果
        DataOutputStream dataOutputStream = null;
        BufferedReader bufferedReader = null;// 读取响应输入流

        try {
            // 打开 URL 连接
            HttpURLConnection connection = getPostConnection(url, true);

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
            // 定义BufferedReader输入流来读取URL的响应，并设置编码方式
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            // 读取返回的内容
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        } finally {
            IOUtils.close(dataOutputStream, bufferedReader);
        }
        return result.toString();
    }

    /**
     * 文件上传
     *
     * @param url  发送请求的 URL
     * @param file 需要上传的文件
     * @return 远程资源的响应结果
     */
    public static String upload(final String url, File file) {
        return upload(url, file, null);
    }

    /**
     * 文件下载，请求方式为 POST
     *
     * @param url      发送请求的 URL
     * @param params   请求参数
     * @param savePath 下载文件保存路径
     * @param fileName 下载文件名称
     * @return 下载的文件
     */
    public static File downloadPost(final String url, final String params, final String savePath, String fileName) {
        String path = savePath + File.separatorChar + fileName;
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedInputStream bufferedInputStream = null;
        OutputStream out = null;

        try {
            // 打开URL连接
            HttpURLConnection connection = getPostConnection(url, false);

            // 请求参数
            if (StringUtils.isNotBlank(params)) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(params.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                IOUtils.closeQuietly(outputStream);
            }

            // 获得输出流
            out = new FileOutputStream(file);
            // 获得输入流
            bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            int size;
            byte[] buffer = new byte[1024];
            while ((size = bufferedInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        } finally {
            IOUtils.close(out, bufferedInputStream);
        }
        return file;
    }

    /**
     * 文件下载，请求方式为 POST
     *
     * @param url      发送请求的 URL
     * @param savePath 下载文件保存路径
     * @param fileName 下载文件名称
     * @return 下载的文件
     */
    public static File downloadPost(final String url, final String savePath, String fileName) {
        return downloadPost(url, null, savePath, fileName);
    }

    /**
     * 文件下载，请求方式为 POST
     *
     * @param url    发送请求的 URL
     * @param params 请求参数
     * @return {@code BufferedInputStream} 输入流
     */
    public static BufferedInputStream downloadPost(final String url, final String params) {
        try {
            // 打开URL连接
            HttpURLConnection connection = getPostConnection(url, false);

            // 请求参数
            if (StringUtils.isNotBlank(params)) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(params.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                IOUtils.closeQuietly(outputStream);
            }

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
    public static BufferedInputStream downloadPost(final String url) {
        return downloadPost(url, null);
    }

    /**
     * 文件下载，请求方式为 GET
     *
     * @param url      发送请求的 URL
     * @param savePath 下载文件保存路径
     * @param fileName 下载文件名称
     * @return 下载的文件
     */
    public static File downloadGet(final String url, final String savePath, final String fileName) {
        String path = savePath + File.separatorChar + fileName;
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        BufferedInputStream bufferedInputStream = downloadGet(url);
        OutputStream out = null;
        try {
            // 获得输出流
            out = new FileOutputStream(file);
            int size;
            byte[] buffer = new byte[1024];
            while ((size = bufferedInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        } finally {
            IOUtils.close(out);
            IOUtils.close(bufferedInputStream);
        }
        return file;
    }

    /**
     * 文件下载，请求方式为 GET
     *
     * @param url      发送请求的 URL
     * @param savePath 下载文件保存路径
     * @return 下载的文件
     */
    public static File downloadGet(final String url, final String savePath) {
        File file;
        BufferedInputStream bufferedInputStream = null;
        OutputStream out = null;

        try {
            // 打开URL连接
            HttpURLConnection connection = getGetConnection(url);

            // 文件路径
            String filePath = connection.getURL().getFile();
            filePath = filePath.replace(File.separatorChar, '/');
            // 文件名
            String fileFullName = filePath.substring(filePath.lastIndexOf('/') + 1);

            String path = savePath + File.separatorChar + fileFullName;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // 获得输出流
            out = new FileOutputStream(file);
            // 获得输入流
            bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            int size;
            byte[] buffer = new byte[1024];
            while ((size = bufferedInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        } finally {
            IOUtils.close(out, bufferedInputStream);
        }
        return file;
    }

    /**
     * 文件下载，请求方式为 GET
     *
     * @param url 发送请求的 URL
     * @return {@code BufferedInputStream} 输入流
     */
    public static BufferedInputStream downloadGet(final String url) {
        try {
            // 打开URL连接
            HttpURLConnection connection = getGetConnection(url);

            // 获得输入流
            return new BufferedInputStream(connection.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("Request exception", e);
        }
    }

    /**
     * 获取 GET 请求连接
     *
     * @param url 发送请求的 URL
     * @return {@link HttpURLConnection}
     */
    private static HttpURLConnection getGetConnection(final String url) {
        // 创建URL对象
        URL connURL;
        // 打开URL连接
        HttpURLConnection connection;
        try {
            connURL = new URL(url);
            connection = (HttpURLConnection) connURL.openConnection();
            // 设置通用属性
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            connection.setRequestMethod("GET");
            connection.setReadTimeout(TIMEOUT_IN_MILLIONS);
            connection.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            // 建立实际的连接
            connection.connect();
        } catch (IOException e) {
            throw new RuntimeException("Request exception", e);
        }

        return connection;
    }

    /**
     * 获取 POST 请求连接
     *
     * @param url      发送请求的 URL
     * @param isUpload 是否是文件上传，{@code true} 是，{@code false} 否
     * @return {@link HttpURLConnection}
     */
    private static HttpURLConnection getPostConnection(final String url, boolean isUpload) {
        // 创建 URL 对象
        URL connURL;
        // 打开 URL 连接
        HttpURLConnection connection;
        try {
            connURL = new URL(url);
            connection = (HttpURLConnection) connURL.openConnection();
            // 设置通用属性，请求头信息
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Connection", "Keep-Alive");
            if (isUpload)
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            else
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            // 设定请求的方法，默认是 GET
            connection.setRequestMethod("POST");
            // 设置是否向 connection 输出
            connection.setDoOutput(true);
            // 设置是否从 connection 读入，默认情况下是 true
            connection.setDoInput(true);
            // POST 请求不能使用缓存
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setReadTimeout(TIMEOUT_IN_MILLIONS);
            connection.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            // 建立实际的连接
            connection.connect();
        } catch (IOException e) {
            throw new RuntimeException("Request exception", e);
        }

        return connection;
    }
}
