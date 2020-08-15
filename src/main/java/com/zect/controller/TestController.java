package com.zect.controller;

import com.zect.domain.ActivityDTO;
import com.zect.domain.Events;
import com.zect.domain.States;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
public class TestController {

    @Resource
    private StateMachineFactory<States, Events> stateMachineFactory;

    @RequestMapping("/testMachine")
    @ResponseBody
    public void testMachine() {
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine();

        ActivityDTO activity1 = new ActivityDTO();
        activity1.setStatus(0);//草稿
        activity1.setStartTime(new Date());

        /*ActivityDTO activity2 = new ActivityDTO();
        activity2.setStatus(0);//草稿
        activity2.setStartTime(new Date());

        ActivityDTO activity3 = new ActivityDTO();
        activity3.setStatus(0);//草稿
        activity3.setStartTime(new Date());*/

        stateMachine.start();
        Message message = MessageBuilder.withPayload(Events.SUBMIT).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(Events.AUDIT).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(Events.ChECK_START).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(Events.PAUSE).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(Events.CONTINUE).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(Events.TERMINATE).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("最终状态：" + stateMachine.getState().getId());
    }

}
