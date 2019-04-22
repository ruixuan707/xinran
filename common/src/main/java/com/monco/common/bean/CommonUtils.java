package com.monco.common.bean;


import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Utils    杂七杂八的工具类
 *
 * @author Monco
 * @version 1.0.0
 */
public class CommonUtils {

    /**
     * 数组转字符串
     *
     * @param array
     * @return
     */
    public static String LongArray2String(Long[] array) {
        String path = "";
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                path = path + ",";
            }
            path = path + array[i];
        }
        return path;
    }

    /**
     * 字符串转Long
     *
     * @param str
     * @param regex
     * @return
     */
    public static Long[] string2Long(String str, String regex) {
        if (StringUtils.isNotBlank(str)) {
            String strs[] = str.split(regex);
            Long array[] = new Long[strs.length];
            for (int i = 0; i < strs.length; i++) {
                array[i] = Long.parseLong(strs[i]);
            }
            return array;
        }
        return null;
    }

    /**
     * String 转 list
     *
     * @param str
     * @param regex
     * @return
     */
    public static List string2List(String str, String regex) {
        if (StringUtils.isNotBlank(str)) {
            List list = Arrays.asList(str.split(regex));
            return list;
        }
        return null;
    }


    /**
     * 集合转数组
     *
     * @param list
     * @return
     */
    public static Long[] list2Array(List<Long> list) {
        Long[] longs = new Long[list.size()];
        return list.toArray(longs);
    }


    public static List<Object> getCommonList(List<List<Object>> allList) {
        List<Object> commonList = allList.stream()
                .reduce((list1, list2) -> {
                    list1.retainAll(list2);
                    return list1;
                })
                .orElse(Collections.emptyList());
        return commonList;
    }

    /**
     * 集合去重
     *
     * @param list
     * @return
     */
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    /**
     * 过滤字段
     *
     * @param fields
     * @param object
     * @throws IllegalAccessException
     */
    public static void filterPage(String fields, Object object) throws IllegalAccessException {
        if (!"".equals(fields)) {
            List<String> list = new ArrayList<>();
            list = Arrays.asList(fields.split(","));
            //获得当前对象的所有字段
            Field[] fieldArray = object.getClass().getDeclaredFields();
            for (Field field : fieldArray) {
                String name = field.getName();
                String type = field.getGenericType().toString(); //获取属性的类型
                if (!list.contains(name) && !"serialVersionUID".equals(name)) {
                    field.setAccessible(true);
                    field.set(object, null);
                }
            }
        }
    }


    public static String doGet(String url) {
        try {
            URL thisUrl = new URL(url); // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) thisUrl.openConnection();// 打开连接
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println("失败!");
            return null;
        }
    }

    public static String getAddress() {
        String address = "";
        if ("windows".equals(getServerType())) {
            address = "D:/data/upTemp/";
        } else {
            address = "/upTemp/";
        }
        return address;
    }

    public static String getServerType() {
        String serverType = "linux";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            serverType = "windows";
        }
        return serverType;
    }
}