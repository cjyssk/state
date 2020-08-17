package com.zect.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ActivityStates {


    DRAFT("0","草稿"),
    UN_START("1","未开始"),
    IN_PROGRESS("2","活动中"),
    FINISH("3","已结束"),
    STOP("4","已终止"),
    UN_AUDIT("5","待平台审核"),
    REFUSED("6","已拒绝"),
    PAUSED("7","已暂停"),

    //虚拟状态
    //CREATE("100","新增活动"),
    //CREATE_JUMP("101","新增成功后跳转")


    ;
    private String id;
    private String value;


    /**
     * 判断status是否相等
     * @param status
     * @param statusEnum
     * @return
     */
    public static boolean equals(String status, ActivityStates statusEnum) {
        return StringUtils.equalsIgnoreCase(status, statusEnum.getId());

    }


    public static String getStateById(String id) throws Exception {
        //获取Class对象
        Class<?> clzz = ActivityStates.class;
        // 获取所有常量
        Object[] objects = ActivityStates.values();
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
