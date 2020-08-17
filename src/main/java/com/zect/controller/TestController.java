package com.zect.controller;

import com.zect.domain.ActivityDTO;
import com.zect.domain.ActivityEvents;
import com.zect.domain.ActivityStates;
import com.zect.util.DateUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

@RestController
public class TestController {

    @Resource
    private StateMachineFactory<ActivityStates, ActivityEvents> stateMachineFactory;

    @Resource(name="activityPersister")
    private StateMachinePersister<ActivityStates, ActivityEvents, ActivityDTO> persister;


    Date startTime = DateUtils.strToDate("2020-08-15 19:00:10");
    Date endTime = DateUtils.strToDate("2020-08-16 10:10:10");

    public TestController() throws ParseException {
    }

    @RequestMapping("/testMachine")
    @ResponseBody
    public void testMachine() {

        System.out.println("---------------------------------活动1------------------------------");

        StateMachine<ActivityStates, ActivityEvents> stateMachine = stateMachineFactory.getStateMachine();


        ActivityDTO activity1 = new ActivityDTO();
        activity1.setId(1);
        activity1.setStatus(0);//草稿
        activity1.setStartTime(startTime);
        activity1.setEndTime(endTime);

        /*ActivityDTO activity2 = new ActivityDTO();
        activity2.setStatus(0);//草稿
        activity2.setStartTime(new Date());

        ActivityDTO activity3 = new ActivityDTO();
        activity3.setStatus(0);//草稿
        activity3.setStartTime(new Date());*/

        stateMachine.start();

        System.out.println("当前状态：" + stateMachine.getState().getId());

        Message message = MessageBuilder.withPayload(ActivityEvents.EVT_SUBMIT).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);

        message = MessageBuilder.withPayload(ActivityEvents.EVT_AUDIT_APPROVED).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("审核后，当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_CHECK_START).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("检查活动开始后，当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_PAUSE).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("暂停后，当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_CONTINUE).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("继续，当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_CHECK_START).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        /*System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_TERMINATE).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);*/
        System.out.println("最终状态：" + stateMachine.getState().getId());


        System.out.println("---------------------------------活动2------------------------------");
        ActivityDTO activity2 = new ActivityDTO();
        activity2.setId(2);
        activity2.setStatus(0);//草稿
        activity2.setStartTime(startTime);
        activity2.setEndTime(endTime);
        /*
        ActivityDTO activity3 = new ActivityDTO();
        activity3.setStatus(0);//草稿
        activity3.setStartTime(new Date());
        */
        stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.start();
        message = MessageBuilder.withPayload(ActivityEvents.EVT_SUBMIT).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        System.out.println("提交后，当前状态：" + stateMachine.getState().getId());

        //activity1.setStatus(5);//待审核
        message = MessageBuilder.withPayload(ActivityEvents.EVT_AUDIT_APPROVED).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        System.out.println("审核后，当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_CHECK_START).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        System.out.println("检查活动开始，当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_PAUSE).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        /*System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_CONTINUE).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_TERMINATE).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);*/
        System.out.println("最终状态：" + stateMachine.getState().getId());

    }


    @RequestMapping("/cutMachine")
    @ResponseBody
    public void cutMachine() throws Exception {

        System.out.println("---------------------------------活动1------------------------------");

        StateMachine<ActivityStates, ActivityEvents> stateMachine = stateMachineFactory.getStateMachine("activityMachine");


        ActivityDTO activity1 = new ActivityDTO();
        activity1.setId(1);
        activity1.setStatus(1);//未开始
        activity1.setStartTime(startTime);
        activity1.setEndTime(endTime);

        /*ActivityDTO activity2 = new ActivityDTO();
        activity2.setStatus(0);//草稿
        activity2.setStartTime(new Date());

        ActivityDTO activity3 = new ActivityDTO();
        activity3.setStatus(0);//草稿
        activity3.setStartTime(new Date());*/

        persister.restore(stateMachine, activity1);

        /*activity1.setStatus(6);//已拒绝
        Message message = MessageBuilder.withPayload(ActivityEvents.EVT_SUBMIT).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_AUDIT).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);*/
        System.out.println("当前状态：" + stateMachine.getState().getId());

        Message message = MessageBuilder.withPayload(ActivityEvents.EVT_CHECK_START).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("检查活动开始，当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_PAUSE).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("暂停后，当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_CONTINUE).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("继续，当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_CHECK_START).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("检查活动开始，当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_TERMINATE).setHeader("activity", activity1).build();
        stateMachine.sendEvent(message);
        System.out.println("最终状态：" + stateMachine.getState().getId());


        System.out.println("---------------------------------活动2------------------------------");
        ActivityDTO activity2 = new ActivityDTO();
        activity2.setId(2);
        activity2.setStatus(7);//已暂停
        activity2.setStartTime(startTime);
        activity2.setEndTime(endTime);
        /*
        ActivityDTO activity3 = new ActivityDTO();
        activity3.setStatus(0);//草稿
        activity3.setStartTime(new Date());
        */

        stateMachine = stateMachineFactory.getStateMachine("");
        persister.restore(stateMachine, activity2);
        /*message = MessageBuilder.withPayload(ActivityEvents.EVT_SUBMIT).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        activity1.setStatus(5);//待审核
        message = MessageBuilder.withPayload(ActivityEvents.EVT_AUDIT).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_ChECK_START).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_PAUSE).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());*/


        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_CONTINUE).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_CHECK_START).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        System.out.println("当前状态：" + stateMachine.getState().getId());

        message = MessageBuilder.withPayload(ActivityEvents.EVT_TERMINATE).setHeader("activity", activity2).build();
        stateMachine.sendEvent(message);
        System.out.println("最终状态：" + stateMachine.getState().getId());

    }

}
