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

import com.chanus.yuntao.utils.core.function.Editor;
import com.chanus.yuntao.utils.core.function.Filter;
import com.chanus.yuntao.utils.core.function.Matcher;

import java.util.*;
import java.util.function.Predicate;

/**
 * 集合工具类
 *
 * @author Chanus
 * @date 2020-06-20 11:44:05
 * @since 1.0.0
 */
public class CollectionUtils {
    /**
     * 判断 Collection 集合是否为空
     *
     * @param collection Collection 集合
     * @return {@code true} 集合为空；{@code false} 集合不为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断 Iterator 是否为空
     *
     * @param iterator {@link Iterator}
     * @return {@code true} Iterator 为空；{@code false} Iterator 不为空
     * @since 1.4.0
     */
    public static boolean isEmpty(Iterator<?> iterator) {
        return iterator == null || !iterator.hasNext();
    }

    /**
     * 判断 Iterable 是否为空
     *
     * @param iterable {@link Iterable}
     * @return {@code true} Iterable 为空；{@code false} Iterable 不为空
     * @since 1.4.0
     */
    public static boolean isEmpty(Iterable<?> iterable) {
        return iterable == null || isEmpty(iterable.iterator());
    }

    /**
     * 判断 Enumeration 是否为空
     *
     * @param enumeration {@link Enumeration}
     * @return {@code true} Enumeration 为空；{@code false} Enumeration 不为空
     * @since 1.4.0
     */
    public static boolean isEmpty(Enumeration<?> enumeration) {
        return enumeration == null || !enumeration.hasMoreElements();
    }

    /**
     * 判断 Collection 集合是否不为空
     *
     * @param collection Collection 集合
     * @return {@code true} Collection 集合不为空；{@code false} Collection 集合为空
     * @since 1.4.0
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断 Iterator 是否不为空
     *
     * @param iterator {@link Iterator}
     * @return {@code true} Iterator 不为空；{@code false} Iterator 为空
     * @since 1.4.0
     */
    public static boolean isNotEmpty(Iterator<?> iterator) {
        return !isEmpty(iterator);
    }

    /**
     * 判断 Iterable 是否不为空
     *
     * @param iterable {@link Iterable}
     * @return {@code true} Iterable 不为空；{@code false} Iterable 为空
     * @since 1.4.0
     */
    public static boolean isNotEmpty(Iterable<?> iterable) {
        return !isEmpty(iterable);
    }

    /**
     * 判断 Enumeration 是否不为空
     *
     * @param enumeration {@link Enumeration}
     * @return {@code true} Enumeration 不为空；{@code false} Enumeration 为空
     * @since 1.4.0
     */
    public static boolean isNotEmpty(Enumeration<?> enumeration) {
        return !isEmpty(enumeration);
    }

    /**
     * 合并多个集合，保留全部元素
     *
     * @param <T>        集合元素类型
     * @param coll1      集合1
     * @param coll2      集合2
     * @param otherColls 其它集合
     * @return 合并后的集合，返回 {@link ArrayList}
     * @since 1.4.0
     */
    @SafeVarargs
    public static <T> List<T> unionAll(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
        final List<T> result;
        if (isEmpty(coll1)) {
            result = new ArrayList<>();
        } else {
            result = new ArrayList<>(coll1);
        }

        if (isNotEmpty(coll2)) {
            result.addAll(coll2);
        }

        if (ArrayUtils.isNotEmpty(otherColls)) {
            for (Collection<T> otherColl : otherColls) {
                result.addAll(otherColl);
            }
        }

        return result;
    }

    /**
     * 合并多个集合，去除重复元素
     *
     * @param <T>        集合元素类型
     * @param coll1      集合1
     * @param coll2      集合2
     * @param otherColls 其它集合
     * @return 合并后的集合，返回 {@link LinkedHashSet}
     * @since 1.4.0
     */
    @SafeVarargs
    public static <T> Set<T> unionDistinct(Collection<T> coll1, Collection<T> coll2, Collection<T>... otherColls) {
        final Set<T> result;
        if (isEmpty(coll1)) {
            result = new LinkedHashSet<>();
        } else {
            result = new LinkedHashSet<>(coll1);
        }

        if (isNotEmpty(coll2)) {
            result.addAll(coll2);
        }

        if (ArrayUtils.isNotEmpty(otherColls)) {
            for (Collection<T> otherColl : otherColls) {
                result.addAll(otherColl);
            }
        }

        return result;
    }

    /**
     * 判断集合是否包含指定元素
     *
     * @param collection 集合
     * @param value      需要查找的元素
     * @return {@code true} 集合包含指定元素；{@code false} 集合为空或不包含指定元素
     * @since 1.4.0
     */
    public static boolean contains(Collection<?> collection, Object value) {
        return isNotEmpty(collection) && collection.contains(value);
    }

