package com.zect.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum States {


    DRAFT("0","草稿"),
    UN_START("1","未开始"),
    IN_PROGRESS("2","活动中"),
    FINISH("3","已结束"),
    STOP("4","已终止"),
    UN_AUDIT("5","待平台审核"),
    REFUSED("6","已拒绝"),
    PAUSED("7","已暂停"),
    ;
    private String id;
    private String value;


    public static String getStateById(String id) throws Exception {
        //获取Class对象
        Class<?> clzz = States.class;
        // 获取所有常量
        Object[] objects = States.values();
        //获取指定方法
        Method coinAddressId = clzz.getMethod("getId");
        for (Object o : objects) {
            if (id.equals(coinAddressId.invoke(o))) {
                return o.toString();
            }
        }
        return null;
    }
}
