package com.zect.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
