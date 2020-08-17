package com.zect.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ActivityEvents {
    //EVT_CREATE("0","新增活动"),
    EVT_SAVE("1","保存"),
    EVT_SUBMIT("2","提交"),
    EVT_AUDIT_APPROVED("3","审核通过"),
    EVT_AUDIT_REFUSE("3","审核拒绝"),
    EVT_PAUSE("4","暂停"),
    EVT_CONTINUE("5","继续"),
    EVT_TERMINATE("6","终止"),
    EVT_CHECK_START("7","检查活动开始"),    //检查活动是否已开始:是-进行中
    EVT_TIMED_TASK_REFUSE("8","检查已拒绝"),   //由定时任务发起，检查待审核活动是否超过活动结束时间:是-已拒绝
    EVT_TIMED_TASK_START("9","检查活动开始"),    //由定时任务发起，检查活动是否已开始:是-进行中
    EVT_TIMED_TASK_FINISH("10","检查活动结束"), //由定时任务发起，检查未开始、活动中、已暂停活动是否已结束:是-已结束
    ;
    private String id;
    private String value;
}
