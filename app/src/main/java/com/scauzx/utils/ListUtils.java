package com.scauzx.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author scauzx
 */
public final class ListUtils {

    public static <D> boolean isEmpty(List<D> list) {
        return list == null || list.isEmpty();
    }

    public static <D> boolean isEmpty(D[] list) {
        return list == null || list.length == 0;
    }

    public static <D> boolean isEmpty(Set<D> set) {
        return set == null || set.isEmpty();
    }

    public static <D, R> boolean isEmpty(Map<D, R> map) {
        return map == null || map.isEmpty();
    }


    /**
     * 将集合转为整型int数组的方法
     * @param allSet
     * @return
     */
    public static int[] changeCollectionToInt(Collection<Integer> allSet) {
        try {
            // 先将set集合转为Integer型数组
            Integer[] temp = allSet.toArray(new Integer[] {});//关键语句

            // 再将Integer型数组转为int型数组
            int[] intArray = new int[temp.length];
            for (int i = 0; i < temp.length; i++) {
                intArray[i] = temp[i].intValue();
            }
            return intArray;
        } catch (Exception e) {
            return null;
        }
    }

}
