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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则相关工具类
 *
 * @author Chanus
 * @since 1.3.0
 */
public class RegexUtils {
    /**
     * 正则表达式匹配数字
     */
    public static final Pattern NUMERIC_PATTERN = Pattern.compile("\\d+");
    /**
     * 正则表达式匹配数值
     */
    public static final Pattern NUMBER_PATTERN = Pattern.compile("^([+|-]?0|([+|-]?0\\.\\d+)|^([+|-]?[1-9]\\d*(\\.\\d+)?))$");
    /**
     * 正则表达式匹配中文汉字
     */
    public static final String REGEX_CHINESE = "[一-\u9FFF]";
    /**
     * 正则表达式匹配中文字符串
     */
    public static final String RE_CHINESES = REGEX_CHINESE + "+";

    /**
     * 正则中需要被转义的关键字
     */
    private static final Set<Character> REGEX_KEYS = new HashSet<>();

    static {
        REGEX_KEYS.addAll(Arrays.asList('$', '(', ')', '*', '+', '.', '[', ']', '?', '\\', '^', '{', '}', '|'));
    }

    private RegexUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获得匹配的字符串，获得正则中分组0的内容
     *
     * @param regex   正则表达式
     * @param content 内容
     * @return 匹配后得到的字符串，未匹配返回 {@code null}
     */
    public static String getGroup0(String regex, CharSequence content) {
        return get(regex, content, 0);
    }

    /**
     * 获得匹配的字符串，获得正则中分组1的内容
     *
     * @param regex   正则表达式
     * @param content 内容
     * @return 匹配后得到的字符串，未匹配返回 {@code null}
     */
    public static String getGroup1(String regex, CharSequence content) {
        return get(regex, content, 1);
    }

    /**
     * 获得匹配的字符串
     *
     * @param regex      正则表达式
     * @param content    内容
     * @param groupIndex 匹配正则的分组序号
     * @return 匹配后得到的字符串，未匹配返回 {@code null}
     */
    public static String get(String regex, CharSequence content, int groupIndex) {
        if (regex == null || content == null) {
            return null;
        }

        return get(Pattern.compile(regex, Pattern.DOTALL), content, groupIndex);
    }

    /**
     * 获得匹配的字符串，获得正则中分组0的内容
     *
     * @param pattern 正则模式
     * @param content 内容
     * @return 匹配后得到的字符串，未匹配返回 {@code null}
     */
    public static String getGroup0(Pattern pattern, CharSequence content) {
        return get(pattern, content, 0);
    }

    /**
     * 获得匹配的字符串，获得正则中分组1的内容
     *
     * @param pattern 正则模式
     * @param content 内容
     * @return 匹配后得到的字符串，未匹配返回 {@code null}
     */
    public static String getGroup1(Pattern pattern, CharSequence content) {
        return get(pattern, content, 1);
    }

