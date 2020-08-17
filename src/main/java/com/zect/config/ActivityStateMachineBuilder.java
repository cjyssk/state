package com.zect.config;

import com.zect.domain.ActivityDTO;
import com.zect.domain.ActivityEvents;
import com.zect.domain.ActivityStates;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.EnumSet;

import static com.zect.domain.ActivityEvents.*;
import static com.zect.domain.ActivityStates.*;


@Component
public class ActivityStateMachineBuilder extends EnumStateMachineConfigurerAdapter implements MarketingStateMachineBuilder {

    @Override
    public String getName() {
        return ACTIVITY_BUILDER_NAME;
    }

    public StateMachine<ActivityStates, ActivityEvents> build(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<ActivityStates, ActivityEvents> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(true)
                .beanFactory(beanFactory)
                .machineId("ActivityStateMachineId");
        // 初始化状态机，并指定状态集合
        builder.configureStates()
                .withStates()
                .initial(DRAFT)

                .end(REFUSED)
                .end(FINISH)
                .end(STOP)
                .states(EnumSet.allOf(ActivityStates.class))
        ;
        // 指定状态机有哪些节点，即迁移动作
        builder.configureTransitions()

                .withExternal().source(DRAFT).target(UN_AUDIT).event(EVT_SUBMIT)
                /**
                 * 审核通过
                 */
                .and().withExternal().source(UN_AUDIT).target(UN_START).event(EVT_AUDIT_APPROVED)
                .and().withExternal().source(UN_AUDIT).target(IN_PROGRESS).event(EVT_AUDIT_APPROVED).guard(startChoiceGurad())
                /**
                 * 审核拒绝
                 */
                .and().withExternal().source(UN_AUDIT).target(REFUSED).event(EVT_AUDIT_REFUSE)
                //.and().withChoice().source(UN_AUDIT).first(UN_START, new AuditChoiceGuard()).last(REFUSED)
                //审核通过检查活动是否已开始
                .and().withExternal().source(UN_START).target(IN_PROGRESS).event(EVT_CHECK_START).guard(startChoiceGurad())
                /**
                 * 活动暂停
                 * source:未开始、活动中
                 */
                .and().withExternal().source(UN_START).target(PAUSED).event(EVT_PAUSE)
                .and().withExternal().source(IN_PROGRESS).target(PAUSED).event(EVT_PAUSE)
                //活动继续
                .and().withExternal().source(PAUSED).target(UN_START).event(EVT_CONTINUE)
                /**
                 * 活动终止
                 * source:未开始、活动中、已暂停
                 */
                .and().withExternal().source(UN_START).target(STOP).event(EVT_TERMINATE)
                .and().withExternal().source(IN_PROGRESS).target(STOP).event(EVT_TERMINATE)
                .and().withExternal().source(PAUSED).target(STOP).event(EVT_TERMINATE)

                /**
                 * 定时任务，检查待审核活动是否过期
                 */
                .and().withExternal().source(UN_AUDIT).target(REFUSED).event(EVT_TIMED_TASK_REFUSE)
                /**
                 * 定时任务，检查活动是否已开始
                 */
                .and().withExternal().source(UN_START).source(IN_PROGRESS).event(EVT_TIMED_TASK_START)
                /**
                 * 定时任务，检查活动是否已结束
                 * source:未开始、活动中、已暂停
                 */
                .and().withExternal().source(UN_START).target(FINISH).event(EVT_TIMED_TASK_FINISH)
                .and().withExternal().source(IN_PROGRESS).target(FINISH).event(EVT_TIMED_TASK_FINISH)
                .and().withExternal().source(PAUSED).target(FINISH).event(EVT_TIMED_TASK_FINISH)


        ;

        return builder.build();

    }

    /**
     * 判断活动是否已开始
     * @return
     */
    private Guard<ActivityStates, ActivityEvents> startChoiceGurad() {
        return context -> {
            Date now = new Date();
            System.out.println("StartChoiceGuard!!!!!!!活动是否已开始!!!!!!");
            ActivityDTO activity = context.getMessageHeaders().get("activity", ActivityDTO.class);
            if (activity.getStartTime().compareTo(now) < 0) {
                System.out.println("true");
                return true;
            }
            System.out.println("false");
            return false;
        };
    }


}
