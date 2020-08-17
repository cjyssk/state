package com.zect.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionCodeEnum {

    NO_CORRESPONDING_STATEMACHINE_BUILDER("10241100","当前业务没有对应的状态机配置，请检查"),
    ACTIVITY_STATE_MACHINE_EXECUTE_ERR("10241101","活动状态机执行错误"),
    NO_ACTIVITY_STATE_MACHINE_TRANSTION_ERR("10241102","当前活动无法执行请求动作"),
    ACTIVITY_COMMON_ILLEGAL_ARGUMENT("10241103","请求数据异常"),
    NO_CORRESPONDING_STATEMACHINE_ERR("10241104","不存在活动对应的状态机实例");
    private String id;
    private String value;

}