    /**
     * 获得匹配的字符串，对应分组0表示整个匹配内容，1表示第一个括号分组内容，依次类推
     *
     * @param pattern    正则模式
     * @param content    内容
     * @param groupIndex 匹配正则的分组序号，0表示整个匹配内容，1表示第一个括号分组内容，依次类推
     * @return 匹配后得到的字符串，未匹配返回 {@code null}
     */
    public static String get(Pattern pattern, CharSequence content, int groupIndex) {
        if (pattern == null || content == null) {
            return null;
        }

        final Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(groupIndex);
        }
        return null;
    }

    /**
     * 获得匹配的字符串匹配到的所有分组
     *
     * @param pattern 正则模式
     * @param content 内容
     * @return 匹配后得到的字符串数组，按照分组顺序依次列出，未匹配到返回空列表，任何一个参数为  {@code null} 返回  {@code null}
     */
    public static List<String> getAllGroups(Pattern pattern, CharSequence content) {
        return getAllGroups(pattern, content, true);
    }

    /**
     * 获得匹配的字符串匹配到的所有分组
     *
     * @param pattern    正则模式
     * @param content    内容
     * @param withGroup0 是否包括分组0，此分组表示全匹配的信息
     * @return 匹配后得到的字符串数组，按照分组顺序依次列出，未匹配到返回空列表，任何一个参数为 {@code null} 返回  {@code null}
     */
    public static List<String> getAllGroups(Pattern pattern, CharSequence content, boolean withGroup0) {
        if (pattern == null || content == null) {
            return null;
        }

        ArrayList<String> result = new ArrayList<>();
        final Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            final int startGroup = withGroup0 ? 0 : 1;
            final int groupCount = matcher.groupCount();
            for (int i = startGroup; i <= groupCount; i++) {
                result.add(matcher.group(i));
            }
        }
        return result;
    }

    /**
     * 删除给定内容中匹配正则的第一个内容
     *
     * @param regex   正则表达式
     * @param content 内容
     * @return 删除匹配的第一个内容后剩余的内容
     */
    public static String delFirst(String regex, CharSequence content) {
        if (regex == null || content == null) {
            return StringUtils.toString(content);
        }

        return delFirst(Pattern.compile(regex, Pattern.DOTALL), content);
    }

    /**
     * 删除给定内容中匹配正则的第一个内容
     *
     * @param pattern 正则模式
     * @param content 内容
     * @return 删除匹配的第一个内容后剩余的内容
     */
    public static String delFirst(Pattern pattern, CharSequence content) {
        if (pattern == null || content == null) {
            return StringUtils.toString(content);
        }

        return pattern.matcher(content).replaceFirst(StringUtils.EMPTY);
    }

    /**
     * 删除给定内容中匹配正则的全部内容
     *
     * @param regex   正则表达式
     * @param content 内容
     * @return 删除匹配内容后剩余的内容
     */
    public static String delAll(String regex, CharSequence content) {
        if (regex == null || content == null) {
            return StringUtils.toString(content);
        }

        return delAll(Pattern.compile(regex, Pattern.DOTALL), content);
    }

    /**
     * 删除给定内容中匹配正则的全部内容
     *
     * @param pattern 正则模式
     * @param content 内容
     * @return 删除匹配内容后剩余的内容
     */
    public static String delAll(Pattern pattern, CharSequence content) {
        if (pattern == null || content == null) {
            return StringUtils.toString(content);
        }

        return pattern.matcher(content).replaceAll(StringUtils.EMPTY);
    }

    /**
     * 获取给定内容中匹配正则的所有结果
     *
     * @param <T>        集合类型
     * @param regex      正则表达式
     * @param content    内容
     * @param group      正则的分组
     * @param collection 返回的集合
     * @return 给定内容中匹配正则的结果集合
     */
    public static <T extends Collection<String>> T findAll(String regex, CharSequence content, int group, T collection) {
        if (regex == null) {
            return collection;
        }

        return findAll(Pattern.compile(regex, Pattern.DOTALL), content, group, collection);
    }

    /**
     * 获取给定内容中匹配正则的所有结果
     *
     * @param regex   正则表达式
     * @param content 内容
     * @param group   正则的分组
     * @return 给定内容中匹配正则的结果列表
     */
    public static List<String> findAll(String regex, CharSequence content, int group) {
        return findAll(regex, content, group, new ArrayList<>());
    }

    /**
     * 获取给定内容中匹配正则的所有结果
     *
     * @param <T>        集合类型
     * @param pattern    正则模式
     * @param content    内容
     * @param group      正则的分组
     * @param collection 返回的集合
     * @return 给定内容中匹配正则的结果集合
     */
    public static <T extends Collection<String>> T findAll(Pattern pattern, CharSequence content, int group, T collection) {
        if (pattern == null || content == null) {
            return null;
        }

        if (collection == null) {
            throw new NullPointerException("Null collection param provided!");
        }

        final Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            collection.add(matcher.group(group));
        }
        return collection;
    }

    /**
     * 获取给定内容中匹配正则的所有结果
     *
     * @param pattern 正则模式
     * @param content 内容
     * @param group   正则的分组
     * @return 给定内容中匹配正则的结果列表
     */
    public static List<String> findAll(Pattern pattern, CharSequence content, int group) {
        return findAll(pattern, content, group, new ArrayList<>());
    }

    /**
     * 计算给定内容中匹配正则的个数
     *
     * @param regex   正则表达式
     * @param content 内容
     * @return 匹配正则的个数
     */
    public static int count(String regex, CharSequence content) {
        if (regex == null || content == null) {
            return 0;
        }

        return count(Pattern.compile(regex, Pattern.DOTALL), content);
    }

    /**
     * 计算给定内容中匹配正则的个数
     *
     * @param pattern 正则模式
     * @param content 内容
     * @return 匹配正则的个数
     */
    public static int count(Pattern pattern, CharSequence content) {
        if (pattern == null || content == null) {
            return 0;
        }

        int count = 0;
        final Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            count++;
        }

        return count;
    }

    /**
     * 判断给定内容中是否包含正则表达式匹配的内容
     *
     * @param regex   正则表达式
     * @param content 内容
     * @return {@code true} 包含正则表达式匹配的内容；{@code false} 不包含正则表达式匹配的内容
     */
    public static boolean contains(String regex, CharSequence content) {
        return regex != null && content != null && contains(Pattern.compile(regex, Pattern.DOTALL), content);
    }

    /**
     * 判断给定内容中是否包含正则表达式匹配的内容
     *
     * @param pattern 正则模式
     * @param content 内容
     * @return {@code true} 包含正则表达式匹配的内容；{@code false} 不包含正则表达式匹配的内容
     */
    public static boolean contains(Pattern pattern, CharSequence content) {
        return pattern != null && content != null && pattern.matcher(content).find();
    }

    /**
     * 判断给定内容是否匹配正则
     *
     * @param regex   正则表达式
     * @param content 内容
     * @return {@code true} 匹配；{@code false} 不匹配
     */
    public static boolean isMatch(String regex, CharSequence content) {
        return regex != null && content != null && isMatch(Pattern.compile(regex, Pattern.DOTALL), content);
    }

    /**
     * 判断给定内容是否匹配正则
     *
     * @param pattern 正则模式
     * @param content 内容
     * @return {@code true} 匹配；{@code false} 不匹配
     */
    public static boolean isMatch(Pattern pattern, CharSequence content) {
        return pattern != null && content != null && pattern.matcher(content).matches();
    }

    /**
     * 转义字符，将正则的关键字转义
     *
     * @param c 字符
     * @return 转义后的文本
     */
    public static String escape(char c) {
        return REGEX_KEYS.contains(c) ? (StringUtils.BACKSLASH + c) : String.valueOf(c);
    }

    /**
     * 转义字符串，将正则的关键字转义
     *
     * @param content 文本
     * @return 转义后的文本
     */
    public static String escape(CharSequence content) {
        if (StringUtils.isBlank(content)) {
            return StringUtils.toString(content);
        }

        return StringUtils.edit(content, c -> REGEX_KEYS.contains(c) ? (StringUtils.BACKSLASH + c) : String.valueOf(c));
    }
}
