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

import com.chanus.yuntao.utils.core.CharsetUtils;
import com.chanus.yuntao.utils.core.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    public void isWindowsTest() {
        System.out.println(FileUtils.isWindows());
    }

    @Test
    public void lsTest() {
        File[] files = FileUtils.ls("F:\\test");
        for (File file : files) {
            System.out.println(file.getPath());
        }
    }

    @Test
    public void isEmptyTest() {
        System.out.println(FileUtils.isEmpty(new File("F:\\test")));
        System.out.println(FileUtils.isEmpty(new File("F:\\test\\test11")));
        System.out.println(FileUtils.isEmpty(new File("F:\\test\\1.jpg")));
        System.out.println(FileUtils.isEmpty(new File("F:\\test\\test.txt")));
    }

    @Test
    public void isNotEmptyTest() {
        System.out.println(FileUtils.isNotEmpty(new File("F:\\test")));
        System.out.println(FileUtils.isNotEmpty(new File("F:\\test\\test11")));
        System.out.println(FileUtils.isNotEmpty(new File("F:\\test\\1.jpg")));
        System.out.println(FileUtils.isNotEmpty(new File("F:\\test\\test.txt")));
    }

    @Test
    public void loopFilesTest() {
        List<File> files = FileUtils.loopFiles("F:\\test");
        files.forEach(file -> System.out.println(file.getPath()));

        List<File> files2 = FileUtils.loopFiles(new File("F:\\test"));
        files2.forEach(file -> System.out.println(file.getPath()));
    }

    @Test
    public void readTest() {
        File file = new File(path);
        String s1 = FileUtils.read(file, CharsetUtils.UTF_8);
        System.out.println(s1);
        String s2 = FileUtils.read(path, CharsetUtils.UTF_8);
        System.out.println(s2);
    }

    @Test
    public void writeTest() {
        FileUtils.write(path, "abcdefg", false);
        FileUtils.write(path, "\r\nhijklmn\r\n", true);
        List<String> content = new ArrayList<String>() {
            private static final long serialVersionUID = -7265293864991894455L;

            {
                add("opq rst");
                add("uvw xyz");
            }
        };
        FileUtils.write(path, content, true);

        FileInputStream fis;
        String path2 = ROOT + "/test2.txt";
        try {
            fis = new FileInputStream(path2);
            FileUtils.writeFromStream(new File(path), fis, true);
            fis = new FileInputStream(path2);
            FileUtils.writeFromStream(path, fis, true);
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
        FileUtils.createFile(new File("F:\\test1\\test2"));
        FileUtils.createFile(new File("F:\\test1\\test2.txt"));

        FileUtils.createFile("F:\\test1\\test3");
        FileUtils.createFile("F:\\test1\\test3.txt");

        FileUtils.createFile("F:\\test1", "test4");
        FileUtils.createFile("F:\\test1", "test4.txt");
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
    public void equalsTest() {
        System.out.println(FileUtils.equals(new File("F:\\test\\test1\\test2"), new File("F:\\test\\test1")));// false
        System.out.println(FileUtils.equals(new File("F:\\test\\test1\\test2"), new File("F:\\test\\test1\\test2")));// true
    }

    @Test
    public void pathEqualsTest() {
        System.out.println(FileUtils.pathEquals(new File("F:\\test\\test1\\test2"), new File("F:\\test\\test1")));// false
        System.out.println(FileUtils.pathEquals(new File("F:\\test\\test1\\test2"), new File("F:\\test\\test1\\test2")));// true
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
        // 源为文件，目标为已存在目录，则拷贝到目录下，文件名不变
        // 覆盖
        FileUtils.copyFile(new File("F:\\test\\1.jpg"), new File("F:\\test\\test2"));
        FileUtils.copyFile("F:\\test\\test.txt", "F:\\test\\test2");
        // 不覆盖
        FileUtils.copyFile(new File("F:\\test\\1.jpg"), new File("F:\\test\\test3"), false);
        FileUtils.copyFile("F:\\test\\test.txt", "F:\\test\\test3", false);
        // 源为文件，目标为不存在路径，则目标以文件对待（自动创建父级目录）比如：/dest/aaa，如果aaa不存在，则aaa被当作文件名
        // 覆盖
        FileUtils.copyFile(new File("F:\\test\\1.jpg"), new File("F:\\test\\test2\\aaa"));
        FileUtils.copyFile("F:\\test\\test.txt", "F:\\test\\test2\\ccc");
        // 不覆盖
        FileUtils.copyFile(new File("F:\\test\\1.jpg"), new File("F:\\test\\test3\\bbb"), false);
        FileUtils.copyFile("F:\\test\\test.txt", "F:\\test\\test2\\ccc", false);
        // 源为文件，目标是一个已存在的文件
        // 覆盖
        FileUtils.copyFile(new File("F:\\test\\1.jpg"), new File("F:\\test\\test2\\2.jpg"));
        FileUtils.copyFile("F:\\test\\test.txt", "F:\\test\\test2\\test.txt");
        // 不覆盖
        FileUtils.copyFile(new File("F:\\test\\1.jpg"), new File("F:\\test\\test3\\2.jpg"), false);
        FileUtils.copyFile("F:\\test\\test.txt", "F:\\test\\test2\\test.txt", false);
    }

    @Test
    public void copyDirTest() {
        // 允许覆盖，只拷贝文件，将源目录下的内容拷贝到目标目录下
        FileUtils.copyDir("F:\\test", "F:\\test1", true, true, true);
        // 允许覆盖，只拷贝文件，将源目录拷贝到目标目录下
        FileUtils.copyDir("F:\\test", "F:\\test2", true, true, false);
        // 允许覆盖，拷贝文件和子目录，将源目录下的内容拷贝到目标目录下
        FileUtils.copyDir("F:\\test", "F:\\test3", true, false, true);
        // 允许覆盖，拷贝文件和子目录，将源目录下的内容拷贝到目标目录下
        FileUtils.copyDir("F:\\test", "F:\\test4", true, false, false);
        // 允许覆盖，拷贝文件和子目录，将源目录下的内容拷贝到目标目录下
        FileUtils.copyDir("F:\\test", "F:\\test5");
    }

    @Test
    public void copyTest() throws IOException {
        // 源为文件，目标为已存在目录，则拷贝到目录下，文件名不变
        // 覆盖
        FileUtils.copy(new File("F:\\test\\1.jpg"), new File("F:\\test\\test2"), true, true, true);
        // 不覆盖
        FileUtils.copy(new File("F:\\test\\1.jpg"), new File("F:\\test\\test3"), false, true, true);
        // 源为文件，目标为不存在路径，则目标以文件对待（自动创建父级目录）比如：/dest/aaa，如果aaa不存在，则aaa被当作文件名
        // 覆盖
        FileUtils.copy(new File("F:\\test\\1.jpg"), new File("F:\\test\\test2\\aaa"), true, true, true);
        // 不覆盖
        FileUtils.copy(new File("F:\\test\\1.jpg"), new File("F:\\test\\test3\\bbb"), false, true, true);
        // 源为文件，目标是一个已存在的文件
        // 覆盖
        FileUtils.copy(new File("F:\\test\\1.jpg"), new File("F:\\test\\test2\\2.jpg"), true, true, true);
        // 不覆盖
        FileUtils.copy(new File("F:\\test\\1.jpg"), new File("F:\\test\\test3\\2.jpg"), false, true, true);
        FileUtils.copy(new File("F:\\test\\1.jpg"), new File("F:\\test\\test3\\3.jpg"), false);
    }

    @Test
    public void moveTest() throws IOException {
        // 移动文件到文件
        FileUtils.move(new File("F:\\test2\\1.jpg"), new File("F:\\test2\\2.jpg"), true);
        // 移动文件到目录
        FileUtils.move(new File("F:\\test2\\1.jpg"), new File("F:\\test3"), true);
        // 移动目录中的内容到目录
        FileUtils.move(new File("F:\\test2"), new File("F:\\test4"), false);

        FileUtils.move(new File("F:\\test2\\1.jpg"), new File("F:\\test3\\1.jpg"));

        FileUtils.move("F:\\test1\\test.txt", "F:\\test2\\test.txt");
    }

    @Test
    public void renameTest() {
        FileUtils.rename(new File("F:\\test\\1.jpg"), "2", true);
        FileUtils.rename(new File("F:\\test\\test.txt"), "test2.txt");
    }

    @Test
    public void cleanTest() {
        FileUtils.clean(new File("F:\\test\\test2.txt"));

        FileUtils.clean("F:\\test\\test3.txt");
    }

    @Test
    public void isSubTest() {
        System.out.println(FileUtils.isSub(new File("F:\\test\\test1\\test2"), new File("F:\\test\\test1")));// false
        System.out.println(FileUtils.isSub(new File("F:\\test\\test1"), new File("F:\\test\\test1\\test2")));// true
    }
}
