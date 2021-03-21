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
package com.chanus.yuntao.utils.extra.test;

import com.chanus.yuntao.utils.core.CharsetUtils;
import com.chanus.yuntao.utils.core.FileUtils;
import com.chanus.yuntao.utils.core.IOUtils;
import com.chanus.yuntao.utils.extra.ftp.Ftp;
import com.chanus.yuntao.utils.extra.ftp.FtpConfig;
import com.chanus.yuntao.utils.extra.ftp.FtpException;
import org.junit.Test;

import java.io.IOException;

/**
 * Ftp 测试类
 *
 * @author Chanus
 * @date 20port-03-19 16:20:22
 * @since 1.5.0
 */
public class FtpTest {
    private final String host = "192.168.1.3";
    private final int port = 21;
    private final String user = "admin";
    private final String password = "123123";

    @Test
    public void reconnectIfTimeoutTest() throws InterruptedException {
        // 该测试方法看不出效果
        FtpConfig ftpConfig = FtpConfig.create().setHost(host).setPort(port).setUser(user).setPassword(password)
                                       .setCharset(CharsetUtils.CHARSET_UTF_8).setConnectionTimeout(1)
                                       .setSoTimeout(1).setDataTimeout(1);
        Ftp ftp = new Ftp(ftpConfig, null);

        System.out.println("打印pwd: " + ftp.pwd());

        System.out.println("休眠一段时间，然后再次发送pwd命令，抛出异常表明连接超时");
        Thread.sleep(10 * 1000);

        try {
            System.out.println("打印pwd: " + ftp.pwd());
        } catch (FtpException e) {
            e.printStackTrace();
        }

        System.out.println("判断是否超时并重连...");
        ftp.reconnectIfTimeout();

        System.out.println("打印pwd: " + ftp.pwd());

        IOUtils.close(ftp);
    }

    @Test
    public void cdTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.pwd());
            ftp.cd("/aaa");
            System.out.println(ftp.pwd());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cdToParentTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            ftp.cd("/bbb/ccc/ddd");
            System.out.println(ftp.pwd());
            ftp.cdToParent();
            System.out.println(ftp.pwd());
            ftp.cdToParent();
            System.out.println(ftp.pwd());
            ftp.cdToParent();
            System.out.println(ftp.pwd());
            ftp.cdToParent();
            System.out.println(ftp.pwd());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pwdTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.pwd());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mkdirTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.pwd());

            ftp.mkdir("/ddd");
            ftp.cd("/ddd");
            System.out.println(ftp.pwd());
            ftp.mkdir("eee");
            ftp.mkdir("eee/ggg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mkdirsTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.pwd());
            ftp.cd("/aaa");
            System.out.println(ftp.pwd());
            ftp.mkdirs("fff/f1/f2/f3");
            System.out.println(ftp.pwd());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void existTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.exist("/"));
            System.out.println(ftp.exist("/aaa"));
            System.out.println(ftp.exist("/aaa/bbb"));
            System.out.println(ftp.exist("/aaa/ccc"));
            System.out.println(ftp.exist("/aaa/attendance.html"));
            System.out.println(ftp.exist("/aaa/attendance2.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lsTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.ls("/"));
            System.out.println(ftp.ls());
            ftp.cd("/aaa");
            System.out.println(ftp.ls());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lsRecursiveTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            ftp.cd("/aaa");
            System.out.println(ftp.pwd());
            System.out.println(ftp.lsRecursive("bbb"));
            System.out.println(ftp.pwd());
            ftp.cd("bbb/css");
            System.out.println(ftp.lsRecursive());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void statTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.stat("/"));
            System.out.println(ftp.stat("/aaa"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isEmptyTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.isEmpty("/"));
            System.out.println(ftp.isEmpty("/aaa"));
            System.out.println(ftp.isEmpty("/aaa/bbb/ccc"));
            System.out.println(ftp.isEmpty("/global.css"));
            System.out.println(ftp.isEmpty("/global2.css"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delFileTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.delFile("/css/global.css"));
            System.out.println(ftp.delFile("/aaa/fff/f1/f2"));
            ftp.cd("/bbb");
            System.out.println(ftp.delFile("ccc"));
            System.out.println(ftp.delFile("attendance.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delDirTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.delDir("/css/global.css"));
            System.out.println(ftp.delDir("/aaa/fff/f1/f2"));
            ftp.cd("/bbb");
            System.out.println(ftp.delDir("ccc"));
            System.out.println(ftp.delDir("attendance.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uploadToPwdTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.pwd());
            ftp.cd("/aaa");
            System.out.println(ftp.pwd());
            System.out.println(ftp.uploadToPwd(FileUtils.newFile("E:/test/teachers.html")));
            System.out.println(ftp.uploadToPwd(FileUtils.newFile("E:/test/teachers.html"), "teachers2.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uploadTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.pwd());
            System.out.println(ftp.upload(FileUtils.newFile("E:/test/attendance.html"), "/bbb"));
            System.out.println(ftp.pwd());
            ftp.cd("/bbb");
            System.out.println(ftp.pwd());
            System.out.println(ftp.upload(FileUtils.newFile("E:/test/attendance.html"), "ccc/ddd/eee"));
            System.out.println(ftp.pwd());
            System.out.println(ftp.upload(FileUtils.newFile("E:/test/attendance.html"), "/aaa", "attendance2.html"));
            System.out.println(ftp.pwd());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uploadRecursiveToPwdTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.pwd());
            ftp.cd("/aaa");
            System.out.println(ftp.pwd());
            ftp.uploadRecursiveToPwd("E:/test/css");
            System.out.println(ftp.pwd());
            ftp.cd("/bbb");
            System.out.println(ftp.pwd());
            ftp.uploadRecursiveToPwd(FileUtils.newFile("E:/test/css"));
            System.out.println(ftp.pwd());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uploadRecursiveTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.pwd());
            ftp.uploadRecursive("E:/test/css", "/aaa/bbb");
            System.out.println(ftp.pwd());
            ftp.cd("/bbb");
            System.out.println(ftp.pwd());
            ftp.uploadRecursive(FileUtils.newFile("E:/test/css"), "eee/fff");
            System.out.println(ftp.pwd());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void downloadTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            System.out.println(ftp.download("/aaa/teachers.html", "d:/test/download/teachers.html"));
            System.out.println(ftp.download("/aaa/teachers.html", FileUtils.newFile("d:/test/download/teachers2.html")));
            System.out.println(ftp.download("/css", "logo.css", "d:/test/download/css", "logo-new.css"));
            System.out.println(ftp.download("/css", "logo.css", "d:/test/download/css/logo-new2.css"));
            System.out.println(ftp.download("/css", "order.css", "d:/test/download/css"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void downloadRecursiveTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            ftp.downloadRecursive("/aaa/css", "d:/test/download");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            ftp.cd("/aaa");
            System.out.println(ftp.move("bbb/teachers.html", "/eee/teachers.html"));
            System.out.println(ftp.move("css2", "teacher-register.css", "ccc", "teacher-register-new.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveToDirTest() {
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            ftp.cd("/aaa");
            System.out.println(ftp.moveToDir("ccc", "teacher-register-new.html", "ddd"));
            System.out.println(ftp.moveToDir("ddd/teacher-register-new.html", "/aaa/eee"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
