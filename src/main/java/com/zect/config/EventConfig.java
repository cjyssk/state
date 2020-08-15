package com.zect.config;

import com.zect.domain.Events;
import com.zect.domain.States;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

//注解方式配置监听器
@WithStateMachine(id = "activityStateMachineId")
public class EventConfig {

    @OnTransition(target = "DRAFT")
    public void save() {
        System.out.println("-------保存，"+States.DRAFT.getValue());
    }

    @OnTransition(source = "DRAFT", target = "UN_AUDIT")
    public void commit(Message<Events> message) {
        System.out.println("传参：" + message.getHeaders().get("activity").toString());
        System.out.println("---------提交，"+States.UN_AUDIT.getValue());
    }

    /**
     * 不会执行
     */
    @OnTransition(source = "UN_AUDIT", target = "UN_START")
    public void auditPass() {
        System.out.println("---------审核通过，"+States.UN_START.getValue());
    }

    /**
     * 不会执行
     */
    @OnTransition(source = "UN_AUDIT", target = "REFUSED")
    public void auditRefused() {
        System.out.println("---------审核拒绝，"+States.REFUSED.getValue());
    }

    @OnTransition(source = "UN_START", target = "IN_PROGRESS")
    public void start() {
        System.out.println("-------活动开始，"+States.IN_PROGRESS.getValue());
    }

    @OnTransition(source = "UN_START", target = "PAUSED")
    public void unStartPause() {
        System.out.println("--------未开始活动暂停，"+States.PAUSED.getValue());
    }

    @OnTransition(source = "IN_PROGRESS", target = "PAUSED")
    public void inProgressPause() {
        System.out.println("---------活动中暂停，"+States.PAUSED.getValue());
    }

    @OnTransition(source = "PAUSED", target = "UN_START")
    public void unStartContinue() {
        System.out.println("---------已暂停未开始活动继续，"+States.UN_START.getValue());
    }

    @OnTransition(source = "PAUSED", target = "IN_PROGRESS")
    public void inProgressContinue() {
        System.out.println("---------已暂停活动中活动继续，"+States.IN_PROGRESS.getValue());
    }

    @OnTransition(source = "UN_START", target = "STOP")
    public void unStartStop() {
        System.out.println("---------未开始活动终止，"+States.STOP.getValue());
    }

    @OnTransition(source = "IN_PROGRESS", target = "STOP")
    public void inProgressStop() {
        System.out.println("---------活动中活动终止，"+States.STOP.getValue());
    }

    @OnTransition(source = "PAUSED", target = "STOP")
    public void pausedStop() {
        System.out.println("---------已暂停活动终止，"+States.STOP.getValue());
    }



    public void onPersist(State<States, Events> state, Message<Events> message, Transition<States, Events> transition, StateMachine<States, Events> stateMachine) {
//        if (message != null && message.getHeaders().containsKey("gdActivityDto")) {
//            GdActivityDto group = message.getHeaders().get("gdActivityDto", GdActivityDto.class);
//            System.out.println("end-----orderId"+group.getActivityId());
//        }
        System.out.println("---------通用监听事件，活動id");
    }

}
