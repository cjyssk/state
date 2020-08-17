package com.zect.config;

import com.zect.domain.ActivityEvents;
import com.zect.domain.ActivityStates;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
//注解方式配置监听器
@WithStateMachine(id = "activityStateMachineId")
public class EventConfig {

    @OnTransition(target = "DRAFT")
    public void save() {
        System.out.println("-------保存，"+ ActivityStates.DRAFT.getValue());
    }

    @OnTransition(source = "DRAFT", target = "UN_AUDIT")
    public void commit(Message<ActivityEvents> message) {
        System.out.println("传参：" + message.getHeaders().get("activity").toString());
        System.out.println("---------提交，"+ActivityStates.UN_AUDIT.getValue());
    }

    /**
     * 不会执行
     */
    @OnTransition(source = "UN_AUDIT", target = "UN_START")
    public void auditPass() {
        System.out.println("---------审核通过，"+ActivityStates.UN_START.getValue());
    }

    /**
     * 不会执行
     */
    @OnTransition(source = "UN_AUDIT", target = "REFUSED")
    public void auditRefused() {
        System.out.println("---------审核拒绝，"+ActivityStates.REFUSED.getValue());
    }

    @OnTransition(source = "UN_START", target = "IN_PROGRESS")
    public void start() {
        System.out.println("-------活动开始，"+ActivityStates.IN_PROGRESS.getValue());
    }

    @OnTransition(source = "UN_START", target = "PAUSED")
    public void unStartPause() {
        System.out.println("--------未开始活动暂停，"+ActivityStates.PAUSED.getValue());
    }

    @OnTransition(source = "IN_PROGRESS", target = "PAUSED")
    public void inProgressPause() {
        System.out.println("---------活动中暂停，"+ActivityStates.PAUSED.getValue());
    }

    @OnTransition(source = "PAUSED", target = "UN_START")
    public void unStartContinue() {
        System.out.println("---------已暂停未开始活动继续，"+ActivityStates.UN_START.getValue());
    }

    @OnTransition(source = "PAUSED", target = "IN_PROGRESS")
    public void inProgressContinue() {
        System.out.println("---------已暂停活动中活动继续，"+ActivityStates.IN_PROGRESS.getValue());
    }

    @OnTransition(source = "UN_START", target = "STOP")
    public void unStartStop() {
        System.out.println("---------未开始活动终止，"+ActivityStates.STOP.getValue());
    }

    @OnTransition(source = "IN_PROGRESS", target = "STOP")
    public void inProgressStop() {
        System.out.println("---------活动中活动终止，"+ActivityStates.STOP.getValue());
    }

    @OnTransition(source = "PAUSED", target = "STOP")
    public void pausedStop() {
        System.out.println("---------已暂停活动终止，"+ActivityStates.STOP.getValue());
    }



}