    /**
     * 根据自定义函数判断集合是否包含指定元素
     *
     * @param collection 集合
     * @param predicate  自定义判断函数
     * @param <T>        集合元素类型
     * @return {@code true} 集合包含指定元素；{@code false} 集合为空或不包含指定元素
     * @since 1.4.0
     */
    public static <T> boolean contains(Collection<T> collection, Predicate<? super T> predicate) {
        if (isEmpty(collection))
            return false;

        for (T t : collection) {
            if (predicate.test(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将集合以指定连接符连接成字符串
     *
     * @param <T>       集合元素类型
     * @param iterable  集合
     * @param separator 连接符
     * @return 以 {@code separator} 连接后的字符串
     * @since 1.4.0
     */
    public static <T> String join(Iterable<T> iterable, String separator) {
        if (iterable == null)
            return null;

        return join(iterable.iterator(), separator);
    }

    /**
     * 将集合以指定连接符连接成字符串
     *
     * @param <T>       集合元素类型
     * @param iterator  集合
     * @param separator 连接符
     * @return 以 {@code separator} 连接后的字符串
     * @since 1.4.0
     */
    public static <T> String join(Iterator<T> iterator, String separator) {
        if (iterator == null)
            return null;

        final StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        T item;
        while (iterator.hasNext()) {
            if (isFirst) {
                isFirst = false;
            } else {
                stringBuilder.append(separator);
            }

            item = iterator.next();
            if (ArrayUtils.isArray(item)) {
                stringBuilder.append(ArrayUtils.join((Object[]) item, separator, false));
            } else if (item instanceof Iterable<?>) {
                stringBuilder.append(join((Iterable<?>) item, separator));
            } else if (item instanceof Iterator<?>) {
                stringBuilder.append(join((Iterator<?>) item, separator));
            } else {
                stringBuilder.append(item);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 过滤集合，产生一个新集合
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @param filter     过滤器
     * @return 过滤后的集合
     * @since 1.4.0
     */
    public static <T> Collection<T> filter(Collection<T> collection, Filter<T> filter) {
        if (collection == null || filter == null)
            return collection;

        Collection<T> collection2 = ObjectUtils.clone(collection);
        try {
            collection2.clear();
        } catch (UnsupportedOperationException e) {
            // 克隆后的对象不支持清空，说明为不可变集合对象，使用默认的 ArrayList 保存结果
            collection2 = new ArrayList<>();
        }

        for (T t : collection) {
            if (filter.accept(t)) {
                collection2.add(t);
            }
        }
        return collection2;
    }

    /**
     * 过滤 List 集合，产生一个新集合
     *
     * @param <T>    集合元素类型
     * @param list   List 集合
     * @param filter 过滤器
     * @return 过滤后的 List 集合
     * @since 1.4.0
     */
    public static <T> List<T> filter(List<T> list, Filter<T> filter) {
        if (list == null || filter == null)
            return list;

        final List<T> list2 = (list instanceof LinkedList) ? new LinkedList<>() : new ArrayList<>(list.size());
        for (T t : list) {
            if (filter.accept(t)) {
                list2.add(t);
            }
        }
        return list2;
    }

    /**
     * 编辑集合，产生一个新集合
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @param editor     编辑器
     * @return 编辑后的集合
     * @since 1.4.0
     */
    public static <T> Collection<T> edit(Collection<T> collection, Editor<T, T> editor) {
        if (collection == null || editor == null)
            return collection;

        Collection<T> collection2 = ObjectUtils.clone(collection);
        try {
            collection2.clear();
        } catch (UnsupportedOperationException e) {
            // 克隆后的对象不支持清空，说明为不可变集合对象，使用默认的 ArrayList 保存结果
            collection2 = new ArrayList<>();
        }

        for (T t : collection) {
            collection2.add(editor.edit(t));
        }
        return collection2;
    }

    /**
     * 编辑 List 集合，修改原集合
     *
     * @param <T>    集合元素类型
     * @param list   List 集合
     * @param editor 编辑器
     * @return 编辑后的集合
     * @since 1.4.0
     */
    public static <T> List<T> edit(List<T> list, Editor<T, T> editor) {
        if (list == null || editor == null)
            return list;

        for (int i = 0; i < list.size(); i++) {
            list.set(i, editor.edit(list.get(i)));
        }

        return list;
    }

    /**
     * 去除集合中的元素，修改原集合
     *
     * @param <T>        集合类型
     * @param <E>        集合元素类型
     * @param collection 集合
     * @param filter     过滤器
     * @return 原集合
     * @since 1.4.0
     */
    public static <T extends Collection<E>, E> T remove(T collection, Filter<E> filter) {
        if (collection == null || filter == null)
            return collection;

        collection.removeIf(filter::accept);

        return collection;
    }

    /**
     * 去除集合中的元素，修改原集合
     *
     * @param <T>        集合类型
     * @param <E>        集合元素类型
     * @param collection 集合
     * @param removed    被去掉的元素数组
     * @return 原集合
     * @since 1.4.0
     */
    @SafeVarargs
    public static <T extends Collection<E>, E> T removeAny(T collection, E... removed) {
        collection.removeAll(new HashSet<>(Arrays.asList(removed)));
        return collection;
    }

    /**
     * 去除集合中的 {@code null} 元素，修改原集合
     *
     * @param <T>        集合类型
     * @param <E>        集合元素类型
     * @param collection 集合
     * @return 原集合
     * @since 1.4.0
     */
    public static <T extends Collection<E>, E> T removeNull(T collection) {
        return remove(collection, Objects::isNull);
    }

    /**
     * 去除集合中的 {@code null} 或者 "" 元素，修改原集合
     *
     * @param <T>        集合类型
     * @param <E>        集合元素类型
     * @param collection 集合
     * @return 原集合
     * @since 1.4.0
     */
    public static <T extends Collection<E>, E extends CharSequence> T removeEmpty(T collection) {
        return remove(collection, StringUtils::isEmpty);
    }

    /**
     * 去除集合中的 {@code null} 或者 "" 或者空白字符串元素，修改原集合
     *
     * @param <T>        集合类型
     * @param <E>        集合元素类型
     * @param collection 集合
     * @return 原集合
     * @since 1.4.0
     */
    public static <T extends Collection<E>, E extends CharSequence> T removeBlank(T collection) {
        return remove(collection, StringUtils::isBlank);
    }

    /**
     * 获取集合中指定下标的元素，下标可以为负数，例如-1表示最后一个元素
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @param index      下标，支持负数
     * @return 集合中指定下标的元素，如果下标越界，返回 {@code null}
     * @since 1.4.0
     */
    public static <T> T get(Collection<T> collection, int index) {
        if (collection == null)
            return null;

        final int size = collection.size();
        if (size == 0)
            return null;

        if (index < 0)
            index += size;

        // 检查越界
        if (index >= size || index < 0)
            return null;

        if (collection instanceof List) {
            final List<T> list = ((List<T>) collection);
            return list.get(index);
        } else {
            int i = 0;
            for (T t : collection) {
                if (i > index) {
                    break;
                } else if (i == index) {
                    return t;
                }
                i++;
            }
        }
        return null;
    }

    /**
     * 获取集合中指定多个下标的元素，下标可以为负数，例如-1表示最后一个元素
     *
     * @param <T>        元素类型
     * @param collection 集合
     * @param indexes    下标，支持负数
     * @return 元素列表
     * @since 1.2.5
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getAny(Collection<T> collection, int... indexes) {
        final int size = collection.size();
        final List<T> result = new ArrayList<>();
        if (collection instanceof List) {
            final List<T> list = ((List<T>) collection);
            for (int index : indexes) {
                if (index < 0) {
                    index += size;
                }
                result.add(list.get(index));
            }
        } else {
            final Object[] array = collection.toArray();
            for (int index : indexes) {
                if (index < 0) {
                    index += size;
                }
                result.add((T) array[index]);
            }
        }
        return result;
    }

    /**
     * 获取匹配规则定义中匹配到元素的所有位置
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @param matcher    匹配器，为空则全部匹配
     * @return 位置数组
     * @since 1.2.5
     */
    public static <T> int[] indexOfAll(Collection<T> collection, Matcher<T> matcher) {
        final List<Integer> indexList = new ArrayList<>();
        if (collection != null) {
            int index = 0;
            for (T t : collection) {
                if (null == matcher || matcher.match(t)) {
                    indexList.add(index);
                }
                index++;
            }
        }
        return indexList.stream().mapToInt(Integer::valueOf).toArray();
    }

    /**
     * 查找集合中第一个匹配的元素
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @param filter     过滤器
     * @return 满足过滤条件的第一个元素
     * @since 1.4.0
     */
    public static <T> T findOne(Iterable<T> collection, Filter<T> filter) {
        if (collection != null) {
            for (T t : collection) {
                if (filter.accept(t)) {
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * 排序集合，不会修改原集合
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @param comparator 比较器
     * @return 排序后的集合 {@link ArrayList}
     * @since 1.4.0
     */
    public static <T> List<T> sort(Collection<T> collection, Comparator<? super T> comparator) {
        List<T> list = new ArrayList<>(collection);
        list.sort(comparator);
        return list;
    }

    /**
     * 排序 List 集合，会修改原集合
     *
     * @param <T>        集合元素类型
     * @param list       List 集合
     * @param comparator 比较器
     * @return 原 List 集合
     * @since 1.4.0
     */
    public static <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
        list.sort(comparator);
        return list;
    }

    /**
     * 去重集合
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @return {@link ArrayList}
     * @since 1.4.0
     */
    public static <T> ArrayList<T> distinct(Collection<T> collection) {
        if (isEmpty(collection)) {
            return new ArrayList<>();
        } else if (collection instanceof Set) {
            return new ArrayList<>(collection);
        } else {
            return new ArrayList<>(new LinkedHashSet<>(collection));
        }
    }

    /**
     * 清除一个或多个集合内的元素，每个集合调用 clear() 方法
     *
     * @param collections 一个或多个集合
     * @since 1.4.0
     */
    public static void clear(Collection<?>... collections) {
        for (Collection<?> collection : collections) {
            if (isNotEmpty(collection)) {
                collection.clear();
            }
        }
    }
}
