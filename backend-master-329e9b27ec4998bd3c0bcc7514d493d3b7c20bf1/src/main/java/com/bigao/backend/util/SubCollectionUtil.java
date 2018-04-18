package com.bigao.backend.util;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 * Created by wait on 2016/1/10.
 */
public class SubCollectionUtil {

    public static <T> List<List<T>> toSub(Collection<T> original, int limitSize) {
        List<List<T>> allSubsets = Lists.newArrayList();

        List<T> tempClone = Lists.newArrayList();
        int count = 0;
        for (T element : original) {
            tempClone.add(element);
            count++;
            if (count >= limitSize) {
                allSubsets.add(tempClone);
                tempClone = Lists.newArrayList();
                count = 0;
            }
        }
        if (tempClone.size() > 0) {
            allSubsets.add(tempClone);
        }
        return allSubsets;
    }

    public static String toSqlIn(Collection<?> data) {
        StringBuilder builder = new StringBuilder();
        for (Object t : data) {
            builder.append(t).append(",");
        }
        if (builder.toString().endsWith(",")) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        List<Integer> all = Lists.newArrayList();
        for (int i = 0; i < 899; i++) {
            all.add(i);
        }
        List<List<Integer>> finalData = toSub(all, 300);
        for (List<Integer> t : finalData) {
            System.err.println(toSqlIn(t));
        }
    }
}
