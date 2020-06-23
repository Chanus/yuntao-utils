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
package com.chanus.yuntao.utils.core.test;

import com.chanus.yuntao.utils.core.FileUtils;
import com.chanus.yuntao.utils.core.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * FileUtils 测试类
 *
 * @author Chanus
 * @date 2020-06-22 08:52:01
 * @since 1.0.0
 */
public class FileUtilsTest {
    public static final String ROOT = "F:/test";
    public static final String path = ROOT + "/test.txt";

    @Test
    public void readTest() {
        File file = new File(path);
        String s1 = FileUtils.read(file, StringUtils.CHARSET_UTF8);
        System.out.println(s1);
        String s2 = FileUtils.read(path, StringUtils.CHARSET_UTF8);
        System.out.println(s2);
    }

    @Test
    public void writeTest() {
        FileUtils.write(path, "abcdefg", false);
        FileUtils.write(path, "\r\nhijklmn\r\n", true);
        List<String> content = new ArrayList<String>() {{
            add("opq rst");
            add("uvw xyz");
        }};
        FileUtils.write(path, content, true);

        FileInputStream fis;
        String path2 = ROOT + "/test2.txt";
        try {
            fis = new FileInputStream(path2);
            FileUtils.write(new File(path), fis, true);
            fis = new FileInputStream(path2);
            FileUtils.write(path, fis, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getFileNameTest() {
        System.out.println("================= getFileName(File file) =================");
        System.out.println(FileUtils.getFileName(new File(path)));
        System.out.println(FileUtils.getFileName(new File("F:\\test\\test2.txt")));
        System.out.println(FileUtils.getFileName(new File("F:\\test\\test3.txt")));
        System.out.println(FileUtils.getFileName(new File("F:\\test/test2.txt.t")));
        System.out.println(FileUtils.getFileName(new File("abcdefg")));
        System.out.println(FileUtils.getFileName(new File("abcdefg.hi")));
        System.out.println(FileUtils.getFileName(new File("abcdefg.hi.jk")));

        System.out.println("================= getFileName(String path) =================");
        System.out.println(FileUtils.getFileName(path));
        System.out.println(FileUtils.getFileName("F:\\test\\test2.txt"));
        System.out.println(FileUtils.getFileName("F:\\test\\test3.txt"));
        System.out.println(FileUtils.getFileName("F:\\test/test2.txt.t"));
        System.out.println(FileUtils.getFileName("abcdefg"));
        System.out.println(FileUtils.getFileName("abcdefg.hi"));
        System.out.println(FileUtils.getFileName("abcdefg.hi.jk"));
    }

    @Test
    public void getFileNameWithoutExtensionTest() {
        System.out.println("================= getFileNameWithoutExtension(File file) =================");
        System.out.println(FileUtils.getFileNameWithoutExtension(new File(path)));
        System.out.println(FileUtils.getFileNameWithoutExtension(new File("F:\\test\\test2.txt")));
        System.out.println(FileUtils.getFileNameWithoutExtension(new File("F:\\test\\test3.txt")));
        System.out.println(FileUtils.getFileNameWithoutExtension(new File("F:\\test/test2.txt.t")));
        System.out.println(FileUtils.getFileNameWithoutExtension(new File("abcdefg")));
        System.out.println(FileUtils.getFileNameWithoutExtension(new File("abcdefg.hi")));
        System.out.println(FileUtils.getFileNameWithoutExtension(new File("abcdefg.hi.jk")));

        System.out.println("================= getFileNameWithoutExtension(String path) =================");
        System.out.println(FileUtils.getFileNameWithoutExtension(path));
        System.out.println(FileUtils.getFileNameWithoutExtension("F:\\test\\test2.txt"));
        System.out.println(FileUtils.getFileNameWithoutExtension("F:\\test\\test3.txt"));
        System.out.println(FileUtils.getFileNameWithoutExtension("F:\\test/test2.txt.t"));
        System.out.println(FileUtils.getFileNameWithoutExtension("abcdefg"));
        System.out.println(FileUtils.getFileNameWithoutExtension("abcdefg.hi"));
        System.out.println(FileUtils.getFileNameWithoutExtension("abcdefg.hi.jk"));
    }

    @Test
    public void getFileExtensionTest() {
        System.out.println("================= getFileExtension(File file) =================");
        System.out.println(FileUtils.getFileExtension(new File(path)));
        System.out.println(FileUtils.getFileExtension(new File("F:\\test\\test2.txt")));
        System.out.println(FileUtils.getFileExtension(new File("F:\\test\\test3.txt")));
        System.out.println(FileUtils.getFileExtension(new File("F:\\test/test2.txt.t")));
        System.out.println(FileUtils.getFileExtension(new File("abcdefg")));
        System.out.println(FileUtils.getFileExtension(new File("abcdefg.hi")));
        System.out.println(FileUtils.getFileExtension(new File("abcdefg.hi.jk")));

        System.out.println("================= getFileExtension(String path) =================");
        System.out.println(FileUtils.getFileExtension(path));
        System.out.println(FileUtils.getFileExtension("F:\\test\\test2.txt"));
        System.out.println(FileUtils.getFileExtension("F:\\test\\test3.txt"));
        System.out.println(FileUtils.getFileExtension("F:\\test/test2.txt.t"));
        System.out.println(FileUtils.getFileExtension("abcdefg"));
        System.out.println(FileUtils.getFileExtension("abcdefg.hi"));
        System.out.println(FileUtils.getFileExtension("abcdefg.hi.jk"));
    }

    @Test
    public void getFolderNameTest() {
        System.out.println("================= getFolderName(File file) =================");
        System.out.println(FileUtils.getFolderName(new File(path)));
        System.out.println(FileUtils.getFolderName(new File("F:\\test\\test2.txt")));
        System.out.println(FileUtils.getFolderName(new File("F:\\test\\test3.txt")));
        System.out.println(FileUtils.getFolderName(new File("F:\\test/test2.txt.t")));
        System.out.println(FileUtils.getFolderName(new File("F:\\abcdefg")));
        System.out.println(FileUtils.getFolderName(new File("F:\\")));
        System.out.println(FileUtils.getFolderName(new File("F:")));
        System.out.println(FileUtils.getFolderName(new File("abcdefg.hi")));
        System.out.println(FileUtils.getFolderName(new File("abcdefg.hi.jk")));

        System.out.println("================= getFolderName(String path) =================");
        System.out.println(FileUtils.getFolderName(path));
        System.out.println(FileUtils.getFolderName("F:\\test\\test2.txt"));
        System.out.println(FileUtils.getFolderName("F:\\test\\test3.txt"));
        System.out.println(FileUtils.getFolderName("F:\\test/test2.txt.t"));
        System.out.println(FileUtils.getFolderName("F:\\abcdefg"));
        System.out.println(FileUtils.getFolderName("F:\\"));
        System.out.println(FileUtils.getFolderName("F:"));
        System.out.println(FileUtils.getFolderName("abcdefg.hi"));
        System.out.println(FileUtils.getFolderName("abcdefg.hi.jk"));
    }

    @Test
    public void getFileSizeTest() {
        System.out.println("================= getFileSize(File file) =================");
        System.out.println(FileUtils.getFileSize(new File(path)));
        System.out.println(FileUtils.getFileSize(new File("F:\\test\\test2.txt")));
        System.out.println(FileUtils.getFileSize(new File("F:\\test\\test3.txt")));
        System.out.println(FileUtils.getFileSize(new File("F:\\test/test2.txt.t")));
        System.out.println(FileUtils.getFileSize(new File("F:\\abcdefg")));
        System.out.println(FileUtils.getFileSize(new File("F:\\")));
        System.out.println(FileUtils.getFileSize(new File("F:")));
        System.out.println(FileUtils.getFileSize(new File("abcdefg.hi")));
        System.out.println(FileUtils.getFileSize(new File("abcdefg.hi.jk")));

        System.out.println("================= getFileSize(String path) =================");
        System.out.println(FileUtils.getFileSize(path));
        System.out.println(FileUtils.getFileSize("F:\\test\\test2.txt"));
        System.out.println(FileUtils.getFileSize("F:\\test\\test3.txt"));
        System.out.println(FileUtils.getFileSize("F:\\test/test2.txt.t"));
        System.out.println(FileUtils.getFileSize("F:\\abcdefg"));
        System.out.println(FileUtils.getFileSize("F:\\"));
        System.out.println(FileUtils.getFileSize("F:"));
        System.out.println(FileUtils.getFileSize("abcdefg.hi"));
        System.out.println(FileUtils.getFileSize("abcdefg.hi.jk"));
    }

    @Test
    public void getFileMD5Test() {
        System.out.println("================= getFileMD5(File file) =================");
        System.out.println(FileUtils.getFileMD5(new File(path)));
        System.out.println(FileUtils.getFileMD5(new File("F:\\test\\test2.txt")));
        System.out.println(FileUtils.getFileMD5(new File("F:\\test\\test3.txt")));
        System.out.println(FileUtils.getFileMD5(new File("F:\\test/test2.txt.t")));
        System.out.println(FileUtils.getFileMD5(new File("F:\\abcdefg")));
        System.out.println(FileUtils.getFileMD5(new File("F:\\")));
        System.out.println(FileUtils.getFileMD5(new File("F:")));
        System.out.println(FileUtils.getFileMD5(new File("abcdefg.hi")));
        System.out.println(FileUtils.getFileMD5(new File("abcdefg.hi.jk")));

        System.out.println("================= getFileMD5(String path) =================");
        System.out.println(FileUtils.getFileMD5(path));
        System.out.println(FileUtils.getFileMD5("F:\\test\\test2.txt"));
        System.out.println(FileUtils.getFileMD5("F:\\test\\test3.txt"));
        System.out.println(FileUtils.getFileMD5("F:\\test/test2.txt.t"));
        System.out.println(FileUtils.getFileMD5("F:\\abcdefg"));
        System.out.println(FileUtils.getFileMD5("F:\\"));
        System.out.println(FileUtils.getFileMD5("F:"));
        System.out.println(FileUtils.getFileMD5("abcdefg.hi"));
        System.out.println(FileUtils.getFileMD5("abcdefg.hi.jk"));
    }

    @Test
    public void getFileMimeTypeTest() {
        System.out.println("================= getFileMimeType(File file) =================");
        System.out.println(FileUtils.getFileMimeType(new File(path)));
        System.out.println(FileUtils.getFileMimeType(new File("F:\\test\\test2.txt")));
        System.out.println(FileUtils.getFileMimeType(new File("F:\\test\\test3.txt")));
        System.out.println(FileUtils.getFileMimeType(new File("F:\\test/test2.txt.t")));
        System.out.println(FileUtils.getFileMimeType(new File("F:\\abcdefg")));
        System.out.println(FileUtils.getFileMimeType(new File("F:\\")));
        System.out.println(FileUtils.getFileMimeType(new File("F:")));
        System.out.println(FileUtils.getFileMimeType(new File("abcdefg.hi")));
        System.out.println(FileUtils.getFileMimeType(new File("abcdefg.hi.jk")));

        System.out.println("================= getFileMimeType(String path) =================");
        System.out.println(FileUtils.getFileMimeType(path));
        System.out.println(FileUtils.getFileMimeType("F:\\test\\test2.txt"));
        System.out.println(FileUtils.getFileMimeType("F:\\test\\test3.txt"));
        System.out.println(FileUtils.getFileMimeType("F:\\test/test2.txt.t"));
        System.out.println(FileUtils.getFileMimeType("F:\\abcdefg"));
        System.out.println(FileUtils.getFileMimeType("F:\\"));
        System.out.println(FileUtils.getFileMimeType("F:"));
        System.out.println(FileUtils.getFileMimeType("abcdefg.hi"));
        System.out.println(FileUtils.getFileMimeType("abcdefg.hi.jk"));
    }

    @Test
    public void getFileLastModifyTimeTest() {
        System.out.println("================= getFileLastModifyTime(File file) =================");
        System.out.println(FileUtils.getFileLastModifyTime(new File(path)));
        System.out.println(FileUtils.getFileLastModifyTime(new File("F:\\test\\test2.txt")));
        System.out.println(FileUtils.getFileLastModifyTime(new File("F:\\test\\test3.txt")));
        System.out.println(FileUtils.getFileLastModifyTime(new File("F:\\test/test2.txt.t")));
        System.out.println(FileUtils.getFileLastModifyTime(new File("F:\\abcdefg")));
        System.out.println(FileUtils.getFileLastModifyTime(new File("F:\\")));
        System.out.println(FileUtils.getFileLastModifyTime(new File("F:")));
        System.out.println(FileUtils.getFileLastModifyTime(new File("abcdefg.hi")));
        System.out.println(FileUtils.getFileLastModifyTime(new File("abcdefg.hi.jk")));

        System.out.println("================= getFileLastModifyTime(String path) =================");
        System.out.println(FileUtils.getFileLastModifyTime(path));
        System.out.println(FileUtils.getFileLastModifyTime("F:\\test\\test2.txt"));
        System.out.println(FileUtils.getFileLastModifyTime("F:\\test\\test3.txt"));
        System.out.println(FileUtils.getFileLastModifyTime("F:\\test/test2.txt.t"));
        System.out.println(FileUtils.getFileLastModifyTime("F:\\abcdefg"));
        System.out.println(FileUtils.getFileLastModifyTime("F:\\"));
        System.out.println(FileUtils.getFileLastModifyTime("F:"));
        System.out.println(FileUtils.getFileLastModifyTime("abcdefg.hi"));
        System.out.println(FileUtils.getFileLastModifyTime("abcdefg.hi.jk"));
    }

    @Test
    public void mkdirsTest() {
        FileUtils.mkdirs(new File("F:\\test\\test3"));
        FileUtils.mkdirs(new File("F:\\test\\test3.txt"));

        FileUtils.mkdirs("F:\\test\\test4");
        FileUtils.mkdirs("F:\\test\\test4.txt");
    }

    @Test
    public void createFileTest() {
        FileUtils.createFile(new File("F:\\test1\\test5"));
        FileUtils.createFile(new File("F:\\test1\\test5.txt"));

        FileUtils.createFile("F:\\test1\\test6");
        FileUtils.createFile("F:\\test1\\test6.txt");
    }

    @Test
    public void isFileExistTest() {
        System.out.println(FileUtils.isFileExist(new File("F:\\test\\test")));
        System.out.println(FileUtils.isFileExist(new File("F:\\test\\test.txt")));

        System.out.println(FileUtils.isFileExist("F:\\test\\test"));
        System.out.println(FileUtils.isFileExist("F:\\test\\test.txt"));
    }

    @Test
    public void isFolderExistTest() {
        System.out.println(FileUtils.isFolderExist(new File("F:\\test")));
        System.out.println(FileUtils.isFolderExist(new File("F:\\test\\test.txt")));

        System.out.println(FileUtils.isFolderExist("F:\\test"));
        System.out.println(FileUtils.isFolderExist("F:\\test\\test.txt"));
    }

    @Test
    public void deleteTest() {
        FileUtils.delete(new File("F:\\test\\test3"));
        FileUtils.delete("F:\\test\\test3.txt", "F:\\test\\test4", "F:\\test\\test5.txt");
    }

    @Test
    public void deleteBigFileTest() {
        FileUtils.deleteBigFile(new File("F:\\test\\test3.txt"));

        FileUtils.deleteBigFile("F:\\test\\test4.txt");
    }

    @Test
    public void copyFileTest() {
        FileUtils.copyFile(new File("F:\\test\\test.txt"), new File("F:\\test1\\test3.txt"));

        FileUtils.copyFile("F:\\test\\test.txt", "F:\\test1\\test4.txt");
    }

    @Test
    public void copyDirTest() {
        FileUtils.copyDir(new File("F:\\test"), new File("F:\\test1"));

        FileUtils.copyDir("F:\\test", "F:\\test2");
    }

    @Test
    public void moveTest() {
        FileUtils.move(new File("F:\\test"), new File("F:\\test1"));

        FileUtils.move("F:\\test1\\test.txt", "F:\\test2\\test.txt");
    }

    @Test
    public void cleanTest() {
        FileUtils.clean(new File("F:\\test\\test2.txt"));

        FileUtils.clean("F:\\test\\test3.txt");
    }
}
