package com.zect.config;

import com.zect.domain.Events;
import com.zect.domain.States;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "activityStateMachineFactory")
public class StateConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    private static final String activityStateMachineId = "activityStateMachineId";

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> config) throws Exception {
        config
                .withStates()
                .initial(States.DRAFT)
                .choice(States.UN_AUDIT)
                .choice(States.UN_START)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .machineId(activityStateMachineId);
    }

    /**
     * 初始化当前状态机的所有状态迁移动作
     * source来源状态 target目标状态 event触发事件
     * @throws:Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal().source(States.DRAFT).target(States.UN_AUDIT).event(Events.SUBMIT)
                .and().withChoice().source(States.UN_AUDIT).first(States.UN_START, new AuditChoiceGuard()).last(States.REFUSED)
                .and().withChoice().source(States.UN_START).first(States.IN_PROGRESS, new SatrtChoiceGuard()).last(States.UN_START)
                .and().withExternal().source(States.UN_START).target(States.PAUSED).event(Events.PAUSE)
                .and().withExternal().source(States.IN_PROGRESS).target(States.PAUSED).event(Events.PAUSE)
                .and().withExternal().source(States.PAUSED).target(States.UN_START).event(Events.CONTINUE)
                .and().withExternal().source(States.PAUSED).target(States.IN_PROGRESS).event(Events.CONTINUE)
                .and().withExternal().source(States.UN_START).target(States.STOP).event(Events.TERMINATE)
                .and().withExternal().source(States.IN_PROGRESS).target(States.STOP).event(Events.TERMINATE)
                .and().withExternal().source(States.PAUSED).target(States.STOP).event(Events.TERMINATE)

        ;
    }


}
