package com.zect.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Events {

    SAVE("1","保存"),
    SUBMIT("2","提交"), //提交
    AUDIT("3","审核:通过/拒绝"),  //审核:通过/拒绝
    PAUSE("4","暂停"),  //暂停
    //继续
    CONTINUE("5","继续"),
    TERMINATE("6","终止"),  //终止

    CHECK_REFUSE("7","检查已拒绝"),   //检查待审核活动是否超过活动结束时间:是-已拒绝
    ChECK_START("8","检查活动开始"),    //检查活动是否已开始:是-进行中
    CHECK_FINISH("9","检查活动结束"), //检查未开始、活动中、已暂停活动是否已结束:是-已结束
    ;
    private String id;
    private String value;
}
