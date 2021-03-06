package com.lgx.jdk8.part02;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream的使用介绍
 */
public class Test04Stream {
    public static void main(String[] args) {
        //创建流的几种方式
        Stream stream1 = Stream.of("hello", "world", "hello world");

        String[] myArray = new String[]{"hello", "world", "hello world"};
        Stream stream2 = Stream.of(myArray);

        Stream stream3 = Arrays.stream(myArray);

        List<String> myList = Arrays.asList("hello", "world", "hello world");
        Stream stream4 = myList.stream();

        System.out.println("------------------------");

        //IntStream的使用
        IntStream.of(new int[]{3,4,5,6,7}).forEach(System.out::print);System.out.println();
        IntStream.range(3,8).forEach(System.out::print);System.out.println();
        IntStream.rangeClosed(3,7).forEach(System.out::print);System.out.println();

        System.out.println("------------------------");

        //每个数乘以2再求和
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
        //传统写法
        /*int sum = 0;
        for(Integer integer: list){
            sum += integer*2;
        }
        System.out.println("sum = [" + sum + "]");*/
        //jdk8写法
        System.out.println("sum = [" + list.stream().map(i -> i*2).reduce(0, Integer::sum) + "]");

        //流转换成集合，再遍历
        Stream<String> streams = Stream.of("hello", "world", "hello world");
        /*String[] strArray = streams.toArray(length -> new String[length]);
        Arrays.asList(strArray).forEach(System.out::println);*/

        //用方法引用来写
        /*String[] strArray2 = streams.toArray(String[]::new);
        Arrays.asList(strArray2).forEach(System.out::println);*/

        //用collect(),另外方法来写
        /*List<String> strList = streams.collect(() -> new ArrayList(), (theList, item) -> theList.add(item),
                (theList1, theList2) -> theList1.addAll(theList2));
        strList.forEach(System.out::println);*/

        //用collect(),参数方法引用来写
        /*List<String> strList2 = streams.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        strList2.forEach(System.out::println);*/

        //用collect()配合Collectors.toList()来写
        /*List<String> strList3 = streams.collect(Collectors.toList());
        strList3.forEach(System.out::println);*/

        //用collect()配合Collectors.toCollection来写,可以指定类型：如ArrayList,LinkList等
        /*List<String> strList4 = streams.collect(Collectors.toCollection(ArrayList::new));
        strList4.forEach(System.out::println);*/

        //转换成set
        /*Set<String> set = streams.collect(Collectors.toSet());
        set.forEach(System.out::println);
        System.out.println("set type = [" + set.getClass() + "]");*/

        //转换成set,指定类型
        /*Set<String> set = streams.collect(Collectors.toCollection(TreeSet::new));
        set.forEach(System.out::println);
        System.out.println("set type = [" + set.getClass() + "]");*/

        //拼接
        /*String str = streams.collect(Collectors.joining());
        System.out.println("拼接str=" + str);*/
        /*String str = streams.collect(Collectors.joining(", "));
        System.out.println("拼接str2=" + str);*/
        /*String str = streams.collect(Collectors.joining(", ", "[", "]"));
        System.out.println("拼接str3=" + str);*/

        //过滤，将每个参数由小写变成大写，map函数就是映射，针对每一个元素
        List<String> list2 = Arrays.asList("hello", "world", "hello world");
        list2.stream().map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);

        //过滤，将每个参数变成之前的平方
        List<Integer> list3 = Arrays.asList(2,3,4,5);
        list3.stream().map(item -> item * item).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("==========================");

        //flatMap操作,扁平化map，会将里面的集合元素,合并到一个集合
        Stream<List<Integer>> stream5 = Stream.of(Arrays.asList(1,2,3), Arrays.asList(13,14), Arrays.asList(25,26,27));
        stream5.flatMap(theList -> theList.stream()).map(item -> item * item).forEach(System.out::println);

        /**
         * generate方法使用
         * findFirst之所以返回一个Optional,是因为避免NPE(空指针异常)，因为可能一个都没找到
         */
        Stream<String> stream6 = Stream.generate(UUID.randomUUID()::toString);
        stream6.findFirst().ifPresent(System.out::println);

        //iterate方法使用
        Stream.iterate(1, item -> item + 2).limit(10).forEach(System.out::println);

        //找出上面那个流中大于2的元素，然后将每个元素乘以3，然后忽略掉前4个元素，然后再取出流中的前5个元素，最后求出流中元素的总和
        //mapToInt是map的一个具化，省去类型转换，skip标示忽略掉前几个元素，limit是取几个元素
        Stream<Integer> stream7 = Stream.iterate(1, item -> item + 2).limit(10);
        /*int sum = stream7.filter(item -> item > 2).mapToInt(item -> item * 3).skip(4).limit(5).sum();
        System.out.println("sum=" + sum);*/

        //如果是求最大值
        /*OptionalInt max = stream7.filter(item -> item > 2).mapToInt(item -> item * 3).skip(4).limit(5).max();
        max.ifPresent(System.out::print);*/

        //如果我们又想求总和，或者最大值，最小值，平均值，个数之类的应该怎么写呢
        //summaryStatistics是总结统计的，是一个终止操作
        IntSummaryStatistics intSummaryStatistics = stream7.filter(item -> item > 2).mapToInt(item -> item * 3).skip(4).limit(5).summaryStatistics();
        System.out.println("sum = [" + intSummaryStatistics.getSum() + "]");
        System.out.println("max = [" + intSummaryStatistics.getMax() + "]");
        System.out.println("min = [" + intSummaryStatistics.getMin() + "]");
        System.out.println("ave = [" + intSummaryStatistics.getAverage() + "]");
        System.out.println("count = [" + intSummaryStatistics.getCount() + "]");

    }

}
