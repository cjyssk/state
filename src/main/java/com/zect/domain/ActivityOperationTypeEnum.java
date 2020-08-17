package com.zect.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ActivityOperationTypeEnum {

    EVT_CREATE("0","新增活动"),
    EVT_SAVE("1","保存"),
    EVT_SUBMIT("2","提交"),
    EVT_AUDIT_APPROVED("3","审核通过"),
    EVT_AUDIT_REFUSE("3","审核拒绝"),
    EVT_PAUSE("4","暂停"),
    EVT_CONTINUE("5","继续"),
    EVT_TERMINATE("6","终止");

    private String id;
    private String value;
}
